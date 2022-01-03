package org.kiworkshop.learningfpinkotlin.chapter7

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter7.FunList.Cons
import org.kiworkshop.learningfpinkotlin.chapter7.FunList.Nil
import org.kiworkshop.learningfpinkotlin.chapter7.FunList.Nil.first
import org.kiworkshop.learningfpinkotlin.chapter7.FunList.Nil.size

class Exercise7 : AnnotationSpec() {
    @Test
    fun `test7-1 FunList를 Functor의 인스턴스로 만들어라`() {
        val funList: FunList<Int> = Cons(1, Cons(2, Cons(3, Nil)))


        funList.fmap { it * 3 } shouldBe
                Cons(3, Cons(6, Cons(9, Nil)))
        funList.first() shouldBe 1
        funList.size() shouldBe 3

        val funList2: FunList<Int> = Nil

        funList2.fmap { it * 3 } shouldBe Nil
        funList2.size() shouldBe 0
    }

}