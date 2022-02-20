package org.kiworkshop.learningfpinkotlin.c9

import org.kiworkshop.learningfpinkotlin.c5.funListOf

interface Foldable<out A> {

    fun <B> foldLeft(acc: B, f: (B, A) -> B): B

    fun <B> foldMap(f: (A) -> B, m: Monoid<B>): B = foldLeft(m.mempty()) { b: B, a: A ->
        m.mappend(b, f(a))
    }
}

sealed class BinaryTree<out A> : Foldable<A> {

    override fun <B> foldLeft(acc: B, f: (B, A) -> B): B = when (this) {
        EmptyTree -> acc
        is Node -> {
            val leftAcc = left.foldLeft(acc, f)
            val rootAcc = f(leftAcc, value)
            right.foldLeft(rootAcc, f)
        }
    }
}

data class Node<A>(
    val value: A,
    val left: BinaryTree<A> = EmptyTree,
    val right: BinaryTree<A> = EmptyTree,
) : BinaryTree<A>()

object EmptyTree : BinaryTree<Nothing>()

sealed interface FunList<out T> : Foldable<T>

class Cons<out T>(val head: T, val tail: FunList<T>) : FunList<T> {
    override fun <B> foldLeft(acc: B, f: (B, T) -> B): B = tail.foldLeft(f(acc, head), f)
}

object Nil : FunList<Nothing> {
    override fun <B> foldLeft(acc: B, f: (B, Nothing) -> B): B = acc
}

fun <T> FunList<T>.contains(t: T): Boolean = foldMap({ it == t }, AnyMonoid())

sealed interface Tree<out T> : Foldable<T> {

    data class Node<out T>(val value: T, val forest: FunList<Node<T>> = Nil) : Tree<T> {

        override fun <B> foldLeft(acc: B, f: (B, T) -> B): B = when (forest) {
            Nil -> f(acc, value)
            is Cons -> f(loop(forest, acc, f), value)
        }

        private tailrec fun <B> loop(list: FunList<Node<T>>, acc: B, f: (B, T) -> B): B = when (list) {
            Nil -> acc
            is Cons -> loop(list.tail, list.head.foldLeft(acc, f), f)
        }
    }
}

fun <T> Tree<T>.contains(t: T): Boolean = foldMap({ it == t }, AnyMonoid())

fun <T> Tree<T>.toFunList(): org.kiworkshop.learningfpinkotlin.c5.FunList<T> = foldMap({ funListOf(it) }, ListMonoid())
