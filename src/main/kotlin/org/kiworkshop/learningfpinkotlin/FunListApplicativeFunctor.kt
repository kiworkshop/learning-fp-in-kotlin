package org.kiworkshop.learningfpinkotlin

import kotlin.Nothing

sealed class FunctorList<out A> : ApplicativeFunctor<A> {
    abstract override fun toString(): String

    override fun <V> pure(value: V): ApplicativeFunctor<V> = FCons(value, FNil)

    companion object {
        fun <V> pure(value: V): ApplicativeFunctor<V> = FCons(0, FNil).pure(value)
    }
}

object FNil : FunctorList<kotlin.Nothing>() {
    override fun toString(): String = "Nil"

    override fun <B> fmap(f: (kotlin.Nothing) -> B): FunctorList<kotlin.Nothing> = FNil

    override fun <B> apply(ff: ApplicativeFunctor<(Nothing) -> B>): ApplicativeFunctor<B> = FNil
}

data class FCons<out A>(val head: A, val tail: FunctorList<A>) : FunctorList<A>() {
    override fun toString(): String = "Cons($head, $tail)"

    override fun <B> fmap(f: (A) -> B): FunctorList<B> = FCons(f(head), tail.map(f))

    override fun <B> apply(ff: ApplicativeFunctor<(A) -> B>): FunctorList<B> = when (ff) {
        is FCons -> fmap(ff.head)
        else -> FNil
    }
}

fun <A, B> FunctorList<A>.map(f: (A) -> B): FunctorList<B> = when (this) {
    is FNil -> FNil
    is FCons -> this.fmap(f)
}

fun <A> FunctorList<A>.first(): A = when (this) {
    is FNil -> throw NoSuchElementException()
    is FCons -> head
}

tailrec fun <A> FunctorList<A>.size(acc: Int = 0): Int = when (this) {
    is FNil -> acc
    is FCons -> this.tail.size(acc + 1)
}

tailrec fun <A> FunctorList<A>.toList(acc: MutableList<A> = mutableListOf()): List<A> = when (this) {
    is FNil -> acc
    is FCons -> {
        acc.add(head)
        tail.toList(acc)
    }
}
