plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

}

android {
    namespace = "com.hk.transportProject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hk.transportProject"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions{
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
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
    implementation(libs.simplexml)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Add Dependency Injection
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("com.google.dagger:hilt-android:2.48")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")

    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.mockito:mockito-core:3.12.4")
    testImplementation ("org.mockito:mockito-inline:3.12.4")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")

}