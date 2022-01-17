package org.kiworkshop.learningfpinkotlin.chapter8

import org.kiworkshop.learningfpinkotlin.chapter7.Cons
import org.kiworkshop.learningfpinkotlin.chapter7.FunList
import org.kiworkshop.learningfpinkotlin.chapter7.Functor
import org.kiworkshop.learningfpinkotlin.chapter7.Nil

interface Applicative<out A> : Functor<A> {
    fun <V> pure(value: V): Applicative<V>

    infix fun <B> apply(ff: Applicative<(A) -> B>): Applicative<B>
}

sealed class AMaybe<out A> : Applicative<A> {
    companion object {
        fun <V> pure(value: V): Applicative<V> = AJust(0).pure(value)
    }

    override fun <V> pure(value: V): Applicative<V> = AJust(value)

    abstract override fun <B> apply(ff: Applicative<(A) -> B>): AMaybe<B>
}

data class AJust<out A>(val value: A) : AMaybe<A>() {
    override fun toString(): String = "AJust($value)"

    override fun <B> apply(ff: Applicative<(A) -> B>): AMaybe<B> = when (ff) {
        is AJust -> fmap(ff.value)
        else -> ANothing
    }

    override fun <B> fmap(f: (A) -> B): AMaybe<B> = AJust(f(value))
}

object ANothing : AMaybe<kotlin.Nothing>() {
    override fun toString(): String = "ANothing"

    override fun <B> apply(ff: Applicative<(Nothing) -> B>): AMaybe<B> = ANothing

    override fun <B> fmap(f: (Nothing) -> B): AMaybe<B> = ANothing
}

/*
* 연습문제 8-2 : 7장의 리스트 펑터를 Applicative의 인스턴스로 만들기
* */
sealed class AFunList<out A> : Applicative<A> {
    abstract fun first(): A

    abstract fun size(): Int

    companion object {
        fun <V> pure(value: V): Applicative<V> = ACons(0, ANil).pure(value)
    }

    override fun <V> pure(value: V): Applicative<V> = ACons(value, ANil)

    abstract override fun <B> apply(ff: Applicative<(A) -> B>): AFunList<B>

    abstract override fun <B> fmap(f: (A) -> B): AFunList<B>
}

object ANil : AFunList<kotlin.Nothing>() {
    override fun <B> fmap(f: (kotlin.Nothing) -> B): AFunList<B> = ANil

    override fun first(): kotlin.Nothing = throw NoSuchElementException()

    override fun size(): Int = 0

    override fun <B> apply(ff: Applicative<(kotlin.Nothing) -> B>): AFunList<B> = ANil
}

data class ACons<A>(val head: A, val tail: AFunList<A>) : AFunList<A>() {
    override fun <B> fmap(f: (A) -> B): AFunList<B> = ACons(f(head), tail.fmap(f))

    override fun first(): A = head

    override fun size(): Int = 1 + tail.size()

    override fun <B> apply(ff: Applicative<(A) -> B>): AFunList<B> = when (ff) {
        /*
        * fmap(ff.head)
        * = ACons(ff.head(head), tail.fmap(ff.head))
        * = ACons(ff.head(head), ACons(ff.head(tail.head), tail.tail.fmap(ff.tail.head))
        * ...
        * */
        is ACons -> fmap(ff.head)
        else -> ANil
    }
}

/*
* 연습문제 8-3
* */
fun <A> FunList.Companion.pure(value: A): FunList<A> = Cons(value, Nil)

infix fun <A> FunList<A>.append(other: FunList<A>): FunList<A> = when (this) {
    is Cons -> Cons(this.head, this.tail append other)
    is Nil -> other
}

infix fun <A, B> FunList<(A) -> B>.apply(f: FunList<A>): FunList<B> = when (this) {
    is Cons -> f.fmap(head) append tail.apply(f)
    is Nil -> Nil
}

/*
* 트리 애플리케이티브 펑터 만들기
* */

sealed class Tree<out A> : Functor<A> {
    abstract override fun <B> fmap(f: (A) -> B): Functor<B>

    companion object
}

data class Node<out A>(val value: A, val forest: List<Node<A>> = emptyList()) : Tree<A>() {
    override fun toString(): String = "$value $forest"
    override fun <B> fmap(f: (A) -> B): Node<B> = Node(f(value), forest.map { it.fmap(f) })
}

fun <A> Tree.Companion.pure(value: A) = Node(value)

infix fun <A, B> Node<(A) -> B>.apply(node: Node<A>): Node<B> = Node(
    value(node.value),
    node.forest.map { it.fmap(value) } + forest.map { it.apply(node) }
)
