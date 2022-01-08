package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.FunList
import org.kiworkshop.learningfpinkotlin.MyNode
import org.kiworkshop.learningfpinkotlin.MyTree
import org.kiworkshop.learningfpinkotlin.apply
import org.kiworkshop.learningfpinkotlin.cons
import org.kiworkshop.learningfpinkotlin.curried
import org.kiworkshop.learningfpinkotlin.funListOf
import org.kiworkshop.learningfpinkotlin.pure

class Practice17 : FreeSpec() {
    fun <T> sequenceA(treeList: FunList<MyNode<T>>): MyNode<FunList<T>> =
        when (treeList) {
            is FunList.Nil -> MyNode(funListOf())
            is FunList.Cons -> MyTree.pure(cons<T>().curried()) apply treeList.head apply sequenceA(
                treeList.tail
            )
        }

    init {
        "Tree에도 동작하는 sequenceA 함수를 추가하고 테스트해 보자."{
            val list = funListOf(MyNode(1), MyNode(2), MyNode(3))

            sequenceA(list) shouldBe MyNode(funListOf(1, 2, 3))
        }
    }
}
