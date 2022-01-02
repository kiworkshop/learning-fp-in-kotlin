package org.kiworkshop.learningfpinkotlin

sealed class Maybe<out A> : Functor<A> {
    abstract override fun toString(): String
    abstract override fun <B> fmap(f: (A) -> B): Maybe<B>
}

data class Just<out A>(val value: A) : Maybe<A>() {
    override fun toString(): String = "Just($value)"
    override fun <B> fmap(f: (A) -> B): Maybe<B> = Just(f(value))
}

object Nothing : Maybe<kotlin.Nothing>() {
    override fun toString(): String = "Nothing"
    override fun <B> fmap(f: (kotlin.Nothing) -> B): Maybe<B> = Nothing
}

fun main() {
    println(Just(10).fmap { it + 10 })
    println(Nothing.fmap { a: Int -> a + 10 })
}
