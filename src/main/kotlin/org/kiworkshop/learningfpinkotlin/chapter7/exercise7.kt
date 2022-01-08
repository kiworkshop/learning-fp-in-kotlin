package org.kiworkshop.learningfpinkotlin.chapter7

import org.kiworkshop.learningfpinkotlin.chapter4.compose

/*
* 연습문제 7-1
* */
interface Functor<out A> {
    fun <B> fmap(f: (A) -> B): Functor<B>
}

sealed class FunList<out A> : Functor<A> {
    abstract override fun <B> fmap(f: (A) -> B): FunList<B>
    abstract fun first(): A
    abstract fun size(): Int
}

object Nil : FunList<Nothing>() {
    override fun <B> fmap(f: (Nothing) -> B): FunList<B> = Nil

    override fun first(): Nothing = throw NoSuchElementException()

    override fun size(): Int = 0
}

data class Cons<A>(val head: A, val tail: FunList<A>) : FunList<A>() {
    override fun <B> fmap(f: (A) -> B): FunList<B> = Cons(f(head), tail.fmap(f))

    override fun first(): A = head

    override fun size(): Int = 1 + tail.size()
}

/*
* 펑터 제1 법칙 : 항등 함수에 펑터를 통해서 매핑하면, 반환되는 펑터는 원래의 평터와 같다.
* 펑터 제2 법칙 : 두 함수를 합성한 함수의 매핑은 각 함수를 매핑한 결과를 함성한 것과 같다.
* */

fun <T> identity(x: T): T = x

val f = { a: Int -> a + 1 }
val g = { b: Int -> b * 2 }

sealed class MaybeCounter<out A> : Functor<A> {
    abstract override fun toString(): String
    abstract override fun <B> fmap(f: (A) -> B): Functor<B>
}

data class JustCounter<out A>(val value: A, val count: Int) : MaybeCounter<A>() {
    override fun toString(): String = "JustCounter($value, $count)"
    override fun <B> fmap(f: (A) -> B): Functor<B> = JustCounter(f(value), count + 1)
}

object NothingCounter : MaybeCounter<Nothing>() {
    override fun toString(): String = "NotingCounter"
    override fun <B> fmap(f: (Nothing) -> B): Functor<B> = NothingCounter
}

fun main() {
    println("펑터 제1 법칙 검증")
    println(JustCounter(5, 0).fmap { identity(it) }) // JustCounter(5, 1)
    println(identity(JustCounter(5, 0))) // JustCounter(5, 0)
    println("펑터 제2 법칙 검증")
    println(JustCounter(5, 0).fmap(f compose g)) // JustCounter(11, 1)
    println(JustCounter(5, 0).fmap(g).fmap(f)) // JustCounter(11, 2)
}
