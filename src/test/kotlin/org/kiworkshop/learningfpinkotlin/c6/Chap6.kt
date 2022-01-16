package org.kiworkshop.learningfpinkotlin.c6

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


class Chap6 : StringSpec({
    "Example 6-1" {
        val stringTree = initializeTree<String>()
    }

    "Example 6-2" {
        val tree = initializeTree<Int>()
            .insert(1)
            .insert(2)
            .insert(0)
            .insert(-1)
            .insert(5)
            .insert(4)
        println(tree.toTreeString())
    }

    "Example 6-3" {
        @Suppress("NAME_SHADOWING")
        fun insert(tree: Tree<Int>, count: Int): Tree<Int> {
            var tree = tree
            (1..count).forEach {
                println(it)
                tree = tree.insert(it)
            }
            return tree
        }

        shouldThrow<StackOverflowError> { insert(initializeTree(), 30_000) }
    }

    "Example 6-4" {
        val tree = initializeTree<Int>()
            .insertTailrec(1)
            .insertTailrec(2)
            .insertTailrec(0)
            .insertTailrec(-1)
            .insertTailrec(5)
            .insertTailrec(4)
        println(tree.toTreeString())

        @Suppress("NAME_SHADOWING")
        fun insert(tree: Tree<Int>, count: Int): Tree<Int> {
            var tree = tree
            (1..count).forEach {
                println(it)
                tree = tree.insertTailrec(it)
            }
            return tree
        }

        shouldNotThrow<StackOverflowError> { insert(initializeTree(), 30_000) }
    }

    "Example 6-5" {
        val tree = initializeTree<Int>()
            .insertTailrec(1)
            .insertTailrec(2)
            .insertTailrec(0)
            .insertTailrec(-1)
            .insertTailrec(5)
            .insertTailrec(4)

        tree.contains(0) shouldBe true
        tree.contains(-2) shouldBe false
    }
})
