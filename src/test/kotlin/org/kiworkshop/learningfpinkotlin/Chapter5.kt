package org.kiworkshop.learningfpinkotlin

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.FunList.*

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}

fun <T> FunList<T>.addHead(head: T): FunList<T> = Cons(head, this)
//fun <T> FunList<T>.appendTail(value: T): FunList<T> = when (this) {
//    Nil -> Cons(value, Nil)
//    is Cons -> Cons(head, tail.appendTail(value))
//}

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = Nil): FunList<T> = when (this) {
    Nil -> acc
    is Cons -> tail.reverse(acc.addHead(head))
}

tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = Nil): FunList<T> = when (this) {
    Nil -> Cons(value, acc).reverse()
    is Cons -> tail.appendTail(value, acc.addHead(head))
}

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> tail
}

tailrec fun <T> FunList<T>.filter(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    Nil -> acc.reverse()
    is Cons -> if (p(head)) {
        tail.filter(acc.addHead(head), p)
    } else {
        tail.filter(acc, p)
    }
}

class Chapter5 : StringSpec({
    "5-1" {
        val list: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
    }

    "5-2" {
        val list: FunList<Double> = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
    }
    
    "5-3" {
        fun <T> FunList<T>.getHead(): T = when (this) {
            Nil -> throw NoSuchElementException()
            is Cons -> head
        }

        val list = Cons(1, Cons(2, Cons(3, Nil)))

        list.getHead() shouldBe 1
        list.getTail().getHead() shouldBe 2
    }

    "5-4" {
        tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (this) {
            Nil -> this
            is Cons -> when {
                n < 0 -> throw IllegalArgumentException()
                n == 0 -> this
                else -> tail.drop(n - 1)
            }
        }

        Nil.appendTail(1).appendTail(2).appendTail(3)
            .drop(1) shouldBe Cons(2, Cons(3, Nil))
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .drop(2) shouldBe Cons(3, Nil)
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .drop(3) shouldBe Nil
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .drop(4) shouldBe Nil
    }

    "5-5" {
        tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
            Nil -> this
            is Cons -> when {
                !p(head) -> this
                else -> tail.dropWhile(p)
            }
        }

        Nil.appendTail(1).appendTail(2).appendTail(3)
            .dropWhile { it < 2 } shouldBe Cons(2, Cons(3, Nil))
        Nil.appendTail(1).appendTail(2).appendTail(3)
            .dropWhile { it < 100 } shouldBe Nil
    }
})