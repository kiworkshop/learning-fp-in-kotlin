package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class Exercise3 : AnnotationSpec() {
    @Test
    fun `test3-7`() {
        takeSequence(3, repeat(3)) shouldBe listOf(3, 3, 3)
    }
}
