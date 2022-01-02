package org.kiworkshop.learningfpinkotlin.seonghunlee

import org.kiworkshop.learningfpinkotlin.seonghunlee.FunList.Cons
import org.kiworkshop.learningfpinkotlin.seonghunlee.FunList.Nil
import java.lang.Math.sqrt

sealed class FunList<out T> {
    object Nil : FunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}

fun <T> FunList<T>.addHead(head: T): FunList<T> = Cons(head, this)

fun <T> FunList<T>.appendTail(value: T): FunList<T> = when (this) {
    FunList.Nil -> Cons(value, Nil)
    is Cons -> Cons(head, tail.appendTail(value))
}

tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = Nil): FunList<T> = when (this) {
    FunList.Nil -> Cons(value, acc).reverse()
    is Cons -> tail.appendTail(value, acc.addHead(head))
}

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc
    is Cons -> tail.reverse(acc.addHead(head))
}

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is Cons -> tail
}

// ex 5-3
fun <T> FunList<T>.getHead(): T = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is Cons -> head
}

fun imperativeFilter(numList: List<Int>): List<Int> {
    val newList = mutableListOf<Int>()
    for (num in numList) {
        if (num % 2 == 0) {
            newList.add(num)
        }
    }
    return numList
}

fun functionalFilter(numList: List<Int>): List<Int> =
    numList.filter { it % 2 == 0 }

tailrec fun <T> FunList<T>.filter(acc: FunList<T> = FunList.Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is Cons -> if (p(head)) {
        tail.filter(acc.addHead(head), p)
    } else {
        tail.filter(acc, p)
    }
}

tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (this) {
    Nil -> Nil
    is Cons -> if (n == 0) {
        this
    } else tail.drop(n - 1)
}

tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
    Nil -> Nil
    is Cons -> if (p(head)) {
        tail
    } else {
        tail.dropWhile(p)
    }
}

tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = Nil): FunList<T> = when (this) {
    Nil -> acc.reverse()
    is Cons -> if (n == 0) {
        acc.reverse()
    } else {
        tail.take(n - 1, acc.addHead(head))
    }
}

tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    Nil -> acc.reverse()
    is Cons -> if (p(head)) {
        tail.takeWhile(acc.addHead(head), p)
    } else {
        acc.reverse()
    }
}

fun imperativeMap(numList: List<Int>): List<Int> {
    val newList = mutableListOf<Int>()
    for (num in numList) {
        newList.add(num + 2)
    }
    return newList
}

fun functionalMap(numList: List<Int>): List<Int> {
    return numList.map { it + 2 }
}

fun add3(list: FunList<Int>): FunList<Int> = when (list) {
    FunList.Nil -> Nil
    is FunList.Cons -> FunList.Cons(list.head + 3, add3(list.tail))
}

fun product3(list: FunList<Double>): FunList<Double> = when (list) {
    Nil -> Nil
    is Cons -> Cons(list.head * 3, product3(list.tail))
}

tailrec fun <T, R> FunList<T>.map(acc: FunList<R> = Nil, f: (T) -> R): FunList<R> = when (this) {
    Nil -> acc.reverse()
    is Cons -> tail.map(acc.addHead(f(head)), f)
}

fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

private fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> Nil
    else -> Cons(this[0], this.copyOfRange(1, this.size).toFunList())
}

tailrec fun <T, R> FunList<T>.indexedMap(index: Int = 0, acc: FunList<R> = Nil, f: (Int, T) -> R): FunList<R> =
    when (this) {
        Nil -> acc.reverse()
        is Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, head)), f)
    }

fun sum(list: FunList<Int>): Int = when (list) {
    Nil -> 0
    is Cons -> list.head + sum(list.tail)
}

tailrec fun <T, R> FunList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    Nil -> acc
    is Cons -> tail.foldLeft(f(acc, head), f)
}

fun sumByFoldLeft(list: FunList<Int>): Int = list.foldLeft(0) { acc, x -> acc + x }

