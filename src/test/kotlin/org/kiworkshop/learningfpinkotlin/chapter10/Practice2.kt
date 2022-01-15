package org.kiworkshop.learningfpinkotlin.chapter10

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlin.Nothing

class Practice2 : FreeSpec() {
    init {
        "연습문제 10-1에서 작성한 리스트 모나드의 fmap, pure, apply, leadTo, flatMap 함수가 잘 동작하는지 테스트해 보자" {
            val list = Cons(1, Cons(2, Nil))

            list.pure(5) shouldBe Cons(5, Nil)
            list.fmap { it + 1 } shouldBe Cons(2, Cons(3, Nil))
            list.flatMap { x -> Cons(x * 2, Nil) } shouldBe Cons(2, Cons(4, Nil))
            list.leadTo(Cons(5, Nil)) shouldBe Cons(5, Cons(5, Nil))
            Cons({ x: Int -> x * 2 }, Nil) apply Cons(5, Nil) shouldBe Cons(10, Nil)

            Nil.pure(1) shouldBe Nil
            Nil.fmap { x: Int -> x * 2 } shouldBe Nil
            Nil.flatMap { Cons(0, Nil) } shouldBe Nil
            Nil.leadTo(Cons(5, Nil)) shouldBe Nil
            (Nil as FunList<(Int) -> Nothing>).apply(Cons(5, Nil)) shouldBe Nil
        }
    }
}
