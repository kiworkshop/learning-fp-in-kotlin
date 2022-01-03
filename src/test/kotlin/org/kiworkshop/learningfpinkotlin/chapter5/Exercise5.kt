package org.kiworkshop.learningfpinkotlin.chapter5

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.funListOf
import org.kiworkshop.learningfpinkotlin.funStreamOf

class Exercise5 : AnnotationSpec() {

    @Test
    fun `test5-4  주어진 리스트에서 앞이 값이 n개 제외된 리스트를 반환`() {
        funListOf(1, 2, 3, 4, 5, 6).drop(3) shouldBe funListOf(4, 5, 6)
    }

    @Test
    fun `test5-5  주어진 리스트에서 앞에서 부터 p를 만족하기 전까지 drop하고, 나머지를 반환`() {
        funListOf(1, 2, 3, 4, 5, 6).dropWhile { it < 4 } shouldBe funListOf(4, 5, 6)
        funListOf(1, 2, 3, 4, 5, 6).dropWhile { it < 1 } shouldBe funListOf(1, 2, 3, 4, 5, 6)
    }

    @Test
    fun `test5-6  주어진 리스트에서 앞에서 부터 n 반환`() {
        funListOf(1, 2, 3, 4, 5, 6).take(3) shouldBe funListOf(1, 2, 3)
    }

    @Test
    fun `test5-7  주어진 리스트에서 앞에서 부터 p를 만족하는 값들의 리스트 반환`() {
        funListOf(1, 2, 3, 4, 5, 6).takeWhile { it % 2 == 0 } shouldBe funListOf(2, 4, 6)
//        funListOf(1, 2, 3, 4, 5, 6).takeWhile { it > 6 } shouldBe funListOf(1, 2, 3, 4, 5, 6) // ERROR
    }

    @Test
    fun `test5-8  map 함수에서 고차함수가 값들의 인덱스도 받아오는 함수 구현`() {
        funListOf(1, 2, 3, 4, 5, 6).indexedMap { i, t -> t + i } shouldBe funListOf(1, 3, 5, 7, 9, 11)
    }

    @Test
    fun `test5-9  foldLeft 함수를 이용한 maximum 함수 구현`() {
        funListOf(1, 2, 3, 4, 5, 6).maximumByFoldLeft() shouldBe 6
    }

    @Test
    fun `test5-10  foldLeft 함수를 이용한 filter 함수 구현`() {
        funListOf(1, 2, 3, 4, 5, 6).filterByFoldLeft { it % 2 == 0 } shouldBe funListOf(2, 4, 6)
    }

    @Test
    fun `test5-11  foldRight 함수를 이용한 reverse 함수 구현`() {
        funListOf(1, 2, 3, 4, 5, 6).reverseByFoldRight() shouldBe funListOf(6, 5, 4, 3, 2, 1)
    }

    @Test
    fun `test5-12  foldRight 함수를 이용한 filter 함수 구현`() {
        funListOf(1, 2, 3, 4, 5, 6).filterByFoldRight { it % 2 != 0 } shouldBe funListOf(1, 3, 5)
    }

    @Test
    fun `test5-13  FunList 에 zip 함수 구현`() {
        funListOf(1, 2, 3, 4, 5, 6).zip(funListOf('a', 'b', 'c')) shouldBe funListOf(
            1 to 'a',
            2 to 'b',
            3 to 'c'
        )
    }

    data class Book(
        val name: String,
        val price: Int
    )

    enum class Type {
        A, B
    }

    @Test
    fun `test5-14  리스트의 값을 입력받은 조합함수에 의해 맵을 생성하는 함수 구현`() {
        funListOf(
            Book("name1", 1000),
            Book("name2", 1500),
            Book("name1", 2000)
        ).associate { it to it.price } shouldBe mapOf(
            Book("name1", 1000) to 1000,
            Book("name2", 1500) to 1500,
            Book("name1", 2000) to 2000
        )
        funListOf(1, 2, 3, 4, 5).associate { it to it * 10 } shouldBe mapOf(1 to 10, 2 to 20, 3 to 30, 4 to 40, 5 to 50)
    }

    @Test
    fun `test5-15  FunList의 값들을 입력받은 키 생성 함수를 기준으로 맵을 생성`() {
        funListOf(
            Book("name1", 1000),
            Book("name2", 1500),
            Book("name1", 2000)
        ).groupBy { it ->
            when {
                it.price < 2000 -> Type.A
                else -> Type.B
            }
        } shouldBe mapOf(
            Type.A to funListOf(
                Book("name1", 1000),
                Book("name2", 1500)
            ),
            Type.B to funListOf(
                Book("name1", 2000)
            )
        )
        funListOf(1, 2, 3).groupBy { it } shouldBe mapOf(1 to funListOf(1), 2 to funListOf(2), 3 to funListOf(3))
        funListOf(1, 2, 3, 4, 5, 6).groupBy { it % 2 == 0 } shouldBe
                mapOf(false to funListOf(1, 3, 5), true to funListOf(2, 4, 6))
    }

    @Test
    fun `test5-17  FunStream의 sum 함수`() {
        funStreamOf(1, 2, 3).sum() shouldBe 6
    }

    @Test
    fun `test5-18  FunStream의 product 함수`() {
        funStreamOf(1, 2, 4).product() shouldBe 8
    }

    @Test
    fun `test5-19  FunStream의 appendTail 함수`() {
        funStreamOf(1, 2, 3).appendTail(4) shouldBe funStreamOf(1, 2, 3, 4)
    }

    @Test
    fun `test5-20  FunStream의 filter 함수`() {
        funStreamOf(1, 2, 3, 4, 5, 6).filter { it % 2 != 0 } shouldBe funStreamOf(1, 3, 5)
    }

    @Test
    fun `test5-21  FunStream의 map 함수`() {
        funStreamOf(1, 2, 3, 4, 5, 6).map { it + 1 } shouldBe funStreamOf(2, 3, 4, 5, 6, 7)
    }

    @Test
    fun `test5-22  FunStream의 take 함수`() {
        funStreamOf(1, 2, 3, 4, 5, 6).take(2) shouldBe funStreamOf(1, 2)
    }

    @Test
    fun `test5-23  FunStream의 toString 함수`() {
        funStreamOf(1, 2, 3).toString("") shouldBe "[1, 2, 3]"
    }

    @Test
    fun `test5-24  자연수의 제곱근의 합이 1000을 넘으려면 자연수 몇개가 필요한가`() {
        getNumSqrtSum(0, 0.0) shouldBe 131
    }
}