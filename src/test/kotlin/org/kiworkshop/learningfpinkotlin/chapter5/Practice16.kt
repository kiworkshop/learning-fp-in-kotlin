package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.FreeSpec
import org.kiworkshop.learningfpinkotlin.functionWay
import org.kiworkshop.learningfpinkotlin.imperativeWay
import org.kiworkshop.learningfpinkotlin.realFunctionWay

class Practice16 : FreeSpec() {

    init {
        """
           앞에서의 예제와 반대로 이번에는 큰 수에서 감소하는 값들을 가진 리스트를 입력으로 할 때를 비교해 보자.
           세 가지 함수의 성능을 비교하고 테스트 결과를 분석해 보자.
        """{
            val bigIntList = (10000000 downTo 1).toList()
            var start = System.currentTimeMillis()
            imperativeWay(bigIntList)
            println("${System.currentTimeMillis() - start} ms") // 23ms

            start = System.currentTimeMillis()
            functionWay(bigIntList)
            println("${System.currentTimeMillis() - start} ms") // 176ms

            start = System.currentTimeMillis()
            realFunctionWay(bigIntList)
            println("${System.currentTimeMillis() - start} ms") // 6ms
        }
    }
}
