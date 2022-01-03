package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength
import org.kiworkshop.learningfpinkotlin.FunList.Cons
import org.kiworkshop.learningfpinkotlin.FunList.Nil

class Chapter5 : StringSpec({
    val list: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))

    "Exercise 5-1" {
        val intList: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
    }

    "Exercise 5-2" {
        val doubleList: FunList<Double> = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
    }

    "Exercise 5-3" {
        fun <T> FunList<T>.getHead() = when (this) {
            Nil -> throw NoSuchElementException()
            is Cons -> head
        }

        val intList: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))

        intList.getHead() shouldBe 1
    }

    "Exercise 5-4" {
        tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (this) {
            Nil -> this
            is Cons -> if (n == 0) this else this.tail.drop(n - 1)
        }

        list.drop(n = 2) shouldBe Cons(3, Cons(4, Cons(5, Nil)))
    }

    "Exercise 5-5" {
        tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
            Nil -> this
            is Cons -> if (p(head)) {
                this
            } else {
                tail.dropWhile(p)
            }
        }

        list.dropWhile { it > 2 } shouldBe Cons(3, Cons(4, Cons(5, Nil)))
    }

    "Exercise 5-6" {
        tailrec fun <T> FunList<T>.takeWhile(n: Int, acc: FunList<T> = Nil): FunList<T> = when (this) {
            Nil -> this
            is Cons -> if (n > 0) tail.takeWhile(n - 1, acc.addHead(head)) else acc.reverse()
        }

        list.takeWhile(2) shouldBe Cons(1, Cons(2, Nil))
    }

    "Exercise 5-7" {
        tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
            Nil -> this
            is Cons -> if (p(head)) tail.takeWhile(acc.addHead(head), p) else acc.reverse()
        }

        list.takeWhile { it < 3 } shouldBe Cons(1, Cons(2, Nil))
    }

    "Exercise 5-8" {
        tailrec fun <T, R> FunList<T>.indexedMap(index: Int = 0, acc: FunList<R> = Nil, f: (Int, T) -> R): FunList<R> =
            when (this) {
                Nil -> acc.reverse()
                is Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, this.head)), f)
            }
    }

    "Exercise 5-9" {
        val seed = listOf(
            Array(10) { it.toChar() },
            Array(26) { (it + 65).toChar() }
        )
            .flatMap { it.asList() }

        fun randomStr(length: Int, seed: List<Char>): String {
            val random = java.util.Random()
            return generateSequence(1) { it + 1 }.take(length)
                .map { seed[random.nextInt(36)] }
                .joinToString(separator = "")
        }

        randomStr(10, seed) shouldHaveLength 10
    }

    "Exercise 5-10" {}

    "Exercise 5-11" {}

    "Exercise 5-12" {}

    "Exercise 5-13" {}

    "Exercise 5-14" {}

    "Exercise 5-15" {}
})

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}

fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

private fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> Nil
    else -> Cons(this[0], this.copyOfRange(1, this.size).toFunList())
}
