package org.kiworkshop.learningfpinkotlin.kimjuyeop

import io.kotest.core.spec.style.StringSpec
import org.kiworkshop.learningfpinkotlin.FunList
import org.kiworkshop.learningfpinkotlin.FunList.Cons
import org.kiworkshop.learningfpinkotlin.FunList.Nil
import org.kiworkshop.learningfpinkotlin.FunStream
import org.kiworkshop.learningfpinkotlin.addHead
import org.kiworkshop.learningfpinkotlin.appendTail
import org.kiworkshop.learningfpinkotlin.foldLeft
import org.kiworkshop.learningfpinkotlin.foldRight
import org.kiworkshop.learningfpinkotlin.getHead
import org.kiworkshop.learningfpinkotlin.getTail
import org.kiworkshop.learningfpinkotlin.reverse
import org.kiworkshop.learningfpinkotlin.FunStream.Cons as Cons2
import org.kiworkshop.learningfpinkotlin.FunStream.Nil as Nil2

class Chapter05 : StringSpec({

    // P.117 : FunList를 사용해서 intList 생성
    "Example 5-1" {
        val intList: FunList<Int> = Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))
    }

    // P.117 : FunList를 사용해서 doubleList 생성
    "Example 5-2" {
        val doubleList: FunList<Double> = Cons(1.0, Cons(2.0, Cons(3.0, Cons(4.0, Cons(5.0, Nil)))))
    }

    // P.120 : 리스트의 첫번째 값을 가져오는 함수 작성
    "Example 5-3" {
        fun <T> FunList<T>.getHead(): T = when (this) {
            Nil -> throw NoSuchElementException()
            is Cons -> head
        }
    }

    // P.123 : n개의 제외된 리스트를 반환하는 함수 작성
    // 테스트
    "Example 5-4" {
        tailrec fun <T> FunList<T>.drop(n: Int): FunList<T> = when (n) {
            0 -> this
            else -> if (this is Cons) {
                tail.drop(n - 1)
            } else {
                throw NoSuchElementException()
            }
        }
    }

    // P.123 : 함수 p를 만족하기 전까지는 drop, 만족 이후의 리스트 값을 반환하는 함수 작성
    "Example 5-5" {
        tailrec fun <T> FunList<T>.dropWhile(p: (T) -> Boolean): FunList<T> = when (this) {
            Nil -> throw NoSuchElementException()
            is Cons -> if (p(head)) {
                this
            } else {
                tail.dropWhile(p)
            }
        }
    }

    // P.123 : n개의 값을 가진 리스트를 반환하는 함수 작성
    "Example 5-6" {
        tailrec fun <T> FunList<T>.take(n: Int, acc: FunList<T> = Nil): FunList<T> = when (n) {
            0 -> acc.reverse()
            else -> if (this is Cons) {
                tail.take(n - 1, acc.addHead(head))
            } else {
                throw NoSuchElementException()
            }
        }
    }

    // TODO Example 5-7 (원본 리스트 반환법을 모르겠다)
    // P.123 : 함수 p를 만족하는 값들의 리스트를 반환하는 함수 작성, 한개도 만족하지 않는다면 원본 리스트 반환
    // p가 만족하지 않으면 끝내기
    "Example 5-7" {
        tailrec fun <T> FunList<T>.takeWhile(acc: FunList<T> = Nil, p: (T) -> Boolean): FunList<T> = when (this) {
            Nil -> if (acc == Nil) this else acc.reverse()
            is Cons -> if (p(head)) {
                tail.takeWhile(acc.addHead(head), p)
            } else {
                tail.takeWhile(acc, p)
            }
        }
    }

    // P.127 : 인덱스를 함께 포함한 map 함수 작성
    "Example 5-8" {
        tailrec fun <T, R> FunList<T>.indexedMap(index: Int = 0, acc: FunList<R> = Nil, f: (Int, T) -> R): FunList<R> =
            when (this) {
                Nil -> acc.reverse()
                is Cons -> tail.indexedMap(index.plus(1), acc.addHead(f(index, head)), f)
            }
    }

    // P.130 : foldLeft를 활용해 maximum 함수 다시 작성
    "Example 5-9" {
        fun FunList<Int>.maximumByFoldLeft(): Int = foldLeft(0) { acc: Int, x: Int ->
            if (acc > x) acc else x
        }
    }

    // P.130 : foldLeft를 활용해 filter 함수 다시 작성
    // 개선
    "Example 5-10" {
        fun <T> FunList<T>.filterByFoldLeft(p: (T) -> Boolean): FunList<T> = foldLeft(Nil) { acc: FunList<T>, x: T ->
            if (p(x)) acc.appendTail(x) else acc
        }
    }

    // P.132 : foldRight을 활용해 reverse 함수 다시 작성
    "Example 5-11" {
        fun <T> FunList<T>.reverseByFoldRight(): FunList<T> = foldRight(Nil) { x: T, acc: FunList<T> ->
            acc.appendTail(x)
        }
    }

    // P.132 : foldRight을 활용해 filter 함수 다시 작성
    "Example 5-12" {
        fun <T> FunList<T>.filterByFoldRight(p: (T) -> Boolean): FunList<T> = foldRight(Nil) { x: T, acc: FunList<T> ->
            if (p(x)) acc.appendTail(x) else acc
        }
    }

    // P.134 : FunList로 zip 함수 다시 작성
    "Example 5-13" {
        tailrec fun <T, R> FunList<T>.zip(other: FunList<R>, acc: FunList<Pair<T, R>>): FunList<Pair<T, R>> = when {
            this == Nil || other == Nil -> acc
            else -> this.getTail().zip(other.getTail(), acc.appendTail(Pair(this.getHead(), other.getHead())))
        }
    }

    // TODO Example 5-14 (문제 의미를 모르겠다)
    // P.136 : 입력받은 조합 함수에 의해서 연관 자료구조 Map을 생성하는 함수 작성
    "Example 5-14" {
        fun <T, R> FunList<T>.associate(f: (T) -> Pair<T, R>): Map<T, R> = when (this) {
            Nil -> throw NoSuchElementException()
            is Cons -> mapOf(f(head))
        }
    }

    // TODO Example 5-15 (문제 의미를 모르겠다)
    // P.136 : 입력받은 키 생성 함수를 기준으로 Map을 생성하는 함수 작성
    "Example 5-15" {
        fun <T, K> FunList<T>.groupBy(f: (T) -> K): Map<K, FunList<T>> = when (this) {
            Nil -> throw NoSuchElementException()
            is Cons -> mapOf(f(head) to this)
        }
    }

    // P.139 : 명령형, 함수형, 진짜 함수형 함수의 성능 비교 함수 작성
    "Example 5-16" {
        fun imperativeWay(intList: List<Int>): Int {
            for (value in intList) {
                val doubleValue = value * value
                if (doubleValue < 10) {
                    return doubleValue
                }
            }

            throw NoSuchElementException("There is no value")
        }

        fun functionalWay(intList: List<Int>): Int =
            intList
                .map { n -> n * n }
                .filter { n -> n < 10 }
                .first()

        fun realFunctionalWay(intList: List<Int>): Int =
            intList.asSequence()
                .map { n -> n * n }
                .filter { n -> n < 10 }
                .first()

        fun main() {
            val bigIntList = (10000000 downTo 1).toList()
            var start = System.currentTimeMillis()

            imperativeWay(bigIntList)
            println("${System.currentTimeMillis() - start} ms")

            start = System.currentTimeMillis()
            functionalWay(bigIntList)
            println("${System.currentTimeMillis() - start} ms")

            start = System.currentTimeMillis()
            realFunctionalWay(bigIntList)
            println("${System.currentTimeMillis() - start} ms")
        }

        main()
    }

    // P.141 : FunStream으로 sum 함수 다시 작성
    "Example 5-17" {
        tailrec fun FunStream<Int>.sum(acc: Int = 0): Int = when (this) {
            Nil2 -> acc
            is Cons2 -> getTail().sum(acc + getHead())
        }
    }

    // P.141 : FunStream으로 product 함수 다시 작성
    "Example 5-18" {
        tailrec fun FunStream<Int>.product(acc: Int = 1): Int = when (this) {
            Nil2 -> acc
            is Cons2 -> getTail().product(acc * getHead())
        }
    }

    // P.141 : FunStream으로 appendTail 함수 다시 작성
    "Example 5-19" {
        fun <T> FunStream<T>.appendTail(value: T, acc: FunStream<T>): FunStream<T> = when (this) {
            Nil2 -> Cons2({ value }) { Nil2 }
            is Cons2 -> Cons2(this.head) { this.tail().appendTail(value) }
        }
    }

    // P.142 : FunStream으로 Filter 함수 다시 작성
    "Example 5-20" {
        fun <T> FunStream<T>.filter(p: (T) -> Boolean): FunStream<T> = when (this) {
            Nil2 -> this
            is Cons2 -> if (p(head())) {
                Cons2({ head() }) { tail().filter(p) }
            } else {
                tail().filter(p)
            }
        }
    }

    // P.142 : FunStream으로 map 함수 다시 작성
    "Example 5-21" {
        fun <T, R> FunStream<T>.map(f: (T) -> R): FunStream<R> = when (this) {
            Nil2 -> Nil2
            is Cons2 -> Cons2({ f(head()) }) { tail().map(f) }
        }
    }
})