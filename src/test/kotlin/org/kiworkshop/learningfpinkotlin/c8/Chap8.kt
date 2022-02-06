package org.kiworkshop.learningfpinkotlin.c8

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.c4.curried
import org.kiworkshop.learningfpinkotlin.c7.FunList
import org.kiworkshop.learningfpinkotlin.c7.Just
import org.kiworkshop.learningfpinkotlin.c7.Maybe
import org.kiworkshop.learningfpinkotlin.c7.funListOf


class Chap8 : StringSpec({
    "Example 8-1" {
        val product = { a: Int, b: Int -> a * b }.curried()
        val productListFunction = funListOf(1, 2, 3, 4).fmap(product)

        productListFunction.fmap { it(5) } shouldBe funListOf(5, 10, 15, 20)
    }

    "AMaybe" {
        AJust(10).fmap { it * 2 } shouldBe AJust(20)
        ANothing.fmap { x: Int -> x * 2 } shouldBe ANothing

        AJust(10).pure(20) shouldBe AJust(20)
        AMaybe.pure(20) shouldBe AJust(20)

        AJust(10) apply AJust { x: Int -> x * 2 } shouldBe AJust(20)
        ANothing apply AJust { x: Int -> x * 2 } shouldBe ANothing
    }

    "Example 8-2" {
        val applied = FunList.pure { x: Int -> x * 2 }

        funListOf(1, 2, 3) apply applied shouldBe funListOf(2, 4, 6)
    }

    "Applicative Style" {
        val result = AMaybe.pure(10) apply AJust { it * 2 } apply AJust { it + 5 }
        result shouldBe AJust(25)

        // compile error
        // AMaybe.pure { x: Int -> x * 2 } apply AJust(5)

        AMaybe.pure { x: Int -> x * 2 } apply AJust { it(5) } shouldBe AJust(10)

        Maybe.pure { x: Int -> x * 2 } apply Just(10) shouldBe Just(20)

        Maybe.pure({ x: Int, y: Int -> x - y }.curried()) apply Just(10) apply Just(5) shouldBe Just(5)
    }

    "Example 8-3" {
        // append
        aFunListOf(1, 2, 3) append aFunListOf(4, 5) shouldBe aFunListOf(1, 2, 3, 4, 5)

        // pure apply
        AFunList.pure { x: Int -> x * 2 } apply aFunListOf(1, 2, 3, 4) shouldBe aFunListOf(2, 4, 6, 8)

        // multiple apply
        aFunListOf({ x: Int -> x * 2 }, { x: Int -> x - 2 }) apply aFunListOf(1, 2) shouldBe aFunListOf(2, 4, -1, 0)

        // curried
        // 첫 apply에 aFunListOf(1 - y, 2 - y, 3 - y) 생성
        // 두번째 apply에 aFunListOf(1 - 4, 1 - 5, 2 - 4, 2 - 5, 3 - 4, 3 - 5) 생성
        val result =
            AFunList.pure({ x: Int, y: Int -> x - y }.curried()) apply aFunListOf(1, 2, 3) apply aFunListOf(4, 5)
        result shouldBe aFunListOf(-3, -4, -2, -3, -1, -2)
    }

    "Tree" {
        val tree = FNode(1, listOf(FNode(2), FNode(3)))

        tree.fmap { it * 2 } shouldBe FNode(2, listOf(FNode(4), FNode(6)))
    }

    "Example 8-4" {
        val first = FNode(4, listOf(FNode(5), FNode(6)))
        val second = FNode(1, listOf(FNode(2), FNode(3)))

        val func = FTree.pure({ x: Int, y: Int -> x * y }.curried())

        func apply first apply second shouldBe FNode(
            4,
            listOf(
                FNode(
                    8
                ),
                FNode(
                    12
                ),
                FNode(
                    5,
                    listOf(
                        FNode(
                            10
                        ), FNode(
                            15
                        )
                    )
                ),
                FNode(
                    6,
                    listOf(
                        FNode(
                            12
                        ), FNode(
                            18
                        )
                    )
                )
            ),
        )
    }

    "Example 8-6" {
        val list = zipFunListOf(10, 20, 30)
        val func = zipFunListOf({ x: Int -> x * 5 }, { x: Int -> x + 10 })

        func apply list shouldBe zipFunListOf(50, 30)
    }


    "Example 8-7" {
        fun identity(): (Int) -> Int = { x: Int -> x }

        val funListAf = aFunListOf(1)
        val leftFunList: AFunList<Int> = AFunList.pure(identity()) apply funListAf

        leftFunList shouldBe funListAf
    }

    "Example 8-8" {
        fun <P1, P2, P3> compose() = { f: (P2) -> P3, g: (P1) -> P2, v: P1 -> f(g(v)) }

        val funListAf1 = aFunListOf({ x: Int -> x * 2 })
        val funListAf2 = aFunListOf({ x: Int -> x - 2 })
        val funListAf3 = aFunListOf(30)

        val leftFunList =
            AFunList.pure(compose<Int, Int, Int>().curried()) apply funListAf1 apply funListAf2 apply funListAf3
        val rightFunList = funListAf1 apply (funListAf2 apply funListAf3)

        leftFunList shouldBe rightFunList
    }

    "Example 8-9" {
        fun function() = { x: Int -> x * 2 }
        val x = 10

        val leftFunList = AFunList.pure(function()) apply AFunList.pure(x)
        val rightFunList = AFunList.pure(function()(x))

        leftFunList shouldBe rightFunList
    }

    "Example 8-10" {
        fun <T, R> of(value: T) = { f: (T) -> R -> f(value) }

        val x = 10

        val funListAf = aFunListOf({ y: Int -> y * 2 })
        val leftFunList = funListAf apply AFunList.pure(x)
        val rightFunList = AFunList.pure(of<Int, Int>(x)) apply funListAf

        leftFunList shouldBe rightFunList
    }

    "Example 8-11" {
        fun function() = { x: Int -> x * 2 }

        val funListAf = aFunListOf(10)
        val leftFunList = AFunList.pure(function()) apply funListAf
        val rightFunList = funListAf.fmap(function())

        leftFunList shouldBe rightFunList
    }

    "Example 8-12" {
        fun <A, B, R> liftA2(binary: (A, B) -> R) = { f1: AFunList<A>, f2: AFunList<B> ->
            AFunList.pure(binary.curried()) apply f1 apply f2
        }

        val lifted = liftA2 { x: Int, y: String -> x + y.toInt() }

        lifted(aFunListOf(1), aFunListOf("3")) shouldBe aFunListOf(4)
        lifted(aFunListOf(3, 4), aFunListOf("4", "5")) shouldBe aFunListOf(7, 8, 8, 9)
    }

    "Example 8-13" {
        fun <A, B, R> liftA2(binary: (A, B) -> R) = { f1: FNode<A>, f2: FNode<B> ->
            FTree.pure(binary.curried()) apply f1 apply f2
        }

        val lifted = liftA2 { x: Int, y: String -> x + y.toInt() }

        lifted(FNode(1, listOf(FNode(2))), FNode("3", listOf(FNode("5")))) shouldBe
                FNode(
                    4,
                    listOf(
                        FNode(
                            6
                        ),
                        FNode(
                            5,
                            listOf(
                                FNode(
                                    7
                                )
                            )
                        )
                    )
                )
    }

    "Example 8-15" {
        fun <A, B, C, R> liftA3(ternary: (A, B, C) -> R) = { f1: Maybe<A>, f2: Maybe<B>, f3: Maybe<C> ->
            Maybe.pure(ternary.curried()) apply f1 apply f2 apply f3
        }

        val lifted = liftA3 { x: Int, y: List<Int>, z: Array<Int> -> listOf(x, y.first(), z.first()) }
        lifted(Maybe.pure(3), Maybe.pure(listOf(4)), Maybe.pure(arrayOf(5))) shouldBe
                Maybe.pure(listOf(3, 4, 5))
    }
})
