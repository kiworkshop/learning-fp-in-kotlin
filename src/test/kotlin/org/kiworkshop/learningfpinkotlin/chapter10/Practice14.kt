package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.FunStream
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.flatMap
import org.kiworkshop.learningfpinkotlin.chapter10.list.monad.funStreamOf

class Practice14 : FreeSpec() {
    init {
        "FunStream에 foldRight, flatten, flatMap 함수를 추가해 보자." {
            val stream = FunStream.Cons({
                println("eval")
                1
            }, {
                FunStream.Cons({
                    println("eval2")
                    2
                }, { FunStream.Nil })
            })

            stream.flatMap { funStreamOf(it + 1) } shouldBe funStreamOf(2, 3)
        }
    }
}
