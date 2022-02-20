package org.kiworkshop.learningfpinkotlin.c9

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.c5.funListOf

class Chap9 : StringSpec({
    "Example 1, 3" {
        val x = true
        val y = false
        val z = true

        AnyMonoid().run {
            mappend(m1 = true, m2 = true) shouldBe true
            mappend(m1 = true, m2 = false) shouldBe true
            mappend(m1 = false, m2 = true) shouldBe true
            mappend(m1 = false, m2 = false) shouldBe false

            mappend(true, mempty()) shouldBe true
            mappend(mempty(), true) shouldBe true

            mappend(false, mempty()) shouldBe false
            mappend(mempty(), false) shouldBe false

            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
        }
    }

    "Example 2, 4" {
        val x = true
        val y = false
        val z = true

        AllMonoid().run {
            mappend(m1 = true, m2 = true) shouldBe true
            mappend(m1 = true, m2 = false) shouldBe false
            mappend(m1 = false, m2 = true) shouldBe false
            mappend(m1 = false, m2 = false) shouldBe false

            mappend(true, mempty()) shouldBe true
            mappend(mempty(), true) shouldBe true

            mappend(false, mempty()) shouldBe false
            mappend(mempty(), false) shouldBe false

            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
        }
    }

    "Example 5" {
        AnyMonoid().run {
            mconcat(funListOf(true, true, true)) shouldBe true
            mconcat(funListOf(false, false, false)) shouldBe false
            mconcat(funListOf(false, true, false)) shouldBe true
        }
    }

    "Example 6" {
        AllMonoid().run {
            mconcat(funListOf(true, true, true)) shouldBe true
            mconcat(funListOf(false, false, false)) shouldBe false
            mconcat(funListOf(false, true, false)) shouldBe false
        }
    }

    "Example 7, 8" {
        val x = funListOf(1, 2)
        val y = funListOf(3, 4)
        val z = funListOf(5)

        ListMonoid<Int>().run {
            mappend(funListOf(1, 2), funListOf(3, 4)) shouldBe funListOf(1, 2, 3, 4)

            mappend(mempty(), x) shouldBe x
            mappend(x, mempty()) shouldBe x
            mappend(mappend(x, y), z) shouldBe mappend(x, mappend(y, z))
        }
    }

    "Example 9" {
        val result = ListMonoid<Int>().mconcat(funListOf(funListOf(1, 2), funListOf(3, 4), funListOf(5)))
        result shouldBe funListOf(1, 2, 3, 4, 5)
    }

    "Foldable Binary Tree" {
        val tree = Node(
            1,
            Node(
                2,
                Node(3),
                Node(4),
            ),
            Node(
                5,
                Node(6),
                Node(7),
            ),
        )

        tree.foldLeft(0) { a, b -> a + b } shouldBe 28
        tree.foldLeft(1) { a, b -> a * b } shouldBe 5040

        tree.foldMap({ a -> a * 2 }, SumMonoid()) shouldBe 56
        tree.foldMap({ a -> a - 1 }, ProductMonoid()) shouldBe 0
    }

    "Example 10" {
        val list = Cons(1, Cons(2, Cons(3, Nil)))
        list.foldLeft(0) { a, b -> a + b } shouldBe 6
        list.foldMap({ a -> a * 2 }, SumMonoid()) shouldBe 12
    }

    "Example 11" {
        val tree = Tree.Node(
            1,
            Cons(
                Tree.Node(2),
                Cons(
                    Tree.Node(3),
                    Nil,
                )
            )
        )
        tree.foldLeft(0) { a, b -> a + b } shouldBe 6
        tree.foldMap({ a -> a * 2 }, SumMonoid()) shouldBe 12
    }

    "Example 12" {
        val list = Cons(1, Cons(2, Cons(3, Nil)))
        list.contains(1) shouldBe true
        list.contains(2) shouldBe true
        list.contains(3) shouldBe true
        list.contains(4) shouldBe false
    }

    "Example 13" {
        val tree = Tree.Node(
            1,
            Cons(
                Tree.Node(2),
                Cons(
                    Tree.Node(3),
                    Nil,
                )
            )
        )
        tree.contains(1) shouldBe true
        tree.contains(2) shouldBe true
        tree.contains(3) shouldBe true
        tree.contains(4) shouldBe false
    }

    "Example 14" {
        val tree = Tree.Node(
            1,
            Cons(
                Tree.Node(2),
                Cons(
                    Tree.Node(3),
                    Nil,
                )
            )
        )
        tree.toFunList() shouldBe funListOf(2, 3, 1)
    }
})
