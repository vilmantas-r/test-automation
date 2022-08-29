plugins {
    id("org.jetbrains.kotlin.kapt")
}

dependencies {
    kapt(libs.springboot.configuration.processor)

    api(project(":test-automation-core"))
    api(libs.selenide)
    api(libs.selenide.proxy)
    api(libs.allure.selenide)
}
