package org.kiworkshop.learningfpinkotlin.chapter9

sealed class FunListFoldable<out T> : Foldable<T>

object Nil : FunListFoldable<Nothing>() {
    override fun <B> foldLeft(acc: B, f: (B, Nothing) -> B): B = acc
}

data class Cons<out T>(val head: T, val tail: FunListFoldable<T>) : FunListFoldable<T>() {
    override fun <B> foldLeft(acc: B, f: (B, T) -> B): B = tail.foldLeft(f(acc, head), f)
}

fun <T> FunListFoldable<T>.contains(value: T) = foldMap({ it == value }, AnyMonoid())
