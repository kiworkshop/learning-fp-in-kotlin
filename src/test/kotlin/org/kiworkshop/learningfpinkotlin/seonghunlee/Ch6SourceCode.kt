package org.kiworkshop.learningfpinkotlin.seonghunlee

import org.kiworkshop.learningfpinkotlin.seonghunlee.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.seonghunlee.Tree.Node

sealed class Tree<out T> {
    object EmptyTree : Tree<Nothing>()
    data class Node<out T>(val value: Int, val left: Tree<T>, val right: Tree<T>) : Tree<T>()
}

fun Tree<Int>.insert(elem: Int): Tree<Int> = when (this) {
    EmptyTree -> Node(elem, EmptyTree, EmptyTree)
    is Node -> {
        if (value > elem) Node(value, left.insert(elem), right) else Node(value, left, right.insert(elem))
    }
}

// data class Path(val list: List<Pair<Node<Int>, Boolean>>)

fun path(tree: Tree<Int>, v: Int): List<Pair<Node<Int>, Boolean>> {
    tailrec fun loop(t: Tree<Int>, p: List<Pair<Node<Int>, Boolean>>): List<Pair<Node<Int>, Boolean>> = when (t) {
        EmptyTree -> p
        is Node<Int> -> {
            if (t.value > v) loop(t.left, listOf(Pair(t, false)) + p)
            else loop(t.right, listOf(Pair(t, true)) + p)
        }
    }
    return loop(tree, listOf())
}

fun rebuild(path: List<Pair<Node<Int>, Boolean>>, v: Int): Tree<Int> {
    tailrec fun loop(path: List<Pair<Node<Int>, Boolean>>, subTree: Tree<Int>): Tree<Int> = when {
        path.isEmpty() -> subTree
        !path.first().second -> loop(path.drop(1), Node(path.first().first.value, subTree, path.first().first.right))
        else -> loop(path.drop(1), Node(path.first().first.value, path.first().first.left, subTree))
    }
    return loop(path, Node(v, EmptyTree, EmptyTree))
}
//
// fun Tree<Int>.insertTailrec(elem: Int): Tree<Int> {
//    tailrec fun insertTailrec(elem: Int, acc: Tree<Int>): Tree<Int> = when (this) {
//        EmptyTree -> acc
//    }
// }

fun Tree<Int>.contains(elem: Int): Boolean = when (this) {
    EmptyTree -> false
    is Node -> when {
        elem == value -> true
        elem > value -> right.contains(elem)
        else -> left.contains(elem)
    }
}

fun main() {
//    println(path(Node(5, Node(1, Node(0, EmptyTree, EmptyTree), Node(3, EmptyTree, EmptyTree)), EmptyTree), 2))
    println(rebuild(path(Node(5, Node(3, EmptyTree, EmptyTree), EmptyTree), 4), 4))
}

class Ch6SourceCode
