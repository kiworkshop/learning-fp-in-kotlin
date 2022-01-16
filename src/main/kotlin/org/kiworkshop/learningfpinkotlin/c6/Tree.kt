package org.kiworkshop.learningfpinkotlin.c6

import org.kiworkshop.learningfpinkotlin.c5.FunStream
import org.kiworkshop.learningfpinkotlin.c5.addHead
import org.kiworkshop.learningfpinkotlin.c5.foldLeft
import org.kiworkshop.learningfpinkotlin.c5.funStreamOf

sealed interface Tree<out T>

object EmptyTree : Tree<Nothing>

data class Node<T>(val value: T, val left: Tree<T>, val right: Tree<T>) : Tree<T> {

    companion object {
        fun <T> leaf(value: T) = Node(value, EmptyTree, EmptyTree)
    }
}

class PartialNode<T>(private val partial: Node<T>, private val missing: Direction) {

    init {
        when (missing) {
            Direction.LEFT -> require(partial.left == EmptyTree)
            Direction.RIGHT -> require(partial.right == EmptyTree)
        }
    }

    fun fill(node: Node<T>): Node<T> = when (missing) {
        Direction.LEFT -> partial.copy(left = node)
        Direction.RIGHT -> partial.copy(right = node)
    }

    enum class Direction { LEFT, RIGHT }

    companion object {
        fun <T> rightOf(node: Node<T>) = PartialNode(node.copy(left = EmptyTree), missing = Direction.LEFT)

        fun <T> leftOf(node: Node<T>) = PartialNode(node.copy(right = EmptyTree), missing = Direction.RIGHT)
    }
}

fun <T> initializeTree(): Tree<T> = EmptyTree

fun <T : Comparable<T>> Tree<T>.insert(elem: T): Tree<T> = when (this) {
    EmptyTree -> Node.leaf(elem)
    is Node -> when (elem.isOnLeft(this)) {
        true -> Node(value, left.insert(elem), right)
        false -> Node(value, left, right.insert(elem))
    }
}

tailrec fun <T : Comparable<T>> Tree<T>.insertTailrec(
    elem: T,
    partialNodes: FunStream<PartialNode<T>> = funStreamOf(),
): Tree<T> = when (this) {
    EmptyTree -> restore(partialNodes, elem)
    is Node -> when (elem.isOnLeft(this)) {
        true -> left.insertTailrec(elem, partialNodes.addHead(PartialNode.rightOf(this)))
        false -> right.insertTailrec(elem, partialNodes.addHead(PartialNode.leftOf(this)))
    }
}

private fun <T : Comparable<T>> restore(partialNodes: FunStream<PartialNode<T>>, elem: T): Tree<T> {
    return partialNodes.foldLeft(Node.leaf(elem)) { restored, partialNode ->
        partialNode.fill(restored)
    }
}

tailrec fun <T : Comparable<T>> Tree<T>.contains(elem: T): Boolean = when (this) {
    EmptyTree -> false
    is Node -> when {
        elem == value -> true
        elem.isOnLeft(this) -> left.contains(elem)
        else -> right.contains(elem)
    }
}

private fun <T : Comparable<T>> T.isOnLeft(tree: Node<T>) = (tree.value > this)

fun <T> Tree<T>.toTreeString(): String = when (this) {
    EmptyTree -> ""
    is Node -> "${left.toTreeString()} $value ${right.toTreeString()}"
}
