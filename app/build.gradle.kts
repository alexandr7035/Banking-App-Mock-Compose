import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20"
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties().apply {
    load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "by.alexandr7035.banking"
    compileSdk = 35

    signingConfigs {
        create("config") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    defaultConfig {
        applicationId = "by.alexandr7035.banking"
        minSdk = 23
        targetSdk = 34
        versionCode = 200
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("config")
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("config")
            applicationIdSuffix = ".debug"
            isDebuggable = true
            versionNameSuffix = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(platform("androidx.compose:compose-bom:2025.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation("androidx.compose.material3:material3:1.3.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    val koinVersion = "4.0.4"
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
    implementation("io.insert-koin:koin-androidx-workmanager:$koinVersion")

    implementation("androidx.compose.ui:ui-text-google-fonts:1.8.1")

    val navVersion = "2.9.0"
    implementation("androidx.navigation:navigation-compose:$navVersion")
    implementation("com.github.leonard-palm:compose-state-events:2.1.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.0")

    implementation("com.github.cioccarellia:ksprefs:2.4.1")

    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")

    val coilVersion = "2.6.0"
    implementation("io.coil-kt:coil-compose:$coilVersion")
    implementation("io.coil-kt:coil-svg:$coilVersion")

    implementation("com.valentinilk.shimmer:compose-shimmer:1.3.2")

    val roomVersion = "2.7.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("androidx.security:security-crypto:1.1.0-alpha07")
    implementation("androidx.biometric:biometric-ktx:1.2.0-alpha05")

    implementation("androidx.paging:paging-compose:3.3.6")

    val workVersion = "2.10.1"
    implementation("androidx.work:work-runtime-ktx:$workVersion")

    implementation("com.guolindev.permissionx:permissionx:1.7.1")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0") {
        isTransitive = false
    }
    //noinspection GradleDependency
    implementation("com.google.zxing:core:3.3.0")
}
