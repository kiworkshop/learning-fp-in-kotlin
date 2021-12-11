package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import org.kiworkshop.learningfpinkotlin.MyFunList.Cons
import org.kiworkshop.learningfpinkotlin.MyFunList.Nil

class Practice2 : FreeSpec() {
    init {
        "FunList를 사용해서 [1.0, 2.0, 3.0, 4.0, 5.0]를 가지는 doubleList를 생성하자"{
            val doubleList = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
        }
    }
}
