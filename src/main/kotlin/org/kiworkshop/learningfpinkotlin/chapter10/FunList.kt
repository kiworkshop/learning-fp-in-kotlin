package org.kiworkshop.learningfpinkotlin.chapter10

import kotlin.Nothing

sealed class FunList<out T> : Monad<T> {
    companion object {
        infix fun <V> pure(value: V): FunList<V> = Cons(0, Nil).pure(value)
    }

    override fun <V> pure(value: V): FunList<V> = when (this) {
        Nil -> Nil
        is Cons -> Cons(value, Nil)
    }

    override fun <B> flatMap(f: (T) -> Monad<B>): FunList<B> = when (this) {
        is Cons -> try {
            (f(head) as FunList<B>) mappend tail.flatMap(f)
        } catch (e: ClassCastException) {
            Nil
        }
        Nil -> Nil
    }

    infix fun <V> FunList<V>.mappend(other: FunList<V>): FunList<V> = when (this) {
        Nil -> other
        is Cons -> when (other) {
            Nil -> this
            is Cons -> Cons(head, tail mappend other)
        }
    }

    override fun <B> leadTo(m: Monad<B>): FunList<B> = flatMap { m }
}

data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()

object Nil : FunList<Nothing>()

infix fun <A, B> FunList<(A) -> B>.apply(f: FunList<A>): FunList<B> = when (this) {
    Nil -> Nil
    is Cons -> (f.fmap(head) as FunList<B>) mappend (tail apply f)
}
