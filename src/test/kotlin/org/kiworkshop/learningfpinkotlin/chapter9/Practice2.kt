package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice2 : FreeSpec() {
    init {
        "&& 연산을 All 모노이드로 만들어 보자." {
            AllMonoid().run {
                mappend(false, mempty()) shouldBe false
                mappend(true, mempty()) shouldBe true

                mappend(m1 = false, m2 = false) shouldBe false
                mappend(m1 = true, m2 = false) shouldBe false
                mappend(m1 = false, m2 = true) shouldBe false
                mappend(m1 = true, m2 = true) shouldBe true
            }
        }
    }
}
