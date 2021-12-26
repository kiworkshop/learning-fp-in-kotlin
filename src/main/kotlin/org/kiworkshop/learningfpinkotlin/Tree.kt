package org.kiworkshop.learningfpinkotlin

import org.kiworkshop.learningfpinkotlin.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.Tree.Node
import kotlin.time.ExperimentalTime

sealed class Tree<out T> {
    object EmptyTree : Tree<Nothing>() {
        override fun toString() = ""
    }

    data class Node<out T>(val value: T, val left: Tree<T>, val right: Tree<T>) : Tree<T>() {
        override fun toString(): String {
            if (left == EmptyTree) {
                return if (right == EmptyTree) {
                    "[$value]"
                } else {
                    "[$value, $right]"
                }
            }
            if (right == EmptyTree) {
                return "[$left, $value]"
            }
            return "[$left, $value, $right]"
        }
    }

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

@OptIn(ExperimentalTime::class)
fun Tree<Int>.insertTailrec(elem: Int): Tree<Int> {
    data class InsertPath(val tree: Tree<Int>, val shouldGoLeft: Boolean)

    tailrec fun Tree<Int>.makePathList(elem: Int, acc: List<InsertPath> = emptyList()): List<InsertPath> = when (this) {
        EmptyTree -> acc
        is Node ->
            if (elem < value) { // insert하려는 값이 트리의 값보다 작으므로 왼쪽 트리에 값이 들어갈 것이다.
                // 따라서 shoudGoLeft를 true로 해서 현재 트리를 넣어준다.
                // 실제로 이 Path를 가지고 트리를 재구성할 때는 변하지 않은 오른쪽 트리를 사용한다.
                left.makePathList(elem, listOf(InsertPath(this, true)) + acc)
            } else { // insert하려는 값이 트리의 값보다 크거나 같으므로 오른쪽 트리에 값이 들어갈 것이다.
                // 따라서 shoudGoLeft를 false 해서 현재 트리를 넣어준다.
                // 실제로 이 Path를 가지고 트리를 재구성할 때는 변하지 않은 왼쪽 트리를 사용한다.
                right.makePathList(elem, listOf(InsertPath(this, false)) + acc)
            }
    }

    tailrec fun makeTree(path: List<InsertPath>, tree: Tree<Int>): Tree<Int> = when {
        path.isEmpty() -> tree
        else -> {
            val pathTree = path.head().tree as Node
            if (path.head().shouldGoLeft) makeTree(
                path.tail(),
                // Path에 따르면 왼쪽 트리가 변한다고 했다. 따라서 왼쪽에는 지금까지 만든 acc인 Tree를 넣어주고
                // 오른쪽 트리는 변하지 않으므로 Path에 저장된 기존 오른쪽 트리를 넣어준다.
                Node(pathTree.value, tree, pathTree.right)
            )
            else makeTree(
                path.tail(),
                // Path에 따르면 오른쪽 트리가 변한다고 했다. 따라서 오른쪽 지금까지 만든 acc인 Tree를 넣어주고
                // 왼쪽 트리는 변하지 않으므로 Path에 저장된 기존 왼쪽 트리를 넣어준다.
                Node(pathTree.value, pathTree.left, tree)
            )
        }
    }

    return makeTree(makePathList(elem), Tree.leafNode(elem))
}

fun Tree<Int>.contains(elem: Int): Boolean = when (this) {
    EmptyTree -> false
    is Node ->
        if (elem == value) true
        else if (elem < value) left.contains(elem)
        else right.contains(elem)
}
