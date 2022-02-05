package org.kiworkshop.learningfpinkotlin

interface ApplicativeFunctor<out A> : Functor<A> {

    fun <V> pure(value: V): ApplicativeFunctor<V>

    infix fun <B> apply(ff: ApplicativeFunctor<(A) -> B>): ApplicativeFunctor<B>
}
