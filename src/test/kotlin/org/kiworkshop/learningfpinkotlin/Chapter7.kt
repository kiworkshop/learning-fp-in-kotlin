package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.expectSpec
import io.kotest.matchers.shouldBe

class Chapter7 : StringSpec({

    "Ex 1" {
    }

    "Ex 2" {
        val funList = Cons(1, Cons(2, Cons(3, Nil)))

        funList shouldBe funList.fmap { identity(it) }

        val add = { it: Int -> it + 2 }
        val multiply = { it: Int -> it * 2 }

        val first = funList.fmap { add compose multiply }
        val second = funList.fmap(add).fmap(multiply)
        // 똑같이 안 나옴
        first shouldBe second
    }
})
