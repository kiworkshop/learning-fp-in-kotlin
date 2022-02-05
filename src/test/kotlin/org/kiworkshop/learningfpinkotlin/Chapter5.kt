package org.kiworkshop.learningfpinkotlin

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.math.max

class Chapter5 : FunSpec({

    tailrec fun <T> FunList<T>.convertToList(acc: MutableList<T> = mutableListOf()): List<T> = when (this) {
        is Nil -> acc
        is Cons -> {
            acc.add(head)
            tail.convertToList(acc)
        }
    }

    tailrec fun <T> FunStream<T>.convertToList(acc: MutableList<T> = mutableListOf()): List<T> = when (this) {
        is FunStream.Nil -> acc
        is FunStream.Cons -> {
            acc.add(getHead())
            getTail().convertToList(acc)
        }
    }

    context("P.117 연습문제") {
        test("5-1. FunList를 사용해서 [1, 2, 3, 4, 5]를 가지는 intList를 생성하자.") {
            val intList = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))

            intList.convertToList() shouldBe listOf(1, 2, 3, 4, 5)
        }

        test("5-2. FunList를 사용해서 [1.0, 2.0, 3.0, 4.0, 5.0]를 가지는 doubleList를 생성하자.") {
            val doubleList = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))

            doubleList.convertToList() shouldBe listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        }
    }

    context("P.120 연습문제 5-3") {
        test("리스트의 첫번째 값을 가져오는 getHead 함수를 작성해보자.") {
            fun <T> FunList<T>.getHead(): T = when (this) {
                is Nil -> throw NoSuchElementException()
                is Cons -> head
            }

            val list = Cons(1, Nil)
            list.addHead(10).getHead() shouldBe 10
            list.addHead(0).getHead() shouldBe 0
        }
    }

    context("P.123 연습문제 5-4") {
        test(
            "주어진 리스트에서 앞의 값이 n개 제외된 리스트를 반환하는 drop 함수를 구현하자" +
                    "이때 원본 리스트가 바뀌지 않아야하고, 새로운 리스트를 반환할 때마다 리스트를 생성하면 안된다."
        ) {
            tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (n) {
                0 -> this
                else -> this.getTail().drop(n - 1)
            }

            val intList = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
            intList.drop(3).convertToList() shouldBe listOf(4, 5)

            shouldThrowExactly<NoSuchElementException> { intList.drop(6) }
        }
    }

    context("P.123 연습문제 5-5") {
        test(
            "다음과 같이 동작하는 dropWhile 함수를 구현하자. 타입 T를 입력받아 Boolean을 반환하는 함수 p를 입력받는다." +
                    "리스트의 앞에서부터 함수 p를 만족하기 전까지 drop을 하고, 나머지 값들의 리스트를 반환한다." +
                    "이때 원본 리스트가 바뀌지 않아야하고, 새로운 리스트를 반환할 때마다 리스트를 생성하면 안된다."
        ) {
            tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
                is Nil -> this
                is Cons -> if (p(head)) this else tail.dropWhile(p)
            }

            val intList = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
            intList.dropWhile { it > 3 }.convertToList() shouldBe listOf(4, 5)
            intList.dropWhile { it > 5 }.convertToList() shouldBe emptyList()
        }
    }

    context("P.123 연습문제 5-6") {
        test(
            "리스트의 앞에서부터 n개의 값을 가진 리스트를 반환하는 take 함수를 구현하자." +
                    "이때 원본 리스트가 바뀌지 않고 새로운 리스트를 반환할 때마다 리스트를 생성하면 안된다."
        ) {
            tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = Nil): FunList<T> = when (n) {
                0 -> acc.reverse()
                else -> this.getTail().take(n - 1, acc.addHead(this.getHead()))
            }

            val intList = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
            intList.take(3).convertToList() shouldBe listOf(1, 2, 3)
            shouldThrowExactly<NoSuchElementException> { intList.take(6) }
        }
    }

    context("P.123 연습문제 5-7") {
        test(
            "다음과 같이 동작하는 takeWhile 함수를 구현하자. 타입 T를 입력받아 Boolean을 반환하는 함수 p를 받는다." +
                    "리스트의 앞에서부터 함수 p를 만족하는 값들의 리스트를 반환한다. (모든 값이 함수 p를 만족하지 않는다면 원본 List를 반환)" +
                    "이때 원본 리스트가 바뀌지 않고 새로운 리스트를 반환할 때 매번 리스트를 생성하지 않아야 한다."
        ) {
            // TODO: 2022/01/01 어떻게 원본 List를 반환하는지 모르겠다...
            tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
                is Nil -> acc.reverse()
                is Cons -> if (p(head)) tail.takeWhile(acc.addHead(head), p) else tail.takeWhile(acc, p)
            }

            val intList = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
            intList.takeWhile { it > 3 }.convertToList() shouldBe listOf(4, 5)
            intList.takeWhile { it <= 3 }.convertToList() shouldBe listOf(1, 2, 3)
//            intList.takeWhile { it > 5 }.convertToList() shouldBe listOf(1, 2, 3, 4, 5)
        }
    }

    context("P.127 연습문제 5-8") {
        test("앞서 작성한 map 함수에서 고차 함수가 값들의 순서값(인덱스)도 같이 받아올 수 있는 indexedMap 함수를 만들자") {
            tailrec fun <T, R> FunList<T>.indexedMap(
                index: Int = 0,
                acc: FunList<R> = Nil,
                f: (Int, T) -> R,
            ): FunList<R> = when (this) {
                is Nil -> acc.reverse()
                is Cons -> tail.indexedMap(index + 1, acc.addHead(f(index, head)), f)
            }

            funListOf(1, 2, 3, 4, 5).indexedMap { index: Int, head: Int -> if (index % 2 == 0) head * head else head }
                .convertToList() shouldBe listOf(1, 2, 9, 4, 25)
        }
    }

    context("P.130 연습문제") {
        test("5-9. 3장에서 작성한 maximum 함수를 foldLeft 함수를 사용해서 다시 작성해보자.") {
            fun FunList<Int>.maximumByFoldLeft(): Int = this.foldLeft(0) { acc, element -> max(acc, element) }

            funListOf(1, 2, 3, 4, 5).maximumByFoldLeft() shouldBe 5
        }

        test("5-10. filter 함수를 foldLeft 함수를 사용해서 다시 작성해보자.") {
            fun <T> FunList<T>.filterByFoldLeft(p: (T) -> Boolean): FunList<T> =
                this.foldLeft(Nil) { acc: FunList<T>, element -> if (p(element)) acc.appendTail(element) else acc } // appendTail을 써서 시간복잡도가 O(n^2)가 됐다.
            // addHead()와 reverse()를 사용해서 시간복잡도가 O(n)인 로직을 변경 필요

            funListOf(1, 2, 3, 4, 5).filterByFoldLeft { it % 2 == 0 }.convertToList() shouldBe listOf(2, 4)
        }
    }

    context("P.132 연습문제") {
        test("5-11. 3장에서 작성한 reverse 함수를 foldRight 함수를 사용해서 다시 작성해보자.") {
            // 3장에서 작성한 reverse 함수
            tailrec fun <T> FunList<T>.reverse(acc: FunList<T> = Nil): FunList<T> = when (this) {
                is Nil -> acc
                is Cons -> tail.reverse(acc.addHead(head))
            }

            fun <T> FunList<T>.reverseByFoldRight(): FunList<T> =
                this.foldRight(Nil as FunList<T>) { element, acc -> acc.appendTail(element) }

            funListOf(1, 2, 3, 4, 5)
                .reverseByFoldRight()
                .convertToList() shouldBe listOf(5, 4, 3, 2, 1)
        }

        test("5-12. filter 함수를 foldRight 함수를 사용해서 다시 작성해보자.") {
            fun <T> FunList<T>.filterByFoldRight(p: (T) -> Boolean): FunList<T> =
                this.foldRight(Nil as FunList<T>) { element, acc -> if (p(element)) acc.addHead(element) else acc }

            funListOf(1, 2, 3, 4, 5).filterByFoldRight { it % 2 == 0 }.convertToList() shouldBe listOf(2, 4)
        }
    }

    context("P.134 연습문제 5-13") {
        test("zip 함수는 3장에서 이미 설명했다. 여기서는 직접 FunList에 zip 함수를 작성해보자.") {
            tailrec fun <T, R> FunList<T>.zip(other: FunList<R>, acc: FunList<Pair<T, R>> = Nil): FunList<Pair<T, R>> =
                when (this) {
                    is Nil -> acc.reverse()
                    is Cons -> tail.zip(other.getTail(), acc.addHead(Pair(head, other.getHead())))
                }

            val intList = funListOf(1, 2, 3, 4, 5)
            val reversedIntList = funListOf(1, 2, 3, 4, 5).reverse()
            intList.zip(reversedIntList).convertToList() shouldBe listOf(
                Pair(1, 5),
                Pair(2, 4),
                Pair(3, 3),
                Pair(4, 2),
                Pair(5, 1)
            )
        }
    }

    context("P.136 연습문제") {
        test(
            "5-14. zip 함수는 리스트와 리스트를 조합해서 리스트를 생성하는 함수다." +
                    "여기서는 리스트의 값을 입력받은 조합 함수에 의해서 연관 자료구조인 맵을 생성하는 associate 함수를 작성해보자."
        ) {
            fun <T, R> FunList<T>.associate(f: (T) -> Pair<T, R>): Map<T, R> =
                this.map(f = f).foldLeft(mapOf()) { acc, element -> acc.plus(element) }

            funListOf(1, 2, 3, 4, 5)
                .associate { Pair(it, it) } shouldBe mapOf(
                Pair(1, 1),
                Pair(2, 2),
                Pair(3, 3),
                Pair(4, 4),
                Pair(5, 5)
            )
        }

        test("5-15. FunList의 값들을 입력받은 키 생성 함수를 기준으로 맵을 생성하는 groupBy 함수를 작성해보자.") {
            fun <T, K> FunList<T>.groupBy(f: (T) -> K): Map<K, FunList<T>> =
                this.foldLeft(mutableMapOf()) { acc, element ->
                    acc[f(element)] =
                        (acc[f(element)] ?: funListOf()).addHead(element) // 깔끔하게 순서까지 유지하면서 그루핑하는 방법을 모르겠다...
                    acc
                }

            funListOf(1, 2, 3, 4, 5)
                .groupBy { if (it % 2 == 0) "even" else "odd" }
                .let { groupBy ->
                    groupBy["even"]!!.convertToList() shouldBe listOf(4, 2)
                    groupBy["odd"]!!.convertToList() shouldBe listOf(5, 3, 1)
                }
        }
    }

    context("P.141 연습문제") {
        test("5-17. FunList에서 작성했던 sum 함수를 FunStream에도 추가하자.") {
            fun FunStream<Int>.sum(): Int = when (this) {
                is FunStream.Nil -> 0
                is FunStream.Cons -> getHead() + getTail().sum()
            }

            funStreamOf(1, 2, 3, 4, 5).sum() shouldBe 15
        }

        test("5-18. FunList에서 작성했던 product 함수를 FunStream에도 추가하자.") {
            fun FunStream<Int>.product(): Int = when (this) {
                is FunStream.Nil -> 1
                is FunStream.Cons -> getHead() * getTail().product()
            }

            funStreamOf(1, 2, 3, 4, 5).product() shouldBe 120
        }

        test("5-19. FunList에서 작성했던 appendTail 함수를 FunStream에도 추가하자.") {
            fun <T> FunStream<T>.appendTail(value: T): FunStream<T> = when (this) {
                is FunStream.Nil -> FunStream.Cons({ value }) { FunStream.Nil }
                is FunStream.Cons -> FunStream.Cons(head) { getTail().appendTail(value) } // 매번 재귀가 돌때마다 Cons를 만들어내는게 과연 효율적인걸까?
            }

            funStreamOf(1, 2, 3, 4, 5).appendTail(6).convertToList() shouldBe listOf(1, 2, 3, 4, 5, 6)
        }
    }

    context("P. 142 연습문제") {
        test("5-20. FunList에서 작성했던 filter 함수를 FunStream에도 추가하자.") {
            fun <T> FunStream<T>.filter(p: (T) -> Boolean): FunStream<T> = when (this) {
                is FunStream.Nil -> this
                is FunStream.Cons -> if (p(getHead()))
                    FunStream.Cons(head) { getTail().filter(p) }
                else
                    getTail().filter(p) // 여긴 즉시 연산이기 때문에 꼬리재귀가 아닌 이상 else만 타는 큰 리스트일 경우 stack overflow 및 성능 이슈 발생
            }

            funStreamOf(1, 2, 3, 4, 5).filter { it % 2 == 0 }.convertToList() shouldBe listOf(2, 4)
        }

        test("5-21. FunList에서 작성했던 map 함수를 FunStream에도 추가하자.") {
            fun <T, R> FunStream<T>.map(f: (T) -> R): FunStream<R> = when (this) {
                is FunStream.Nil -> this
                is FunStream.Cons -> FunStream.Cons({ f(getHead()) }) { getTail().map(f) }
            }

            funStreamOf(1, 2, 3, 4, 5).map { it * it }.convertToList() shouldBe listOf(1, 4, 9, 16, 25)
        }
    }
})
