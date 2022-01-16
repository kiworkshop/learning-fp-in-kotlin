package org.kiworkshop.learningfpinkotlin.c7

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.beInstanceOf
import org.kiworkshop.learningfpinkotlin.c4.curried
import org.kiworkshop.learningfpinkotlin.c6.EmptyTree
import org.kiworkshop.learningfpinkotlin.c6.Node
import org.kiworkshop.learningfpinkotlin.compose

class Chap7 : StringSpec({
    "Example 7-1" {
        val funList = FunList.Nil
            .addHead(1)
            .addHead(2)

        val newList = funList.fmap { it + 1 }

        funList.first() shouldBe 2
        funList.size() shouldBe 2
        newList.first() shouldBe 3
        newList.size() shouldBe 2
    }

    "Either" {
        fun divideTenBy(n: Int): Either<String, Int> = try {
            Right(10 / n)
        } catch (_: ArithmeticException) {
            Left("divide by zero")
        }

        divideTenBy(5) shouldBe Right(2)
        divideTenBy(0) should beInstanceOf(Left::class)
        divideTenBy(5).fmap { it * 2 } shouldBe Right(4)
        divideTenBy(0).fmap { it * 2 } should beInstanceOf(Left::class)
    }

    "Unary" {
        val f = { a: Int -> a + 1 }
        val g = { b: Int -> b * 2 }
        val fg = UnaryFunction(g).fmap(f)
        fg(5) shouldBe 11

        val h = { c: Int -> c * 2 }
        val k = { d: Int -> Just(d) }
        val kh = UnaryFunction(h).fmap(k)
        kh(5) shouldBe Just(10)
    }

    "identity" {
        Nothing.fmap { identity(it) } shouldBe identity(Nothing)
        Just(5).fmap { identity(it) } shouldBe identity(Just(5))

        val tree = Node(1, Node.leaf(2), Node.leaf(3))
        EmptyTree.fmap { identity(it) } shouldBe identity(EmptyTree)
        tree.fmap { identity(it) } shouldBe identity(tree)

        Left("error").fmap { identity(it) } shouldBe identity(Left("error"))
        Right(5).fmap { identity(it) } shouldBe identity(Right(5))
    }

    "compose" {
        val f = { a: Int -> a + 1 }
        val g = { b: Int -> b * 2 }

        val nothingLeft = Nothing.fmap(f compose g)
        val nothingRight = Nothing.fmap(g).fmap(f)
        nothingLeft shouldBe nothingRight

        val justLeft = Just(5).fmap(f compose g)
        val justRight = Just(5).fmap(g).fmap(f)
        justLeft shouldBe justRight

        val tree = Node(1, Node.leaf(2), Node.leaf(3))
        EmptyTree.fmap(f compose g) shouldBe EmptyTree.fmap(g).fmap(f)
        tree.fmap(f compose g) shouldBe tree.fmap(g).fmap(f)

        Left("error").fmap(f compose g) shouldBe Left("error").fmap(g).fmap(f)
        Right(5).fmap(f compose g) shouldBe Right(5).fmap(g).fmap(f)
    }

    "Example 7-2" {
        val funList = FunList.Nil
            .addHead(1)
            .addHead(2)
        FunList.Nil.fmap { identity(it) } shouldBe identity(FunList.Nil)
        funList.fmap { identity(it) } shouldBe identity(funList)

        val f = { a: Int -> a + 1 }
        val g = { b: Int -> b * 2 }

        FunList.Nil.fmap(f compose g) shouldBe FunList.Nil.fmap(g).fmap(f)
        funList.fmap(f compose g) shouldBe funList.fmap(g).fmap(f)
    }

    "NotFunctor" {
        NothingCounter.fmap { x: Int -> x * 2 } shouldBe NothingCounter
        JustCounter(3, 1).fmap { it * 2 } shouldBe JustCounter(6, 2)

        NothingCounter.fmap { identity(it) } shouldBe identity(NothingCounter)
        JustCounter(3, 1).fmap { identity(it) } shouldNotBe identity(JustCounter(3, 1))

        val f = { a: Int -> a + 1 }
        val g = { b: Int -> b * 2 }
        NothingCounter.fmap(f compose g) shouldBe NothingCounter.fmap(g).fmap(f)
        JustCounter(3, 1).fmap(f compose g) shouldNotBe JustCounter(3, 1).fmap(g).fmap(f)
    }

    "Curring" {
        val product: (Int, Int) -> Int = { a, b -> a * b }
        val curriedProduct = product.curried()

        Just(10).fmap(curriedProduct).fmap { it(2) } shouldBe Just(20)
    }
})
