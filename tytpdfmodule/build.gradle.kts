// tytpdfmodule/build.gradle.kts

plugins {
    alias(libs.plugins.android.library) // Android 라이브러리 플러그인 참조
    alias(libs.plugins.kotlin.android)  // Kotlin Android 플러그인 참조
}



android {
    namespace = "com.pdfmoduletyt.tytpdfmodule"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        // ✅ 커스텀 BuildConfig 필드 추가 예시
        buildConfigField("String", "API_URL", "\"https://api.tytpdf.kr\"")
        buildConfigField("Boolean", "USE_ANALYTICS", "false")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true // ✅ BuildConfig 클래스 생성을 활성화
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}




dependencies {

    //implementation("com.itextpdf:layout:9.2.0")

    //implementation("androidx.compose.material:material-icons-core:1.7.8")
// 예시 버전, 최신 버전 확인
    // implementation("androidx.compose.material:material-icons-extended:1.7.8")
// 예시 버전, 최신 버전 확인
    // 여기에 tytpdfmodule 모듈에 필요한 의존성을 추가합니다.
    // 예: implementation(libs.androidx.core.ktx) // 만약 libs 버전을 사용한다면
    // 예: implementation("androidx.core:core-ktx:1.16.0") // 직접 버전을 명시한다면 (libs.versions.toml의 coreKtx 버전 참조)

// 예: implementation("com.google.android.material:material:1.12.0") // 만약 Material 컴포넌트를 사용한다면 (libs.versions.toml의 material 버전 참조)


    //인터패이스 모듈.------------->>>>>>>>>
    // iText 7 PDF 생성 라이브러리
    implementation("com.itextpdf:itext7-core:7.2.5") // 7.2.5는 예시 버전입니다. 최신 안정 버전을 확인하세요.
    implementation("com.itextpdf:layout:7.2.5")    // itext7-core와 버전을 맞춰주세요.
    implementation("com.itextpdf:io:7.2.5")          // 폰트 처리 등에 필요할 수 있습니다. 버전을 맞춰주세요.
    //인터패이스 모듈.------------->>>>>>>>>

    // 만약 SLF4J 관련 경고가 발생한다면, 다음을 추가하여 해결할 수 있습니다.
    // implementation("org.slf4j:slf4j-android:1.7.32") // 예시 버전


}
