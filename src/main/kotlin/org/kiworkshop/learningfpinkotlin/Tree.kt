package org.kiworkshop.learningfpinkotlin

sealed class Tree<out T>{
    object EmptyTree : Tree<Nothing>()
    data class ParentNode<out T>(val value: T, val left: Tree<T>, val right: Tree<T>): Tree<T>()
}

