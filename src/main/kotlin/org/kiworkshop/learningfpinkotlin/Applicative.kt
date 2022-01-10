package org.kiworkshop.learningfpinkotlin

interface Applicative<out A> : Functor<A> {
    fun <V> pure(value: V): Applicative<V>

    infix fun <B> apply(ff: Applicative<(A) -> B>): Applicative<B>
}

fun identify() = { x: Int -> x }
