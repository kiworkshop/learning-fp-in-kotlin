package org.kiworkshop.learningfpinkotlin


sealed class BinaryTree<out T> {
    abstract override fun toString(): String
}

object EmptyNode : BinaryTree<Nothing>() {
    override fun toString() = "E"
}

data class Node<out T>(
    val value: T,
    val leftTree: BinaryTree<T> = EmptyNode,
    val rightTree: BinaryTree<T> = EmptyNode
) : BinaryTree<T>() {
    override fun toString(): String = "(N $value $leftTree $rightTree)"
}

fun BinaryTree<Int>.insert(element: Int): BinaryTree<Int> = when (this) {
    is EmptyNode -> Node(element)
    is Node -> when {
        this.value > element -> Node(this.value, this.leftTree.insert(element), this.rightTree)
        this.value < element -> Node(this.value, this.leftTree, this.rightTree.insert(element))
        else -> this
    }
}