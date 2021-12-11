package org.kiworkshop.learningfpinkotlin.chapter4

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize

class Practice8 : FreeSpec() {
    private fun <T> Sequence<T>.head() = first()
    private fun <T> Sequence<T>.tail() = drop(1)

    private tailrec fun <T> takeWhile(
        predicate: (T) -> Boolean,
        sequence: Sequence<T>,
        acc: List<T> = emptyList()
    ): List<T> = when {
        sequence.none() -> acc
        !predicate(sequence.head()) -> acc
        else -> takeWhile(predicate, sequence.tail(), acc + sequence.head())
    }

    init {
        "연습문제 4-7에서 작성한 takeWhile을 수정하여, 무한대를 입력받을 수 있는 takeWhile을 꼬리 재귀로 작성해 보자."{
            takeWhile({ a: Int -> a < 3 }, generateSequence(1) { it + 1 }) shouldContainInOrder listOf(1, 2)
            takeWhile({ a: Int -> a < 3 }, generateSequence(null as Int?) { it + 1 }) shouldHaveSize 0
            takeWhile({ a: Int -> a % 13 != 0 }, generateSequence(1) { it + 1 }) shouldContainInOrder (1..12).toList()
        }
    }
}
