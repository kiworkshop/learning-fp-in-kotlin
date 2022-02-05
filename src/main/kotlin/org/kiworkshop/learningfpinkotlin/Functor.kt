package org.kiworkshop.learningfpinkotlin

interface Functor<out A> {
    fun <B> fmap(f: (A) -> B): Functor<B>
}

sealed class FunctorList<out A> : Functor<A> {
    abstract override fun toString(): String
}

object Nil : FunctorList<kotlin.Nothing>() {
    override fun <B> fmap(f: (kotlin.Nothing) -> B): FunctorList<kotlin.Nothing> = Nil

    override fun toString(): String = "Nil"
}

data class Cons<out A>(val head: A, val tail: FunctorList<A>) : FunctorList<A>() {
    override fun <B> fmap(f: (A) -> B): FunctorList<B> = Cons(f(head), tail.map(f))

    override fun toString(): String = "Cons($head, $tail)"
}

fun <A, B> FunctorList<A>.map(f: (A) -> B): FunctorList<B> = when (this) {
    is Nil -> Nil
    is Cons -> this.fmap(f)
}

fun <A> FunctorList<A>.first(): A = when (this) {
    is Nil -> throw NoSuchElementException()
    is Cons -> head
}

tailrec fun <A> FunctorList<A>.size(acc: Int = 0): Int = when (this) {
    is Nil -> acc
    is Cons -> this.tail.size(acc + 1)
}

tailrec fun <A> FunctorList<A>.toList(acc: MutableList<A> = mutableListOf()): List<A> = when (this) {
    is Nil -> acc
    is Cons -> {
        acc.add(head)
        tail.toList(acc)
    }
}
