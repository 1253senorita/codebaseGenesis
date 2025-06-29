// 파일 경로: com/TYTgoogle/TYTfirebase/TYTexample/ui/routes/AppRoutes.kt (기존 MovieDetailRoute.kt에서 이름 변경 또는 확장)
package com.TYTgoogle.TYTfirebase.TYTexample.ui.routes // 패키지 경로는 실제 구조에 맞게 조정

import kotlinx.serialization.Serializable

// --- 기존 제공된 라우트 ---
@Serializable
object MajorRoute // 어떤 화면으로 매핑될지 정의 필요

@Serializable
object LoginRoute // 기존 AppNavigation.kt의 LoginRoute 대체

@Serializable
object MoviesRoute // 영화 목록 화면 등으로 사용

@Serializable
object SignUpRoute // 회원가입 화면으로 사용

@Serializable
data class MovieDetailRoute(val movieId: String)






// --- AppNavigation.kt의 MainHubRoute를 Serializable로 변경 ---
@Serializable
data class MainHubRoute(val userEmail: String?) // 인자가 있으므로 data class






// --- AA 시리즈 라우트 (Serializable) ---

@Serializable
object AADominantRoute

@Serializable
object AAL1Screen1Route // 만약 인자가 필요하면 data class로 변경 (예: data class AAL1Screen1Route(val filter: String))

@Serializable
object AAL1Screen2Route


// --- AB 시리즈 라우트 (Serializable) ---

@Serializable
object ABDominantRoute

@Serializable
object ABL1Screen1Route // 인자 필요시 data class로 변경

@Serializable
object ABL1Screen2Route

// 여기에 다른 시리즈나 필요한 모든 라우트를 @Serializable 형태로 추가하면 됩니다.


