package org.kiworkshop.learningfpinkotlin.c8

import org.kiworkshop.learningfpinkotlin.c7.Functor

sealed interface AFunList<out A> : Functor<A> {

    companion object;

    override fun <B> fmap(f: (A) -> B): AFunList<B>
}

object Nil : AFunList<kotlin.Nothing> {
    override fun <B> fmap(f: (Nothing) -> B): AFunList<B> = Nil
}

data class Cons<A>(val head: A, val tail: AFunList<A>) : AFunList<A> {
    override fun <B> fmap(f: (A) -> B): AFunList<B> = Cons(f(head), tail.fmap(f))
}

fun <A> AFunList.Companion.pure(value: A) = Cons(value, Nil)

infix fun <A> AFunList<A>.append(other: AFunList<A>): AFunList<A> {
    tailrec fun AFunList<A>.appendReverse(acc: AFunList<A>): AFunList<A> = when (this) {
        Nil -> acc
        is Cons -> tail.appendReverse(acc.addHead(head))
    }

    return reverse().appendReverse(other)
}

infix fun <A, B> AFunList<(A) -> B>.apply(f: AFunList<A>): AFunList<B> = when (this) {
    Nil -> Nil
    is Cons -> f.fmap(head) append (tail apply f)
}

fun <T> AFunList<T>.addHead(head: T): AFunList<T> = Cons(head, this)

fun <T> aFunListOf(vararg elements: T): AFunList<T> = elements.toMutableList().toAFunList()

tailrec fun <T> Collection<T>.toAFunList(acc: AFunList<T> = Nil): AFunList<T> = when {
    isEmpty() -> acc.reverse()
    else -> drop(1).toAFunList(acc.addHead(first()))
}

tailrec fun <T> AFunList<T>.reverse(acc: AFunList<T> = Nil): AFunList<T> = when (this) {
    Nil -> acc
    is Cons -> tail.reverse(acc.addHead(head))
}
