plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    namespace = "com.example.tuan6"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tuan6"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

kotlin {
    jvmToolchain(17)
}


dependencies {
    // 1. Compose Bill of Materials (BOM) - Quản lý phiên bản cho các thư viện Compose
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))

    // 2. Các thư viện Compose (Không cần ghi phiên bản vì đã có BOM)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.9.0") // Thư viện này không thuộc BOM nên cần phiên bản

    // 3. Thư viện để tải ảnh từ URL (Giữ phiên bản mới nhất)
    implementation("io.coil-kt:coil-compose:2.6.0")

    // 4. Thư viện để gọi API (Giữ phiên bản mới nhất)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // 5. Thư viện chỉ dành cho debug (Không cần phiên bản vì đã có BOM)
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
