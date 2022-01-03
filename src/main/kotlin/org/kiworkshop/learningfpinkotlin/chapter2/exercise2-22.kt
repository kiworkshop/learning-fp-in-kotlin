package org.kiworkshop.learningfpinkotlin.chapter2

fun main() {
    val kotlin: Kotlin = Kotlin()
    kotlin.printFoo() //"Foo" 출력
    kotlin.printBar() //"Kotlin - Bar" 출력
    kotlin.printKotlin() //"Foo Kotlin\nBar Kotlin" 출력
}

/*
* Kotlin interface 특징
*
* 1. 다중 상속이 가능하다.
* 2. 추상함수를 가질 수 있다.
* 3. 함수의 본문을 구현할 수 있다.
* 4. 여러 인터페이스에서 같은 이름의 함수를 가질 수 있다. (ex: printKotlin())
* 5. 추상 프로퍼티를 가질 수 있다.
* */
interface Foo {

    val bar: Int //인터페이스에서 추상 프로퍼티를 직접 할당 할 수 없다.
        get() = 3 //대신, 게터를 구현해서 초기화 한다.

    fun printSomething() //추상함수
    fun printFoo() {
        println("Foo")
    }

    fun printKotlin() { //추상함수 구현 가능
        println("Foo Kotlin")
    }
}

interface Bar {
    fun printBar() {
        println("Bar")
    }

    fun printKotlin() {
        println("Bar Kotlin")
    }
}

class Kotlin : Foo, Bar {

    override fun printSomething() {
        TODO("Not yet implemented")
    }

    override fun printBar() {
        println("Kotlin - Bar")
    }

    override fun printKotlin() {
        super<Foo>.printKotlin()
        super<Bar>.printKotlin()
    }
}