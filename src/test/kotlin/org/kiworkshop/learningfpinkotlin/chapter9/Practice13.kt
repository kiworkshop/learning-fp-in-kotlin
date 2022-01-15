package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.funListOf

class Practice13 : FreeSpec() {
    init {
        "foldMap 함수를 사용하여 폴더블 트리에 contains 함수를 구현해 보자." {
            val tree = Tree.Node(
                1,
                funListOf(
                    Tree.Node(3), Tree.Node(4, funListOf(Tree.Node(5), Tree.Node(6)))
                )
            )

            tree.contains(1) shouldBe true
            tree.contains(4) shouldBe true
            tree.contains(0) shouldBe false
            tree.contains(7) shouldBe false
        }
    }
}
