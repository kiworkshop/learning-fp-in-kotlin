package org.kiworkshop.learningfpinkotlin.chapter6

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertDoesNotThrow
import org.kiworkshop.learningfpinkotlin.Tree
import org.kiworkshop.learningfpinkotlin.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.Tree.Node
import org.kiworkshop.learningfpinkotlin.insert
import org.kiworkshop.learningfpinkotlin.insertTailrec
import kotlin.system.measureTimeMillis

class Practice4 : FreeSpec() {
    init {
        "StackOverflowError가 일어나지 않도록 insertTailrec을 작성해보자." - {

            "insertTailrec 함수를 사용해도 정상적으로 insert가 된다." {
                val tree = EmptyTree
                    .insertTailrec(3)
                    .insertTailrec(2)
                    .insertTailrec(5)
                    .insertTailrec(1) as Node
                tree.value shouldBe 3

                (tree.left as Node).value shouldBe 2
                ((tree.left as Node).left as Node).value shouldBe 1
                ((tree.left as Node).left as Node).left shouldBe EmptyTree
                ((tree.left as Node).left as Node).right shouldBe EmptyTree

                (tree.right as Node).value shouldBe 5
                (tree.right as Node).left shouldBe EmptyTree
                (tree.right as Node).right shouldBe EmptyTree
            }

            "StackOverflowErrorr가 발생하지 않는다".config(false) {
                assertDoesNotThrow {
                    makeStackOverflowError()
                }
            }

            "기존 insert와 성능 비교".config(false) {
                val insertTime = measureTimeMillis {
                    var tree: Tree<Int> = EmptyTree
                    for (i in 1..1000) {
                        tree = tree.insert(i)
                    }
                }
                println("insertTime = ${insertTime}")
                val insertTailrecTime = measureTimeMillis {
                    var tree: Tree<Int> = EmptyTree
                    for (i in 1..1000) {
                        tree = tree.insertTailrec(i)
                    }
                }
                println("insertTailrecTime = ${insertTailrecTime}")
            }
        }
    }

    private fun makeStackOverflowError() {
        var tree: Tree<Int> = EmptyTree
        for (i in 1..40000) {
            tree = tree.insertTailrec(i)
        }
    }
}
