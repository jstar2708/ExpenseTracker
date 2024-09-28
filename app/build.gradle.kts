plugins {
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.room)
    kotlin("kapt")
}

android {
    namespace = "com.jaideep.expensetracker"
    compileSdk = 34

//    ksp {
//        room {
//            schemaDirectory("$rootDir/schemas")
//        }
//    }         This throws error as using KSP we cannot give spaces in schema name (i.e. root directory)

    room {
        schemaDirectory("$rootDir/schemas")
    }
    defaultConfig {
        applicationId = "com.jaideep.expensetracker"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.app.compat)
    implementation(libs.material)

    kapt(libs.hilt.compiler)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.coroutine.core)
    implementation(libs.coroutines.android)

    implementation(libs.splash.screen)

    implementation(libs.room)
    kapt(libs.annotation.processor.room)
    implementation(libs.datastore)

    implementation(libs.ktx)

    implementation(libs.icons)
    implementation(libs.paging)
    implementation(libs.paging.compose)
    implementation(libs.paging.with.room)

    implementation(libs.kotlin.immutable.lists)
}