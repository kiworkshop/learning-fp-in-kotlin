package org.kiworkshop.learningfpinkotlin.chapter8

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.chapter7.Cons
import org.kiworkshop.learningfpinkotlin.chapter7.FunList
import org.kiworkshop.learningfpinkotlin.chapter7.Nil
import org.kiworkshop.learningfpinkotlin.curried

class Exercise8 : AnnotationSpec() {
    @Test
    fun `test8-1 FunList를 사용해서 product를 적용해보기`() {
        val product: (Int, Int) -> Int = { x: Int, y: Int -> x * y }
        val curriedProduct: (Int) -> (Int) -> Int = product.curried()

        val funListProduct = Cons(1, Cons(2, Cons(3, Cons(4, Nil)))).fmap(curriedProduct)
        funListProduct.fmap { it(5) } shouldBe Cons(5, Cons(10, Cons(15, Cons(20, Nil))))
    }

    @Test
    fun `test8-2 FunList를 Applicative 인스턴스로 만들기`() {
        AFunList.pure(1).fmap { it * 10 } shouldBe ACons(10, ANil)
        ACons(1, ACons(2, ACons(3, ANil))).fmap { it + 10 } shouldBe ACons(11, ACons(12, ACons(13, ANil)))
        ANil.fmap { x: Int -> x + 10 } shouldBe ANil
    }

    @Test
    fun `test8-3 FunList를 확장함수로 Applicative 인스턴스로 만들기`() {
        val funList2: FunList<(Int) -> Int> =
            Cons(
                { it * 3 },
                Cons(
                    { it * 10 },
                    Cons<(Int) -> Int>({ it - 2 }, Nil)
                )
            )
        funList2 apply Cons(1, Cons(2, Cons(3, Nil))) shouldBe
                Cons(
                    3, Cons(
                        6, Cons(
                            9,
                            Cons(
                                10, Cons(
                                    20, Cons(
                                        30,
                                        Cons(-1, Cons(0, Cons(1, Nil)))
                                    )
                                )
                            )
                        )
                    )
                )
    }

    @Test
    fun `test8-4 트리 애플리케이티브 펑터 테스트`() {
        val tree =
            Node(
                4,
                listOf(
                    Node(8), Node(12), Node(
                        5,
                        listOf(Node(10), Node(15))
                    ),
                    Node(
                        6,
                        listOf(Node(12), Node(18))
                    )
                )
            )

        Tree.pure({ x: Int, y: Int -> x * y }.curried()) apply Node(4, listOf(Node(5), Node(6))) apply Node(
            1,
            listOf(Node(2), Node(3))
        ) shouldBe tree
    }

    @Test
    fun `test8-5 트리 애플리케이티브 펑터 테스트2`() {
        val tree: Tree<Int> =
            Node(
                5, listOf(
                    Node(6),
                    Node(
                        7, listOf(
                            Node(8),
                            Node(9)
                        )
                    ),
                    Node(
                        10, listOf(
                            Node(12),
                            Node(
                                14, listOf(
                                    Node(16),
                                    Node(18)
                                )
                            ),
                            Node(
                                15, listOf(
                                    Node(18),
                                    Node(
                                        21, listOf(
                                            Node(24),
                                            Node(27)
                                        )
                                    )
                                )
                            )
                        )
                    ),
                    Node(
                        20, listOf(
                            Node(24),
                            Node(
                                28, listOf(
                                    Node(32),
                                    Node(36)
                                )
                            )
                        )
                    )
                )
            )

        Tree.pure({ x: Int, y: Int -> x * y }.curried()) apply Node(
            1,
            listOf(Node(2, listOf(Node(3))), Node(4, listOf()))
        ) apply Node(5, listOf(Node(6), Node(7, listOf(Node(8), Node(9))))) shouldBe tree
    }
    

}