package org.kiworkshop.learningfpinkotlin.chapter3

import io.kotest.core.spec.style.FreeSpec

class Practice1Of3 : FreeSpec({
    "재귀로 구현한 피보나치 문제를 수학적 귀납법으로 증명해 보자." {
        """
            명제: fib(n)은 n번째 피보나치 수를 반환한다.
            
            함수 fib(n)은 아래를 만족한다고 가정해보자.
            n이 0인 경우 0번쨰 피보나치 수는 0으로 정의되어 있으므로 0을 반환한다.
            n이 1인 경우 1번째 피보나치 수는 1로 정의되어 있으므로 1을 반환한다.
            임의의 양의 정수 k에 대해서 n < k 인 경우, n번째 피보나치 수를 올바르게 반환한다고 가정하자.
            
            이때, n = k일 때 fib(n) 함수는 fib(n - 1)과 fib(n - 2)의 합을 반환한다. 
            위 가정에서 의해 fib(n - 1)과 fib(n - 2)는 n-1번째 피보나치 수와 n-2번째 피보나치 수를 올바르게 반환한다. 
            피보나치 수의 정의에 따라 n번째 피보나치 수는 n-1번째 피보나치 수와 n-2번째 피보나치 수의 합과 같으므로 
            fib(n) 또한 올바르게 n번째 피보나치 수를 반환하다.
             
             따라서 수학적 귀납법에 의해 함수 fib(n)은 n번째 피보나치 수를 반환한다고 증명할 수 있다.
        """.trimIndent()
    }
})
