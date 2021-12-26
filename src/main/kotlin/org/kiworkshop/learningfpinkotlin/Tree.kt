package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.Tree.Node

sealed class Tree<out T> {
    object EmptyTree : Tree<Nothing>()
    data class Node<out T>(val value: T, val left: Tree<T>, val right: Tree<T>) : Tree<T>()
    companion object {
        fun <T> leafNode(value: T) = Node(value, EmptyTree, EmptyTree)
    }
}

fun Tree<Int>.insert(elem: Int): Tree<Int> = when (this) {
    EmptyTree -> Tree.leafNode(elem)
    is Node ->
        if (elem < value) Node(value, left.insert(elem), right)
        else Node(value, left, right.insert(elem))
}
