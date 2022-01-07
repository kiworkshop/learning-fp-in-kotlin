package org.kiworkshop.learningfpinkotlin

sealed class Maybe<out A> : Functor<A> {
    abstract override fun toString(): String
    abstract override fun <B> fmap(f: (A) -> B): Maybe<B>

    companion object
}

data class Just<out A>(val value: A) : Maybe<A>() {
    override fun toString(): String = "Just($value)"
    override fun <B> fmap(f: (A) -> B): Maybe<B> = Just(f(value))
}

object Nothing : Maybe<kotlin.Nothing>() {
    override fun toString(): String = "Nothing"
    override fun <B> fmap(f: (kotlin.Nothing) -> B): Maybe<B> = Nothing
}

fun <A> Maybe.Companion.pure(value: A) = Just(value)

infix fun <A, B> Maybe<(A) -> B>.apply(f: Maybe<A>): Maybe<B> = when (this) {
    is Just -> f.fmap(value)
    is Nothing -> Nothing
}

fun main() {
    println(Just(10).fmap { it + 10 })
    println(Nothing.fmap { a: Int -> a + 10 })

    println(Maybe.pure(10))
    println(Maybe.pure({ x: Int -> x * 2 }))

    println(Maybe.pure({ x: Int -> x * 2 }) apply Just(10))
    println(Maybe.pure({ x: Int -> x * 2 }) apply Nothing)

    println(
        Maybe.pure({ x: Int, y: Int -> x * y }.curried())
                apply Just(10)
                apply Just(20)
    )
    println(
        Maybe.pure({ x: Int, y: Int, z: Int -> x * y + z }.curried())
                apply Just(10)
                apply Just(20)
                apply Just(30)
    )

}
