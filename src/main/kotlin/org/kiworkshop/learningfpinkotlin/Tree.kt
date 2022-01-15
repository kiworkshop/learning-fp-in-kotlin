package org.kiworkshop.learningfpinkotlin


sealed class Tree<out T>

object EmptyTree : Tree<Nothing>()

data class Node<out T>(val value: T, val leftTree: Tree<T> = EmptyTree, val rightTree: Tree<T> = EmptyTree)