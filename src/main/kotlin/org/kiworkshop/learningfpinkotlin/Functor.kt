package org.kiworkshop.learningfpinkotlin

interface Functor<out A> {
    fun <B> fmap(f: (A) -> B): Functor<B>
}

fun <T> identity(x: T): T = x
