package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice14 : FreeSpec({
    tailrec fun toBinary(n: Int, acc: String): String {
        if (n == 0) return "0"
        if (n shr 1 == 0) return "${n and 1}" + acc
        return toBinary(n shr 1, (n and 1).toString() + acc)
    }

    fun toBinary(n: Int): String = toBinary(n, "")

    "연습문제 3-4에서 작성한 toBinary 함수가 꼬리 재귀인지 확인해 보자. 만약 꼬리 재귀가 아니라면 개선해 보자." {
        toBinary(0) shouldBe "0"
        toBinary(1) shouldBe "1"
        toBinary(2) shouldBe "10"
        toBinary(5) shouldBe "101"
        toBinary(11) shouldBe "1011"
        toBinary(75178) shouldBe "10010010110101010"
        toBinary(1231541) shouldBe "100101100101010110101"
    }
})
