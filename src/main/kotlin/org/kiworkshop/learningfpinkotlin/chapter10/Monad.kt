package org.kiworkshop.learningfpinkotlin.chapter10

import org.kiworkshop.learningfpinkotlin.Functor

interface Monad<out A> : Functor<A> {
    fun <V> pure(value: V): Monad<V>

    override fun <B> fmap(f: (A) -> B): Monad<B> = flatMap { a -> pure(f(a)) }

    infix fun <B> flatMap(f: (A) -> Monad<B>): Monad<B>

    infix fun <B> leadTo(m: Monad<B>): Monad<B> = flatMap { m }
}

infix fun <F, G, R> ((F) -> Monad<R>).compose(g: (G) -> Monad<F>): (G) -> Monad<R> {
    return { gInput: G -> g(gInput) flatMap this }
}
