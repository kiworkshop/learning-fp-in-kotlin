package org.kiworkshop.learningfpinkotlin.c8

import org.kiworkshop.learningfpinkotlin.c7.Functor
import org.kiworkshop.learningfpinkotlin.c7.Just
import org.kiworkshop.learningfpinkotlin.c7.Maybe
import org.kiworkshop.learningfpinkotlin.c7.Nothing

interface Applicative<out A> : Functor<A> {

    fun <V> pure(value: V): Applicative<V>

    infix fun <B> apply(ff: Applicative<(A) -> B>): Applicative<B>
}

sealed interface AMaybe<out A> : Applicative<A> {

    companion object {
        fun <V> pure(value: V) = AJust(value)
    }

    override fun <V> pure(value: V): Applicative<V> = AMaybe.pure(value)

    override fun <B> apply(ff: Applicative<(A) -> B>): AMaybe<B>
}

data class AJust<out A>(val value: A) : AMaybe<A> {

    override fun toString() = "AJust($value)"

    override fun <B> apply(ff: Applicative<(A) -> B>): AMaybe<B> = when (ff) {
        is AJust -> fmap(ff.value)
        else -> ANothing
    }

    override fun <B> fmap(f: (A) -> B): AMaybe<B> = AJust(f(value))

}

object ANothing : AMaybe<kotlin.Nothing> {

    override fun toString() = "ANothing"

    override fun <B> fmap(f: (kotlin.Nothing) -> B): AMaybe<kotlin.Nothing> = ANothing

    override fun <B> apply(ff: Applicative<(kotlin.Nothing) -> B>): AMaybe<B> = ANothing
}

fun <A> Maybe.Companion.pure(value: A) = Just(value)

infix fun <A, B> Maybe<(A) -> B>.apply(f: Maybe<A>): Maybe<B> = when (this) {
    is Just -> f.fmap(value)
    is Nothing -> Nothing
}
