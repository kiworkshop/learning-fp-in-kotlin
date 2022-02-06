package org.kiworkshop.learningfpinkotlin.c7

import org.kiworkshop.learningfpinkotlin.c8.Applicative
import kotlin.Nothing

sealed interface FunList<out T> : Applicative<T> {

    companion object {
        fun <V> pure(value: V) = funListOf(value)
    }

    abstract override fun <B> fmap(f: (T) -> B): FunList<B>

    override fun <V> pure(value: V): FunList<V> = Companion.pure(value)

    override fun <B> apply(ff: Applicative<(T) -> B>): FunList<B>

    object Nil : FunList<Nothing> {
        override fun <B> fmap(f: (Nothing) -> B): FunList<B> = Nil
        override fun <B> apply(ff: Applicative<(Nothing) -> B>): FunList<B> = Nil
    }

    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T> {
        override fun <B> fmap(f: (T) -> B): FunList<B> = Cons(f(head), tail.fmap(f))
        override fun <B> apply(ff: Applicative<(T) -> B>): FunList<B> = when (ff) {
            is Cons -> Cons(ff.head(head), tail.apply(ff))
            else -> Nil
        }
    }
}

fun <T> FunList<T>.first(): T = when (this) {
    FunList.Nil -> throw IllegalArgumentException()
    is FunList.Cons -> head
}

tailrec fun <T> FunList<T>.size(count: Int = 0): Int = when (this) {
    FunList.Nil -> count
    is FunList.Cons -> tail.size(count + 1)
}

fun <T> FunList<T>.addHead(head: T): FunList<T> = FunList.Cons(head, this)

fun <T> funListOf(vararg elements: T): FunList<T> = elements.toMutableList().toFunList()

tailrec fun <T> Collection<T>.toFunList(acc: FunList<T> = FunList.Nil): FunList<T> = when {
    isEmpty() -> acc.reverse()
    else -> drop(1).toFunList(acc.addHead(first()))
}

fun <T> FunList<T>.reverse(acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.reverse(acc.addHead(head))
}
