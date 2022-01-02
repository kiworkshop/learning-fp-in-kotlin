package org.kiworkshop.learningfpinkotlin.chapter7

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Cons
import org.kiworkshop.learningfpinkotlin.FunctorFunList
import org.kiworkshop.learningfpinkotlin.Nil
import org.kiworkshop.learningfpinkotlin.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.Tree.Node
import org.kiworkshop.learningfpinkotlin.insert

class Practice1 : FreeSpec() {
    init {
        """
           5장에서 만든 FunList를 Functor의 인스턴스로 만들어 보자.
           FunList에 이미 map 함수 등이 존재하지만, fmap, first, size와 같은 기본적인 기능만 제공하는 형태로 다시 작성하라.
           
           펑터의 의미에 집중하기 위해 꼬리 재귀나, 효율은 생각하지 않고 작성한다.
        """{
            val funList = Cons(1, Nil)

            funList.head shouldBe 1
            funList.size() shouldBe 1
            funList.fmap { it + 1 } shouldBe Cons(2, Nil)

            val emptyFunLit = Nil as FunctorFunList<Int>

            emptyFunLit.size() shouldBe 0
            emptyFunLit.fmap { it + 1 } shouldBe Nil

            val funList2 = Cons(1, Cons(2, Nil))

            funList2.head shouldBe 1
            funList2.size() shouldBe 2
            funList2.fmap { it * 2 } shouldBe Cons(2, Cons(4, Nil))
        }
    }
}
