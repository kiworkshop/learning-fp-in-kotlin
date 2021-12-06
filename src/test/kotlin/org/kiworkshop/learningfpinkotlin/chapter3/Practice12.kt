package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.math.BigInteger

class Practice12 : FreeSpec() {
    private fun factorialFP(n: Int): BigInteger = factorialFP(n, 1.toBigInteger())

    private tailrec fun factorialFP(n: Int, acc: BigInteger): BigInteger = when {
        n <= 0 -> acc
        else -> factorialFP(n - 1, acc * n.toBigInteger())
    }

    init {
        "연습문제 3-11에서 작성한 factorial 함수가 꼬리 재귀인지 확인해 보자. 만약 꼬리 재귀가 아니라면 최적화되도록 수정하자." {
            // 기존 구현이 이미 꼬리 재귀였다.
            factorialFP(0) shouldBe 1.toBigInteger()
            factorialFP(1) shouldBe 1.toBigInteger()
            factorialFP(2) shouldBe (1 * 2).toBigInteger()
            factorialFP(3) shouldBe (1 * 2 * 3).toBigInteger()
            factorialFP(6) shouldBe (1 * 2 * 3 * 4 * 5 * 6).toBigInteger()
            factorialFP(10) shouldBe 3628800.toBigInteger()
            factorialFP(15) shouldBe BigInteger("1307674368000")
        }
    }
}
