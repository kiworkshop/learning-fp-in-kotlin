package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec

class Chap5 : StringSpec({
//    // 연습문제 5-7, takeWhile에서 element가 모두 p를 만족하지 못했을 때 원본 리스트가 아니라 빈 리스트를 내보내야 하는거 아닌가?
//    // 142쪽에 IntRange를 FunStream<Int>로 변환하는 코드 작성하기가 꽤 머리아프다.
//    // IntRange를 FunList<Int>로 변환하는 코드를 꼭 재귀를 사용해서 구현해보자.
//
//    val list = funListOf(1, 2, 3, 4, 5)
//
//    fun <T> FunList<T>.toList(): List<T> {
//        tailrec fun FunList<T>.toList(acc: MutableList<T>): MutableList<T> = when (this) {
//            is Nil -> acc
//            is Cons -> this.tail.toList(acc.add(this.head).let { acc })
//        }
//
//        return this.toList(mutableListOf())
//    }
//
//    fun <T> FunStream<T>.toList(): List<T> {
//        tailrec fun FunStream<T>.toList(acc: MutableList<T>): MutableList<T> = when (this) {
//            is FunStream.Nil -> acc
//            is FunStream.Cons -> this.tail().toList(acc.add(this.head()).let { acc })
//        }
//
//        return this.toList(mutableListOf())
//    }
//
//    "Example 5-1" {
//        // when
//        val intList: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
//
//        // then
//        intList.toList().shouldContainExactly(1, 2, 3, 4, 5)
//    }
//
//    "Example 5-2" {
//        // when
//        val doubleList: FunList<Double> = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
//
//        // then
//        doubleList.toList().shouldContainExactly(1.0, 2.0, 3.0, 4.0, 5.0)
//    }
//
//    "Example 5-3" {
//        Cons(1, Nil).addHead(2).toList().shouldContainExactly(2, 1)
//        Cons(1, Nil).appendTail(2).toList().shouldContainExactly(1, 2)
//        Cons(1, Nil).appendTail(2).reverse().toList().shouldContainExactly(2, 1)
//        Cons(1, Nil).addHead(2).addHead(3).getTail().toList().shouldContainExactly(2, 1)
//        Cons(1, Nil).addHead(2).addHead(3).getHead() shouldBe 3
//    }
//
//    "Example 5-4" {
//        // test filter
//        list.filter { it < 3 }.toList().shouldContainExactly(1, 2)
//
//        // test drop
//        list.toList().shouldContainExactly(1, 2, 3, 4, 5)
//
//        list.drop(3).toList().shouldContainExactly(4, 5)
//        list.drop(10).toList().shouldBeEmpty()
//
//        list.toList().shouldContainExactly(1, 2, 3, 4, 5)
//    }
//
//    "Example 5-5" {
//        list.toList().shouldContainExactly(1, 2, 3, 4, 5)
//        list.dropWhile { it != 3 }.toList().shouldContainExactly(3, 4, 5)
//        list.toList().shouldContainExactly(1, 2, 3, 4, 5)
//    }
//
//    "Example 5-6" {
//        list.take(3).toList().shouldContainExactly(1, 2, 3)
//    }
//
//    "Example 5-7" {
//        list.takeWhile { it < 3 }.toList().shouldContainExactly(1, 2)
//        list.takeWhile { false }.toList().shouldBeEmpty()
//
//        listOf(1, 2, 3, 4, 5).takeWhile { false }.shouldBeEmpty()
//    }
//
//    "Example 5-8" {
//        list.map { it * 2 }.toList().shouldContainExactly(2, 4, 6, 8, 10)
//        list.indexedMap { i, elem -> (i + 1) * 10 + elem }.toList().shouldContainExactly(11, 22, 33, 44, 55)
//    }
//
//    "Example 5-9" {
//        list.maximumByFoldLeft() shouldBe 5
//    }
//
//    "Example 5-10" {
//        list.filterByFoldLeft { it and 1 == 1 }.toList().shouldContainExactly(1, 3, 5)
//    }
//
//    "Example 5-11" {
//        list.reverseByFoldRight().toList().shouldContainExactly(5, 4, 3, 2, 1)
//    }
//
//    "Example 5-12" {
//        list.filterByFoldRight { it and 1 == 1 }.toList().shouldContainExactly(1, 3, 5)
//    }
//
//    "Example 5-13" {
//        funListOf(1, 2, 3, 4, 5).zip(funListOf(1.0, 2.0)).toList().mapIndexed { index, pair ->
//            index.shouldBeLessThan(2)
//            pair.first shouldBe (index + 1)
//            pair.second shouldBe (index + 1).toDouble()
//        }
//    }
//
//    data class Entry(val english: String, val numeric: Int)
//    "Example 5-14" {
//        funListOf(1, 2, 3, 4, 5).zipWith(::Pair, funListOf(1.0, 2.0)).toList().mapIndexed { index, pair ->
//            index.shouldBeLessThan(2)
//            pair.first shouldBe (index + 1)
//            pair.second shouldBe (index + 1).toDouble()
//        }
//
//        funListOf(Entry("One", 1), Entry("Two", 2), Entry("Three", 3))
//            .associate { Pair(it, it.numeric) }
//            .let { map ->
//                map[Entry("One", 1)] shouldBe 1
//                map[Entry("Two", 2)] shouldBe 2
//                map[Entry("Three", 3)] shouldBe 3
//            }
//    }
//
//    "Example 5-15" {
//        funListOf(
//            Entry("Three", 30),
//            Entry("One", 1),
//            Entry("Two", 20),
//            Entry("One", 10),
//            Entry("Two", 2),
//            Entry("Three", 3)
//        )
//            .groupBy { it.english }
//            .let { map ->
//                map.getValue("One").toList().shouldContainExactly(Entry("One", 1), Entry("One", 10))
//                map.getValue("Two").toList().shouldContainExactly(Entry("Two", 20), Entry("Two", 2))
//                map.getValue("Three").toList().shouldContainExactly(Entry("Three", 30), Entry("Three", 3))
//            }
//    }
//
//    // list와 sequence의 차이, atomic kotlin에서 horizontal과 vertical로 비교한거 공유하자.
//    fun testBigIntList(
//        bigIntList: List<Int>,
//        assertImperativeWay: (Long) -> Unit,
//        assertFunctionalWay: (Long) -> Unit,
//        assertRealFunctionalWay: (Long) -> Unit
//    ) {
//        fun imperativeWay(intList: List<Int>): Int {
//            for (value in intList) {
//                val doubleValue = value * value
//                if (doubleValue < 10) {
//                    return doubleValue
//                }
//            }
//
//            throw NoSuchElementException("There is no value")
//        }
//
//        fun functionalWay(intList: List<Int>): Int = intList.map { n -> n * n }.first { n -> n < 10 }
//        fun realFunctionalWay(intList: List<Int>): Int = intList.asSequence().map { n -> n * n }.first { n -> n < 10 }
//
//        fun test(f: (List<Int>) -> Int, assert: (Long) -> Unit) {
//            System.currentTimeMillis().let { start ->
//                f(bigIntList)
//                (System.currentTimeMillis() - start).let {
//                    println("imperativeWay: $it ms")
//                    assert(it)
//                }
//            }
//        }
//
//        test(::imperativeWay, assertImperativeWay)
//        test(::functionalWay, assertFunctionalWay)
//        test(::realFunctionalWay, assertRealFunctionalWay)
//    }
//
//    "Example 5-16" {
//        println("increasing list")
//        testBigIntList(
//            bigIntList = (1..10000000).toList(),
//            assertImperativeWay = { millis: Long -> millis.shouldBeLessThanOrEqual(1) },
//            assertFunctionalWay = { millis: Long -> millis.shouldBeGreaterThan(100) },
//            assertRealFunctionalWay = { millis: Long -> millis.shouldBeLessThan(10) },
//        )
//
//        println("\ndecreasing list")
//        testBigIntList(
//            bigIntList = (10000000 downTo 1).toList(),
//            assertImperativeWay = { millis: Long -> millis.shouldBeLessThanOrEqual(1) },
//            assertFunctionalWay = { millis: Long -> millis.shouldBeGreaterThan(100) }, // 왜 functionalWay만 100ms가 넘지?
//            assertRealFunctionalWay = { millis: Long -> millis.shouldBeLessThanOrEqual(1) },
//        )
//    }
//
//    "Example 5-17" {
//        funStreamOf(1, 2, 3).getHead() shouldBe 1
//        funStreamOf(1, 2, 3).getTail()
//            .toString() shouldBe "Cons(head=() -> T, tail=() -> org.kiworkshop.learningfpinkotlin.FunStream<T>)"
//
//        funStreamOf(1, 2, 3).getTail().toList().shouldContainExactly(2, 3)
//
//        funStreamOf(1, 2, 3).sum() shouldBe 6
//        funStreamOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).sum() shouldBe 55
//    }
//
//    "Example 5-18" {
//        funStreamOf(1, 2, 3).product() shouldBe 6
//        funStreamOf(2, 5).product() shouldBe 10
//    }
//
//    "Example 5-19" {
//        funStreamOf(1, 2, 3).appendTail(4).toList().shouldContainExactly(1, 2, 3, 4)
//    }
//
//    "Example 5-20" {
//        funStreamOf(1, 2, 3).filter { it and 1 == 1 }.toList().shouldContainExactly(1, 3)
//    }
//
//    "Example 5-21" {
//        funStreamOf(1, 2, 3).map { it * 2.0 }.toList().shouldContainExactly(2.0, 4.0, 6.0)
//    }
//
//    "performanceTest" {
//        fun funListWay(intList: FunList<Int>): Int = intList
//            .map { n -> n * n }
//            .filter { n -> n < 1000000 }
//            .map { n -> n - 2 }
//            .filter { n -> n < 1000 }
//            .map { n -> n * 10 }
//            .getHead()
//
//        fun funStreamWay(intList: FunStream<Int>): Int = intList
//            .map { n -> n * n }
//            .filter { n -> n < 1000000 }
//            .map { n -> n - 2 }
//            .filter { n -> n < 1000 }
//            .map { n -> n * 10 }
//            .getHead()
//
//        fun IntRange.toFunList(): FunList<Int> {
//            tailrec fun IntIterator.toFunList(acc: FunList<Int> = Nil): FunList<Int> =
//                if (this.hasNext()) {
//                    toFunList(Cons(this.nextInt(), acc))
//                } else {
//                    acc
//                }
//
//            return this.reversed().iterator().toFunList()
//        }
//
//        fun IntRange.toFunStream(): FunStream<Int> {
//            fun IntIterator.toFunStream(): FunStream<Int> {
//                if (!this.hasNext()) {
//                    return FunStream.Nil
//                }
//
//                val next = this.nextInt()
//                return if (this.hasNext())
//                    FunStream.Cons({ next }) { toFunStream() }
//                else
//                    FunStream.Cons({ next }) { FunStream.Nil }
//            }
//
//            return this.iterator().toFunStream()
//        }
//
//        (1..3).toFunList().toList().shouldContainExactly(1, 2, 3)
//        (1..3).toFunStream().toList().shouldContainExactly(1, 2, 3)
//        (0 until 0).toFunStream().toList().shouldBeEmpty()
//
//        fun howMuchMillsItTakes(testName: String, runnable: () -> Unit): Long {
//            return System.currentTimeMillis().let { start ->
//                println(testName)
//                runnable()
//                val calculationMillis = (System.currentTimeMillis() - start)
//                println("$calculationMillis ms")
//                calculationMillis
//            }
//        }
//
//        val bigIntList = (1..10000000).toFunList()
//        val bigIntStream = (1..10000000).toFunStream()
//
// //        이 테스트 하나만 돌릴때는 괜찮은데 전체 테스트를 돌리면 메모리가 부족해서 예외가 발생한다.
// //        howMuchMillsItTakes("funListWay") { funListWay(bigIntList) } shouldBeGreaterThan 1000
//
//        println()
//        howMuchMillsItTakes("funStreamWay") { funStreamWay(bigIntStream) } shouldBeLessThan 10
//    }
//
//    "Example 5-22" {
//        val infiniteValue = generateFunStream(0) { it + 5 }
//        infiniteValue.take(5).toList().shouldContainExactly(0, 5, 10, 15, 20)
//    }
//
//    "Example 5-23" {
//        funListOf(1, 2, 3).toString("") shouldBe "[1, 2, 3]"
//        funListOf('a', 'b', 'c').toString("") shouldBe "[a, b, c]"
//        funListOf<Int>().toString("") shouldBe "[]"
//
//        funListOf(1, 2, 3).toStringByFoldLeft() shouldBe "[1, 2, 3]"
//        funListOf('a', 'b', 'c').toStringByFoldLeft() shouldBe "[a, b, c]"
//        funListOf<Int>().toStringByFoldLeft() shouldBe "[]"
//    }
//
//    "Example 5-24" {
//        tailrec fun f(target: Double = 1000.0, nextNaturalNumber: Int = 1, sum: Double = 0.0, count: Int = 0): Int =
//            if (sum > target) count
//            else f(target, nextNaturalNumber + 1, sqrt(nextNaturalNumber.toDouble()) + sum, count + 1)
//
//        f(-1.0) shouldBe 0
//        f(0.0) shouldBe 1
//        f(0.999999) shouldBe 1
//        f(1.0) shouldBe 2
//        f(2.4) shouldBe 2
//        f(1 + 1.4 + 1.7) shouldBe 3
//        f(1000.0) shouldBe 131
//
//        // imperative
//        var sum = 0.0
//        var count = 0
//        var nextNaturalNumber = 1
//        val target = 1000.0
//
//        while (sum < target) {
//            sum += sqrt(nextNaturalNumber.toDouble())
//            nextNaturalNumber++
//            count++
//        }
//        count shouldBe 131
//    }
})
