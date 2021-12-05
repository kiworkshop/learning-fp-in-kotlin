package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Practice7Of3 : ShouldSpec({
    context("원소와 사이즈가 주어지면") {
        should("사이즈 크기의 원소가 반복되는 리스트를 구한다") {
            listOf(
                TakeSequenceTestData(5, 3, listOf(3, 3, 3, 3, 3)),
                TakeSequenceTestData(4, 2, listOf(2, 2, 2, 2)),
                TakeSequenceTestData(0, 1, emptyList()),
            ).forEach { (size, element, expected) ->
                val result = takeSequence(size, repeat(element))

                result shouldBe expected
            }
        }
    }
})

fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }

fun takeSequence(size: Int, sequence: Sequence<Int>): List<Int> {
    if (size == 0) {
        return emptyList()
    }
    return takeSequence(size - 1, sequence) + sequence.first()
}

operator fun <T> Sequence<T>.plus(other: () -> Sequence<T>) = object : Sequence<T> {
    private val iterator by lazy { this@plus.iterator() }
    private val otherIterator by lazy { other().iterator() }

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        override fun next(): T {
            return when {
                iterator.hasNext() -> iterator.next()
                otherIterator.hasNext() -> otherIterator.next()
                else -> throw NoSuchElementException()
            }
        }

        override fun hasNext(): Boolean {
            return iterator.hasNext() || otherIterator.hasNext()
        }
    }
}

private data class TakeSequenceTestData(
    val size: Int,
    val element: Int,
    val expected: List<Int>,
)
