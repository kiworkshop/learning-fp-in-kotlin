package org.kiworkshop.learningfpinkotlin.chapter6

import io.kotest.core.spec.style.FreeSpec
import org.junit.jupiter.api.assertThrows
import org.kiworkshop.learningfpinkotlin.Tree
import org.kiworkshop.learningfpinkotlin.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.insert

class Practice3 : FreeSpec() {
    init {
        "연습문제 6-2에서 작성한 insert 코드를 100,000만 번 이상 연속해서 insert해 보자."{
            assertThrows<StackOverflowError> {
                makeStackOverflowError()
            }
        }
    }

    private fun makeStackOverflowError() {
        var tree: Tree<Int> = EmptyTree
        for (i in 1..100000 * 10000) {
            tree = tree.insert(i)
        }
    }
}
