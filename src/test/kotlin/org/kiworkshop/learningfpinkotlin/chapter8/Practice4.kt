package org.kiworkshop.learningfpinkotlin.chapter8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.MyNode
import org.kiworkshop.learningfpinkotlin.MyTree
import org.kiworkshop.learningfpinkotlin.apply
import org.kiworkshop.learningfpinkotlin.curried
import org.kiworkshop.learningfpinkotlin.pure

class Practice4 : FreeSpec() {
    init {
        "다음가 같이 두 트리를 반대의 순서로 적용했을 때는 어떤 트리가 완성될지 예상해 보고, 테스트해 보자."{
            (MyTree.pure({ x: Int, y: Int -> x * y }.curried())
                    apply MyNode(4, listOf(MyNode(5), MyNode(6)))
                    apply MyNode(1, listOf(MyNode(2), MyNode(3)))
                    ) shouldBe MyNode(
                4,
                listOf(
                    MyNode(8),
                    MyNode(12),
                    MyNode(5, listOf(MyNode(10), MyNode(15))),
                    MyNode(6, listOf(MyNode(12), MyNode(18)))
                )
            )
        }
    }
}
