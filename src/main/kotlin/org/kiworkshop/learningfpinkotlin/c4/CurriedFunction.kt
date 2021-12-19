package org.kiworkshop.learningfpinkotlin.c4

fun <P1, P2, R> ((P1, P2) -> R).curried(): (P1) -> (P2) -> R = { p1 -> { p2 -> this(p1, p2) } }

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).curried(): (P1) -> (P2) -> (P3) -> R =
    { p1 -> { p2 -> { p3 -> this(p1, p2, p3) } } }

fun <P1, P2, P3, R> ((P1) -> (P2) -> (P3) -> R).uncurried(): (P1, P2, P3) -> R =
    { p1, p2, p3 -> this(p1)(p2)(p3) }

