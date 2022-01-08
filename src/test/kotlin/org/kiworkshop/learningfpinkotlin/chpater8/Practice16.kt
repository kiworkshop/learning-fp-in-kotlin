package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.ApplicativeFunList
import org.kiworkshop.learningfpinkotlin.FunList
import org.kiworkshop.learningfpinkotlin.applicativeFunListOf
import org.kiworkshop.learningfpinkotlin.apply
import org.kiworkshop.learningfpinkotlin.cons
import org.kiworkshop.learningfpinkotlin.curried
import org.kiworkshop.learningfpinkotlin.foldRight
import org.kiworkshop.learningfpinkotlin.funListOf

class Practice16 : FreeSpec() {
    fun <T> sequenceA(aFunListList: FunList<ApplicativeFunList<T>>): ApplicativeFunList<FunList<T>> =
        when (aFunListList) {
            is FunList.Nil -> applicativeFunListOf(funListOf())
            is FunList.Cons -> ApplicativeFunList.pure(cons<T>().curried()) apply aFunListList.head apply sequenceA(
                aFunListList.tail
            )
        }

    fun <A, B, R> liftA2(binaryFunction: (A, B) -> R) = { f1: ApplicativeFunList<A>, f2: ApplicativeFunList<B> ->
        ApplicativeFunList.pure(binaryFunction.curried()) apply f1 apply f2
    }

    fun <T> sequenceAByFoldRight(aFunListList: FunList<ApplicativeFunList<T>>) =
        aFunListList.foldRight(ApplicativeFunList.pure(funListOf()), liftA2(cons()))

    init {
        "AFunList에도 동작하는 sequenceA 함수를 추가하고 테스트해 보자."{
            val list = funListOf(applicativeFunListOf(1), applicativeFunListOf(3))

            sequenceA(list) shouldBe applicativeFunListOf(funListOf(1, 3))
            sequenceAByFoldRight(list) shouldBe applicativeFunListOf(funListOf(1, 3))
        }
    }
}
