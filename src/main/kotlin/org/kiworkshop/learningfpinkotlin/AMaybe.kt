package org.kiworkshop.learningfpinkotlin

import kotlin.Nothing

sealed class AMaybe<out A> : Applicative<A> {
    companion object {
        fun <V> pure(value : V) : Applicative<V> = AJust(0).pure(value)
    }

    override fun <V> pure(value: V): Applicative<V> = AJust(value)

    abstract override fun <B> apply(ff: Applicative<(A) -> B>): AMaybe<B>
}

data class AJust<out A>(val value: A) : AMaybe<A>() {
    override fun toString(): String = "AJust($value)"

    override fun <B> apply(ff: Applicative<(A) -> B>): AMaybe<B> = when (ff) {
        is AJust -> fmap(ff.value)
        else -> ANothing
    }

    override fun <B> fmap(f: (A) -> B): AMaybe<B> = AJust(f(value))
}


object ANothing : AMaybe<Nothing>() {
    override fun toString(): String = "ANothing"

    override fun <B> apply(ff: Applicative<(Nothing) -> B>): AMaybe<B> = ANothing

    override fun <B> fmap(f: (Nothing) -> B): AMaybe<B> = ANothing
}
