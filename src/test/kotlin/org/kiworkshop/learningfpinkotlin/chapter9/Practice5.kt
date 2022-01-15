package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.AnyMonoid
import org.kiworkshop.learningfpinkotlin.funListOf
import org.kiworkshop.learningfpinkotlin.mconcat

class Practice5 : FreeSpec() {
    init {
        "Any 모노이드의 mconcat 함수를 테스트해 보자. 입력이 [true, true, true], [false, false, false], [true, false, true]라면 어떤 결과가 나오는가?" {
            val input1 = funListOf(true, true, true)
            val input2 = funListOf(false, false, false)
            val input3 = funListOf(true, false, true)
            AnyMonoid().run {
                mconcat(input1) shouldBe true
                mconcat(input2) shouldBe false
                mconcat(input3) shouldBe true
            }
        }
    }
}
