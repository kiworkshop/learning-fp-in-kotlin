package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.MyFunList.Cons
import org.kiworkshop.learningfpinkotlin.MyFunList.Nil
import kotlin.math.max

sealed class MyFunList<out T> {
    object Nil : MyFunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: MyFunList<T>) : MyFunList<T>()
}

// O(1)
fun <T> MyFunList<T>.addHead(head: T): MyFunList<T> = Cons(head, this)

// O(n)
tailrec fun <T> MyFunList<T>.appendTail(value: T, acc: MyFunList<T> = Nil): MyFunList<T> = when (this) {
    Nil -> Cons(value, acc).reverse()
    is Cons -> tail.appendTail(value, acc.addHead(head))
}

// O(n)
tailrec fun <T> MyFunList<T>.reverse(acc: MyFunList<T> = Nil): MyFunList<T> = when (this) {
    Nil -> acc
    is Cons -> tail.reverse(acc.addHead(head))
}

// O(1)
fun <T> MyFunList<T>.getTail(): MyFunList<T> = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> tail
}

// O(1)
fun <T> MyFunList<T>.getHead(): T = when (this) {
    Nil -> throw NoSuchElementException()
    is Cons -> head
}

fun <T> myFunListOf(vararg elements: T): MyFunList<T> = elements.toMyFunList()
fun <T> emptyMyFunList(): MyFunList<T> = Nil

// copyRange 함수를 이용하면 매번 새로운 Array를 생성하면서 함수형스럽게? 작성할 수 있다. 하지만 매번 객체 생성 비용이 추가된다.
// 따라서 인덱스를 사용하는 꼬리 재귀 형태로 함수를 작성했다.
// 다만 이 방법이 성능은 좋을지 몰라도 가독성은 copyRange 함수를 이용하는 것이 더 좋기 때문에 뭐가 더 좋은 선택일지 고민된다.
tailrec fun <T> Array<out T>.toMyFunList(index: Int = this.size - 1, acc: MyFunList<T> = Nil): MyFunList<T> = when {
    index < 0 -> acc
    else -> this.toMyFunList(index - 1, acc.addHead(this[index]))
}

// O(n)
tailrec fun <T> MyFunList<T>.filter(acc: MyFunList<T> = Nil, pred: (T) -> Boolean): MyFunList<T> = when (this) {
    Nil -> acc.reverse()
    is Cons -> if (pred(head)) tail.filter(acc.addHead(head), pred) else tail.filter(acc, pred)
}

tailrec fun <T> MyFunList<T>.drop(n: Int): MyFunList<T> = when (n) {
    0 -> this
    else -> when (this) {
        Nil -> throw NoSuchElementException()
        is Cons -> tail.drop(n - 1)
    }
}

tailrec fun <T> MyFunList<T>.dropWhile(p: (T) -> Boolean): MyFunList<T> = when (this) {
    Nil -> this
    is Cons -> if (p(head)) tail.dropWhile(p) else this
}

/*
tailrec fun <T> MyFunList<T>.take(n: Int, acc: MyFunList<T> = Nil): MyFunList<T> = when (n) {
    0 -> acc.reverse()
    else -> tail.take(n - 1, acc.addHead(getHead()))
}
도 가능하다.
 */
tailrec fun <T> MyFunList<T>.take(n: Int, acc: MyFunList<T> = Nil): MyFunList<T> = when (n) {
    0 -> acc.reverse()
    else -> when (this) {
        Nil -> throw NoSuchElementException()
        is Cons -> tail.take(n - 1, acc.addHead(head))
    }
}

tailrec fun <T> MyFunList<T>.takeWhile(acc: MyFunList<T> = Nil, p: (T) -> Boolean): MyFunList<T> = when (this) {
    Nil -> acc.reverse()
    is Cons -> if (p(head)) tail.takeWhile(acc.addHead(head), p) else acc.reverse()
}

fun add3(list: MyFunList<Int>): MyFunList<Int> = when (list) {
    Nil -> Nil
    is Cons -> Cons(list.head + 3, add3(list.tail))
}

fun product3(list: MyFunList<Double>): MyFunList<Double> = when (list) {
    Nil -> Nil
    is Cons -> Cons(list.head * 3, product3(list.tail))
}

tailrec fun <T, R> MyFunList<T>.map(acc: MyFunList<R> = Nil, f: (T) -> R): MyFunList<R> = when (this) {
    Nil -> acc.reverse()
    is Cons -> tail.map(acc.addHead(f(head)), f)
}

tailrec fun <T, R> MyFunList<T>.indexedMap(
    index: Int = 0,
    acc: MyFunList<R> = Nil,
    f: (Int, T) -> R
): MyFunList<R> = when (this) {
    Nil -> acc.reverse()
    is Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, head)), f)
}

tailrec fun <T, R> MyFunList<T>.foldLeft(acc: R, f: (R, T) -> R): R = when (this) {
    Nil -> acc
    is Cons -> tail.foldLeft(f(acc, head), f)
}

fun MyFunList<Int>.sum(): Int = this.foldLeft(0) { a, b -> a + b }

fun toUpper(list: MyFunList<Char>): MyFunList<Char> = list.foldLeft(Nil) { acc: MyFunList<Char>, char ->
    acc.appendTail(char.uppercaseChar())
}

fun <T, R> MyFunList<T>.mapByFoldLef(f: (T) -> R): MyFunList<R> = this.foldLeft(Nil) { acc: MyFunList<R>, element ->
    acc.appendTail(f(element))
}

fun MyFunList<Int>.maximumByFoldLeft() = this.foldLeft(0) { acc, element ->
    max(acc, element)
}

fun <T> MyFunList<T>.filterByFoldLeft(pred: (T) -> Boolean) = this.foldLeft(Nil) { acc: MyFunList<T>, element ->
    if (pred(element)) acc.appendTail(element) else acc
}

fun <T, R> MyFunList<T>.foldRight(acc: R, f: (T, R) -> R): R = when (this) {
    Nil -> acc
    is Cons -> f(head, tail.foldRight(acc, f))
}

fun <T> MyFunList<T>.reverseByFoldRight(): MyFunList<T> = foldRight(Nil) { element, acc: MyFunList<T> ->
    acc.appendTail(element)
}

fun <T> MyFunList<T>.filterByFoldRight(f: (T) -> Boolean): MyFunList<T> = foldRight(Nil) { element, acc: MyFunList<T> ->
    if (f(element)) acc.addHead(element) else acc
}

tailrec fun <T, R> MyFunList<T>.zip(other: MyFunList<R>, acc: MyFunList<Pair<T, R>> = Nil): MyFunList<Pair<T, R>> =
    when {
        this === Nil || other === Nil -> acc.reverse()
        else -> getTail().zip(other.getTail(), acc.addHead(getHead() to other.getHead()))
    }
