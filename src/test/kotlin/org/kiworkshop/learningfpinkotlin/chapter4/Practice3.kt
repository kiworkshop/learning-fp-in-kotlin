package org.kiworkshop.learningfpinkotlin.chapter4

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlin.math.max

class Practice3 : FreeSpec() {

    fun curriedMax(a: Int) = { b:Int -> max(a, b) }

    init {
        "두 개의 매개변수를 받아서 큰 값을 반환하는 max 함수를, 커링을 사용할 수 있도록 구현하라."{
            curriedMax(1)(2) shouldBe 2
            curriedMax(3)(2) shouldBe 3
            curriedMax(4)(4) shouldBe 4
            curriedMax(1)(123) shouldBe 123
            curriedMax(12542)(123) shouldBe 12542
        }
    }
}
