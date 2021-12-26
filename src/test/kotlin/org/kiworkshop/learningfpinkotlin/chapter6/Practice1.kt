package org.kiworkshop.learningfpinkotlin.chapter6

import io.kotest.core.spec.style.FreeSpec
import org.kiworkshop.learningfpinkotlin.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.Tree.ParentNode

class Practice1 : FreeSpec() {
    init {
        """
           재귀적 자료구조를 활용하여 이진 트리를 만들어 보자. 여기서 이진 트리는 균형 잡힌 트리가 아니고 일반적인 트리다.
           트리의 정의는 다음과 같다.
           
           모든 노드는 하위 노드가 없거나(EmptyTree) 최대 두 개의 하위 노드(Node)를 ㄱ진다.
           두 개의 하위 노드 중, 하나는 왼쪽에 다른 노드는 오른쪽에 있다.
           함수의 기본 선언은 다음과 같다.
           sealed class Tree<out T>
        """{
            /*
                    1
                 2    9
               3   4    7
                  5  2
             */
            val tree = ParentNode(
                1,
                ParentNode(
                    2,
                    ParentNode(3, EmptyTree, EmptyTree),
                    ParentNode(
                        4,
                        ParentNode(5, EmptyTree, EmptyTree), ParentNode(2, EmptyTree, EmptyTree)
                    ),
                ),
                ParentNode(
                    9,
                    EmptyTree, ParentNode(7, EmptyTree, EmptyTree)
                )
            )
        }
    }
}
