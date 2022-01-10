package org.kiworkshop.learningfpinkotlin

interface Monoid<T> {

    fun mempty(): T

    fun mappend(m1: T, m2: T): T
}

fun <T> Monoid<T>.mconcat(list: FunList<T>): T = list.foldRight(mempty(), ::mappend)

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

object FunListLiftMonoid {

    fun <T> monoid(inValue: Monoid<T>): Monoid<FunList<T>> = object : Monoid<FunList<T>> {
        override fun mempty(): FunList<T> = FunList.Nil

        override fun mappend(m1: FunList<T>, m2: FunList<T>): FunList<T> = when {
            m1 is FunList.Nil -> m2
            m2 is FunList.Nil -> m1
            m1 is FunList.Cons && m2 is FunList.Cons -> FunList.liftA2 { a: T, b: T -> inValue.mappend(a, b) }(m1, m2)
            else -> FunList.Nil
        }
    }
}

class FunListMonoid<T> : Monoid<FunList<T>> {

    override fun mempty(): FunList<T> = FunList.Nil

    override fun mappend(m1: FunList<T>, m2: FunList<T>): FunList<T> = m1 append m2
}

class FunStreamMonoid<T> : Monoid<FunStream<T>> {

    override fun mempty(): FunStream<T> = FunStream.Nil

    override fun mappend(m1: FunStream<T>, m2: FunStream<T>): FunStream<T> = m1 append m2
}

class FunTreeMonoid<T> : Monoid<FunTree<T>> {
    override fun mempty() = FunTree.Nil
    override fun mappend(m1: FunTree<T>, m2: FunTree<T>): FunTree<T> {
        infix fun <A> FunTree<A>.mappend(other: FunTree<A>): FunTree<A> = when (this) {
            FunTree.Nil -> other
            is FunTree.Node -> when (other) {
                FunTree.Nil -> this
                is FunTree.Node -> when (left) {
                    FunTree.Nil -> FunTree.Node(value, other, right)
                    is FunTree.Node -> when (right) {
                        FunTree.Nil -> FunTree.Node(value, left, other)
                        is FunTree.Node -> FunTree.Node(value, left.mappend(other), right)
                    }
                }
            }
        }

        return m1.mappend(m2)
    }
}
