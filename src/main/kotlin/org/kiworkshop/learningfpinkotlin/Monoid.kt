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

// Code 9-7
fun <T> Monoid<T>.mconcat(list: FunList<T>): T = list.foldRight(mempty(), ::mappend)

// Exercise 9-7
object ListMonoid {
    fun <T> monoid(inValue: Monoid<T>) = object : Monoid<FunList<T>> {
        override fun mempty(): FunList<T> = FunList.Nil
        override fun mappend(m1: FunList<T>, m2: FunList<T>): FunList<T> = when {
            m1 is FunList.Nil -> m2
            m2 is FunList.Nil -> m1
            m1 is FunList.Cons && m2 is FunList.Cons -> FunList.Cons(inValue.mappend(m1.head, m2.head), mappend(m1.tail, m2.tail))
            else -> FunList.Nil
        }
    }
}