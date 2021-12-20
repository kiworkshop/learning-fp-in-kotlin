package org.kiworkshop.learningfpinkotlin.practice

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter5 : StringSpec({
    fun assertThatArrayIsExpectedArray(realValue: FunList<*>, expectedArray: Array<*>) {
        var value: FunList<*> = realValue
        var i = 0

        while (value is FunList.Cons<*>) {
            (value as FunList.Cons<*>).head shouldBe expectedArray[i]
            i++
            value = (value as FunList.Cons<*>).tail
        }
    }

    // 꼬리 재귀가 아니므로 스택에 안전하지 않음
    fun addInt(n: Int, isLast: Boolean = false): FunList<Int> {
        return if (isLast) {
            FunList.Cons(head = n, FunList.Nil)
        } else {
            addInt(n - 1, n == 2)
        }
    }

    "Example 5-1" {
        assertThatArrayIsExpectedArray(addInt(5), arrayOf(1, 2, 3, 4, 5))
    }
    // 꼬리 재귀가 아니므로 스택에 안전하지 않음
    fun addDouble(n: Double, step: Double = 1.0, isLast: Boolean = false): FunList<Double> {
        return if (isLast) {
            FunList.Cons(head = n, FunList.Nil)
        } else {
            addDouble(n - step, step, n == 2.0)
        }
    }
    "Example 5-2" {
        assertThatArrayIsExpectedArray(addDouble(5.0), arrayOf(1.0, 2.0, 3.0, 4.0, 5.0))
    }

    "Code 5-7" {
        addInt(5).getTail() shouldBe FunList.Nil
        shouldThrow<NoSuchElementException> { FunList.Nil.getTail() }
    }

    "Example 5-3" {
        addInt(5).getHead() shouldBe 1
        shouldThrow<NoSuchElementException> { FunList.Nil.getHead() }
    }
})

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> tail
}

fun <T> FunList<T>.getHead(): T = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> head
}

private fun <T> FunList<T>.addHead(value: T): FunList<T> = FunList.Cons(head = value, tail = this)

// 꼬리 재귀가 아니므로 스택에 안전하지 않음
private fun <T> FunList<T>.appendTail(value: T): FunList<T> = when (this) {
    FunList.Nil -> FunList.Cons(head = value, tail = FunList.Nil)
    is FunList.Cons -> FunList.Cons(head = head, tail = tail.appendTail(value))
}

tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> FunList.Cons(value, acc).reverse()
    is FunList.Cons -> tail.appendTail(value, acc.addHead(head))
}

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.reverse(acc.addHead(head))
}

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}
