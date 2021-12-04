package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec

class Practice1Of3 : ShouldSpec({
    context("피보나치 재귀 정의의 귀납적 증명") {
        should("증명한다") {
            val result = Fibonacci()

            println("명제: ${result.statement}")
            println("기저: ${result.baseStep()}")
            println("가정: ${result.assumptionStep()}")
            println("증명: ${result.inductiveStep()}")
        }
    }
})
