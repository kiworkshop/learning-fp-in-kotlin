package org.kiworkshop.learningfpinkotlin

interface Monoid<T> {
    /**
     * 항등원 반환
     */
    fun mempty(): T

    /**
     * 바이너리 함수를 반환
     */
    fun mappend(m1: T, m2: T): T
}

class SumMonoid : Monoid<Int> {
    override fun mempty(): Int = 0

    override fun mappend(m1: Int, m2: Int): Int = m1 + m2
}

class ProductMonoid : Monoid<Int> {
    override fun mempty(): Int = 1

    override fun mappend(m1: Int, m2: Int): Int = m1 * m2
}
