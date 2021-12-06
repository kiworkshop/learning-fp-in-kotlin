package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice4 : FreeSpec({
    fun toBinary(n: Int): String {
        if (n == 0) return "0"
        if (n shr 1 == 0) return "${n and 1}"
        return toBinary(n shr 1) + (n and 1)
    }

    "10진수 숫자를 입력받아서 2진수 문자열로 변환하는 함수를 작성하라" {
        toBinary(0) shouldBe "0"
        toBinary(1) shouldBe "1"
        toBinary(2) shouldBe "10"
        toBinary(5) shouldBe "101"
        toBinary(11) shouldBe "1011"
        toBinary(75178) shouldBe "10010010110101010"
        toBinary(1231541) shouldBe "100101100101010110101"
    }
})
