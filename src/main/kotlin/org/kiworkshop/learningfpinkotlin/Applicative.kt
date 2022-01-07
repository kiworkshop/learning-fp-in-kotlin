package org.kiworkshop.learningfpinkotlin

interface Applicative<out A> : Functor<A> {

    /**
     * 임의의 타입 값을 입력으로 받아서 Applicative 안에 그대로 넣고 반환
     */
    fun <V> pure(value: V): Applicative<V>

    /**
     * 함수를 가진 Applicative를 받아서 펑터 안의 값을 함수에 적용하고, 적용한 결과를 Applicative에 넣어서 반환
     */
    infix fun <B> apply(ff: Applicative<(A) -> B>): Applicative<B>
}
