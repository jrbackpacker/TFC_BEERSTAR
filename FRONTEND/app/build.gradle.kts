plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.beerstar"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.beerstar"
        minSdk = 35
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat) // androidx.appcompat:appcompat
    implementation(libs.material) // com.google.android.material:material
    implementation(libs.activity) // androidx.activity:activity
    implementation(libs.constraintlayout)
    implementation(libs.navigation.runtime) // androidx.constraintlayout:constraintlayout
    testImplementation(libs.junit) // junit:junit
    androidTestImplementation(libs.ext.junit) // androidx.test.ext:junit
    androidTestImplementation(libs.espresso.core) // androidx.test.espresso:espresso-core
    implementation(libs.picasso) // com.squareup.picasso:picasso
    implementation(libs.retrofit) // com.squareup.retrofit2:retrofit
    implementation(libs.gson) // com.squareup.retrofit2:converter-gson
    implementation(libs.okhttp) // com.squareup.okhttp3:logging-interceptor
    implementation(libs.powerspinner)
}