package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.head
import org.kiworkshop.learningfpinkotlin.tail

class Practice16 : FreeSpec({
    tailrec fun elem(num: Int, list: List<Int>, acc: Boolean): Boolean = when {
        list.isEmpty() -> acc
        acc -> acc // 한번이라도 true가 되면 재귀를 끝내는 것이 효율적
        else -> elem(num, list.tail(), list.head() == num) // acc 조건을 앞에서 검사해서 여기서는 검사할 필요가 없음
    }

    fun elem(num: Int, list: List<Int>): Boolean = elem(num, list, false)

    "연습문제 3-6에서 작성한 elem 함수가 꼬리 재귀인지 확인해 보자. 만약 꼬리 재귀가 아니라면 개선해 보자."{
        elem(3, listOf(1, 2, 3, 4, 5)) shouldBe true
        elem(2, emptyList()) shouldBe false
        elem(3, listOf(3, 3, 3, 3, 3)) shouldBe true
        elem(0, listOf(1, 56, 7, 4, 2, 5)) shouldBe false
        elem(9, listOf(2, 5, 6, 1, 23, 67, 7, 9)) shouldBe true
    }
})
