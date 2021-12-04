package org.kiworkshop.learningfpinkotlin.c3

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class Practice5Of3 : ShouldSpec({
    context("음이 아닌 정수 n이 주어졌을 때") {
        should("n의 이진수를 구한다") {
            listOf(
                ListReplicateTestData(0, 0, emptyList()),
                ListReplicateTestData(0, 1, listOf(0)),
                ListReplicateTestData(0, 2, listOf(0, 0)),
                ListReplicateTestData(0, 3, listOf(0, 0, 0)),
                ListReplicateTestData(1, 0, emptyList()),
                ListReplicateTestData(2, 1, listOf(2)),
                ListReplicateTestData(3, 2, listOf(3, 3)),
                ListReplicateTestData(4, 3, listOf(4, 4, 4)),
            ).forEach { (element, replicaSize, expected) ->
                val result = ListReplica.replicate(replicaSize, element)

                result shouldBe expected
            }
        }
    }
})

private data class ListReplicateTestData(
    val element: Int,
    val replicaSize: Int,
    val expected: List<Int>,
)

