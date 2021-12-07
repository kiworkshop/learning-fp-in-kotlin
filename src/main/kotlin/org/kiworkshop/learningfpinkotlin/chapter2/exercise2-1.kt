fun main() {
    require("kotlin".addHelloPrefix() == "Hello, kotlin")
    require("FP".addHelloPrefix() == "Hello, FP")
}

/*
* 확장함수
*
* 이미 작성된 클래스에 함수나 프로퍼티를 추가할 수 있다.
* */
private fun String.addHelloPrefix(): String = "Hello, $this"