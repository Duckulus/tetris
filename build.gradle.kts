plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "de.duckulus"
version = "0.2"

dependencies {
    implementation(fileTree("libs") { include("*.jar") })
}

application {
    mainClass.set("de.duckulus.tetris.Main")
}
