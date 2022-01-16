package org.kiworkshop.learningfpinkotlin.c7

import org.kiworkshop.learningfpinkotlin.compose

interface Functor<out A> {
    fun <B> fmap(f: (A) -> B): Functor<B>
}

sealed interface Maybe<out A> : Functor<A> {

    override fun toString(): String

    override fun <B> fmap(f: (A) -> B): Maybe<B>
}

data class Just<out A>(val value: A) : Maybe<A> {

    override fun toString(): String = "Just($value)"

    override fun <B> fmap(f: (A) -> B): Maybe<B> = Just(f(value))
}

object Nothing : Maybe<kotlin.Nothing> {

    override fun toString(): String = "Nothing"

    override fun <B> fmap(f: (kotlin.Nothing) -> B): Maybe<B> = Nothing
}

sealed interface Either<out L, out R> : Functor<R> {
    override fun <R2> fmap(f: (R) -> R2): Either<L, R2>
}

data class Left<out L>(val value: L) : Either<L, kotlin.Nothing> {
    override fun <R2> fmap(f: (kotlin.Nothing) -> R2): Either<L, R2> = this
}

data class Right<out R>(val value: R) : Either<kotlin.Nothing, R> {
    override fun <R2> fmap(f: (R) -> R2): Either<kotlin.Nothing, R2> = Right(f(value))
}

data class UnaryFunction<in T, out R>(val g: (T) -> R) : Functor<R> {
    override fun <R2> fmap(f: (R) -> R2): UnaryFunction<T, R2> = UnaryFunction { x: T -> (f compose g)(x) }

    operator fun invoke(input: T): R = g(input)
}

fun <T> identity(x: T): T = x

sealed interface MaybeCounter<out A> : Functor<A> {
    override fun <B> fmap(f: (A) -> B): MaybeCounter<B>
}

data class JustCounter<out A>(val value: A, val count: Int) : MaybeCounter<A> {
    override fun toString() = "JustCounter($value, count)"

    override fun <B> fmap(f: (A) -> B): MaybeCounter<B> = JustCounter(f(value), count + 1)
}

object NothingCounter : MaybeCounter<kotlin.Nothing> {
    override fun toString() = "NothingCounter"

    override fun <B> fmap(f: (kotlin.Nothing) -> B): MaybeCounter<B> = this
}
