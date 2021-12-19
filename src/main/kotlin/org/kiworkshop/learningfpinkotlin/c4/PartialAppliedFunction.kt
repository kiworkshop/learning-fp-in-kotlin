package org.kiworkshop.learningfpinkotlin.c4

fun <P1, P2, R> ((P1, P2) -> R).toPartial1(p1: P1): (P2) -> R = { p2 -> this(p1, p2) }
fun <P1, P2, R> ((P1, P2) -> R).toPartial2(p2: P2): (P1) -> R = { p1 -> this(p1, p2) }

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).toPartial1(p1: P1): (P2, P3) -> R = { p2, p3 -> this(p1, p2, p3) }
fun <P1, P2, P3, R> ((P1, P2, P3) -> R).toPartial2(p2: P2): (P1, P3) -> R = { p1, p3 -> this(p1, p2, p3) }
fun <P1, P2, P3, R> ((P1, P2, P3) -> R).toPartial3(p3: P3): (P1, P2) -> R = { p1, p2 -> this(p1, p2, p3) }
