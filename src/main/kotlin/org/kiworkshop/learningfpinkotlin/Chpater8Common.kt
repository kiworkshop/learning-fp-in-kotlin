package org.kiworkshop.learningfpinkotlin

fun <P1, P2, P3> compose() = { f: (P2) -> P3, g: (P1) -> P2, v: P1 -> f(g(v)) }

fun <T, R> of(value: T) = { f: (T) -> R -> f(value) }

fun <A, B, R> liftA2(binaryFunction: (A, B) -> R) = { f1: Maybe<A>, f2: Maybe<B> ->
    Maybe.pure(binaryFunction.curried()) apply f1 apply f2
}
