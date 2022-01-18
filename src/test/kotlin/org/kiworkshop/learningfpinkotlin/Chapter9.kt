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

    "9-5" {
        AnyMonoid().run {
            mconcat(funListOf(true, true, true)) shouldBe true
            mconcat(funListOf(false, false, false)) shouldBe false
            mconcat(funListOf(true, false, true)) shouldBe true
        }
    }

    "9-6" {
        AllMonoid().run {
            mconcat(funListOf(true, true, true)) shouldBe true
            mconcat(funListOf(false, false, false)) shouldBe false
            mconcat(funListOf(true, false, true)) shouldBe false
        }
    }

    "9-7" {
        // ListMonoid in Monoid.kt
    }

    "9-8" {
        val x = funListOf(1, 2, 3)
        val y = funListOf(4, 5)
        val z = funListOf(6, 7, 8, 9)

        ListMonoid.monoid(ProductMonoid()).run {
            mappend(mempty(), x) shouldBe x
            mappend(x, mempty()) shouldBe x
            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
            println(mappend(mappend(x, y), z))
        }
    }

    "9-9" {
        val x = funListOf(funListOf(1, 2), funListOf(3, 4), funListOf(5))
        ListMonoid.monoid(SumMonoid()).run {
            mconcat(x) shouldBe funListOf(9, 6)
        }
    }
})