package org.kiworkshop.learningfpinkotlin.seonghunlee

fun higherOrderFunction1(func: () -> Unit) {
    func()
}

fun higherOrderFunction2(): () -> Unit {
    return { println("Hello, Kotlin") }
}

interface Calcable {
    fun calc(x: Int, y: Int): Int
}

class Sum : Calcable {
    override fun calc(x: Int, y: Int): Int {
        return x + y
    }
}

class Minus : Calcable {
    override fun calc(x: Int, y: Int): Int {
        return x - y
    }
}

class Product : Calcable {
    override fun calc(x: Int, y: Int): Int {
        return x * y
    }
}

fun higherOrder(calc: (Int, Int) -> Int, x: Int, y: Int): Int = calc(x, y)

class TwiceSum : Calcable {
    override fun calc(x: Int, y: Int): Int {
        return (x + y) * 2
    }
}

class PartialFunction<in P, out R>(
    private val condition: (P) -> Boolean,
    private val f: (P) -> R
) : (P) -> R {
    override fun invoke(p: P): R = when {
        condition(p) -> f(p)
        else -> throw IllegalArgumentException("$p isn't supported.")
    }

    fun invokeOrElse(p: P, default: @UnsafeVariance R): R = when {
        condition(p) -> f(p)
        else -> default
    }

    fun orElse(that: PartialFunction<@UnsafeVariance P, @UnsafeVariance R>): PartialFunction<P, R> =
        PartialFunction(
            condition = { p -> this.condition(p) || that.condition(p) },
            f = { p ->
                when {
                    this.condition(p) -> this.invoke(p)
                    that.condition(p) -> that.invoke(p)
                    else -> throw IllegalArgumentException("$p isn't supported")
                }
            }
        )

    fun isDefinedAt(p: P): Boolean = condition(p)
}

fun <P, R> ((P) -> R).toPartialFunction(definedAt: (P) -> Boolean): PartialFunction<P, R> =
    PartialFunction(definedAt, this)

fun <P1, P2, R> ((P1, P2) -> R).partial1(p1: P1): (P2) -> R {
    return { p2 -> this(p1, p2) }
}

fun <P1, P2, R> ((P1, P2) -> R).partial2(p2: P2): (P1) -> R {
    return { p1 -> this(p1, p2) }
}

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial1(p1: P1): (P2, P3) -> R {
    return { p2, p3 -> this(p1, p2, p3) }
}

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial2(p2: P2): (P1, P3) -> R {
    return { p1, p3 -> this(p1, p2, p3) }
}

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial3(p3: P3): (P1, P2) -> R {
    return { p1, p2 -> this(p1, p2, p3) }
}

fun multiThree(a: Int, b: Int, c: Int) = a * b * c
fun multiThree(a: Int) = { b: Int -> { c: Int -> a * b * c } }

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).curried(): (P1) -> (P2) -> (P3) -> R =
    { p1: P1 -> { p2: P2 -> { p3: P3 -> this(p1, p2, p3) } } }

fun <P1, P2, P3, R> ((P1) -> (P2) -> (P3) -> R).uncurried(): ((P1, P2, P3) -> R) =
    { p1: P1, p2: P2, p3: P3 -> this(p1)(p2)(p3) }

fun main() {
    val multiThree = { a: Int, b: Int, c: Int -> a * b * c }
    val curried = multiThree.curried()
    println(curried(1)(2)(3))

    val uncurried = curried.uncurried()
    println(uncurried(1, 2, 3))

//    println(multiThree(1, 2, 3))
//
//    val partial1 = multiThree(1)
//    val partial2 = partial1(2)
//    val partial3 = partial2(3)
//
//    println(partial3)
//
//    println(multiThree(1)(2)(3))

//    val func = { a: String, b: String -> a + b }
//    val partiallyAppliedFunc1 = func.partial1("Hello")
//    val result1 = partiallyAppliedFunc1("World")
//    println(result1)
//
//    val partiallyAppliedFunc2 = func.partial2("World")
//    val result2 = partiallyAppliedFunc2("Hello")
//    println(result2)
//    val condition: (Int) -> Boolean = { it.rem(2) == 0 }
//    val body: (Int) -> String = { "$it is even" }
//    val isEven = body.toPartialFunction(condition)
//
//    if (isEven.isDefinedAt(100)) {
//        print(isEven(100))
//    }

//    val isEven = PartialFunction<Int, String>({ it % 2 == 0 }, { "$it is even" })
//    println(isEven(100))

//    val condition: (Int) -> Boolean = { it in 1..3 }
//    val body: (Int) -> String = {
//        when (it) {
//            1 -> "One!"
//            2 -> "Two!"
//            3 -> "Three!"
//            else -> throw IllegalArgumentException()
//        }
//    }
//
//    val oneTowThree = PartialFunction(condition, body)
//    println(oneTowThree.invoke(1))

//    val over10Values: ArrayList<Int> = ArrayList()
//    val ints: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//    for (element in ints) {
//        val twiceInt = element * 2
//        if (twiceInt > 10) over10Values.add(twiceInt)
//    }
//    println(over10Values)
//    val result = ints.map { it * 2 }
//        .filter { it > 10 }
//    println(result)

//    val twiceSum: (Int, Int) -> Int = { x, y -> (x + y) * 2 }
//    println(higherOrder(twiceSum, 8, 2))
//    val calcTwiceSum = TwiceSum()
//    println(calcTwiceSum.calc(8, 2))

//    val sum: (Int, Int) -> Int = { x, y -> x + y }
//    val minus: (Int, Int) -> Int = { x, y -> x - y }
//    val product: (Int, Int) -> Int = { x, y -> x * y }
//
//    println(higherOrder(sum, 1, 5))
//    println(higherOrder(minus, 1, 5))
//    println(higherOrder(product, 1, 5))

//    val calcSum = Sum()
//    val calcMinus = Minus()
//    val calcProduct = Product()

//    println(calcSum.calc(1, 5))
//    println(calcMinus.calc(5, 1))
//    println(calcProduct.calc(2, 5))
//    higherOrderFunction2()()
}

class Ch4SourceCode
