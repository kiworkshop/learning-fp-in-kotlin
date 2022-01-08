package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Either
import org.kiworkshop.learningfpinkotlin.FunList
import org.kiworkshop.learningfpinkotlin.Left
import org.kiworkshop.learningfpinkotlin.Right
import org.kiworkshop.learningfpinkotlin.apply
import org.kiworkshop.learningfpinkotlin.cons
import org.kiworkshop.learningfpinkotlin.curried
import org.kiworkshop.learningfpinkotlin.funListOf
import org.kiworkshop.learningfpinkotlin.pure

class Practice18 : FreeSpec() {
    fun <A, B> sequenceA(treeList: FunList<Either<A, B>>): Either<A, FunList<B>> =
        when (treeList) {
            is FunList.Nil -> Right(funListOf())
            is FunList.Cons -> Either.pure(cons<B>().curried()) apply treeList.head apply sequenceA(
                treeList.tail
            )
        }

    init {
        "Either에도 동작하는 sequenceA 함수를 추가하고 테스트해 보자."{
            val list1 = funListOf(Right(1), Right(2), Right(3))
            val list2 = funListOf(Right(1), Left(2), Right(3), Left(4))

            sequenceA(list1) shouldBe Right(funListOf(1, 2, 3))
            sequenceA(list2) shouldBe Left(2) // 왜 이렇게 되는거지?
        }
    }
}