fun toUpper(list: FunList<Char>): FunList<Char> = list.foldLeft(Nil) { acc: FunList<Char>, char: Char ->
    acc.appendTail(char.toUpperCase())
}

fun FunList<Int>.maximumByFoldLeft(): Int = this.foldLeft(0) { acc, x -> if (acc > x) acc else x }

fun <T> FunList<T>.filterByFoldLeft(p: (T) -> Boolean): FunList<T> =
    this.foldLeft(Nil) { acc: FunList<T>, x: T -> if (p(x)) acc.appendTail(x) else acc }

fun <T, R> FunList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    FunList.Nil -> acc
    is Cons -> f(head, tail.foldRight(acc, f))
}

fun <T> FunList<T>.reverseByFoldRight(): FunList<T> = this.foldRight(Nil) { x: T, acc: FunList<T> -> acc.appendTail(x) }

fun <T> FunList<T>.filterByFoldRight(p: (T) -> Boolean): FunList<T> =
    this.foldRight(Nil) { x: T, acc: FunList<T> -> if (p(x)) acc.addHead(x) else acc }

fun <T, R> FunList<T>.mapByFoldLeft(f: (T) -> R): FunList<R> = foldLeft(FunList.Nil) { acc: FunList<R>, x ->
    acc.appendTail(f(x))
}

fun <T, R> FunList<T>.mapByFoldRight(f: (T) -> R): FunList<R> =
    foldRight(Nil) { x, acc: FunList<R> -> acc.addHead(f(x)) }

tailrec fun <T, R> FunList<T>.zip(other: FunList<R>, acc: FunList<Pair<T, R>> = FunList.Nil): FunList<Pair<T, R>> =
    when (this) {
        Nil -> acc.reverse()
        is Cons -> {
            if (other == Nil) acc.reverse()
            else tail.zip(other.getTail(), acc.addHead(Pair(this.getHead(), other.getHead())))
        }
    }

tailrec fun <T1, T2, R> FunList<T1>.zipWith(f: (T1, T2) -> R, list: FunList<T2>, acc: FunList<R> = Nil): FunList<R> =
    when {
        this == Nil || list == Nil -> acc.reverse()
        else -> getTail().zipWith(f, list.getTail(), acc.addHead(f(getHead(), list.getHead())))
    }

fun <T, R> FunList<T>.associate(f: (T) -> Pair<T, R>): Map<T, R> = when (this) {
    Nil -> mapOf()
    is Cons -> mapOf(f(head)) + tail.associate(f)
}

fun <T, K> FunList<T>.groupBy(f: (T) -> K, acc: Map<K, FunList<T>>): Map<K, FunList<T>> = when (this) {
    Nil -> acc
    is Cons ->
        tail.groupBy(f, acc.plus(f(head) to acc.getOrDefault(f(head), funListOf()).appendTail(head)))
}

fun <T, K> FunList<T>.groupBy(f: (T) -> K): Map<K, FunList<T>> = this.groupBy(f, emptyMap())

fun imperativeWay(intList: List<Int>): Int {
    for (value in intList) {
        val doubleValue = value * value
        if (doubleValue < 10) {
            return doubleValue
        }
    }
    throw NoSuchElementException("there is no value")
}

fun functionalWay(intList: List<Int>): Int = intList
    .map { it * it }.first { it < 10 }

fun realFunctionalWay(intList: List<Int>): Int =
    intList.asSequence()
        .map { it * it }.filter { n -> n > 10 }.first()

sealed class FunStream<out T> {
    object Nil : FunStream<Nothing>()
    data class Cons<out T>(val head: () -> T, val tail: () -> FunStream<T>) : FunStream<T>() {
        override fun equals(other: Any?): Boolean =
            if (other is Cons<*>) {
                if (head() == other.head()) {
                    tail() == other.tail()
                } else {
                    false
                }
            } else {
                false
            }

