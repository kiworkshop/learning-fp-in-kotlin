package org.kiworkshop.learningfpinkotlin.c9

import org.kiworkshop.learningfpinkotlin.c5.FunList
import org.kiworkshop.learningfpinkotlin.c5.concat
import org.kiworkshop.learningfpinkotlin.c5.foldRight
import org.kiworkshop.learningfpinkotlin.c5.funListOf
import org.kiworkshop.learningfpinkotlin.c7.Just
import org.kiworkshop.learningfpinkotlin.c7.Maybe
import org.kiworkshop.learningfpinkotlin.c7.Nothing

interface Monoid<T> {

    fun mempty(): T

    fun mappend(m1: T, m2: T): T
}

class SumMonoid : Monoid<Int> {

    override fun mempty(): Int = 0

    override fun mappend(m1: Int, m2: Int): Int = (m1 + m2)
}

class ProductMonoid : Monoid<Int> {

    override fun mempty(): Int = 1

    override fun mappend(m1: Int, m2: Int): Int = (m1 * m2)
}

class AnyMonoid : Monoid<Boolean> {

    override fun mempty(): Boolean = false

    override fun mappend(m1: Boolean, m2: Boolean): Boolean = (m1 || m2)
}

class AllMonoid : Monoid<Boolean> {

    override fun mempty(): Boolean = true

    override fun mappend(m1: Boolean, m2: Boolean): Boolean = (m1 && m2)
}

class ListMonoid<T> : Monoid<FunList<T>> {

    override fun mempty(): FunList<T> = funListOf()

    override fun mappend(m1: FunList<T>, m2: FunList<T>): FunList<T> = when {
        m1 is FunList.Nil -> m2
        m2 is FunList.Nil -> m1
        m1 is FunList.Cons && m2 is FunList.Cons -> m1.concat(m2)
        else -> FunList.Nil
    }
}

fun <T> Monoid<T>.mconcat(list: FunList<T>): T = list.foldRight(mempty(), ::mappend)

object MaybeMonoid {

    fun <T> monoid(inValue: Monoid<T>) = object : Monoid<Maybe<T>> {

        override fun mempty(): Maybe<T> = Nothing

        override fun mappend(m1: Maybe<T>, m2: Maybe<T>): Maybe<T> = when {
            m1 is Nothing -> m2
            m2 is Nothing -> m1
            m1 is Just && m2 is Just -> Just(inValue.mappend(m1.value, m2.value))
            else -> Nothing
        }
    }
}
