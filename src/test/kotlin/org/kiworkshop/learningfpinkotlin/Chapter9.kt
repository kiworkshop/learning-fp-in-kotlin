package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter9 : StringSpec({
    "연습문제 9-1" {
        AnyMonoid().mappend(AnyMonoid().mempty(), false) shouldBe false
        AnyMonoid().mappend(AnyMonoid().mempty(), true) shouldBe true
    }

    "연습문제 9-2" {
        AllMonoid().mappend(AllMonoid().mempty(), false) shouldBe false
        AllMonoid().mappend(AllMonoid().mempty(), true) shouldBe true
    }

    "연습문제 9-3" {
        val x = false
        val y = true
        val z = false
        AnyMonoid().run {
            mappend(mempty(), x) shouldBe x
            mappend(x, mempty()) shouldBe x
            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
        }
    }

    "연습문제 9-4" {
        val x = false
        val y = true
        val z = false
        AllMonoid().run {
            mappend(mempty(), x) shouldBe x
            mappend(x, mempty()) shouldBe x
            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
        }
    }
})
