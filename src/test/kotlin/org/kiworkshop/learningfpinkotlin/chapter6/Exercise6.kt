package org.kiworkshop.learningfpinkotlin.chapter6

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter6.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.chapter6.Tree.Node

class Exercise6 : AnnotationSpec() {

    @Test
    fun `test6-2 이진트리 insert구현`() {
        val tree1 = Tree.EmptyTree.insert(5)
        tree1 shouldBe Node(5, EmptyTree, EmptyTree)
        val tree2 = tree1.insert(3)
        tree2 shouldBe
            Node(
                5,
                Node(3, EmptyTree, EmptyTree),
                EmptyTree
            )
        val tree3 = tree2.insert(10)
        tree3 shouldBe
            Node(
                5,
                Node(3, EmptyTree, EmptyTree),
                Node(10, EmptyTree, EmptyTree)
            )
        val tree4 = tree3.insert(20)
        tree4 shouldBe
            Node(
                5,
                Node(3, EmptyTree, EmptyTree),
                Node(
                    10,
                    EmptyTree,
                    Node(20, EmptyTree, EmptyTree)
                )
            )
        val tree5 = tree4.insert(4)
        tree5 shouldBe
            Node(
                5,
                Node(
                    3,
                    EmptyTree,
                    Node(4, EmptyTree, EmptyTree)
                ),
                Node(
                    10,
                    EmptyTree,
                    Node(20, EmptyTree, EmptyTree)
                )
            )
        val tree6 = tree5.insert(2)
        tree6 shouldBe
            Node(
                5,
                Node(
                    3,
                    Node(2, EmptyTree, EmptyTree),
                    Node(4, EmptyTree, EmptyTree)
                ),
                Node(
                    10,
                    EmptyTree,
                    Node(20, EmptyTree, EmptyTree)
                )
            )
        val tree7 = tree6.insert(8)
        tree7 shouldBe
            Node(
                5,
                Node(
                    3,
                    Node(2, EmptyTree, EmptyTree),
                    Node(4, EmptyTree, EmptyTree)
                ),
                Node(
                    10,
                    Node(8, EmptyTree, EmptyTree),
                    Node(20, EmptyTree, EmptyTree)
                )
            )
    }

    @Test
    fun `test6-5 이진 트리에 어떤 노드가 존재하는지 확인`() {
        val tree = Node(
            5,
            Node(
                3,
                Node(2, EmptyTree, EmptyTree),
                Node(4, EmptyTree, EmptyTree)
            ),
            Node(
                10,
                Node(8, EmptyTree, EmptyTree),
                Node(20, EmptyTree, EmptyTree)
            )
        )
        tree.contains(3) shouldBe true
        tree.contains(21) shouldBe false
    }
}
