package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.MyNode
import org.kiworkshop.learningfpinkotlin.MyTree
import org.kiworkshop.learningfpinkotlin.apply
import org.kiworkshop.learningfpinkotlin.curried
import org.kiworkshop.learningfpinkotlin.pure

class Practice13 : FreeSpec() {

    fun <A, B, R> liftA2(binaryFunction: (A, B) -> R) = { f1: MyNode<A>, f2: MyNode<B> ->
        MyTree.pure(binaryFunction.curried()) apply f1 apply f2
    }

    init {
        "Tree에도 동작하는 liftA2 함수롤 추가해 보자."{
            val lifted = liftA2 { x: Int, y: MyNode<Int> -> MyNode(y.value, y.forest + MyNode(x)) }
            lifted(
                MyNode(3),
                MyNode(MyNode(4, listOf(MyNode(5))))
            ) shouldBe MyNode(MyNode(4, listOf(MyNode(5), MyNode(3))))

        }
    }
}
