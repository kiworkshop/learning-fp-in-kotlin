package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlin.math.abs

class Practice8Of3 : ShouldSpec({
    context("리스트가 주어지면") {
        should("오름차순으로 정렬한다") {
            listOf(
                QuickSortTestData(listOf(3, 2, 1), listOf(1, 2, 3)),
                QuickSortTestData(emptyList(), emptyList()),
                QuickSortTestData(listOf(4, 2, 1, 6), listOf(1, 2, 4, 6)),
                QuickSortTestData(listOf(4, 4, 2, 1, 4, 6), listOf(1, 2, 4, 4, 4, 6)),
                QuickSortTestData(listOf(1), listOf(1)),
            ).forEach { (list, expected) ->
                val result = quickSort(list)

                result shouldBe expected
            }
        }
    }
})

private fun <E : Comparable<E>> quickSort(list: List<E>): List<E> {
    if (list.isEmpty() || list.size == 1) {
        return list
    }
    val pivot = list[list.size / 2]
    val (left, right) = list.partition { it < pivot }
    return quickSort(left) + listOf(pivot) + quickSort(right - pivot)
}

private data class QuickSortTestData(
    val list: List<Int>,
    val expected: List<Int>,
)
