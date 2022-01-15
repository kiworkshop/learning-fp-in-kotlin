package org.kiworkshop.learningfpinkotlin.chapter8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.MyNode
import org.kiworkshop.learningfpinkotlin.MyTree
import org.kiworkshop.learningfpinkotlin.append
import org.kiworkshop.learningfpinkotlin.apply
import org.kiworkshop.learningfpinkotlin.curried
import org.kiworkshop.learningfpinkotlin.pure

class Practice5 : FreeSpec() {
    init {
        "다음 두 트리를 apply로 결합하는 프로그램을 만들어 보고, 결과가 맞는지 확인해 보자."{
            val leftTree = MyNode(1, listOf(MyNode(2, listOf(MyNode(3))), MyNode(4)))
            val rightSubTree = MyNode(7, listOf(MyNode(8), MyNode(9)))
            val rightTree = MyNode(5, listOf(MyNode(6), rightSubTree))

            (
                    MyTree.pure({ x: Int, y: Int -> x * y }.curried())
                            apply leftTree
                            apply rightTree
                    ) shouldBe (
                    MyNode(5)
                            append MyNode(6)
                            append rightSubTree
                            append (
                            rightTree.fmap { it * 2 }
                                    append rightTree.fmap { it * 3 }
                            )
                            append rightTree.fmap { it * 4 }
                    )
        }
    }
}
