package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.math.BigInteger

class Practice3 : FreeSpec({
    fun factorial(n: Long): BigInteger = if (n <= 0) 1.toBigInteger() else n.toBigInteger() * factorial(n - 1)

    "입력 n의 팩터리얼(Factorial)인 n!을 구하는 함수를 재귀로 구현해 보자." {
        factorial(0) shouldBe 1.toBigInteger()
        factorial(1) shouldBe 1.toBigInteger()
        factorial(2) shouldBe (1 * 2).toBigInteger()
        factorial(3) shouldBe (1 * 2 * 3).toBigInteger()
        factorial(6) shouldBe (1 * 2 * 3 * 4 * 5 * 6).toBigInteger()
        factorial(10) shouldBe 3628800.toBigInteger()
        factorial(15) shouldBe BigInteger("1307674368000")
    }
})
