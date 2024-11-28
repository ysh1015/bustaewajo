import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.hk.transportProject"
    compileSdk = 34

    viewBinding { enable = true }
    dataBinding { enable = true }

    defaultConfig {
        applicationId = "com.hk.transportProject"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // API 키 로드
        val properties = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            properties.load(FileInputStream(localPropertiesFile))
            buildConfigField(
                "String",
                "TRAFFIC_API_KEY",
                "\"${properties.getProperty("TRAFFIC_API_KEY", "")}\""
            )
        } else {
            buildConfigField("String", "TRAFFIC_API_KEY", "\"g3s/sU96JysexYpblDXIc4+V33peeadeoSi2BpBF5ej8XHRQtmPphiSA4dkF3s7b0CF5gDDO6/N2/weFSIDgCA==\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.retrofit)        // Retrofit 의존성 추가
    implementation(libs.retrofit.gson)   // Gson 컨버터 의존성 추가
    implementation(libs.naver.map.sdk)      // 네이버 지도 SDK 의존성 추가
    implementation(libs.naver.map.service)  // 네이버 지도 위치기반 의존성

    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    // TikXML 의존성 추가
    implementation(libs.tikxml.annotation)
    implementation(libs.tikxml.core)
    implementation(libs.tikxml.retrofit.converter)
    implementation(libs.databinding.runtime)
    annotationProcessor(libs.tikxml.processor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}