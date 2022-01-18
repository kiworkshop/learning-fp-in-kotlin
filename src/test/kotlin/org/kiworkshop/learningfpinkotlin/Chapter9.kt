package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class Chapter9 : StringSpec({

    // Exercise 9-1
    class AnyMonoid : Monoid<Boolean> {
        override fun mempty(): Boolean = false
        override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 || m2
    }

    "9-1" {
    }

    // Exercise 9-2
    class AllMonoid : Monoid<Boolean> {
        override fun mempty(): Boolean = true
        override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 && m2
    }

    "9-2" {
    }

    "9-3" {
        val x = false
        val y = false
        val z = true

        AnyMonoid().run {
            mappend(mempty(), x) shouldBe x
            mappend(x, mempty()) shouldBe x

            mappend(mempty(), z) shouldBe z
            mappend(z, mempty()) shouldBe z

            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
        }
    }

    "9-4" {
        val x = true
        val y = true
        val z = false

        AllMonoid().run {
            mappend(mempty(), x) shouldBe x
            mappend(x, mempty()) shouldBe x

            mappend(mempty(), z) shouldBe z
            mappend(z, mempty()) shouldBe z

            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
        }
    }
})