// tytpdfmodule/build.gradle.kts

plugins {
    alias(libs.plugins.android.library) // Android 라이브러리 플러그인 참조
    alias(libs.plugins.kotlin.android)  // Kotlin Android 플러그인 참조
}

android {
    namespace = "com.pdfmoduletyt.tytpdfmodule" // 고유한 패키지 이름으로 하는 것이 좋습니다.
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    implementation("com.itextpdf:layout:9.2.0")

    implementation("androidx.compose.material:material-icons-core:1.7.8")
// 예시 버전, 최신 버전 확인
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
// 예시 버전, 최신 버전 확인
    // 여기에 tytpdfmodule 모듈에 필요한 의존성을 추가합니다.
    // 예: implementation(libs.androidx.core.ktx)
    // 예: implementation("com.google.android.material:material:1.12.0") // 만약 Material 컴포넌트를 사용한다면
}