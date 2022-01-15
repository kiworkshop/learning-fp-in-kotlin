package org.kiworkshop.learningfpinkotlin.chapter9

import org.kiworkshop.learningfpinkotlin.FunList
import org.kiworkshop.learningfpinkotlin.concat
import org.kiworkshop.learningfpinkotlin.funListOf

class ListMonoid<T> : Monoid<FunList<T>> {
    override fun mempty(): FunList<T> = funListOf()

    override fun mappend(m1: FunList<T>, m2: FunList<T>): FunList<T> = when (m1) {
        is FunList.Nil -> m2
        is FunList.Cons -> m1 concat m2
    }
}
