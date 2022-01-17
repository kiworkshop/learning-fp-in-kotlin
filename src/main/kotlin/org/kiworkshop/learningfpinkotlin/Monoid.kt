package org.kiworkshop.learningfpinkotlin

interface Monoid<T> {
    fun mempty(): T
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

class AnyMonoid : Monoid<Boolean> {
    override fun mempty(): Boolean = false
    override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 || m2
}

class AllMonoid : Monoid<Boolean> {
    override fun mempty(): Boolean = true
    override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 && m2
}
