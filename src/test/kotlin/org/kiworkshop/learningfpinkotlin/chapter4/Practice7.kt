package org.kiworkshop.learningfpinkotlin.chapter4

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import org.kiworkshop.learningfpinkotlin.head
import org.kiworkshop.learningfpinkotlin.tail

class Practice7 : FreeSpec() {
    private tailrec fun <T> takeWhile(list: List<T>, acc: List<T> = emptyList(), predicate: (T) -> Boolean): List<T> =
        when {
            list.isEmpty() -> acc
            predicate(list.head()) -> takeWhile(list.tail(), acc + list.head(), predicate)
            else -> acc
        }

    init {
        """리스트의 값을 조건 함수에 적용했을 때, 결과값이 참인 값의 리스트를 반환하는 takeWhile 함수를 꼬리 재귀로 작성해 보자.
           예를 들어 입력 리스트가 1, 2, 3, 4, 5로 구성되어 있을 때, 조건 함수가 3보다 작은 값이면 1과 2로 구성된 리스트를 반환한다. 
        """{
            takeWhile(listOf(1, 2, 3, 4, 5)) { it < 3 } shouldContainInOrder listOf(1, 2)
            takeWhile(listOf(4, 3, 2, 1)) { it < 3 } shouldHaveSize 0
            takeWhile(listOf(1, 2, 3, 4, 5)) { a: Int -> a >= 3 } shouldHaveSize 0
            takeWhile(listOf(1, 2, 3, 4, 5)) { a: Int -> a % 2 != 0 } shouldContainInOrder listOf(1)
        }
    }
}
