package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter9 : StringSpec({
    "Example 9-1" {
        AnyMonoid().run {
            mappend(true, mempty()) shouldBe true
            mappend(false, mempty()) shouldBe false
        }
    }

    "Example 9-2" {
        AllMonoid().run {
            mappend(true, mempty()) shouldBe true
            mappend(false, mempty()) shouldBe false
        }
    }

    "Example 9-3" {
        val x = true
        val y = false
        val z = false
        AnyMonoid().run {
            mappend(x, mempty()) shouldBe x
            mappend(mempty(), x) shouldBe x
            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
        }
    }

    "Example 9-4" {
        val x = true
        val y = false
        val z = false
        AllMonoid().run {
            mappend(x, mempty()) shouldBe x
            mappend(mempty(), x) shouldBe x
            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
        }
    }

    "Example 9-5" {
        AnyMonoid().run {
            mconcat(funListOf(true, true, true)) shouldBe true
            mconcat(funListOf(false, false, false)) shouldBe false
            mconcat(funListOf(true, false, true)) shouldBe true
        }
    }

    "Example 9-6" {
        AllMonoid().run {
            mconcat(funListOf(true, true, true)) shouldBe true
            mconcat(funListOf(false, false, false)) shouldBe false
            mconcat(funListOf(true, false, true)) shouldBe false
        }
    }

    "Example 9-7" {
        val x = funListOf(1, 2, 3)
        val y = funListOf(4, 5, 6)

        FunListMonoid<Int>().run {
            mappend(x, mempty()) shouldBe x
            mappend(x, y) shouldBe funListOf(1, 2, 3, 4, 5, 6)
        }
    }

    "Example 9-8" {
        val x = funListOf(1, 2, 3)
        val y = funListOf(4, 5, 6)
        val z = funListOf(10, 11, 12)

        FunListMonoid<Int>().run {
            mappend(x, mempty()) shouldBe x
            mappend(mempty(), x) shouldBe x
            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
        }
    }

    "Example 9-9" {
        val x = funListOf(funListOf(1, 2), funListOf(3, 4), funListOf(5))

        FunListMonoid<Int>().run {
            mconcat(x) shouldBe funListOf(1, 2, 3, 4, 5)
        }
    }
})
