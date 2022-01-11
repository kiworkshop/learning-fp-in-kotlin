package org.kiworkshop.learningfpinkotlin

sealed class Maybe<out A> : Functor<A> {
    abstract override fun toString(): String

    abstract override fun <B> fmap(f: (A) -> B): Maybe<B>

    companion object
}

fun <A> Maybe.Companion.pure(value: A) = Just(value)

infix fun <A, B> Maybe<(A) -> B>.apply(f: Maybe<A>): Maybe<B> = when (this) {
    is Just -> f.fmap(value)
    is Nothing -> Nothing
}

data class Just<out A>(val value: A) : Maybe<A>() {
    override fun toString(): String = "Just(value=$value)"

    override fun <B> fmap(f: (A) -> B): Maybe<B> = Just(f(value))
}

object Nothing : Maybe<kotlin.Nothing>() {
    override fun toString(): String = "Nothing"

    override fun <B> fmap(f: (kotlin.Nothing) -> B): Maybe<B> = Nothing
}

fun <A, B, R> Maybe.Companion.liftA2(binaryFunction: (A, B) -> R) =
    { f1: Maybe<A>, f2: Maybe<B> -> Maybe.pure(binaryFunction.curried()) apply f1 apply f2 }

fun <A, B, C, R> Maybe.Companion.liftA3(ternaryFunction: (A, B, C) -> R) =
    { f1: Maybe<A>, f2: Maybe<B>, f3: Maybe<C> -> Maybe.pure(ternaryFunction.curried()) apply f1 apply f2 apply f3 }

fun <T> Maybe.Companion.sequenceA(maybeList: FunList<Maybe<T>>): Maybe<FunList<T>> = when (maybeList) {
    is FunList.Nil -> Just(funListOf())
    is FunList.Cons -> Maybe.pure(cons<T>().curried()) apply maybeList.head apply sequenceA(maybeList.tail)
}

fun <T> Maybe.Companion.sequenceAByFoldRight(maybeList: FunList<Maybe<T>>): Maybe<FunList<T>> =
    maybeList.foldRight(Maybe.pure(funListOf()), Maybe.liftA2(cons()))
