package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.FunSpec

class Chapter6 : FunSpec({

    context("P.169 연습 문제") {
        xtest(
            "6-1. 재귀적 자료구조를 확룡아형 이진 트리를 만들어 보자." +
                    "여기서 이진 트리는 균형잡힌 트리가 아니고 일반적인 트리다. 트리의 정의는 다음과 같다."
        ) {
            /**
             * 모든 노드는 하위 노드가 없거나(EmptyTree) 최대 두 개의 하위 노드(Node)를 가진다.
             * 두 개의 하위 노드 중, 하나는 왼쪽에 다른 노드는 오른쪽에 있다.
             * @see Tree.kt
             */
        }

        test(
            "6-2. 연습문제 6-1에서 만든 이진 트리에 노드를 추가하는 insert 함수를 Tree의 확장 함수로 만들어보자." +
                    "이때 왼쪽 하위 노드의 값은 오른쪽 하위 노드의 값보다 항상 작아야한다. 단, 값을 비교하기 위해서넌 T가 항상 Comparable 속성을 가지고 있어야한다." +
                    "여기서 문제의 복잡도를 낮추기 위해 입력 타입을 Int로 제한한다."
        ) {
            fun Tree<Int>.insert(element: Int): Tree<Int> = TODO()
        }
    }
})