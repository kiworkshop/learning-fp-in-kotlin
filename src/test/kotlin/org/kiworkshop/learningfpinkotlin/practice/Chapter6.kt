package org.kiworkshop.learningfpinkotlin.practice

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows

class Chapter6 : StringSpec({
    "Example 6-1" {
        println("Refer to below class for Tree")
    }
    "Example 6-2" {
        val tree = Tree.Empty
        tree.insertTailrec(3) shouldBe Tree.Node(3)

        val left = Tree.Node<Int>(5)
        val tree1 = Tree.Node(0, left)
        val expectedTree = Tree.Node(0, Tree.Node(5, Tree.Node(4)), Tree.Empty)
        tree1.insertTailrec(4) shouldBe expectedTree
    }
    "Example 6-2 (1)" {
        val left = Tree.Node<Int>(5)
        val right = Tree.Node<Int>(7)
        val tree = Tree.Node(0, left, right)
        val left1 = Tree.Node<Int>(5, Tree.Node(3))
        val expectedTree = Tree.Node(0, left1, right)

        tree.insertTailrec(3) shouldBe expectedTree
    }
    "Example 6-3" {
        var count = 0
        fun stackOverFlowError() {
            val tree = Tree.Empty
            println("$count times to calculate")
            for (i in 1..100_000) {
                tree.insert(i)
                count++
            }
            stackOverFlowError()
        }
        assertThrows<StackOverflowError> { stackOverFlowError() }
    }
    "Example 6-4" {
        var count = 0
        fun stackOverFlowError() {
            val tree = Tree.Empty
            println("$count times to calculate")
            for (i in 1..100_000) {
                tree.insertTailrec(i)
                count++
            }
            stackOverFlowError()
        }
        assertThrows<StackOverflowError> { stackOverFlowError() }
    }
})

sealed class Tree<out T> {
    object Empty : Tree<Nothing>()
    data class Node<T>(val me: Int, val left: Tree<T> = Empty, val right: Tree<T> = Empty) : Tree<T>()

    fun compareTo(elem: Int): Int {
        return when (this) {
            is Empty -> 1
            is Node -> {
                when {
                    me > elem -> 1
                    me < elem -> -1
                    else -> 0
                }
            }
        }
    }
}

// 왼쪽 하위 노드의 값은 오른쪽 하위 노드의 값보다 항상 작아야함
// 단 값을 비교하기 위해서는 T가 항상 comparable 속성을 가지고 있어야함
fun Tree<Int>.insert(elem: Int): Tree<Int> = when (this) {
    Tree.Empty -> Tree.Node(elem)
    is Tree.Node -> {
        if (left.compareTo(elem) < 0) copy(right = right.insert(elem))
        else copy(left = left.insert(elem))
    }
}

fun Tree<Int>.insertTailrec(elem: Int): Tree<Int> {
    tailrec fun Tree<Int>.insert(elem: Int, acc: Tree<Int> = Tree.Empty): Tree<Int> = when (this) {
        Tree.Empty -> acc
        is Tree.Node -> {
            if (left.compareTo(elem) < 0) copy(right = right.insert(elem, acc))
            else copy(left = left.insert(elem, acc))
        }
    }
    return insert(elem, Tree.Node(elem))
}
