plugins {
    id("com.android.application")
    id ("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.flavorfinderjava"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.flavorfinderjava"
        minSdk = 28
        targetSdk = 33
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.google.code.gson:gson:2.9.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // ROOM
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    annotationProcessor ("androidx.room:room-compiler:2.5.2")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.5.1")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata:2.5.1")

    implementation("io.coil-kt:coil:2.4.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}