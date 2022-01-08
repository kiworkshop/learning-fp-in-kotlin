package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.ApplicativeFunList
import org.kiworkshop.learningfpinkotlin.applicativeFunListOf
import org.kiworkshop.learningfpinkotlin.apply
import org.kiworkshop.learningfpinkotlin.curried

class Practice12 : FreeSpec() {

    fun <A, B, R> liftA2(binaryFunction: (A, B) -> R) = { f1: ApplicativeFunList<A>, f2: ApplicativeFunList<B> ->
        ApplicativeFunList.pure(binaryFunction.curried()) apply f1 apply f2
    }

    init {
        "AFunList에도 동작하는 liftA2 함수롤 추가해 보자."{
            val lifted = liftA2 { x: Int, y: ApplicativeFunList<Int> -> ApplicativeFunList.ACons(x, y) }

            lifted(
                applicativeFunListOf(3),
                applicativeFunListOf(applicativeFunListOf(3, 5))
            ) shouldBe applicativeFunListOf(applicativeFunListOf(3, 3, 5))
        }
    }
}
