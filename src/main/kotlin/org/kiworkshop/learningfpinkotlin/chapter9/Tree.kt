package org.kiworkshop.learningfpinkotlin.chapter9

import org.kiworkshop.learningfpinkotlin.FunList

sealed interface Tree<out T> : Foldable<T> {

    data class Node<A>(val value: A, val forest: FunList<Node<A>> = FunList.Nil) : Tree<A> {
        override fun <B> foldLeft(acc: B, f: (B, A) -> B): B = when (forest) {
            is FunList.Nil -> f(acc, value)
            is FunList.Cons -> f(processList(forest, f, acc), value)
        }

        private fun <A, B> processList(list: FunList<Node<A>>, f: (B, A) -> B, acc: B): B = when (list) {
            is FunList.Nil -> acc
            is FunList.Cons -> processList(list.tail, f, list.head.foldLeft(acc, f))
        }
    }
}

fun <T> Tree<T>.contains(value: T) = foldMap({ it == value }, AnyMonoid())
