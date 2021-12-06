package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class Practice5 : FreeSpec({
    fun replicate(n: Int, element: Int): List<Int> {
        if (n == 0) return emptyList()
        return replicate(n - 1, element) + element
    }

    """숫자를 두 개 입력받은 후 두 번째 숫자를 첫 번째 숫자만큼 가지고 있는 리스트를 반환하는 함수를 만들어 보자. 
       예를 들어 replicate(3, 5)를 입력하면 5개 3개 있는 리스트 [5, 5, 5]를 반환한다.""" {
        replicate(0, 2) should beEmpty()
        replicate(1, 0) shouldBe listOf(0)
        replicate(3, 5) shouldBe listOf(5, 5, 5)
        replicate(2, 4) shouldBe listOf(4, 4)
        replicate(10, 7) shouldBe (1..10).map { 7 }
    }
})
