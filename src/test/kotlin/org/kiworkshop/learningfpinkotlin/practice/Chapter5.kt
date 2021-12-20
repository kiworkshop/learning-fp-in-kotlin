package org.kiworkshop.learningfpinkotlin.practice

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter5 : StringSpec({
    fun assertThatIsExpectedList(realValue: FunList<*>, expectedList: List<*>) {
        realValue.toList() shouldBe expectedList
    }

    "Example 5-1" {
        val intList = FunList.Cons(1, FunList.Cons(2, FunList.Cons(3, FunList.Cons(4, FunList.Cons(5, FunList.Nil)))))
        assertThatIsExpectedList(intList, listOf(1, 2, 3, 4, 5))
    }

    "Example 5-2" {
        val doubleList =
            FunList.Cons(1.0, FunList.Cons(2.0, FunList.Cons(3.0, FunList.Cons(4.0, FunList.Cons(5.0, FunList.Nil)))))
        assertThatIsExpectedList(doubleList, listOf(1.0, 2.0, 3.0, 4.0, 5.0))
    }
    "Code 5-7" {
        FunList.Cons(1, FunList.Nil).addHead(3).getTail() shouldBe FunList.Cons(1, FunList.Nil)
        FunList.Cons(1, FunList.Nil).appendTail(3).getTail() shouldBe FunList.Cons(3, FunList.Nil)
        shouldThrow<NoSuchElementException> { FunList.Nil.getTail() }
    }

    "Example 5-3" {
        FunList.Cons(1, FunList.Nil).addHead(3).getHead() shouldBe 3
        shouldThrow<NoSuchElementException> { FunList.Nil.getHead() }
    }

    "Code 5-10" {
        val intList = FunList.Cons(1, FunList.Cons(2, FunList.Cons(3, FunList.Cons(4, FunList.Cons(5, FunList.Nil)))))
        assertThatIsExpectedList(intList.filter { it % 2 == 0 }, listOf(2, 4))
        assertThatIsExpectedList(intList.filter { it % 2 == 1 }, listOf(1, 3, 5))
    }

    "Example 5-4" {
        val intList = FunList.Cons(1, FunList.Cons(2, FunList.Cons(3, FunList.Cons(4, FunList.Cons(5, FunList.Nil)))))

        assertThatIsExpectedList(intList.drop(3), listOf(4, 5))
    }

    "Example 5-5" {
        val intList = FunList.Cons(1, FunList.Cons(2, FunList.Cons(3, FunList.Cons(4, FunList.Cons(5, FunList.Nil)))))

        assertThatIsExpectedList(intList.dropWhile { it % 2 == 0 }, listOf(2, 4))
    }

    "Example 5-6" {
        val intList = FunList.Cons(1, FunList.Cons(2, FunList.Cons(3, FunList.Cons(4, FunList.Cons(5, FunList.Nil)))))

        assertThatIsExpectedList(intList.take(2), listOf(1, 2))
    }

    "Example 5-7" {
        val intList = FunList.Cons(1, FunList.Cons(2, FunList.Cons(3, FunList.Cons(4, FunList.Cons(5, FunList.Nil)))))

        assertThatIsExpectedList(intList.takeWhile { it % 2 == 0 }, listOf(2, 4))
    }
})

// TODO : acc 변수 없이 구현할 것 
/**
 * drop : 주어진 리스트에서 앞의 값 n개가 제외된 리스트 반환 하는 함수
 * 제약조건
 * 1. 원본 리스트는 변경되면 안됨
 * 2. 새로운 리스트를 반환할 때마다 리스트를 생성하면 안됨 (그럼 마지막 한 번은 괜찮은건가?)
 * */
tailrec fun <T> FunList<T>.drop(n: Int, acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> if (n == 0) {
        tail.addHead(head)
    } else {
        tail.drop(n - 1, acc)
    }
}

// TODO : acc 변수 없이 구현할 것
tailrec fun <T> FunList<T>.dropWhile(acc: FunList<T> = FunList.Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> if (p(head)) {
        tail.dropWhile(acc.addHead(head), p)
    } else {
        tail.dropWhile(acc, p)
    }
}

tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> if (n == 0) {
        tail.take(n, acc)
    } else {
        tail.take(n - 1, acc.appendTail(head))
    }
}

// TODO : 내용이 없으면 자기 자신 내뱉기 
tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = FunList.Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> if (p(head)) {
        tail.takeWhile(acc.addHead(head), p)
    } else {
        tail.takeWhile(acc, p)
    }
}

/**
 * @param acc : 꼬리 재귀를 위한 매개변수
 * */
tailrec fun <T> FunList<T>.filter(acc: FunList<T> = FunList.Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse() // addHead 로 값을 넣었기 때문에, 모두 완료하면 reverse
    is FunList.Cons -> if (p(head)) {
        tail.filter(acc.addHead(head), p) // 성공하면, 리스트에 추가하기 위해서 addHead
    } else {
        tail.filter(acc, p)
    }
}

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> tail
}

fun <T> FunList<T>.getHead(): T = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> head
}

private fun <T> FunList<T>.addHead(value: T): FunList<T> = FunList.Cons(value, this)

// 꼬리 재귀가 아니므로 스택에 안전하지 않음
// private fun <T> FunList<T>.appendTail(value: T): FunList<T> = when (this) {
//    FunList.Nil -> FunList.Cons(value, FunList.Nil)
//    is FunList.Cons -> FunList.Cons(head, tail.appendTail(value))
// }

tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> FunList.Cons(value, acc).reverse()
    is FunList.Cons -> tail.appendTail(value, acc.addHead(head))
}

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.reverse(acc.addHead(head))
}

tailrec fun <T> FunList<T>.toList(acc: MutableList<T> = mutableListOf()): List<T> = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.toList(acc = acc.apply { add(head) })
}

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}
