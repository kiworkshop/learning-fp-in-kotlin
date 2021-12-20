plugins {
    id(Libs.Plugins.kotlinJvm) version Libs.Versions.kotlin
    id(Libs.Plugins.ktlint) version Libs.Versions.ktlint
    id(Libs.Plugins.ktlintIdea) version Libs.Versions.ktlint
}

group = "org.kiworkshop.learningfpinkotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib", Libs.Versions.kotlin))

    testImplementation(Libs.Test.kotest)
    testImplementation(Libs.Test.kotestAssertionsCore)
    testImplementation(Libs.Test.kotestPropertyTesting)
    testImplementation(Libs.Test.mockk)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.ktlintFormat.get().group = "verification"
tasks.ktlintCheck.get().group = "other"

tasks.check {
    dependsOn(tasks.ktlintFormat)
}
