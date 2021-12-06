package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Bounce
import org.kiworkshop.learningfpinkotlin.Done
import org.kiworkshop.learningfpinkotlin.More
import org.kiworkshop.learningfpinkotlin.trampoline
import kotlin.math.sqrt

class Practice18 : FreeSpec() {
    private fun sqrtAndDivide(n: Double): Bounce<Double> = More { divide(sqrt(n)) }
    private fun divide(n: Double): Bounce<Double> = when {
        (n / 2) < 1 -> Done(n / 2)
        else -> More { sqrtAndDivide(n / 2) }
    }

    init {
        "trampoline 함수를 사용하여 연습문제 3-17의 함수를 다시 작성해 보자."{
            trampoline(sqrtAndDivide(4.0)) shouldBe 0.5
            trampoline(sqrtAndDivide(2.0)) shouldBe 0.7071067811865476
            trampoline(sqrtAndDivide(16.0)) shouldBe 0.7071067811865476
            trampoline(sqrtAndDivide(64.0)) shouldBe 0.5
        }
    }
}
