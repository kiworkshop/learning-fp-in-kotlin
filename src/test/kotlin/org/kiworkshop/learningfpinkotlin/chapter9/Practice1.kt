package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.Monoid

class Practice1 : FreeSpec() {
    class AnyMonoid : Monoid<Boolean> {
        override fun mempty(): Boolean = false

        override fun mappend(m1: Boolean, m2: Boolean): Boolean = m1 || m2
    }

    init {
        "|| 연산을 Any 모노이드로 만들어 보자." {
            val anyMonoid = AnyMonoid()

            (false || anyMonoid.mempty()) shouldBe false
            (true || anyMonoid.mempty()) shouldBe true
            anyMonoid.mappend(m1 = false, m2 = false) shouldBe false
            anyMonoid.mappend(m1 = true, m2 = false) shouldBe true
            anyMonoid.mappend(m1 = false, m2 = true) shouldBe true
            anyMonoid.mappend(m1 = true, m2 = true) shouldBe true
        }
    }
}
