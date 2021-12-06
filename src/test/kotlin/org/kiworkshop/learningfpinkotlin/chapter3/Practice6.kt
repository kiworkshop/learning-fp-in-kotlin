package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.head
import org.kiworkshop.learningfpinkotlin.tail

class Practice6 : FreeSpec({
    fun elem(num: Int, list: List<Int>): Boolean = when {
        list.isEmpty() -> false
        else -> list.head() == num || elem(num, list.tail())
    }

    "입력값 n이 리스트에 존재하는지 확인하는 함수를 재귀로 작성해보자."{
        elem(3, listOf(1, 2, 3, 4, 5)) shouldBe true
        elem(2, emptyList()) shouldBe false
        elem(3, listOf(3, 3, 3, 3, 3)) shouldBe true
        elem(0, listOf(1, 56, 7, 4, 2, 5)) shouldBe false
        elem(9, listOf(2, 5, 6, 1, 23, 67, 7, 9)) shouldBe true
    }
})
