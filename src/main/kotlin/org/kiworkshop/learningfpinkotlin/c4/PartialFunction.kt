package org.kiworkshop.learningfpinkotlin.c4

class PartialFunction<P, R>(
    private val condition: (P) -> Boolean,
    private val f: (P) -> R,
) : (P) -> R {

    override fun invoke(p: P): R = when {
        condition(p) -> f(p)
        else -> throw IllegalArgumentException("$p isn't supported.")
    }

    fun isDefinedAt(p: P): Boolean = condition(p)

    fun invokeOrElse(p: P, default: R): R = when {
        condition(p) -> invoke(p)
        else -> default
    }

    fun orElse(that: PartialFunction<P, R>): PartialFunction<P, R> {
        val newCondition: (P) -> Boolean = { p ->
            when {
                condition(p) -> true
                else -> that.condition(p)
            }
        }

        val newF: (P) -> R = { p ->
            when {
                condition(p) -> this(p)
                else -> that(p)
            }
        }

        return PartialFunction(newCondition, newF)
    }
}

fun <P, R> ((P) -> R).toPartialFunction(isDefinedAt: (P) -> Boolean): PartialFunction<P, R> =
    PartialFunction(isDefinedAt, this)
