package org.kiworkshop.learningfpinkotlin

interface Functor<out A> {
    fun <B> fmap(f: (A) -> B): Functor<B>
}
