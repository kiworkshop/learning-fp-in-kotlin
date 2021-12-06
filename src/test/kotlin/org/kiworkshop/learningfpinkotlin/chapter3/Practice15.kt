package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class Practice15 : FreeSpec({
    tailrec fun replicate(n: Int, element: Int, acc: List<Int>): List<Int> {
        if (n == 0) return acc
        return replicate(n - 1, element, acc + element)
    }

    fun replicate(n: Int, element: Int): List<Int> = replicate(n, element, emptyList())

    "연습문제 3-5에서 작성한 replicate 함수가 꼬리 재귀인지 확인해 보자. 만약 꼬리 재귀가 아니라면 개선해 보자." {
        replicate(0, 2) should beEmpty()
        replicate(1, 0) shouldBe listOf(0)
        replicate(3, 5) shouldBe listOf(5, 5, 5)
        replicate(2, 4) shouldBe listOf(4, 4)
        replicate(10, 7) shouldBe (1..10).map { 7 }
    }
})
