
/*
 * Copyright 2025 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)



}

android {
    // [수정] namespace를 설정합니다.
    // 이는 R 클래스 생성 및 매니페스트 병합에 사용됩니다.
    namespace = "com.TYTgoogle.TYTfirebase.TYTexample"
    // [제거됨] 이전 위치의 applicationId 선언은 제거되었습니다.

    compileSdk = 35

    defaultConfig {
        // applicationId는 여기에만 정의합니다.
        applicationId = "com.TYTgoogle.TYTfirebase.TYTexample"

        minSdk = 23
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true // <<<--- 이 줄을 추가해 보세요!


    }
}

dependencies {

    implementation(platform(libs.firebase.bom)) // 여기는 libs.firebase.bom을 참조
    implementation(libs.firebase.ui.auth)    // 여기는 libs.firebase.ui.auth를 참조


    implementation(libs.google.android.gms.play.services.auth) // <<<--- 이 줄 추가!!!


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)


    implementation(libs.material) // libs.versions.toml #쩍꿍
    implementation(libs.androidx.material3)

    implementation(libs.coil.compose)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.compose.navigation)

    // Firebase dependencies
    implementation(libs.firebase.auth)
    implementation(libs.firebase.dataconnect)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.core.splashscreen)




    //인터패이스 모듈.------------->>>>>>>>>
    implementation(project(":tytpdfmodule"))
    //implementation(libs.android.pdf.viewer) // <-- Uncomment this line
     //인터패이스 모듈.------------->>>>>>>>>



     //인터패이스 모듈.------------->>>>>>>>>
    // iText 7 PDF 생성 라이브러리
   // implementation("com.itextpdf:itext7-core:7.2.5") // 7.2.5는 예시 버전입니다. 최신 안정 버전을 확인하세요.
    //implementation("com.itextpdf:layout:7.2.5")    // itext7-core와 버전을 맞춰주세요.
    //implementation("com.itextpdf:io:7.2.5")          // 폰트 처리 등에 필요할 수 있습니다. 버전을 맞춰주세요.
    //인터패이스 모듈.------------->>>>>>>>>

    // 만약 SLF4J 관련 경고가 발생한다면, 다음을 추가하여 해결할 수 있습니다.
    // implementation("org.slf4j:slf4j-android:1.7.32") // 예시 버전

// 사용 가능한 최신 버전으로 확인

}