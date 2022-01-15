package org.kiworkshop.learningfpinkotlin

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Chapter6 : FunSpec({

    context("P.169 연습 문제") {
        test(
            "6-1. 재귀적 자료구조를 확룡아형 이진 트리를 만들어 보자." +
                    "여기서 이진 트리는 균형잡힌 트리가 아니고 일반적인 트리다. 트리의 정의는 다음과 같다."
        ) {
            /**
             * 모든 노드는 하위 노드가 없거나(EmptyTree) 최대 두 개의 하위 노드(Node)를 가진다.
             * 두 개의 하위 노드 중, 하나는 왼쪽에 다른 노드는 오른쪽에 있다.
             * @see BinaryTree.kt
             */

            /**
             *          4
             *      2       6
             *   1     3 5      7
             */
            val binaryTree = Node(
                4,
                Node(
                    2,
                    Node(1), Node(3)
                ),
                Node(
                    6,
                    Node(5), Node(7)
                )
            )

            binaryTree.toString() shouldBe "(N 4 (N 2 (N 1 E E) (N 3 E E)) (N 6 (N 5 E E) (N 7 E E)))"
        }

        test(
            "6-2. 연습문제 6-1에서 만든 이진 트리에 노드를 추가하는 insert 함수를 Tree의 확장 함수로 만들어보자." +
                    "이때 왼쪽 하위 노드의 값은 오른쪽 하위 노드의 값보다 항상 작아야한다. 단, 값을 비교하기 위해서넌 T가 항상 Comparable 속성을 가지고 있어야한다." +
                    "여기서 문제의 복잡도를 낮추기 위해 입력 타입을 Int로 제한한다."
        ) {
            fun BinaryTree<Int>.insert(element: Int): BinaryTree<Int> = when (this) {
                is EmptyNode -> Node(element)
                is Node -> when {
                    this.value > element -> Node(this.value, this.leftTree.insert(element), this.rightTree)
                    this.value < element -> Node(this.value, this.leftTree, this.rightTree.insert(element))
                    else -> this
                }
            }

            EmptyNode.insert(4)
                .insert(2)
                .insert(6)
                .insert(1)
                .insert(3)
                .insert(5)
                .insert(7)
                .toString() shouldBe "(N 4 (N 2 (N 1 E E) (N 3 E E)) (N 6 (N 5 E E) (N 7 E E)))"
        }

        test("6-3. 연습문제 6-2에서 작성한 insert 코드를 100,000번 이상 연속해서 insert 해보자.") {
            shouldThrowExactly<StackOverflowError> {
                (1..1_000_000_000).fold(EmptyNode as BinaryTree<Int>) { acc, element ->
                    acc.insert(element)
                }
            }
        }

        test("6-4. StackOverFlowError가 발생하지 않도록 insertTailrec을 작성해보자.") {
            fun BinaryTree<Int>.insertTailrec(element: Int): BinaryTree<Int> = TODO()

            EmptyNode.insertTailrec(4)
                .insertTailrec(2)
                .insertTailrec(6)
                .insertTailrec(1)
                .insertTailrec(3)
                .insertTailrec(5)
                .insertTailrec(7)
                .toString() shouldBe "(N 4 (N 2 (N 1 E E) (N 3 E E)) (N 6 (N 5 E E) (N 7 E E)))"
        }

        test(
            "6-5. 연습문제 6-1에서 만든 이진 트리에 어떤 노드가 존재하는지 확인하는 contains 함수를 추가해보자." +
                    "문제의 복잡도를 낮추기 위해 입력 타입을 Int로 제한한다."
        ) {
            tailrec fun BinaryTree<Int>.contains(element: Int): Boolean = when (this) {
                is EmptyNode -> false
                is Node -> when (this.value) {
                    element -> true
                    else -> when {
                        this.value > element -> this.leftTree.contains(element)
                        else -> this.rightTree.contains(element)
                    }
                }
            }

            val binaryTree = EmptyNode.insert(4)
                .insert(2)
                .insert(6)
                .insert(1)
                .insert(3)
                .insert(5)
                .insert(7)
            binaryTree.contains(4) shouldBe true
            binaryTree.contains(2) shouldBe true
            binaryTree.contains(6) shouldBe true
            binaryTree.contains(1) shouldBe true
            binaryTree.contains(5) shouldBe true
            binaryTree.contains(7) shouldBe true
        }
    }
})
