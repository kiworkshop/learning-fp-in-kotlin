package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter8 : StringSpec({

    "Code 8-7, 8-8, 8-9" {
        println(AJust(10).fmap { it + 10 })
        println(AMaybe.pure(10))
        println(AJust(10) apply AJust { it * 2 })
    }

    "Code 8-11" {
        AMaybe.pure(10)
            .apply(AJust({ x: Int -> x * 2 }))
            .apply(AJust({ x: Int -> x + 10 })) shouldBe AJust(30)
    }

    "Code 8-12" {
        /*
         * Type mismatch.
         * Required: ((Int) → Int) → TypeVariable(B)
         * Found: IntegerLiteralType[Int,Long,Byte,Short]
         */
        //    AMaybe.pure({ x: Int -> x * 2}) apply AJust(5)
    }

    "Code 8-15" {
        Just(10).fmap { it + 10 } shouldBe Just(20)
        Nothing.fmap { it: Int -> it + 10 } shouldBe Nothing
        Maybe.pure(10) shouldBe Just(10)
        println(Maybe.pure { x: Int -> x * 2 })
        Maybe.pure { x: Int -> x * 2 } apply Just(10) shouldBe Just(20)
        Maybe.pure { x: Int -> x * 2 } apply Nothing shouldBe Nothing
    }

    "Code 8-18" {
        Maybe.pure({ x: Int, y: Int -> x * y }.curried()) apply Just(10) apply Just(20) shouldBe Just(200)
        Maybe.pure({ x: Int, y: Int, z: Int -> x * y + z }.curried()) apply Just(10) apply Just(20) apply Just(30) shouldBe Just(
            230
        )
    }

    "Code 8-21" {
        val tree = Node(1, listOf(Node(2), Node(3)))
        tree.fmap { it * 2 } shouldBe Node(2, listOf(Node(4), Node(6)))
    }

    "Code 8-24" {
        val tree = Node(1, listOf(Node(2), Node(3)))
        print(tree.fmap { it * 2 })
        Tree.pure { x: Int -> x * 2 } apply tree shouldBe Node(2, listOf(Node(4), Node(6)))
        print(Tree.pure { x: Int -> x * 2 } apply tree)
    }

    "Code 8-25" {
        println(
            Tree.pure({ x: Int, y: Int -> x * y }.curried())
                .apply(Node(1, listOf(Node(2), Node(3))))
                .apply(Node(4, listOf(Node(5), Node(6))))
        )
    }

    // 항등(identity) : pure(identity) apply af = af
    "Code 8-36" {
        val maybeAf = Just(10)
        val leftMaybe = Maybe.pure(identify()) apply maybeAf
        leftMaybe.toString() shouldBe maybeAf.toString()

        val treeAf = Node(1, listOf(Node(2), Node(3)))
        val leftTree = Tree.pure(identify()) apply treeAf
        leftTree.toString() shouldBe treeAf.toString()

        val eitherAf = Right(10)
        val leftEither = Either.pure(identify()) apply eitherAf
        leftEither.toString() shouldBe eitherAf.toString()
    }

    // 합성(composition) : pure(compose) apply af1 apply af2 apply af3 = af1 apply af2 apply af3
    "Code 8-40" {
        val maybeAf1 = Just({ x: Int -> x * 2 })
        val maybeAf2 = Just({ x: Int -> x + 1 })
        val maybeAf3 = Just(30)
        val leftMaybe = Maybe.pure(compose<Int, Int, Int>().curried()) apply maybeAf1 apply maybeAf2 apply maybeAf3
        val rightMaybe = maybeAf1 apply (maybeAf2 apply maybeAf3)
        leftMaybe.toString() shouldBe rightMaybe.toString()
        println(leftMaybe.toString())
    }

    // 준동형 사상(homomorphism) : pure(function) apply pure(x) = pure(function(x))
    "Code 8-42" {
        val function = { x: Int -> x * 2 }
        val x = 10

        val leftMaybe = Maybe.pure(function) apply Maybe.pure(x)
        val rightMaybe = Maybe.pure(function(x))
        leftMaybe.toString() shouldBe rightMaybe.toString()
        println(leftMaybe.toString())
    }

    // 교환(interchange) : af apply pure(x) = pure(of(x)) apply af
    "Code 8-44" {
        val x = 10

        val maybeAf = Just({ a: Int -> a * 2 })
        val leftMaybe = maybeAf apply Maybe.pure(x)
        val rightMaybe = Maybe.pure(of<Int, Int>(x)) apply maybeAf
        leftMaybe.toString() shouldBe rightMaybe.toString()
        println(leftMaybe.toString())
    }

    // pure(function) apply af = af.fmap(function)
    "Code 8-47" {
        val function = { x: Int -> x * 2 }

        val maybeAf = Just(10)
        val leftMaybe = Maybe.pure(function) apply maybeAf
        val rightMaybe = maybeAf.fmap(function)
        leftMaybe.toString() shouldBe rightMaybe.toString()
        println(leftMaybe.toString())
    }

    "Code 8-50, 8-51" {
        val lifted: (Maybe<Int>, Maybe<FunList<Int>>) -> Maybe<FunList.Cons<Int>> =
            Maybe.liftA2 { x: Int, y: FunList<Int> -> FunList.Cons(x, y) }

        lifted(Just(3), Just(funListOf(10)))
    }

    "Code 8-55" {
        when (val result = Maybe.sequenceAByFoldRight(funListOf(Just(10), Just(20)))) {
            is Nothing -> Nothing
            is Just -> println(result.value)
        }
    }

    "Example 8-1" {
        val funList = funListOf(1, 2, 3, 4)
        val partial1: Functor<(Int) -> Int> = funList.fmap { x: Int -> { y: Int -> x * y } }
        val partial2: Functor<(Int) -> Int> = funList.fmap { x: Int -> { y: Int -> x + y } }

        partial1.fmap { it(5) } shouldBe funListOf(5, 10, 15, 20)
        partial2.fmap { it(0) } shouldBe funListOf(1, 2, 3, 4)
        partial2.fmap { it(2) } shouldBe funListOf(3, 4, 5, 6)
    }

    "Example 8-2" {
        FunList.pure(10) shouldBe funListOf(10)
        funListOf({ x: Int -> x * x }) apply funListOf(1, 2, 3) shouldBe funListOf(1, 4, 9)
    }

    "Example 8-3" {
        FunList.pure(10) shouldBe funListOf(10)
        funListOf({ x: Int -> x * x }) apply funListOf(1, 2, 3) shouldBe funListOf(1, 4, 9)
        funListOf({ x: Int -> x + 1 }) apply funListOf(1, 2, 3) shouldBe funListOf(2, 3, 4)

        funListOf(1, 2) append funListOf(3, 4) shouldBe funListOf(1, 2, 3, 4)
    }

    "Example 8-4" {
        Tree.pure({ x: Int, y: Int -> x * y }.curried())
            .apply(Node(4, listOf(Node(5), Node(6))))
            .apply(Node(1, listOf(Node(2), Node(3))))
            .shouldBe(
                Node(
                    4,
                    listOf(
                        Node(8), Node(12),
                        Node(5, listOf(Node(10), Node(15))),
                        Node(6, listOf(Node(12), Node(18)))
                    )
                )

            )
    }

    "Example 8-5" {
        println(
            Tree.pure({ x: Int, y: Int -> x * y }.curried())
                .apply(Node(1, listOf(Node(2, listOf(Node(3))), Node(4))))
                .apply(Node(5, listOf(Node(6), Node(7, listOf(Node(8), Node(9))))))
        )
        Tree.pure({ x: Int, y: Int -> x * y }.curried())
            .apply(Node(1, listOf(Node(2, listOf(Node(3))), Node(4))))
            .apply(Node(5, listOf(Node(6), Node(7, listOf(Node(8), Node(9))))))
            .shouldBe(
                Node(
                    5 * 1,
                    listOf(
                        Node(6 * 1),
                        Node(7 * 1, listOf(Node(8 * 1), Node(9 * 1))),
                        Node(
                            5 * 2,
                            listOf(
                                Node(6 * 2),
                                Node(7 * 2, listOf(Node(8 * 2), Node(9 * 2))),
                                Node(5 * 3, listOf(Node(6 * 3), Node(7 * 3, listOf(Node(8 * 3), Node(9 * 3)))))
                            )
                        ),
                        Node(5 * 4, listOf(Node(6 * 4), Node(7 * 4, listOf(Node(8 * 4), Node(9 * 4)))))
                    )
                )
            )
    }

    "Example 8-6" {
        funListOf({ x: Int -> x * 5 }, { x: Int -> x + 10 }) zipList funListOf(10, 20, 30) shouldBe funListOf(
            10 * 5,
            20 + 10
        )
    }

    "Example 8-7" {
        val listAf = funListOf(1, 2)
        val leftList = FunList.pure(identify()) apply listAf
        leftList.toString() shouldBe listAf.toString()
    }

    "Example 8-8" {
        val listAf1 = FunList.Cons({ x: Int -> x * 2 }, FunList.Nil)
        val listAf2 = FunList.Cons({ x: Int -> x + 1 }, FunList.Nil)
        val listAf3 = FunList.Cons(30, FunList.Nil)
        val leftList = FunList.pure(compose<Int, Int, Int>().curried()) apply listAf1 apply listAf2 apply listAf3
        val rightList = listAf1 apply (listAf2 apply listAf3)
        leftList.toString() shouldBe rightList.toString()
    }

    "Example 8-9" {
        val function = { x: Int -> x * 2 }
        val x = 10

        val leftList = FunList.pure(function) apply FunList.pure(x)
        val rightList = FunList.pure(function(x))
        leftList.toString() shouldBe rightList.toString()
        println(leftList.toString())
    }

    "Example 8-10" {
        val x = 10

        val listAf = FunList.Cons({ a: Int -> a * 2 }, FunList.Nil)
        val leftList = listAf apply FunList.pure(x)
        val rightList = FunList.pure(of<Int, Int>(x)) apply listAf
        leftList.toString() shouldBe rightList.toString()
        println(leftList.toString())
    }

    "Example 8-11" {
        val function = { x: Int -> x * 2 }

        val listAf = FunList.Cons(10, FunList.Nil)
        val leftList = FunList.pure(function) apply listAf
        val rightList = listAf.fmap(function)
        leftList.toString() shouldBe rightList.toString()
        println(leftList.toString())
    }

    "Example 8-12" {
        val lifted = FunList.liftA2 { x: Int, y: Int -> x + y }
        val result = lifted(funListOf(1, 2), funListOf(4, 5))

        result shouldBe funListOf(5, 6, 6, 7)
    }

    "Example 8-13" {
        val lifted = Tree.liftA2 { x: Int, y: Int -> x + y }
        val result = lifted(Node(1, listOf(Node(2), Node(3))), Node(5, listOf(Node(6), Node(7))))

        result.toString() shouldBe "6 [7 [], 8 [], 7 [8 [], 9 []], 8 [9 [], 10 []]]"
    }

    "Example 8-14" {
        val lifted =
            Either.liftA2<String, Int, FunList<Int>, FunList<Int>> { x: Int, y: FunList<Int> -> FunList.Cons(x, y) }

        lifted(Either.pure(1), Either.pure(funListOf(2, 3))) shouldBe Either.pure(funListOf(1, 2, 3))
        lifted(Left("error"), Either.pure(funListOf(2, 3))) shouldBe Left("error")
    }

    "Example 8-15" {
        val lifted = Maybe.liftA3 { x: Int, y: Int, z: FunList<Int> -> FunList.Cons(x, FunList.Cons(y, z)) }

        lifted(Just(1), Just(2), Just(funListOf(3))) shouldBe Just(funListOf(1, 2, 3))
    }

    "Example 8-16" {
    }

    "Example 8-17" {
    }

    "Example 8-18" {
        val result = Either.sequenceA(funListOf(Either.pure(1), Either.pure(2)))

        result shouldBe Either.pure(funListOf(1, 2))
    }
})
