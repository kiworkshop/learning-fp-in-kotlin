package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import org.kiworkshop.learningfpinkotlin.MyFunList.Cons
import org.kiworkshop.learningfpinkotlin.MyFunList.Nil

class Practice1 : FreeSpec() {
    init {
        "FunList를 사용해서 [1, 2, 3, 4, 5]를 가지는 intList를 생성하자"{
            val doubleList = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
        }
    }
}
