package org.kiworkshop.learningfpinkotlin

object MaybeMonoid {
    fun <T> monoid(inValue: Monoid<T>) = object : Monoid<Maybe<T>> {
        override fun mempty(): Maybe<T> = Nothing

        override fun mappend(m1: Maybe<T>, m2: Maybe<T>): Maybe<T> = when {
            m1 is Nothing -> m2
            m2 is Nothing -> m1
            m1 is Just && m2 is Just -> Just(inValue.mappend(m1.value, m2.value))
            else -> Nothing
        }
    }
}
