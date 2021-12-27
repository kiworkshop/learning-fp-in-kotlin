package org.kiworkshop.learningfpinkotlin.practice

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter6 : StringSpec({
    "Example 6-1" {
        println("Refer to below class for Tree")
    }
    "Example 6-2" {
        val tree = Tree.Empty
        tree.insert(3) shouldBe Tree.Node(3)

        val left = Tree.Node<Int>(5)
        val tree1 = Tree.Node(0, left)
        val expectedTree = Tree.Node(0, Tree.Node(5, Tree.Node(4)), Tree.Empty)
        tree1.insert(4) shouldBe expectedTree
    }
    "Example 6-2 (1)" {
        val left = Tree.Node<Int>(5)
        val right = Tree.Node<Int>(7)
        val tree = Tree.Node(0, left, right)
        val left1 = Tree.Node<Int>(5, Tree.Node(3))
        val expectedTree = Tree.Node(0, left1, right)

        tree.insert(3) shouldBe expectedTree
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
    is Tree.Empty -> Tree.Node(elem)
    is Tree.Node -> {
        if (right == Tree.Empty && left.compareTo(elem) < 1) copy(right = right.insert(elem))
        else copy(left = left.insert(elem))
    }
}
