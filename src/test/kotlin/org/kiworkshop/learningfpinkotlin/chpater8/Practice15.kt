package org.kiworkshop.learningfpinkotlin.chpater8

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Either
import org.kiworkshop.learningfpinkotlin.Maybe
import org.kiworkshop.learningfpinkotlin.MyNode
import org.kiworkshop.learningfpinkotlin.Right
import org.kiworkshop.learningfpinkotlin.apply
import org.kiworkshop.learningfpinkotlin.curried
import org.kiworkshop.learningfpinkotlin.pure

class Practice15 : FreeSpec() {

    fun <A, B, C, R> liftA3(binaryFunction: (A, B, C) -> R) = { f1: Maybe<A>, f2: Maybe<B>, f3: Maybe<C> ->
        Maybe.pure(binaryFunction.curried()) apply f1 apply f2 apply f3
    }

    init {
        "listA3 함수를 구현해 보자. listA3은 삼항 함수를 받아서 세 개의 애플리케이티브 펑터를 적용하는 승급 함수다."{
            val lifted = liftA3 {x: Int, y : Int, z : Int -> Maybe.pure(x + y + z)}
            lifted(Maybe.pure(1), Maybe.pure(2), Maybe.pure(3)) shouldBe Maybe.pure(Maybe.pure(6))
        }
    }
}
