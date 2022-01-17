package org.kiworkshop.learningfpinkotlin.chapter7

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter4.compose

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

    @Test
    fun `test7-2 FunList가 펑터의 법칙을 만족하는지 확인`() {
        val funList: FunList<Int> = Cons(1, Cons(2, Cons(3, Nil)))
        // 펑터 제1 법칙
        funList.fmap { identity(it) } shouldBe identity(funList)
        // 펑터 제2 법칙
        funList.fmap(f compose g) shouldBe funList.fmap(g).fmap(f)
    }
}
