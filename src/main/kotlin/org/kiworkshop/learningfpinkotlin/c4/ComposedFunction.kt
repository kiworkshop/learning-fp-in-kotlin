package org.kiworkshop.learningfpinkotlin.c4

infix fun <F, G, R> ((F) -> R).compose(g: (G) -> F): (G) -> R = { input -> this(g(input)) }
