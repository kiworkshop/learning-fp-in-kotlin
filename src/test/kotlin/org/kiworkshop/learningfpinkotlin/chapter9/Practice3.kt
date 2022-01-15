package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.AnyMonoid

class Practice3 : FreeSpec() {
    init {
        "Any 모노이드가 모노이드의 법칙을 만족하는지 테스트해 보자" {
            val x = false
            val y = true
            val z = false
            AnyMonoid().run {
                mappend(mempty(), x) shouldBe x
                mappend(x, mempty()) shouldBe x
                mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
            }
        }
    }
}
