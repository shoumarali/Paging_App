plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.dagger.hilt.android")
}



android {
    namespace = "com.example.pagingapp"
    compileSdk = 34
    buildFeatures {
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.example.pagingapp"
        minSdk = 24
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)

    //Retrofit + RxJava
    implementation(libs.adapter.rxjava3)

    //Hilt dagger
    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.compiler)

    //Glide
    implementation(libs.glide)

    //Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.rxjava3)

    //lifecycle
    val lifecycle_version = "2.7.0"
    val arch_version = "2.2.0"
    // ViewModel
    implementation(libs.lifecycle.viewmodel.ktx)

    // LiveData
    implementation(libs.lifecycle.livedata.ktx)
}
