// Define the Compose version
//val compose_version = "1.1.0-alpha06" // Change this to the version you are using
val compose_version = "1.1.2"

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
    //id("com.google.devtools.ksp") version "1.9.20-1.0.14"
}

android {
    namespace = "com.example.sodv3203_finalproject_group4"
    //compileSdk = 34
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.sodv3203_finalproject_group4"
        minSdk = 24
        //targetSdk = 34
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        configurations.all {
            resolutionStrategy {
                force("androidx.emoji2:emoji2-views-helper:1.3.0")
                force("androidx.emoji2:emoji2:1.3.0")
            }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        //sourceCompatibility = JavaVersion.VERSION_1_8
        //targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "17"
        //jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
        //kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.core:core-ktx:1.10.1")
    //implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    //implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    //implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    //implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.navigation:navigation-runtime-ktx:2.6.0")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    //implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.android.material:material:1.8.0")
    //implementation("androidx.compose.material:material-android:1.6.2")
    //implementation("androidx.compose.material:material-android:1.5.4")
    implementation("androidx.compose.material:material-android:1.1.2")
    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("androidx.compose.animation:animation:1.0.5")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.foundation:foundation:$compose_version")
    implementation("com.google.code.gson:gson:2.8.8")
    //Room
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:$compose_version"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}