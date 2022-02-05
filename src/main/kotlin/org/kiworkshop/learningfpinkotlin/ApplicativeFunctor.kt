package org.kiworkshop.learningfpinkotlin

interface ApplicativeFunctor<out A> : Functor<A> {

    fun <V> pure(value: V): ApplicativeFunctor<V>

    infix fun <B> apply(ff: ApplicativeFunctor<(A) -> B>): ApplicativeFunctor<B>
}

sealed class AMaybe<out A> : ApplicativeFunctor<A> {

    companion object {
        fun <V> pure(value: V): ApplicativeFunctor<V> = AJust(0).pure(value)
    }

    override fun <V> pure(value: V): ApplicativeFunctor<V> = AJust(value)

    abstract override fun <B> apply(ff: ApplicativeFunctor<(A) -> B>): AMaybe<B>
}

data class AJust<out A>(private val value: A) : AMaybe<A>() {

    override fun toString(): String = "AJust($value)"

    override fun <B> apply(ff: ApplicativeFunctor<(A) -> B>): AMaybe<B> = when (ff) {
        is AJust -> fmap(ff.value)
        else -> ANothing
    }

    override fun <B> fmap(f: (A) -> B): AMaybe<B> = AJust(f(value))
}

object ANothing : AMaybe<kotlin.Nothing>() {

    override fun toString(): String = "ANothing"

    override fun <B> apply(ff: ApplicativeFunctor<(kotlin.Nothing) -> B>): AMaybe<B> = ANothing

    override fun <B> fmap(f: (kotlin.Nothing) -> B): AMaybe<B> = ANothing
}

fun main() {
    // AMaybe 생성
    println(AJust(10).fmap { it + 10 })
    println(ANothing.fmap { x: Int -> x + 10 })
    println(AMaybe.pure(10))

    // apply 사용
    println(AJust(10) apply AJust { x: Int -> x * 2 })
    println(ANothing apply AJust { x: Int -> x * 2 })
    println(AJust(10).apply(AJust { x: Int -> x * 2 })) // 중위 연산도 가능하지만 일반 함수 호출방식으로 사용 가능하다.

    // applicative style
    println(AMaybe.pure(10)
            apply AJust { x: Int -> x * 2 }
            apply AJust { x: Int -> x + 10 })   // AJust(30)

    // compile error when chaining applicative functors with Applicative Functor including function first
//    println(AMaybe.pure { x: Int -> x * 2 }   // 참고로 값 제네릭 타입에 함수를 전달할 수 있다.
//            apply AJust(5))                   // 그러나, 함수 제네릭 타입에 값을 전달할 수 없다.
}