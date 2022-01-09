package org.kiworkshop.learningfpinkotlin.seonghunlee

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.seonghunlee.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.seonghunlee.Tree.Node

class Chapter6 : StringSpec({
    "ch6-1" {
        println(Node(0, EmptyTree, EmptyTree))
    }
    "ch6-2" {
        Node(5, EmptyTree, EmptyTree).insert(3) shouldBe Node(5, Node(3, EmptyTree, EmptyTree), EmptyTree)
    }
    "ch6-3" {
        var tree: Tree<Int> = Node(0, EmptyTree, EmptyTree)
        (1..15000).forEach { tree = tree.insert(it) }
    }
    "ch6-4" {
        var tree: Tree<Int> = Node(0, EmptyTree, EmptyTree)
        (1..500).forEach { tree = tree.insert(it) }
        var tree2: Tree<Int> = Node(0, EmptyTree, EmptyTree)
        (1..500).forEach { tree2 = rebuild(path(tree2, it), it) }
        tree shouldBe tree2
    }
    "ch6-5" {
        val tree = Node(5, EmptyTree, EmptyTree).insert(3).insert(1).insert(2).insert(4).insert(11).insert(9).insert(-2)
        tree.contains(5) shouldBe true
        tree.contains(3) shouldBe true
        tree.contains(1) shouldBe true
        tree.contains(2) shouldBe true
        tree.contains(4) shouldBe true
        tree.contains(11) shouldBe true
        tree.contains(9) shouldBe true
        tree.contains(-2) shouldBe true
        tree.contains(0) shouldBe false
    }
})
