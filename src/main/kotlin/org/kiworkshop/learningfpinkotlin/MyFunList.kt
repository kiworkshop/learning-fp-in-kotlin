package org.kiworkshop.learningfpinkotlin

sealed class MyFunList<out T> {
    object Nil : MyFunList<Nothing>()
    data class Cons<out T>(val head: T, val tail: MyFunList<T>) : MyFunList<T>()
}
