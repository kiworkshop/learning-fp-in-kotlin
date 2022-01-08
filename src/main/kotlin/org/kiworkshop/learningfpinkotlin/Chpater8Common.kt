package org.kiworkshop.learningfpinkotlin

fun <P1, P2, P3> compose() = { f: (P2) -> P3, g: (P1) -> P2, v: P1 -> f(g(v)) }

fun <T, R> of(value: T) = { f: (T) -> R -> f(value)}
