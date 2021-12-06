package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.should
import org.kiworkshop.learningfpinkotlin.head
import org.kiworkshop.learningfpinkotlin.tail

class Practice8 : FreeSpec({
    fun quickSort(list: List<Int>): List<Int> {
        if (list.size <= 1) return list
        val pivot = list.head()
        val rest = list.tail()
        val (greaterOrEqualThanPivot, lessThanPivot) = rest.partition { it >= pivot }
        return quickSort(lessThanPivot) + listOf(pivot) + quickSort(greaterOrEqualThanPivot)
    }

    "퀵정렬 알고리즘의 quicksort 함수를 작성해 보자"{
        quickSort(emptyList()) should beEmpty()
        quickSort(listOf(1)) shouldContainInOrder listOf(1)
        quickSort(listOf(1, 3, 2)) shouldContainInOrder listOf(1, 2, 3)
        quickSort(listOf(1, 2, 3, 4, 5)) shouldContainInOrder listOf(1, 2, 3, 4, 5)
        quickSort(listOf(1, 5, 6, 2, 4, 3, 5, 7, 8, 2, 4, 5)) shouldContainInOrder listOf(1, 2, 3, 4, 5)
    }
})
