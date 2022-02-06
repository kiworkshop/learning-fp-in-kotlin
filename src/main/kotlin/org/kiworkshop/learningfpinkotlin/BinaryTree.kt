package org.kiworkshop.learningfpinkotlin

import kotlin.Nothing

sealed class BinaryTree<out T> {
    abstract override fun toString(): String
}

object EmptyBNode : BinaryTree<Nothing>() {
    override fun toString() = "E"
}

data class BNode<out T>(
    val value: T,
    val leftTree: BinaryTree<T> = EmptyBNode,
    val rightTree: BinaryTree<T> = EmptyBNode
) : BinaryTree<T>() {
    override fun toString(): String = "(N $value $leftTree $rightTree)"
}

fun BinaryTree<Int>.insert(element: Int): BinaryTree<Int> = when (this) {
    is EmptyBNode -> BNode(element)
    is BNode -> when {
        this.value > element -> BNode(this.value, this.leftTree.insert(element), this.rightTree)
        this.value < element -> BNode(this.value, this.leftTree, this.rightTree.insert(element))
        else -> this
    }
}
