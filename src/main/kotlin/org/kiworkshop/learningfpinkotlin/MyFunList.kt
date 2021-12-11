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
