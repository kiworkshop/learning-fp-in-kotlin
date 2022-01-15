package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.ListMonoid
import org.kiworkshop.learningfpinkotlin.funListOf

class Practice8 : FreeSpec() {
    init {
        "리스트 모노이드가 모노이드의 법칙을 만족하는지 확인해 보자." {
            val x = funListOf(1, 2, 3)
            val y = funListOf(1, 3)
            val z = funListOf(1)

            ListMonoid<Int>().run {
                mappend(x, mempty()) shouldBe x
                mappend(mempty(), x) shouldBe x
                mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
            }
        }
    }
}
