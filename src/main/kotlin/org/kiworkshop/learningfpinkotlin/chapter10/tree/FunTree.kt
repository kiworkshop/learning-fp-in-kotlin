package org.kiworkshop.learningfpinkotlin.chapter10.tree

import org.kiworkshop.learningfpinkotlin.chapter10.Monad

sealed class FunTree<out T> : Monad<T> {
    companion object {
        fun <V> pure(value: V): FunTree<V> = Node(0).pure(value)
    }

    object EmptyTree : FunTree<Nothing>() {
        override fun toString(): String = "EmptyTree"
    }

    data class Node<out T>(val value: T, val leftTree: FunTree<T> = EmptyTree, val rightTree: FunTree<T> = EmptyTree) :
        FunTree<T>()

    override fun <B> fmap(f: (T) -> B): FunTree<B> = flatMap { a -> pure(f(a)) }

    override infix fun <V> pure(value: V): FunTree<V> = Node(value)

    override fun <B> flatMap(f: (T) -> Monad<B>): FunTree<B> = when (this) {
        EmptyTree -> EmptyTree
        is Node -> f(value) as FunTree<B> mappend leftTree.flatMap(f) mappend rightTree.flatMap(f)
    }

    override fun <B> leadTo(m: Monad<B>): FunTree<B> = flatMap { m }
}

infix fun <T, R> FunTree<(T) -> R>.apply(f: FunTree<T>): FunTree<R> = when (this) {
    FunTree.EmptyTree -> FunTree.EmptyTree
    is FunTree.Node -> f.fmap(value) mappend (leftTree apply f) mappend (rightTree apply f)
}

fun <T> FunTree<T>.mempty() = FunTree.EmptyTree

infix fun <T> FunTree<T>.mappend(other: FunTree<T>): FunTree<T> = when (this) {
    FunTree.EmptyTree -> other
    is FunTree.Node -> when (other) {
        FunTree.EmptyTree -> this
        is FunTree.Node -> when (leftTree) {
            FunTree.EmptyTree -> FunTree.Node(value, other, rightTree)
            is FunTree.Node -> when (rightTree) {
                FunTree.EmptyTree -> FunTree.Node(value, leftTree, other)
                is FunTree.Node -> FunTree.Node(value, leftTree mappend other, rightTree)
            }
        }
    }
}
