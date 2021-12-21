fun main() {
    val bigIntList = (10000000 downTo 1).toList()

    val imperativeStart = System.currentTimeMillis()
    imperativeWay(bigIntList) // 0
    println("${System.currentTimeMillis() - imperativeStart} ms")

    val funcStart = System.currentTimeMillis()
    functionalWay(bigIntList) // 385
    println("${System.currentTimeMillis() - funcStart} ms")

    val realFuncStart = System.currentTimeMillis()
    realFunctionalWay(bigIntList) // 10
    println("${System.currentTimeMillis() - realFuncStart} ms")
}

fun imperativeWay(intList: List<Int>): Int {
    for (value in intList) {
        val doubleValue = value * value
        if (doubleValue < 10) {
            return doubleValue
        }
    }
    throw NoSuchElementException("No value")
}

fun functionalWay(intList: List<Int>): Int =
    intList
        .map { n -> n * n }.first { n -> n < 10 }

fun realFunctionalWay(intList: List<Int>): Int =
    intList.asSequence()
        .map { n -> n * n }
        .filter { n -> n < 10 }
        .first()