        override fun hashCode(): Int {
            var result = head.hashCode()
            result = 31 * result + tail.hashCode()
            return result
        }
    }
}

fun <T> funStreamOf(vararg elements: T): FunStream<T> = elements.toFunStream()

private fun <T> Array<out T>.toFunStream(): FunStream<T> = when {
    this.isEmpty() -> FunStream.Nil
    else -> FunStream.Cons({ this[0] }, { this.copyOfRange(1, this.size).toFunStream() })
}

fun <T> FunStream<T>.getHead(): T = when (this) {
    FunStream.Nil -> throw NoSuchElementException()
    is FunStream.Cons -> head()
}

fun <T> FunStream<T>.getTail(): FunStream<T> = when (this) {
    FunStream.Nil -> throw NoSuchElementException()
    is FunStream.Cons -> tail()
}

fun FunStream<Int>.sum(): Int = when (this) {
    FunStream.Nil -> 0
    is FunStream.Cons -> head() + tail().sum()
}

fun FunStream<Int>.product(): Int = when (this) {
    FunStream.Nil -> 1
    is FunStream.Cons -> head() * tail().product()
}

fun <T> FunStream<T>.appendTail(value: T): FunStream<T> = when (this) {
    FunStream.Nil -> FunStream.Cons({ value }, { FunStream.Nil })
    is FunStream.Cons -> FunStream.Cons({ head() }, { tail().appendTail(value) })
}

fun <T> FunStream<T>.filter(p: (T) -> Boolean): FunStream<T> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> {
        if (p(head())) {
            FunStream.Cons({ head() }, { tail().filter(p) })
        } else {
            tail().filter(p)
        }
    }
}

fun <T, R> FunStream<T>.map(p: (T) -> R): FunStream<R> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> FunStream.Cons({ p(head()) }, { tail().map(p) })
}

private fun IntRange.toFunList(): FunList<Int> {
    tailrec fun IntIterator.toFunList(acc: FunList<Int> = FunList.Nil): FunList<Int> =
        if (this.hasNext()) {
            toFunList(Cons(this.nextInt(), acc))
        } else {
            acc
        }
    return this.reversed().iterator().toFunList()
}

fun IntRange.toFunStream(): FunStream<Int> {
    fun IntIterator.toFunStream(): FunStream<Int> {
        if (!this.hasNext()) {
            return FunStream.Nil
        }

        val next = this.nextInt()
        return if (this.hasNext())
            FunStream.Cons({ next }) { toFunStream() }
        else
            FunStream.Cons({ next }) { FunStream.Nil }
    }

    return this.iterator().toFunStream()
}

fun funListWay(intList: FunList<Int>): Int = intList.map { n -> n * n }
    .filter { n -> n < 1000000 }
    .map { n -> n - 2 }
    .filter { n -> n < 1000 }
    .map { n -> n * 10 }
    .getHead()

fun funStreamWay(intList: FunStream<Int>): Int = intList.map { n -> n * n }
    .filter { n -> n < 1000000 }
    .map { n -> n - 2 }
    .filter { n -> n < 1000 }
    .map { n -> n * 10 }
    .getHead()

fun <T> generateFunStream(seed: T, generate: (T) -> T): FunStream<T> =
    FunStream.Cons({ seed }, { generateFunStream(generate(seed), generate) })

tailrec fun <T> FunStream<T>.forEach(f: (T) -> Unit): Unit = when (this) {
    FunStream.Nil -> Unit
    is FunStream.Cons -> {
        f(head())
        tail().forEach(f)
    }
}

fun <T> FunStream<T>.take(n: Int): FunStream<T> = when (this) {
    FunStream.Nil -> FunStream.Nil
    is FunStream.Cons -> if (n <= 0) FunStream.Nil else FunStream.Cons({ head() }, { tail().take(n - 1) })
}

