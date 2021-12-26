package org.kiworkshop.learningfpinkotlin.chapter6

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.Tree.ParentNode
import org.kiworkshop.learningfpinkotlin.insert

class Practice2 : FreeSpec() {
    init {
        """
           연습문제 6-1에서 만든 이진 트리에 노드를 추가하는 insert 함수를 Tree의 확장 함수로 만들어 보자.
           이떄 왼쪽 하위 노드의 값은 오른쪽 하위 노드의 값보다 항상 작아야 한다.
           단, 값을 비교하기 위해서는 T가 항상 Comparable 속성을 가지고 있어야 한다.
           여기서는 문제의 복잡도를 낮추기위해 입력 타입을 Int로 제한한다.
           
           함수의 선언 타입은 다음과 같다.
           fun Tree<Int>.insert(elem: Int): Tree<Int> = TODO()
        """{
            val tree = EmptyTree
                .insert(3)
                .insert(2)
                .insert(5)
                .insert(1) as ParentNode

            tree.value shouldBe 3

            (tree.left as ParentNode).value shouldBe 2
            ((tree.left as ParentNode).left as ParentNode).value shouldBe 1
            ((tree.left as ParentNode).left as ParentNode).left shouldBe EmptyTree
            ((tree.left as ParentNode).left as ParentNode).right shouldBe EmptyTree

            (tree.right as ParentNode).value shouldBe 5
            (tree.right as ParentNode).left shouldBe EmptyTree
            (tree.right as ParentNode).right shouldBe EmptyTree
        }
    }
}
