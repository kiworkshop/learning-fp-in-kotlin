package org.kiworkshop.learningfpinkotlin.chapter4

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize

class Practice8 : FreeSpec() {
    private fun <T> Sequence<T>.head() = first()
    private fun <T> Sequence<T>.tail() = drop(1)

    private tailrec fun <T> takeWhile(
        sequence: Sequence<T>,
        acc: List<T> = emptyList(),
        predicate: (T) -> Boolean
    ): List<T> = when {
        sequence.none() -> acc
        !predicate(sequence.head()) -> acc
        else -> takeWhile(sequence.tail(), acc + sequence.head(), predicate)
    }

    init {
        "연습문제 4-7에서 작성한 takeWhile을 수정하여, 무한대를 입력받을 수 있는 takeWhile을 꼬리 재귀로 작성해 보자."{
            var b: Sequence<Int>
            takeWhile(generateSequence(1) { it + 1 }) { it < 3 } shouldContainInOrder listOf(1, 2)
            takeWhile(generateSequence(null as Int?) { it + 1 }) { it < 3 } shouldHaveSize 0
            takeWhile(generateSequence(1) { it + 1 }) { it % 13 != 0 } shouldContainInOrder (1..12).toList()
        }
    }
}
