package org.kiworkshop.learningfpinkotlin

class AnyMonoid : Monoid<Boolean> {
    override fun mempty(): Boolean = false

    override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 || m2
}
