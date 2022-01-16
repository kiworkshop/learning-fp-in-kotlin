package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter10.tree.FunTree
import org.kiworkshop.learningfpinkotlin.chapter10.tree.FunTree.EmptyTree
import org.kiworkshop.learningfpinkotlin.chapter10.tree.FunTree.Node
import org.kiworkshop.learningfpinkotlin.chapter10.tree.apply
import org.kiworkshop.learningfpinkotlin.chapter10.tree.mappend
import org.kiworkshop.learningfpinkotlin.chapter10.tree.mempty
import kotlin.Nothing

class Practice15 : FreeSpec() {
    init {
        "이진 트리 모나드 FunTree를 만들어 보자." - {
            "pure는 전달된 값 그대로를 Node에 담아서 반환한다" {
                FunTree.pure(1) shouldBe Node(1)
                EmptyTree.pure(2) shouldBe Node(2)
            }

            "mempty는 mappend 연산에 대해서 항등원이다" {
                Node(1).apply {
                    this mappend mempty() shouldBe this
                    mempty() mappend this shouldBe this
                }
            }

            "mappend는 트리를 더하는 연관 바이너리 함수다" - {
                "leftTree, rightTree 순으로 빈 곳에 채워넣는다" {
                    Node(1) mappend Node(2) shouldBe Node(1, Node(2))
                    Node(1) mappend Node(2) mappend Node(3) shouldBe Node(1, Node(2), Node(3))
                    Node(1, Node(2)) mappend Node(3) shouldBe Node(1, Node(2), Node(3))
                    Node(1, EmptyTree, Node(3)) mappend Node(2) shouldBe Node(1, Node(2), Node(3))
                }
                "leftTree, rightTree 모두 비지 않았다면 leftTree에 해당 노드를 추가한다" {
                    Node(1, Node(2), Node(3)) mappend Node(4) shouldBe Node(1, Node(2, Node(4)), Node(3))
                    Node(1, Node(2, Node(4)), Node(3)) mappend Node(5) shouldBe Node(
                        1,
                        Node(2, Node(4), Node(5)),
                        Node(3)
                    )
                }
            }

            "fmap은 각 Node가 가지고 있는 값에 전달된 함수를 적용한다" {
                EmptyTree.fmap { x: Int -> x * 2 } shouldBe EmptyTree
                Node(1, Node(2), Node(3)).fmap { it + 1 } shouldBe Node(2, Node(3), Node(4))
                Node(1, Node(2, Node(4)), Node(5)).fmap { it * 2 } shouldBe Node(2, Node(4, Node(8)), Node(10))
            }

            "apply는 전달된 값에 모나드의 함수를 적용한 값을 반환한다" {
                EmptyTree.apply<Nothing, Nothing>(EmptyTree) shouldBe EmptyTree
                EmptyTree.apply<Int, Nothing>(Node(1)) shouldBe EmptyTree
                Node({ x: Int -> x + 1 }) apply Node(1, Node(2, Node(4)), Node(3)) shouldBe Node(
                    2,
                    Node(3, Node(5)),
                    Node(4)
                )
                // Node(2 + 1, Node(2 + 2)) mappend Node(1 * 5, Node(2 * 5))
                Node({ x: Int -> x + 2 }, Node({ y: Int -> y * 5 })) apply Node(1, Node(2)) shouldBe Node(
                    3,
                    Node(4),
                    Node(5, Node(10))
                )
            }

            "flatMap은 각 Node가 가지고 있는 값에 모나드를 반환하는 함수를 적용한 뒤 값을 모나드에서 꺼내어서 결과로 반환한다" {
                EmptyTree.flatMap { x: Int -> Node(x + 1) } shouldBe EmptyTree
                Node(1).flatMap { Node(it * 2) } shouldBe Node(2)
                Node(1, Node(2), Node(3)).flatMap { Node(it + 1) } shouldBe Node(2, Node(3), Node(4))
                // Node(2, Node(20)) mappend Node(3, Node(40)) mappend Node(4, Node(60))
                Node(1, Node(2), Node(3)).flatMap { Node(it + 1, Node(it * 20)) } shouldBe Node(
                    2,
                    Node(
                        20,
                        Node(
                            4,
                            Node(60)
                        )
                    ),
                    Node(
                        3,
                        Node(40)
                    )
                )
            }

            "leadTo는 현재 context를 무시하고 전달된 모나드를 반환한다" {
                EmptyTree.leadTo(Node(5)) shouldBe EmptyTree

                val a = Node(10)
                val b = Node(7, Node(2))
                Node(0).apply {
                    leadTo(a) shouldBe a
                    leadTo(b) shouldBe b
                }

                Node(1, Node(2)).apply {
                    leadTo(a) shouldBe (a mappend a)
                    leadTo(b) shouldBe (b mappend b)
                }

                Node(1, Node(2), Node(3)).apply {
                    leadTo(a) shouldBe (a mappend a mappend a)
                    leadTo(b) shouldBe (b mappend b mappend b)
                }

                Node(1, Node(2, Node(4))).apply {
                    leadTo(a) shouldBe (a mappend (a mappend a))
                    leadTo(b) shouldBe (b mappend (b mappend b))
                }

                Node(1, Node(2, Node(4)), Node(3, Node(5), Node(6))).apply {
                    leadTo(a) shouldBe (a mappend (a mappend a) mappend (a mappend a mappend a))
                    leadTo(b) shouldBe (b mappend (b mappend b) mappend (b mappend b mappend b))
                }
            }
        }
    }
}
