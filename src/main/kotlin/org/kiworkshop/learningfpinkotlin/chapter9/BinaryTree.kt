package org.kiworkshop.learningfpinkotlin.chapter9

sealed class BinaryTree<out A> : Foldable<A> {
    override fun <B> foldLeft(acc: B, f: (B, A) -> B): B = when (this) {
        is EmptyTree -> acc
        is Node -> {
            val leftAcc = leftTree.foldLeft(acc, f)
            val rootAcc = f(leftAcc, value)
            rightTree.foldLeft(rootAcc, f)
        }
    }
}

data class Node<A>(val value: A, val leftTree: BinaryTree<A> = EmptyTree, val rightTree: BinaryTree<A> = EmptyTree) :
    BinaryTree<A>()

object EmptyTree : BinaryTree<Nothing>()

fun <A> BinaryTree<A>.contains(value: A) = foldMap({ it == value }, AnyMonoid())
