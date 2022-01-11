package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.FunList.Cons
import org.kiworkshop.learningfpinkotlin.FunList.Nil

sealed class FunList<out T> : Functor<T> {

    abstract override fun <B> fmap(f: (T) -> B): FunList<B>

    companion object

    object Nil : FunList<kotlin.Nothing>() {
        override fun <B> fmap(f: (kotlin.Nothing) -> B): FunList<B> = Nil
    }

    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>() {
        override fun <B> fmap(f: (T) -> B): FunList<B> = Cons(f(head), tail.fmap(f))

//  Example 8-3
//        override fun <V> pure(value: V): FunList<V> = Cons(value, Nil)
//
//        override fun <B> apply(ff: Applicative<(T) -> B>): Applicative<B> = when (ff) {
//            is FunList -> {
//                ff.fmap { this.fmap(it) }.foldLeft(Nil as FunList<B>) { acc, curr -> acc.append(curr) }
//            }
//            else -> Nil
//        }
    }
}

fun <A> FunList.Companion.pure(value: A): FunList<A> = Cons(value, Nil)

infix fun <A, B> FunList<(A) -> B>.apply(ff: FunList<A>): FunList<B> = when (this) {
    is Nil -> Nil
    is Cons -> this.fmap { ff.fmap(it) }.foldLeft(Nil as FunList<B>) { acc, curr -> acc.append(curr) }
}

infix fun <A> FunList<A>.append(other: FunList<A>): FunList<A> = when (this) {
    is Nil -> other
    is Cons -> when (other) {
        is Nil -> this
        is Cons -> this.tail.append(other).addHead(this.head)
    }
}

fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> Nil
    else -> Cons(this[0], this.copyOfRange(1, this.size).toFunList())
}

fun <T> FunList<T>.addHead(head: T): FunList<T> = Cons(head, this)

tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = Nil): FunList<T> = when (this) {
    is Nil -> Cons(value, acc).reverse()
    is Cons -> tail.appendTail(value, acc.addHead(head))
}

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = Nil): FunList<T> = when (this) {
    is Nil -> acc
    is Cons -> tail.reverse(acc.addHead(head))
}

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    is Nil -> throw NoSuchElementException()
    is Cons -> tail
}

fun <T> FunList<T>.getHead(): T = when (this) {
    is Nil -> throw NoSuchElementException()
    is Cons -> head
}

tailrec fun <T> FunList<T>.filter(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    is Nil -> acc.reverse()
    is Cons -> if (p(head)) tail.filter(acc.addHead(head), p)
    else tail.filter(acc, p)
}

tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (this) {
    is Nil -> this
    is Cons -> if (n < 1) this else this.tail.drop(n - 1)
}

tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
    is Nil -> this
    is Cons -> if (!p(this.head)) this
    else this.tail.dropWhile(p)
}

tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = Nil): FunList<T> = when (this) {
    is Nil -> this
    is Cons -> if (n < 1) acc.reverse() else this.tail.take(n - 1, acc.addHead(this.head))
}

fun <T> FunList<T>.takeWhile(p: (T) -> Boolean): FunList<T> {
    tailrec fun FunList<T>.takeWhile(acc: FunList<T>): FunList<T> = when (this) {
        is Nil -> this
        is Cons -> if (!p(this.head)) acc.reverse()
        else this.tail.takeWhile(acc.addHead(head))
    }

    val result = this.takeWhile(Nil)
    return if (result == Nil) this else result
}

tailrec fun <T, R> FunList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    Nil -> acc
    is Cons -> tail.foldLeft(f(acc, head), f)
}

fun <T, R> FunList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    is Nil -> acc
    is Cons -> f(head, tail.foldRight(acc, f))
}

// Example 8-6
infix fun <A, B> FunList<(A) -> B>.zipList(other: FunList<A>): FunList<B> = when (this) {
    is Nil -> Nil
    is Cons -> when (other) {
        is Nil -> Nil
        is Cons -> Cons(this.head(other.head), this.tail.zipList(other.tail))
    }
}

// Maybe liftA2 와 충돌 -> companion 으로 이동
fun <A, B, R> FunList.Companion.liftA2(binaryFunction: (A, B) -> R) =
    { f1: FunList<A>, f2: FunList<B> -> FunList.pure(binaryFunction.curried()) apply f1 apply f2 }

// 8장. cons
fun <T> cons() = { x: T, xs: FunList<T> -> FunList.Cons(x, xs) }
