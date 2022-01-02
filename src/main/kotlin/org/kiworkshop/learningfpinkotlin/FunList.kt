package org.kiworkshop.learningfpinkotlin

// Code 5-1
sealed class FunList<out T> {
    object Nil : FunList<kotlin.Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}

// Code 5-3
fun <T> FunList<T>.addHead(head: T): FunList<T> = FunList.Cons(head, this)

// Code 5-4
//fun <T> FunList<T>.appendTail(value: T): FunList<T> = when (this) {
//    FunList.Nil -> FunList.Cons(value, FunList.Nil)
//    is FunList.Cons -> FunList.Cons(head, tail.appendTail(value))
//}

// Code 5-6
tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.reverse(acc.addHead(head))
}

// Code 5-5
tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> FunList.Cons(value, acc).reverse()
    is FunList.Cons -> tail.appendTail(value, acc.addHead(head))
}

// Code 5-7
fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> tail
}

// Exercise 5-3
fun <T> FunList<T>.getHead(): T = when (this) {
    FunList.Nil -> throw NoSuchElementException()
    is FunList.Cons -> head
}

// Code 5-10
tailrec fun <T> FunList<T>.filter(acc: FunList<T> = FunList.Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> if (p(head)) {
        tail.filter(acc.addHead(head), p)
    } else {
        tail.filter(acc, p)
    }
}

// Exercise 5-4
tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (this) {
    FunList.Nil -> this
    is FunList.Cons -> when {
        n < 0 -> throw IllegalArgumentException()
        n == 0 -> this
        else -> tail.drop(n - 1)
    }
}

// Exercise 5-5
tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> this
    is FunList.Cons -> when {
        !p(head) -> this
        else -> tail.dropWhile(p)
    }
}

// Exercise 5-6
tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = FunList.Nil): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> when (n) {
        0 -> acc.reverse()
        else -> tail.take(n - 1, acc.addHead(head))
    }
}

// Exercise 5-7
tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = FunList.Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> when {
        !p(head) -> acc.reverse()
        else -> tail.takeWhile(acc.addHead(head), p)
    }
}

// Code 5-15
tailrec fun <T, R> FunList<T>.map(acc: FunList<R> = FunList.Nil, f: (T) -> R): FunList<R> = when (this) {
    FunList.Nil -> acc.reverse()
    is FunList.Cons -> tail.map(acc.addHead(f(head)), f)
}

// Code 5-17
fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

private fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> FunList.Nil
    else -> FunList.Cons(this[0], this.copyOfRange(1, this.size).toFunList())
}

// Exercise 5-8
tailrec fun <T, R> FunList<T>.indexedMap(index: Int = 0, acc: FunList<R> = FunList.Nil, f: (Int, T) -> R): FunList<R> =
    when (this) {
        FunList.Nil -> acc.reverse()
        is FunList.Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, head)), f)
    }

// Code 5-18
//fun sum(list: FunList<Int>): Int = when (list) {
//    FunList.Nil -> 0
//    is FunList.Cons -> list.head + sum(list.tail)
//}

// Code 5-19
tailrec fun <T, R> FunList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> tail.foldLeft(f(acc, head), f)
}

// Code 5-20
fun sumByFoldLeft(list: FunList<Int>): Int = list.foldLeft(0) { acc, x -> acc + x }

// Code 5-21
fun FunList<Int>.sum(): Int = foldLeft(0) { acc, x -> acc + x }

// Exercise 5-9
fun FunList<Int>.maximumByFoldLeft(): Int = this.foldLeft(getHead()) { acc, x ->
    if (acc < x) x else acc
}

// Exercise 5-10
fun <T> FunList<T>.filterByFoldLeft(p: (T) -> Boolean): FunList<T> =
    this.foldLeft(FunList.Nil) { acc: FunList<T>, x ->
        if (p(x)) acc.appendTail(x) else acc
    }

// Code 5-25
fun <T, R> FunList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    FunList.Nil -> acc
    is FunList.Cons -> f(head, tail.foldRight(acc, f))
}

// Exercise 5-11
fun <T> FunList<T>.reverseByFoldRight(): FunList<T> = this.foldRight(FunList.Nil) { x, acc: FunList<T> ->
    acc.appendTail(x)
}

// Exercise 5-12
fun <T> FunList<T>.filterByFoldRight(p: (T) -> Boolean): FunList<T> =
    this.foldRight(FunList.Nil) { x, acc: FunList<T> ->
        if (p(x)) acc.addHead(x) else acc
    }

// Exercise 5-13
tailrec fun <T, R> FunList<T>.zip(other: FunList<R>, acc: FunList<Pair<T, R>> = FunList.Nil): FunList<Pair<T, R>> =
    when {
        this === FunList.Nil || other === FunList.Nil -> acc.reverse()
        else -> this.getTail().zip(other.getTail(), acc.addHead(Pair(this.getHead(), other.getHead())))
    }

// Code 5-30
tailrec fun <T1, T2, R> FunList<T1>.zipWith(
    f: (T1, T2) -> R,
    list: FunList<T2>,
    acc: FunList<R> = FunList.Nil
): FunList<R> =
    when {
        this === FunList.Nil || list === FunList.Nil -> acc.reverse()
        else -> getTail().zipWith(f, list.getTail(), acc.addHead(f(getHead(), list.getHead())))
    }

// Exercise 5-14
fun <T, R> FunList<T>.associate(f: (T) -> Pair<T, R>): Map<T, R> = when {
    this === FunList.Nil -> mapOf()
    else -> mapOf(f(getHead())) + getTail().associate(f)
}

// Exercise 5-15
fun <T, K> FunList<T>.groupBy(f: (T) -> K): Map<K, FunList<T>> = when {
    this === FunList.Nil -> mapOf()
    else -> mapOf(f(getHead()) to funListOf(getHead())) + getTail().groupBy(f)
}