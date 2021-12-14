package org.kiworkshop.learningfpinkotlin.chapter4

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.sequences.shouldContainExactly
import io.kotest.matchers.shouldBe

class Exercise4 : AnnotationSpec() {

    @Test
    fun `test4-3 두개의 매개변수를 받아서 큰 값을 반환하는 max함수를 커링을 사용하도록 구현`() {
        getMax(5)(1) shouldBe 5
    }

    @Test
    fun `test4-4 두 매개변수를 받아서 작은값을 반환하는 min함수를 curried함수를 사용하여 구현`() {
        getMin(5)(1) shouldBe 1
    }

    @Test
    fun `test4-5 숫자(Int)의 리스트를 받아서 최댓값의 제곱을 구하는 함수`() {
        getMaxPower(listOf(3, -1, 5, -2, -4, 8, 9)) shouldBe 81
    }

    @Test
    fun `test4-6 숫자(Int)의 리스트를 받아서 최댓값의 제곱을 구하는 함수를 compose를 사용해 다시 작성`() {
        composed(listOf(3, -1, 5, -2, -4, 8, 9)) shouldBe 81
    }

    @Test
    fun `test4-7 리스트의 값을 조건함수에 적용했을 때 참인 값의 리스트를 반환하는 함수`() {
        takeWhile(
            { it < 3 },
            List(5) { it + 1 }
        ).shouldContainExactly(1, 2)
    }

    @Test
    fun `test4-8 takeWhile함수를 수정하여 무한대를 입력받는 꼬리함수 구현`() {
        takeWhileSeq(
            { it < 3 },
            generateSequence(1) { i -> i + 1 }
        ).shouldContainExactly(1, 2)
    }
}
