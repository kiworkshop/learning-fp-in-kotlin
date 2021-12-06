package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.should

operator fun <T> Sequence<T>.plus(other: () -> Sequence<T>) = object : Sequence<T> {
    private val thisIterator: Iterator<T> by lazy { this@plus.iterator() }
    private val otherIterator: Iterator<T> by lazy { other().iterator() }

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        override fun hasNext() = thisIterator.hasNext() || otherIterator.hasNext()

        override fun next(): T {
            if (thisIterator.hasNext()) {
                return thisIterator.next()
            }
            return otherIterator.next()
        }
    }
}

class Practice7 : FreeSpec({

    fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> {
        if (n <= 0) return emptyList()
        return listOf(sequence.first()) + takeSequence(n - 1, sequence.drop(1)) // 근데 이게 의미가 있는 재귀인가..?
    }

    fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }

    """코드 3-9의 take 함수를 참고하여 repeat 함수를 테스트하기 위해서 사용한 takeSequence 함수를 작성해보자.
       그리고 repeat 함수가 잘 동작하는지 테스트해 보자.
    """ {
        takeSequence(5, repeat(3)) shouldContainAll listOf(3, 3, 3, 3, 3)
        takeSequence(2, repeat(1)) shouldContainAll listOf(1, 1)
        takeSequence(0, repeat(5)) should beEmpty()
    }
})
