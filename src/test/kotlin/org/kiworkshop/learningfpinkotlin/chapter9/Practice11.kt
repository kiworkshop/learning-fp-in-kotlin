package org.kiworkshop.learningfpinkotlin.chapter9

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import org.kiworkshop.learningfpinkotlin.funListOf

class Practice11 : FreeSpec() {
    init {
        """
           일반 트리를 Foldable 타입 클래스의 인스턴스를 만들고, foldLeft, foldMap 함수의 동작을 테스트해 보자. 
           이때 트리의 노드는 값 val value: A와 하위 트리의 리스트인 val forest: FunList<Node<A>>를 프로퍼티로 가진다.
        """ {
            val tree = Tree.Node(
                1,
                funListOf(
                    Tree.Node(3), Tree.Node(4, funListOf(Tree.Node(5), Tree.Node(6)))
                )
            )

            tree.foldLeft(0) { a, b -> a + b } shouldBe 19
            tree.foldLeft(1) { a, b -> a * b } shouldBe 360

            tree.foldMap({ it + 1 }, SumMonoid()) shouldBe 24
        }
    }
}
