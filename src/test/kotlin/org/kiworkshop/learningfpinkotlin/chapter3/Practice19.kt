package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Bounce
import org.kiworkshop.learningfpinkotlin.Done
import org.kiworkshop.learningfpinkotlin.More
import org.kiworkshop.learningfpinkotlin.trampoline
import java.math.BigInteger

class Practice19 : FreeSpec() {
    private fun factorialTrampoline(n: Int, acc: BigInteger): Bounce<BigInteger> {
        if (n <= 0) return Done(acc)
        return More { factorialTrampoline(n - 1, acc * n.toBigInteger()) }
    }

    private fun factorialTrampoline(n: Int): Bounce<BigInteger> = factorialTrampoline(n, 1.toBigInteger())

    init {
        "trampoline 함수를 사용하여 연습문제 3-12의 factorial 함수를 다시 작성해 보자. 100000!값은 얼마인가?" {
            // 기존 구현이 이미 꼬리 재귀였다.
            trampoline(factorialTrampoline(0)) shouldBe 1.toBigInteger()
            trampoline(factorialTrampoline(1)) shouldBe 1.toBigInteger()
            trampoline(factorialTrampoline(2)) shouldBe (1 * 2).toBigInteger()
            trampoline(factorialTrampoline(3)) shouldBe (1 * 2 * 3).toBigInteger()
            trampoline(factorialTrampoline(6)) shouldBe (1 * 2 * 3 * 4 * 5 * 6).toBigInteger()
            trampoline(factorialTrampoline(10)) shouldBe 3628800.toBigInteger()
            trampoline(factorialTrampoline(15)) shouldBe BigInteger("1307674368000")
        }
    }
}
