package org.kiworkshop.learningfpinkotlin

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class Chapter6 : StringSpec({

    "Ex 1" {
        val tree = Leaf(3, Leaf(4, Nil, Nil), Leaf(5, Nil, Nil))
        tree.left shouldBe Leaf(4, Nil, Nil)
    }

    "Ex 2" {
        val tree = Nil
        val tree1 = tree.insert(1)
        tree1 shouldBe Leaf(1, Nil, Nil)

        val tree2 = tree1.insert(2)
        tree2 shouldBe Leaf(1, Nil, Leaf(2, Nil, Nil))

        val tree3 = tree2.insert(3)
        tree3 shouldBe Leaf(1, Nil, Leaf(2, Nil, Leaf(3, Nil, Nil)))

        val tree4 = tree3.insert(1)
        tree4 shouldBe Leaf(1, Leaf(1, Nil, Nil), Leaf(2, Nil, Leaf(3, Nil, Nil)))
    }

    "Ex 3" {
        var tree: Tree<Int> = Nil
        var i = 0
        while (true) {
            tree = tree.insert(3)
            ++i
            println(i) // 2544번
        }
    }

    "Ex 4" {
    }

    "Ex 5" {
        val tree = Leaf(1, Leaf(1, Nil, Nil), Leaf(2, Nil, Leaf(3, Nil, Nil)))

        tree.contains(3) shouldBe true
        tree.contains(4) shouldBe false
    }
})
