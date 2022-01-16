package org.kiworkshop.learningfpinkotlin

interface Monoid<T> {
    fun mempty(): T

    fun mappend(m1: T, m2: T): T
}

class AnyMonoid : Monoid<Boolean> {
    override fun mempty(): Boolean = false
    override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 || m2
}

class AllMonoid : Monoid<Boolean> {
    override fun mempty(): Boolean = true
    override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 && m2
}

fun <T> Monoid<T>.mconcat(list: FunList<T>): T = list.foldRight(mempty(), ::mappend)

class FunListMonoid<T> : Monoid<FunList<T>> {
    override fun mempty(): FunList<T> = FunList.Nil
    override fun mappend(m1: FunList<T>, m2: FunList<T>): FunList<T> = when (m1) {
        is FunList.Nil -> m2
        else -> m1.append(m2)
    }
}
