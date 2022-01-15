package org.kiworkshop.learningfpinkotlin.chapter9

class AllMonoid : Monoid<Boolean> {
    override fun mempty(): Boolean = true

    override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 && m2
}
