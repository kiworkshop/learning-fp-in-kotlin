package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.FunList.Cons
import org.kiworkshop.learningfpinkotlin.FunList.Nil
import kotlin.math.max

sealed class FunList<out T> {
    object Nil : FunList<kotlin.Nothing>()
    data class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T>()
}

fun <T> funListOf(vararg elements: T): FunList<T> = elements.toFunList()

fun <T> Array<out T>.toFunList(): FunList<T> = when {
    this.isEmpty() -> Nil
    else -> Cons(this[0], this.copyOfRange(1, this.size).toFunList())
}

fun <T> FunList<T>.addHead(head: T): FunList<T> = Cons(head, this)

tailrec fun <T> FunList<T>.appendTail(value: T, acc: FunList<T> = Nil): FunList<T> = when (this) {
    Nil -> Cons(value, acc).reverse()
    is Cons -> tail.appendTail(value, acc.addHead(head))
    // 매번 새로운 list를 생성하지 않으면 immutable하지 않게 된다.
    // 마지막 element 뒤에 Cons를 하나 더 붙이면 기존의 list에도 변화가 있는 것이니,
    // immutable하기 위해서는 addHead와 appendTail중 하나는 O(n)이 되어야 한다.
}

tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = Nil): FunList<T> = when (this) {
    Nil -> acc
    is Cons -> tail.reverse(acc.addHead(head))
}

fun <T> FunList<T>.getTail(): FunList<T> = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> tail
}

fun <T> FunList<T>.getHead(): T = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> head
}

tailrec fun <T> FunList<T>.filter(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    Nil -> acc.reverse()
    is Cons -> if (p(head))
        tail.filter(acc.addHead(head), p)
    else
        tail.filter(acc, p)
}

tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (this) {
    Nil -> this
    is Cons ->
        if (n < 1)
            this
        else
            this.tail.drop(n - 1)
}

tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
    Nil -> this
    is Cons -> if (!p(this.head))
        this
    else
        this.tail.dropWhile(p)
}

tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = Nil): FunList<T> = when (this) {
    Nil -> this
    is Cons -> if (n < 1) acc.reverse() else this.tail.take(n - 1, acc.addHead(this.head))
}

tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
    Nil -> this
    is Cons -> if (!p(this.head))
        acc.reverse()
    else
        this.tail.takeWhile(acc.addHead(head), p)
}

tailrec fun <T, R> FunList<T>.map(acc: FunList<R> = Nil, f: (T) -> R): FunList<R> = when (this) {
    Nil -> acc.reverse()
    is Cons -> tail.map(acc.addHead(f(head)), f)
}

tailrec fun <T, R> FunList<T>.indexedMap(index: Int = 0, acc: FunList<R> = Nil, f: (Int, T) -> R): FunList<R> =
    when (this) {
        Nil -> acc.reverse()
        is Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, this.head)), f)
    }

tailrec fun <T, R> FunList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    Nil -> acc
    is Cons -> tail.foldLeft(f(acc, head), f)
}

// precondition: 리스트의 모든 값은 0보다 크고, 리스트의 크기는 1보다 크다.
fun FunList<Int>.maximumByFoldLeft(): Int = foldLeft(0) { acc, it -> max(acc, it) }

fun <T> FunList<T>.filterByFoldLeft(p: (T) -> Boolean): FunList<T> =
    foldLeft(Nil as FunList<T>) { acc, it -> if (p(it)) acc.addHead(it) else acc }.reverse()

fun <T, R> FunList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    Nil -> acc
    is Cons -> f(head, tail.foldRight(acc, f))
}

// appendTail을 사용했으므로 시간복잡도는 O(n^2)다. 굳이 이렇게..?
fun <T> FunList<T>.reverseByFoldRight(): FunList<T> = foldRight(Nil as FunList<T>) { it, acc -> acc.appendTail(it) }

fun <T> FunList<T>.filterByFoldRight(p: (T) -> Boolean): FunList<T> =
    foldRight(Nil as FunList<T>) { it, acc -> if (p(it)) acc.addHead(it) else acc }

fun <T, R> FunList<T>.zip(other: FunList<R>, acc: FunList<Pair<T, R>> = Nil): FunList<Pair<T, R>> =
    zipWith(::Pair, other, acc)

tailrec fun <T1, T2, R> FunList<T1>.zipWith(
    f: (T1, T2) -> R,
    list: FunList<T2>,
    acc: FunList<R> = Nil
): FunList<R> = when {
    this === Nil || list === Nil -> acc.reverse()
    else -> getTail().zipWith(f, list.getTail(), acc.addHead(f(this.getHead(), list.getHead())))
}

// mapOf() 대신 mutableMap()을 사용하면 하나의 맵 객체를 재활용할 수 있다. 마지막에 `.toMap()`을 호출해서 immutable Map 으로 리턴하면 됨.
fun <T, R> FunList<T>.associate(f: (T) -> Pair<T, R>): Map<T, R> = this.map(Nil, f).foldLeft(mapOf()) { acc, curr ->
    acc + curr
}

fun <T, K> FunList<T>.groupBy(f: (T) -> K): Map<K, FunList<T>> =
    this.map(Nil) { Pair(f(it), it) }
        .foldLeft<Pair<K, T>, MutableMap<K, FunList<T>>>(mutableMapOf()) { acc, (key, value) ->
            acc[key] = (acc[key] ?: funListOf()).addHead(value)
            acc
        }.map { (key, value) -> Pair(key, value.reverse()) }
        .toMap()

private const val DELIMITER = ", "
tailrec fun <T> FunList<T>.toString(acc: String): String = when (this) {
    Nil -> "[${acc.drop(DELIMITER.length)}]"
    is Cons -> this.tail.toString("$acc$DELIMITER${this.head}")
}

fun <T> FunList<T>.toStringByFoldLeft(): String =
    "[${foldLeft("") { acc, curr -> "$acc$DELIMITER$curr" }.drop(DELIMITER.length)}]"

infix fun <T> FunList<T>.concat(other: FunList<T>): FunList<T> = when (this) {
    is Nil -> other
    is Cons -> Cons(head, tail concat other)
}
