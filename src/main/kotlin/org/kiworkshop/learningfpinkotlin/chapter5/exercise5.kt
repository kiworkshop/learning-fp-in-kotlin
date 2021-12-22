package org.kiworkshop.learningfpinkotlin.chapter5

import org.kiworkshop.learningfpinkotlin.FunList
import org.kiworkshop.learningfpinkotlin.FunList.Cons
import org.kiworkshop.learningfpinkotlin.FunList.Nil
import org.kiworkshop.learningfpinkotlin.FunStream
import org.kiworkshop.learningfpinkotlin.addHead
import org.kiworkshop.learningfpinkotlin.appendTail
import org.kiworkshop.learningfpinkotlin.foldLeft
import org.kiworkshop.learningfpinkotlin.foldRight
import org.kiworkshop.learningfpinkotlin.funListOf
import org.kiworkshop.learningfpinkotlin.getHead
import org.kiworkshop.learningfpinkotlin.getTail
import org.kiworkshop.learningfpinkotlin.reverse
import java.lang.Integer.max
import java.lang.Math.sqrt

/*
* 연습문제 5-1
* */
val intList: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))

/*
* 연습문제 5-2
* */
val doubleList: FunList<Double> = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))

/*
* 연습문제 5-3
* */
fun <T> FunList<T>.getHead(): T = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> head
}

/*
* 연습문제 5-4
* */
tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (n) {
    0 -> this
    else -> {
        this.getTail().drop(n - 1)
    }
}

/*
* 연습문제 5-5 * drop할게 없으면 원본을 반환
* */
tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> this
    is FunList.Cons ->
        if (!p(head))
            this
        else
            tail.dropWhile(p)
}

/*
* 연습문제 5-6
* */
tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = Nil): FunList<T> = when (n) {
    0 -> acc.reverse()
    else -> {
        this.getTail().take(n - 1, acc.addHead(this.getHead()))
    }
}

/*
* 연습문제 5-7
* */
tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons ->
        if (p(head))
            tail.takeWhile(acc.addHead(head), p)
        else
            tail.takeWhile(acc, p)
}

/*
* 연습문제 5-8
* */
tailrec fun <T, R> FunList<T>.indexedMap(index: Int = 0, acc: FunList<R> = Nil, f: (Int, T) -> R): FunList<R> =
    when (this) {
        FunList.Nil -> acc.reverse()
        is FunList.Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, head)), f)
    }

/*
* 연습문제 5-9
* */
fun FunList<Int>.maximumByFoldLeft(): Int = this.foldLeft(0) { acc, x -> max(acc, x) }

/*
* 연습문제 5-10 * O(n)으로 가능
* */
fun <T> FunList<T>.filterByFoldLeft(p: (T) -> Boolean): FunList<T> =
    this.foldLeft(Nil) { acc: FunList<T>, x ->
        if
                (p(x)) acc.addHead(x)
        else
            acc
    }.reverse()

/*
* 연습문제 5-11
* */
fun <T> FunList<T>.reverseByFoldRight(): FunList<T> =
    this.foldRight(Nil) { x, acc: FunList<T> -> acc.addHead(x) }.reverse()

/*
* 연습문제 5-12
* */
fun <T> FunList<T>.filterByFoldRight(p: (T) -> Boolean): FunList<T> =
    this.foldRight(Nil) { x, acc: FunList<T> -> if (p(x)) acc.addHead(x) else acc }

/*
* 연습문제 5-13
* */
tailrec fun <T, R> FunList<T>.zip(other: FunList<R>, acc: FunList<Pair<T, R>> = FunList.Nil): FunList<Pair<T, R>> =
    when {
        this == FunList.Nil || other == FunList.Nil -> acc.reverse()
        else -> getTail().zip(other.getTail(), acc.addHead(getHead() to other.getHead()))
    }


/*
* 연습문제 5-14
* */
fun <T, R> FunList<T>.associate(f: (T) -> Pair<T, R>): Map<T, R> =
    foldLeft(mapOf()) { acc, x -> acc.plus(f(x)) }


/*
* 연습문제 5-15
* */
fun <T, K> FunList<T>.groupBy(f: (T) -> K): Map<K, FunList<T>> =
    foldLeft(mapOf()) { acc, x ->
        var value: FunList<T> = funListOf()
        if (acc.containsKey(f(x)))
            value = acc[f(x)] ?: funListOf()
        acc.plus(f(x) to value.appendTail(x))
    }

/*
* 연습문제 5-16
* */
fun imperativeWay(intList: List<Long>): Long {
    for (value in intList) {
        val doubleValue = value * value
        if (doubleValue < 10)
            return doubleValue
    }
    throw NoSuchElementException("There is no value")
}

fun functionalWay(intList: List<Long>): Long =
    intList
        .map { n -> n * n }
        .filter { n -> n < 10 }
        .first()

fun main() {
    val bigIntList = (10000000L downTo 1L).toList()
    var start = System.currentTimeMillis()
    imperativeWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms") // "44ms" 출력

    start = System.currentTimeMillis()
    functionalWay(bigIntList)
    println("${System.currentTimeMillis() - start} ms") // "332ms" 출력

    val bigIntList2 = (1L..10000000L).toList()
    start = System.currentTimeMillis()
    imperativeWay(bigIntList2)
    println("${System.currentTimeMillis() - start} ms") // "0ms" 출력

    start = System.currentTimeMillis()
    functionalWay(bigIntList2)
    println("${System.currentTimeMillis() - start} ms") // "310ms" 출력

}

/*
* 연습문제 5-17
* */
fun FunStream<Int>.sum(): Int = when (this) {
    FunStream.Nil -> 0
    is FunStream.Cons -> head() + tail().sum()
}

/*
* 연습문제 5-18
* */

fun FunStream<Int>.product(): Int = when (this) {
    FunStream.Nil -> 1
    is FunStream.Cons -> head() * tail().product()
}

/*
* 연습문제 5-19
* */
fun <T> FunStream<T>.appendTail(value: T): FunStream<T> = when (this) {
    FunStream.Nil -> FunStream.Cons({ value }, { FunStream.Nil })
    is FunStream.Cons -> FunStream.Cons({ head() }, { tail().appendTail(value) })
}

/*
* 연습문제 5-20
* */
fun <T> FunStream<T>.filter(p: (T) -> Boolean): FunStream<T> = when (this) {
    FunStream.Nil -> this
    is FunStream.Cons -> if (p(head())) FunStream.Cons({ head() }, { tail().filter(p) }) else tail().filter(p)
}

/*
* 연습문제 5-21
* */
fun <T, R> FunStream<T>.map(p: (T) -> R): FunStream<R> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> FunStream.Cons({ p(head()) }, { tail().map(p) })
}

/*
* 연습문제 5-22
* */
fun <T> FunStream<T>.take(n: Int): FunStream<T> = when (n) {
    0 -> FunStream.Nil
    else -> FunStream.Cons({ this.getHead() }, { this.getTail().take(n - 1) })
}

/*
* 연습문제 5-23
* */
tailrec fun <T> FunStream<T>.toString(acc: String): String = when (this) {
    FunStream.Nil -> "[$acc]"
    is FunStream.Cons ->
        if (acc.isEmpty())
            this.getTail().toString("${head()}")
        else
            this.getTail().toString("$acc, ${head()}")
}


/*
* 연습문제 5-24
* */
tailrec fun getNumSqrtSum(n: Int, acc: Double): Int = when {
    acc > 1000 -> n
    else -> getNumSqrtSum(n + 1, acc + sqrt((n + 1).toDouble()))
}
