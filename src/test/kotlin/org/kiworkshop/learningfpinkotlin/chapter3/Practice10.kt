package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import io.kotest.matchers.shouldBe
import java.math.BigInteger

class Practice10 : FreeSpec() {
    private val initValue = (-1).toBigInteger()

    private lateinit var memo: Array<BigInteger>

    override fun beforeEach(testCase: TestCase) {
        memo = Array(1000) { initValue }
    }

    private fun factorialMemo(n: Int): BigInteger = when {
        n <= 0 -> 1.toBigInteger()
        memo[n] != initValue -> memo[n]
        else -> {
            memo[n] = n.toBigInteger() * factorialMemo(n - 1)
            memo[n]
        }
    }

    init {
        "연습문제 3-3에서 작성한 factorial 함수를 메모이제이션을 사용해서 개선해 보라." {
            factorialMemo(0) shouldBe 1.toBigInteger()
            factorialMemo(1) shouldBe 1.toBigInteger()
            factorialMemo(2) shouldBe (1 * 2).toBigInteger()
            factorialMemo(3) shouldBe (1 * 2 * 3).toBigInteger()
            factorialMemo(6) shouldBe (1 * 2 * 3 * 4 * 5 * 6).toBigInteger()
            factorialMemo(10) shouldBe 3628800.toBigInteger()
            factorialMemo(15) shouldBe BigInteger("1307674368000")
        }
    }
}
