package org.kiworkshop.learningfpinkotlin

import kotlin.Nothing

sealed class FunctorTree<out A> : Functor<A> {
    abstract override fun toString(): String
    abstract override fun <B> fmap(f: (A) -> B): FunctorTree<B>
}

object EmptyTree : FunctorTree<Nothing>() {
    override fun toString(): String = "E"

    override fun <B> fmap(f: (Nothing) -> B): FunctorTree<B> = EmptyTree
}

data class Node<out A>(val value: A, val leftTree: FunctorTree<A>, val rightTree: FunctorTree<A>) : FunctorTree<A>() {
    override fun toString(): String = "(N $value $leftTree $rightTree)"

    override fun <B> fmap(f: (A) -> B): FunctorTree<B> = Node(f(value), leftTree.fmap(f), rightTree.fmap(f))
}

fun <T> treeOf(value: T, leftTree: FunctorTree<T> = EmptyTree, rightTree: FunctorTree<T> = EmptyTree): FunctorTree<T> =
    Node(value, leftTree, rightTree)
