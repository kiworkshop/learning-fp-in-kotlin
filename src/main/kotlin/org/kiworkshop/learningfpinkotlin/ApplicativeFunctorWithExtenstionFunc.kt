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

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).curried(): (P1) -> (P2) -> (P3) -> R =
    { p1: P1 -> { p2: P2 -> { p3: P3 -> this(p1, p2, p3) } } }

fun main() {
    println(Just(10).fmap { it + 10 })
    println(Nothing.fmap { x: Int -> x + 10 })

    println(Maybe.pure(10))
    println(Maybe.pure { x: Int -> x * 2 })

    println(Maybe.pure { x: Int -> x * 2 } apply Just(10))
    println(Maybe.pure { x: Int -> x * 2 } apply Nothing)

    // pure / apply 확장함수는 Maybe의 제네릭이 값일 경우 (함수 x)에도 호출이 가능할까
//    Maybe.pure(10) apply  // compile error (기본 확장함수인 kotlin.Standard apply만 인식됨)

    // polynomial function applicative chaining with curring function
    println(Maybe.pure({ x: Int, y: Int -> x * y }.curried())
            apply Just(20)
            apply Just(10)) // Just(200)
    println(Maybe.pure({ x: Int, y: Int, z: Int -> x * y + z }.curried())
            apply Just(20)
            apply Just(10)
            apply Just(30)) // Just(230)
}
