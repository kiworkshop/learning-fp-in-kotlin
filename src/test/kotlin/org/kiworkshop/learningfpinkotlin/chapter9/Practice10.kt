package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Practice10 : FreeSpec() {
    init {
        "리스트 모노이드를 Foldable 타입 클래스의 인스턴스로 만들어서 foldLeft 함수를 작성하고, foldMap 함수를 테스트해 보자." {
            val list = Cons(1, Cons(2, Cons(3, Nil)))

            list.foldLeft(0) { a, b -> a + b } shouldBe 6
            list.foldLeft(5) { a, b -> a * b } shouldBe 30

            list.foldMap({ it + 1 }, SumMonoid()) shouldBe 9
            list.foldMap({ it * 2 }, ProductMonoid()) shouldBe 48
        }
    }
}
