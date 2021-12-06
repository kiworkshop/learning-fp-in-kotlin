package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.math.BigInteger

class Practice11 : FreeSpec() {
    private fun factorialFP(n: Int): BigInteger = factorialFP(n, 1.toBigInteger())

    private tailrec fun factorialFP(n: Int, acc: BigInteger): BigInteger = when {
        n <= 0 -> acc
        else -> factorialFP(n - 1, acc * n.toBigInteger())
    }

    init {
        "연습문제 3-10에서 작성한 factorial 함수를 함수형 프로그래밍에 적합한 방식으로 개선해 보라." {
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
