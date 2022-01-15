package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.funListOf

class Practice14 : FreeSpec() {
    init {
        "foldMap 함수를 활용해서 Tree를 FunList로 변환하는 함수 toFunList를 작성해 보자." {
            val tree = Tree.Node(
                1,
                funListOf(
                    Tree.Node(3), Tree.Node(4, funListOf(Tree.Node(5), Tree.Node(6)))
                )
            )
            tree.toFunList() shouldBe funListOf(3, 5, 6, 4, 1)
        }
    }
}
