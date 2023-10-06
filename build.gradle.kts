// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        //make the plugins
        classpath ("com.android.tools.build:gradle:3.5.1")
        classpath ("io.realm:realm-gradle-plugin:10.15.1")
    }
}
//
//allprojects {
//    repositories {
//        //google()
//        mavenCentral()
//
//    }
//
//}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}