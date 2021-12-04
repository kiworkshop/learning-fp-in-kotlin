package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Practice6Of3 : ShouldSpec({
    context("음이 아닌 정수 n이 주어졌을 때") {
        should("n의 이진수를 구한다") {
            listOf(
                ListExistTestData(0, emptyList(), false),
                ListExistTestData(0, listOf(0), true),
                ListExistTestData(0, listOf(0, 1), true),
                ListExistTestData(0, listOf(1, 0), true),
                ListExistTestData(0, listOf(1, 1), false),
                ListExistTestData(0, listOf(1, 2), false),
                ListExistTestData(1, emptyList(), false),
                ListExistTestData(1, listOf(1), true),
                ListExistTestData(1, listOf(1, 0), true),
                ListExistTestData(1, listOf(0, 1), true),
                ListExistTestData(1, listOf(0), false),
                ListExistTestData(2, listOf(0, 1), false),
                ListExistTestData(3, listOf(1, 0), false),
            ).forEach { (element, list, expected) ->
                val result = ListExist.elem(element, list)

                result shouldBe expected
            }
        }
    }
})

private data class ListExistTestData(
    val element: Int,
    val list: List<Int>,
    val expected: Boolean,
)
