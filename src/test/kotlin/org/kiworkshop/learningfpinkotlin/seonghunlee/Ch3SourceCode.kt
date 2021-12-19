package org.kiworkshop.learningfpinkotlin.seonghunlee

fun List<Int>.head() = first()
fun List<Int>.tail() = drop(1)

fun maximum(nums: List<Int>): Int = when {
    nums.isEmpty() -> error("empty list")
    nums.size == 1 -> nums.head()
    nums.head() > maximum(nums.tail()) -> nums.head()
    else -> maximum(nums.tail())
}

fun String.head() = first()
fun String.tail() = drop(1)

fun reverse(input: String): String = when {
    input.length == 1 -> input
    else -> reverse(input.tail()) + input.head()
}

fun take(n: Int, list: List<Int>): List<Int> = when {
    list.isEmpty() -> error("empty list")
    n == 0 -> listOf()
    else -> listOf(list.head()) + take(n - 1, list.tail())
}

operator fun <T> Sequence<T>.plus(other: () -> Sequence<T>) = object : Sequence<T> {
    private val thisIterator: Iterator<T> by lazy { this@plus.iterator() }
    private val otherIterator: Iterator<T> by lazy { other().iterator() }
    override fun iterator() = object : Iterator<T> {
        override fun next(): T = if (thisIterator.hasNext()) {
            thisIterator.next()
        } else {
            otherIterator.next()
        }

        override fun hasNext(): Boolean = thisIterator.hasNext() || otherIterator.hasNext()
    }
}

fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> = when {
    n <= 0 -> listOf()
    !sequence.iterator().hasNext() -> listOf()
    else -> listOf(sequence.iterator().next()) + takeSequence(n - 1, sequence)
}

fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }

fun zip(list1: List<Int>, list2: List<Int>): List<Pair<Int, Int>> = when {
    list1.isEmpty() || list2.isEmpty() -> listOf()
    else -> listOf(Pair(list1.head(), list2.head())) + zip(list1.tail(), list2.tail())
}

fun fiboRecursion(n: Int): Int {
    println("fiboRecursion($n)")
    return when (n) {
        0 -> 0
        1 -> 1
        else -> fiboRecursion(n - 1) + fiboRecursion(n - 2)
    }
}

var memo = Array(100, { -1 })
fun fiboMemoization(n: Int): Int = when {
    n == 0 -> 0
    n == 1 -> 1
    memo[n] != -1 -> memo[n]
    else -> {
        println("fiboMemoization($n")
        memo[n] = fiboMemoization(n - 2) + fiboMemoization(n - 1)
        memo[n]
    }
}

fun fiboFP(n: Int): Int = fiboFP(n, 0, 1)

fun fiboFP(n: Int, first: Int, second: Int): Int = when (n) {
    0 -> first
    1 -> second
    else -> {
        println("fiboFP($n)")
        fiboFP(n - 1, second, first + second)
    }
}

tailrec fun findFixPoint(x: Double = 1.0): Double =
    if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))

fun maximum2(items: List<Int>): Int = when {
    items.isEmpty() -> error("empty list")
    items.size == 1 -> {
        println("head : ${items[0]}, maxVal : ${items[0]}")
        items[0]
    }
    else -> {
        val head = items.head()
        val tail = items.tail()
        val maxValue = maximum2(tail)
        println("head : $head, maxVal : $maxValue")
        if (head > maxValue) head else maxValue
    }
}

tailrec fun tailrecMaximum(items: List<Int>, acc: Int = Int.MIN_VALUE): Int = when {
    items.isEmpty() -> error("empty list")
    items.size == 1 -> {
        println("head : ${items[0]}, maxValue : $acc")
        if (items[0] > acc) items[0] else acc
    }
    else -> {
        val head = items.head()
        val maxValue = if (head > acc) head else acc
        println("head : $head, maxVal : $maxValue")
        tailrecMaximum(items.tail(), maxValue)
    }
}

tailrec fun reverse(str: String, acc: String = ""): String = when {
    str.isEmpty() -> acc
    else -> {
        reverse(str.tail(), str.head() + acc)
    }
}

tailrec fun take(n: Int, list: List<Int>, acc: List<Int> = listOf()): List<Int> = when {
    n <= 0 -> acc
    list.isEmpty() -> acc
    else -> {
        val takeList = acc + listOf(list.head())
        take(n - 1, list.tail(), takeList)
    }
}

fun even(n: Int): Boolean = when (n) {
    0 -> true
    else -> odd(n - 1)
}

fun odd(n: Int): Boolean = when (n) {
    0 -> false
    else -> even(n - 1)
}

sealed class Bounce<A>
data class Done<A>(val result: A) : Bounce<A>()
data class More<A>(val thunk: () -> Bounce<A>) : Bounce<A>()

tailrec fun <A> trampoline(bounce: Bounce<A>): A = when (bounce) {
    is Done -> bounce.result
    is More -> trampoline(bounce.thunk())
}

fun oddMT(n: Int): Bounce<Boolean> = when (n) {
    0 -> Done(false)
    else -> {
        More {
            println("even In $n")
            val evenResult = evenMT(n - 1)
            println("even out")
            evenResult
        }
    }
}

fun evenMT(n: Int): Bounce<Boolean> = when (n) {
    0 -> Done(true)
    else -> {
        More {
            println("odd in $n")
            val oddResult = oddMT(n - 1)
            println("odd out")
            oddResult
        }
    }
}

fun <T> Set<T>.head() = first()
fun <T> Set<T>.tail() = drop(1).toSet()

fun <T> powerset(s: Set<T>): Set<Set<T>> = when {
    s.isEmpty() -> setOf(setOf())
    else -> {
        val head = s.head()
        val restSet = powerset(s.tail())
        restSet + restSet.map { setOf(head) + it }.toSet()
    }
}

tailrec fun <T> powerset(s: Set<T>, acc: Set<Set<T>>): Set<Set<T>> = when {
    s.isEmpty() -> acc
    else -> powerset(s.tail(), acc + acc.map { it + s.head() })
}

fun main(args: Array<String>) {
//    println(maximum(listOf(1, 3, 2, 8, 4)))
//    println(reverse("abcd"))
//    println(take(3, listOf(1, 2, 3, 4, 5)))
//    println(takeSequence(5, repeat(3)))
//    println(zip(listOf(1, 3, 5), listOf(2, 4)))
//    fiboRecursion(6)
//    fiboMemoization(6)
//    println(fiboFP(4))
//    println(findFixPoint())
//    maximum2(listOf(1, 3, 2, 9, 11, 20, 8, 7))
//    println(tailrecMaximum(listOf(1, 3, 2, 9, 11, 20, 8, 21)))
//    println(reverse("abcd", ""))
//    println(take(3, listOf(1, 2, 3, 4, 5), listOf()))
//    println(trampoline(evenMT(10)))
//    println(odd(9999))
//    println(even(9999))
    println(powerset(setOf(1, 2, 3, 4, 5), setOf(setOf())))
}

class Ch3SourceCode
