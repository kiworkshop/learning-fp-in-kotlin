package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Either
import org.kiworkshop.learningfpinkotlin.MyNode
import org.kiworkshop.learningfpinkotlin.Right
import org.kiworkshop.learningfpinkotlin.apply
import org.kiworkshop.learningfpinkotlin.curried
import org.kiworkshop.learningfpinkotlin.pure

class Practice14 : FreeSpec() {

    fun <A1, A2, B1, B2, R> liftA2(binaryFunction: (A2, B2) -> R) = { f1: Either<A1, A2>, f2: Either<B1, B2> ->
        Either.pure(binaryFunction.curried()) apply f1 apply f2
    }

    init {
        "Either에도 동작하는 liftA2 함수롤 추가해 보자."{
            val lifted =
                liftA2<String, Int, String, Either<String, Int>, Either<String, Int>> { x: Int, y: Either<String, Int> -> y.fmap { it + x } }
            lifted(
                Right(5),
                Right(Right(7))
            ) shouldBe Right(Right(12))
        }
    }
}
