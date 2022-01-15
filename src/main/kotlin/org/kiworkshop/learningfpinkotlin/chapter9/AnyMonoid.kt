package org.kiworkshop.learningfpinkotlin.chapter9

class AnyMonoid : Monoid<Boolean> {
    override fun mempty(): Boolean = false

    override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 || m2
}
