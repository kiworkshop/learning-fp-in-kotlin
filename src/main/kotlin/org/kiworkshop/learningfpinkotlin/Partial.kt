package org.kiworkshop.learningfpinkotlin

// in 반공변 타입 S 가 T 의 하위 타입일 때 Box[S] 가 Box[T] 의 상위 타입인 것.
class PartialFunction<in P, out R>(
    private val condition: (P) -> Boolean,
    private val f: (P) -> R
) : (P) -> R {
    override fun invoke(p: P): R = when {
        condition(p) -> f(p)
        else -> throw IllegalArgumentException("$p isn't supported")
    }

    fun invokeOrElse(p: P, default: @UnsafeVariance R): R = when {
        condition(p) -> invoke(p)
        else -> default
    }

    fun orElse(that: PartialFunction<@UnsafeVariance P, @UnsafeVariance R>): PartialFunction<P, R> {
        return PartialFunction({ this.condition(it) || that.condition(it) }) {
            when {
                this.condition(it) -> this(it)
                else -> that(it)
            }
        }
    }

    fun isDefinedAt(p: P): Boolean = condition(p)
}

fun <P, R> ((P) -> R).toPartialFunction(definedAt: (P) -> Boolean): PartialFunction<P, R> =
    PartialFunction(definedAt, this)

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial1(p1: P1): (P2, P3) -> R {
    return { p2, p3 -> this(p1, p2, p3) }
}

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial2(p2: P2): (P1, P3) -> R {
    return { p1, p3 -> this(p1, p2, p3) }
}

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial3(p3: P3): (P1, P2) -> R {
    return { p1, p2 -> this(p1, p2, p3) }
}
