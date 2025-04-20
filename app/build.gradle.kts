plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.projectskripsi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projectskripsi"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.navigation:navigation-compose:2.8.4")
    implementation("androidx.compose.animation:animation:1.7.5")
    implementation("androidx.compose.material:material-icons-extended:1.7.6")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0-alpha05")
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:1.5.1")
    implementation("androidx.compose.material:material:1.5.1")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Untuk penggunaan LiveData dengan Kotlin Coroutines (disarankan)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")

    // Atau, jika tidak menggunakan Kotlin Coroutines
    // implementation("androidx.lifecycle:lifecycle-livedata:2.8.3")

    // Jika kamu menggunakan ViewModel, tambahkan juga ini
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")

    // Jika kamu menggunakan lifecycle-runtime-compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.3")

    //SystemUiController
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.13-rc")

    implementation("androidx.navigation:navigation-compose:2.8.4")
    
    //swipe refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.24.13-rc")

    // Google Sign-In SDK
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Firebase Authentication (jika Anda juga ingin mengintegrasikan dengan Firebase)
    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
    implementation("com.google.firebase:firebase-bom:32.7.0")
    implementation("com.google.firebase:firebase-messaging:24.1.1")
    implementation("com.google.firebase:firebase-firestore")

    //tinggi navigasi dinamis
    implementation("com.google.accompanist:accompanist-insets:0.30.1")

    //HorizontalPager
    implementation("androidx.compose.foundation:foundation:1.6.0")
}