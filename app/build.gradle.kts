plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.petpals"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.petpals"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
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

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    //firebase auth
    implementation (libs.firebase.ui.auth)

    //GSON
    implementation(libs.gson);

    //Firebase database
    implementation(platform(libs.firebase.bom.v3312))
    implementation(libs.firebase.database)

    //Lottie
    implementation (libs.lottie)

    //Photo uploading
    implementation (libs.dhaval2404.imagepicker)

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //firebase storage
    implementation("com.google.firebase:firebase-storage")

    //btn style
    implementation ("com.google.android.material:material:1.3.0")

    //popup dialogs
    implementation ("com.saadahmedev.popup-dialog:popup-dialog:2.0.0")

    // WorkManager dependency
    implementation ("androidx.work:work-runtime-ktx:2.7.1")

    // Firebase Cloud Messaging dependency
    implementation ("com.google.firebase:firebase-messaging")
}