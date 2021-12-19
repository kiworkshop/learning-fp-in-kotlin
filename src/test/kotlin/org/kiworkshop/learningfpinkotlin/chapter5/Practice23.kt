package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.emptyMyFunList
import org.kiworkshop.learningfpinkotlin.myFunListOf
import org.kiworkshop.learningfpinkotlin.toString
import org.kiworkshop.learningfpinkotlin.toStringByFoldLeft

class Practice23 : FreeSpec() {

    init {
        "FunList에 toString 함수를 추가해 보자."{
            myFunListOf(1).toString("") shouldBe "[1]"
            myFunListOf(1, 2).toString("") shouldBe "[1, 2]"
            myFunListOf(1, 2, 3).toString("") shouldBe "[1, 2, 3]"
            emptyMyFunList<Int>().toString("") shouldBe "[]"

            myFunListOf(1).toStringByFoldLeft() shouldBe "[1]"
            myFunListOf(1, 2).toStringByFoldLeft() shouldBe "[1, 2]"
            myFunListOf(1, 2, 3).toStringByFoldLeft() shouldBe "[1, 2, 3]"
            emptyMyFunList<Int>().toStringByFoldLeft() shouldBe "[]"
        }
    }
}
