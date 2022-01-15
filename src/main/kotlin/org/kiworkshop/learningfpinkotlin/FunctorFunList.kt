package org.kiworkshop.learningfpinkotlin

import kotlin.Nothing

sealed class FunctorFunList<out T> : Functor<T> {
    abstract override fun <B> fmap(f: (T) -> B): FunctorFunList<B>
    abstract fun first(): T
    abstract fun size(): Int
}

object Nil : FunctorFunList<Nothing>() {
    override fun <B> fmap(f: (Nothing) -> B): FunctorFunList<B> = Nil

    override fun first(): Nothing = throw NoSuchElementException()

    override fun size(): Int = 0
}

data class Cons<out T>(val head: T, val tail: FunctorFunList<T>) : FunctorFunList<T>() {
    override fun <B> fmap(f: (T) -> B): FunctorFunList<B> = Cons(f(head), tail.fmap(f))

    override fun first(): T = head

    override fun size(): Int = 1 + tail.size()
}

fun <T> functorFunListOf(vararg elements: T): FunctorFunList<T> = elements.toFunctorFunList()

fun <T> Array<out T>.toFunctorFunList(): FunctorFunList<T> = when {
    this.isEmpty() -> Nil
    else -> Cons(this[0], this.copyOfRange(1, this.size).toFunctorFunList())
}
