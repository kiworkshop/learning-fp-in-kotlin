package org.kiworkshop.learningfpinkotlin.c7

import kotlin.Nothing

sealed class FunList<out T> : Functor<T> {

    abstract override fun <B> fmap(f: (T) -> B): FunList<B>

    object Nil : FunList<Nothing>() {
        override fun <B> fmap(f: (Nothing) -> B): FunList<B> = Nil
    }

    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>() {
        override fun <B> fmap(f: (T) -> B): FunList<B> = Cons(f(head), tail.fmap(f))
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
