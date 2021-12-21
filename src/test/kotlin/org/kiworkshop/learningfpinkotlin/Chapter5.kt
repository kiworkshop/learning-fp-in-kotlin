package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.FunList.Cons
import org.kiworkshop.learningfpinkotlin.FunList.Nil
import kotlin.math.max

class Chapter5 : StringSpec({

    fun <T> FunList<T>.toList(): List<T> {
        tailrec fun FunList<T>.toList(acc: MutableList<T>): MutableList<T> = when (this) {
            is Nil -> acc
            is Cons -> this.tail.toList(acc.add(this.head).let { acc })
        }

        return this.toList(mutableListOf())
    }

    fun <T> FunStream<T>.toList(): List<T> {
        tailrec fun FunStream<T>.toList(acc: MutableList<T>): MutableList<T> = when (this) {
            is FunStream.Nil -> acc
            is FunStream.Cons -> this.tail().toList(acc.add(this.head()).let { acc })
        }

        return this.toList(mutableListOf())
    }
    val listForTest: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))

    "Exercise 1,2" {
        val intList: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
        val doubleList: FunList<Double> = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
    }

    "Exercise 3" {
        fun <T> FunList<T>.getHead(): T = when (this) {
            FunList.Nil -> throw NoSuchElementException()
            is FunList.Cons -> head
        }
    }

    "Exercise 4" {
        tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (n) {
            0 -> this
            else -> when (this) {
                Nil -> this
                is Cons -> tail.drop(n - 1)
            }
        }
    }

    "Exercise 5" {
        tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
            Nil -> this
            is Cons -> when {
                p(head) -> this
                else -> tail.dropWhile(p)
            }
        }
    }

    "Exercise 6" {
        tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = Nil): FunList<T> = when (n) {
            0 -> this
            else -> when (this) {
                Nil -> acc
                is Cons -> tail.take(n - 1, acc.appendTail(head))
            }
        }
    }

    "Exercise 7" {
        tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
            Nil -> acc
            is Cons -> when {
                p(head) -> tail.takeWhile(acc.appendTail(head), p)
                else -> tail.takeWhile(acc, p)
            }
        }

        listForTest.takeWhile { it < 3 }.toList() shouldBe(listOf(1, 2))
    }

    "Exercise 8" {
        tailrec fun <T, R> FunList<T>.indexedMap(index: Int = 0, acc: FunList<R> = Nil, f: (Int, T) -> R): FunList<R> = when (this) {
            Nil -> acc.reverse()
            is Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, head)), f)
        }
    }

    "Exercise 9" {
        fun FunList<Int>.maximumByFoldLeft(): Int = foldLeft(0) { acc, it -> max(acc, it) }
    }

    "Exercise 10" {
        fun <T> FunList<T>.filterByFoldLeft(p: (T) -> Boolean): FunList<T> = foldLeft(Nil) { acc: FunList<T>, it ->
            when {
                p(it) -> acc.appendTail(it)
                else -> acc
            }
        }

        listForTest.filterByFoldLeft { it % 2 == 0 }.toList() shouldBe listOf(2, 4)
    }

    "Exercise 11" {
        fun <T> FunList<T>.reverseByFoldRight(): FunList<T> = foldRight(Nil as FunList<T>) { it, acc -> acc.appendTail(it) }

        listForTest.reverseByFoldRight().toList() shouldBe listOf(5, 4, 3, 2, 1)
    }

    "Exercise 12" {
        fun <T> FunList<T>.filterByFoldRight(p: (T) -> Boolean): FunList<T> = foldRight(Nil as FunList<T>) { it, acc ->
            when {
                p(it) -> acc.addHead(it)
                else -> acc
            }
        }
        listForTest.filterByFoldRight { it % 2 == 0 }.toList() shouldBe listOf(2, 4)
    }

    "Exercise 13" {
        tailrec fun <T, R> FunList<T>.zip(other: FunList<R>, acc: FunList<Pair<T, R>> = FunList.Nil): FunList<Pair<T, R>> = when {
            this == Nil || other == Nil -> acc.reverse()
            else -> getTail().zip(other.getTail(), acc.addHead(this.getHead() to other.getHead()))
        }
    }
    listForTest.zip(listForTest).toList() shouldBe listOf(1 to 1, 2 to 2, 3 to 3, 4 to 4, 5 to 5)

    "Exercise 14" {
        fun <T, R> FunList<T>.associate(f: (T) -> Pair<T, R>): Map<T, R> = foldRight(mapOf()) { it, acc -> acc + f(it) }
    }
    listForTest.associate { it to it + 2 }.toMap() shouldBe mapOf(1 to 3, 2 to 4, 3 to 5, 4 to 6, 5 to 7)

    "Exercise 15" {
        // fun <T, K> FunList<T>.groupBy(f: (T) -> K): Map<K, FunList<T>> 키 기준을 뭘로 잡는다는건지?
    }

    "Exercise 17" {
    }
})
