package org.kiworkshop.learningfpinkotlin

// Code 9-1
interface Monoid<T> {
    fun mempty(): T
    fun mappend(m1: T, m2: T): T
}

// Code 9-3
class SumMonoid : Monoid<Int> {
    override fun mempty(): Int = 0
    override fun mappend(m1: Int, m2: Int): Int = m1 + m2
}

// Code 9-4
class ProductMonoid : Monoid<Int> {
    override fun mempty(): Int = 0
    override fun mappend(m1: Int, m2: Int): Int = m1 * m2
}