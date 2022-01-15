package org.kiworkshop.learningfpinkotlin

class ListMonoid<T> : Monoid<FunList<T>> {
    override fun mempty(): FunList<T> = funListOf()

    override fun mappend(m1: FunList<T>, m2: FunList<T>): FunList<T> = when (m1) {
        is FunList.Nil -> m2
        is FunList.Cons -> m1 concat m2
    }
}
