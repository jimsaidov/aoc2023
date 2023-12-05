plugins {
    kotlin("jvm") version "1.9.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs = listOf("-Xms1g", "-Xmx2g")
}