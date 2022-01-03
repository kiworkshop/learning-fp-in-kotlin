package org.kiworkshop.learningfpinkotlin.chapter6

import org.kiworkshop.learningfpinkotlin.chapter6.Tree.EmptyTree
import org.kiworkshop.learningfpinkotlin.chapter6.Tree.Node

/*
* 타입 시스템 용어
*
* 타입 변수 : 제네릭으로 선언된 T
* 값 생성자 : 타입의 값을 반환하는 것 (enum의 경우 값 생성자는 타입으로 사용될 수 없다.)
*
* ex) sealed Class Maybe<T>
* 타입 생성자 :  Maybe
* 타입 매개변수 : T
* */

/*
* 연습문제 6-1
* */
sealed class Tree<out T> {
    object EmptyTree : Tree<Nothing>()
    data class Node<out T>(val value: T, val left: Tree<T>, val right: Tree<T>) : Tree<T>()
}

/*
* 연습문제 6-2
* */
fun Tree<Int>.insert(elem: Int): Tree<Int> = when (this) {
    EmptyTree -> {
        Node(elem, EmptyTree, EmptyTree)
    }
    is Node -> {
        if (elem < this.value)
            Node(this.value, this.left.insert(elem), this.right)
        else
            Node(this.value, this.left, this.right.insert(elem))
    }
}

// TODO
tailrec fun Tree<Int>.insertTailrec(elem: Int): Tree<Int> = when (this) {
    EmptyTree -> {
        Node(elem, EmptyTree, EmptyTree)
    }
    is Node -> {
        if (elem < this.value)
            this.left.insertTailrec(elem)
        else
            this.right.insertTailrec(elem)
    }
}

/*
* 연습문제 6-5
* */
fun Tree<Int>.contains(elem: Int): Boolean = when (this) {
    EmptyTree -> false
    is Node -> {
        if (this.value == elem) true
        else
            this.left.contains(elem) || this.right.contains(elem)
    }
}

fun main() {
//    (1..100000).fold(EmptyTree as Tree<Int>) { acc, i ->
//        acc.insert(i) //stackOverFlowError
//    }
    (1..100000).fold(EmptyTree as Tree<Int>) { acc, i ->
        acc.insertTailrec(i)
    }
}
