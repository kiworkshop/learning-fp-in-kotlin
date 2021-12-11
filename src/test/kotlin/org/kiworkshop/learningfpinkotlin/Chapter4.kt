package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter4 : StringSpec({
    "연습문제 4-1" {
        val isEven = PartialFunction<Int, String>({ it % 2 == 0 }, { "$it is even" })

        val odd = 55
        isEven.invokeOrElse(odd, "$odd isn't even")

        val isEvenOrOdd = isEven.orElse(PartialFunction({ it % 2 == 1 }, { "$it is odd" }))

        isEvenOrOdd(2) shouldBe "2 is even"
        isEvenOrOdd(3) shouldBe "3 is odd"
    }
})
