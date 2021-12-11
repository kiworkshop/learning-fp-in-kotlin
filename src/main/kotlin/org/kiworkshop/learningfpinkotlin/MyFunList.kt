package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.MyFunList.Cons
import org.kiworkshop.learningfpinkotlin.MyFunList.Nil

sealed class MyFunList<out T> {
    object Nil : MyFunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: MyFunList<T>) : MyFunList<T>()
}

// O(1)
fun <T> MyFunList<T>.addHead(head: T): MyFunList<T> = Cons(head, this)

// O(n)
tailrec fun <T> MyFunList<T>.appendTail(value: T, acc: MyFunList<T> = Nil): MyFunList<T> = when (this) {
    Nil -> Cons(value, acc)
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
