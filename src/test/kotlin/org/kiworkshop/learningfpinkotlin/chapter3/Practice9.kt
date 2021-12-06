package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice9 : FreeSpec({
    fun gcd(m: Int, n: Int): Int = when (n) {
        0 -> m // gcd(B, A%B)를 호출하면 n이 0이 된다. 이때 최대공약수는 B가 되는데 이는 m에 전달된 값과 같다.
        else -> gcd(n, m % n)
    }

    "최대공약수를 구하는 gcd 함수를 작성해 보자." {
        gcd(3, 1) shouldBe 1
        gcd(6, 3) shouldBe 3
        gcd(12, 7) shouldBe 1
        gcd(18, 4) shouldBe 2
        gcd(23, 3) shouldBe 1
        gcd(120, 10) shouldBe 10
        gcd(125, 20) shouldBe 5
    }
})
