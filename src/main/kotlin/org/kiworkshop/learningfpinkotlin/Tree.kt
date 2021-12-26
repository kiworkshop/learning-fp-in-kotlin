package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.Tree.ParentNode

sealed class Tree<out T> {
    object EmptyTree : Tree<Nothing>()
    data class ParentNode<out T>(val value: T, val left: Tree<T>, val right: Tree<T>) : Tree<T>()
    companion object {
        fun <T> leafNode(value: T) = ParentNode(value, EmptyTree, EmptyTree)
    }
}

fun Tree<Int>.insert(elem: Int): Tree<Int> = when (this) {
    EmptyTree -> Tree.leafNode(elem)
    is ParentNode ->
        if (elem < value) ParentNode(value, left.insert(elem), right)
        else ParentNode(value, left, right.insert(elem))
}