tailrec fun <T> FunList<T>.toString1(acc: String = ""): String = when (this) {
    Nil -> "[$acc]"
    is Cons -> if (acc.isEmpty()) {
        tail.toString1("$head")
    } else {
        tail.toString1("$acc, $head")
    }
}

tailrec fun <T> FunList<T>.toString2(acc: String = ""): String = when (this) {
    FunList.Nil -> "[${acc.drop(2)}]"
    is Cons -> tail.toString2("$acc, $head")
}

fun <T> printFunList(list: FunList<T>) = list.toStringByFoldLeft()

fun <T> FunList<T>.toStringByFoldLeft(): String =
    "[${foldLeft("") { acc, x -> "$acc, $x" }.drop(2)}]"

fun IntRange.sumSqrtBiggerThanOneThousand(): Int {
    fun IntIterator.sumSqrtBiggerThanOneThousand(acc: Double = 0.0): Int {
        val nextInt = nextInt()
        return if (acc + sqrt(nextInt.toDouble()) > 1000) {
            nextInt
        } else {
            sumSqrtBiggerThanOneThousand(acc + sqrt(nextInt.toDouble()))
        }
    }
    return this.iterator().sumSqrtBiggerThanOneThousand()
}

fun main(args: Array<String>) {
//    println(Cons(1, Cons(2, Nil)).addHead(5).getTail())
//    println(sum(Cons(3, Cons(4, Nil))))
//    val intList = funListOf(1, 2, 3)
//    println(intList)
//    println(sum(intList))
//    println(sumByFoldLeft(intList))
//    println(funListOf(1, 9, 3, 4).maximumByFoldLeft())
//    println(funListOf(1, 3, 5, 2, 9).filterByFoldLeft { it > 4 })
//    println(funListOf(1, 2, 3).reverseByFoldRight())
//    println(funListOf(1, 2, 3).filterByFoldRight { x -> x <= 2 })
//    val intList = funListOf(4, 2, 3)
//    val intList2 = funListOf(1, 3, 10)
//    val lowerCharList = funListOf('a', 'b', 'c')

//    println(intList.associate { x -> Pair(x, x * 2) })
//    println(mapOf(Pair(1, 2), Pair(2, 4), Pair(3, 6)))
//    println(intList.zipWith({ x, y -> x + y }, intList2))
//    println(intList.zipWith({ x, y -> if (x > y) x else y }, intList2))
//    println(intList.zipWith({ x, y -> x to y }, lowerCharList))
//    println(functionalWay(listOf(4, 2, 3)))
//    println(imperativeWay(listOf(4, 2, 3)))
//    val bigIntList = (1..10000000).toList()
//    var start = System.currentTimeMillis()
//    imperativeWay(bigIntList)
//    println("${System.currentTimeMillis() - start} ms")
//
//    start = System.currentTimeMillis()
//    functionalWay(bigIntList)
//    println("${System.currentTimeMillis() - start} ms")
//
//    start = System.currentTimeMillis()
//    realFunctionalWay(bigIntList)
//    println("${System.currentTimeMillis() - start} ms")
//
//    val bigIntList = (10000000 downTo 1).toList()
//    var start = System.currentTimeMillis()
//    imperativeWay(bigIntList)
//    println("${System.currentTimeMillis() - start} ms")
//
//    start = System.currentTimeMillis()
//    functionalWay(bigIntList)
//    println("${System.currentTimeMillis() - start} ms")
//
//    start = System.currentTimeMillis()
//    realFunctionalWay(bigIntList)
//    println("${System.currentTimeMillis() - start} ms")

//    val infiniteVal = generateFunStream(0) { it + 5 }
//    infiniteVal.forEach { println(it) }
//    println(funListOf(1, 2, 3).toString1())
//    println(funListOf(1, 2, 3).toString2())
//    println(funListOf(1, 2, 3).toStringByFoldLeft())
    println((1..1000000).sumSqrtBiggerThanOneThousand())
}

class Ch5SourceCode
