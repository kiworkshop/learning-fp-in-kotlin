package org.kiworkshop.learningfpinkotlin.chapter6

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.contains
import org.kiworkshop.learningfpinkotlin.insert

class Practice5 : FreeSpec() {
    init {
        """
           연습문제 6-1에서 만든 이진 트리에 어떤 노드가 존재하는지 확인하는 contains 함수를 추가해 보자.
           문제의 복잡도를 낮추기 위해 입력 타입은 Int로 제한한다.
           
           함수의 선언 타입은 다음과 같다.
           fun Tree<Int>.contains(elem: Int): Boolean
        """{
            EmptyTree
                .insert(1)
                .insert(3)
                .contains(1) shouldBe true
            EmptyTree
                .insert(1)
                .insert(3)
                .contains(2) shouldBe false

            EmptyTree
                .insert(1)
                .insert(3)
                .insert(3)
                .insert(3)
                .contains(3) shouldBe true

            EmptyTree
                .insert(1)
                .insert(3)
                .insert(2)
                .insert(5)
                .contains(5) shouldBe true

            EmptyTree.contains(1) shouldBe false
        }
    }

}
