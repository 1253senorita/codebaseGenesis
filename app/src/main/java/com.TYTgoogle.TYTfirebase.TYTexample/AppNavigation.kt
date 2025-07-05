package com.TYTgoogle.TYTfirebase.TYTexample // 패키지 선언은 파일 상단에 있어야 합니다.

// AndroidX 및 Jetpack Compose UI 관련
// import androidx.compose.material.icons.filled.ExitToApp // automirrored와 중복 가능성
//import androidx.compose.material.icons.filled.PictureAsPdf

// Navigation 관련

// 프로젝트 내부 데이터 클래스 및 리소스

// Firebase 관련 (필요한 경우)

// Android 시스템 관련


// ... (기존 imports) ...
//import androidx.compose.material3.ButtonDefaults // ButtonDefaults import
// tytpdfmodule의 PdfHandler import 시 패키지명 변경
import com.TYTgoogle.TYTfirebase.TYTexample.data.ActionItem
import com.TYTgoogle.TYTfirebase.TYTexample.data.ActionType
import com.TYTgoogle.TYTfirebase.TYTexample.data.SeriesInfo


// 주석 처리된 이전 import 문 (실제 사용 여부 확인 후 최종 결정)
// import com.google.firebase.auth.FirebaseAuth // FirebaseAuth가 위에 이미 임포트되어 있음
// import com.google.firebase.auth.FirebaseUser
// import com.TYTgoogle.TYTfirebase.TYTexample.ui.LoginScreen


// 전역 변수 또는 객체들은 여기에 위치 (예: allSeriesData, Routes 등)
// 예시로 allSeriesData와 Routes가 이 파일 또는 다른 파일에 정의되어 있다고 가정합니다.
// object Routes { /* ... */ }
// val allSeriesData: List<SeriesInfo> = /* ... */

// ... (AppNavigation.kt 파일의 나머지 코드)








object Routes {
    val seriesRoutes: Map<String, Pair<String, String>> = allSeriesData.associate { seriesInfo ->
        seriesInfo.id to createSeriesRoutes(seriesInfo.id)
    }
}
//여기서 핵심은

fun createSeriesRoutes(seriesId: String): Pair<String, String> {
    val dominantRoute = "${seriesId.lowercase()}_dominant"
    val l1ScreenRoute = "${seriesId.lowercase()}_l1_screen"
    return Pair(dominantRoute, l1ScreenRoute)
}




object LoginRoute {
    const val route = "login"
}

object MajorRoute {
    const val routeTemplate = "major/{userEmail}"
    fun createRoute(userEmail: String?) = "major/${userEmail ?: "Guest"}"
    const val USER_EMAIL_ARG = "userEmail"
}



// import com.TYTgoogle.TYTfirebase.TYTexample.data.SeriesInfo // SeriesInfo 임포트는 유지

// ... (다른 import들)

//private val Icons.AutoMirrored.Filled.HelpOutline: ImageVector

// AppNavigation.kt 파일 내 또는 별도의 Data 파일에 정의 가능
val allSeriesData: List<SeriesInfo> = listOf(
    SeriesInfo(
        id = "BEAUTY01",
        displayName = "뷰티 (네일/헤어)",
        imageUrl = null, // 필요시 이미지 URL 문자열 제공
        iconResId = R.drawable.image01_ic, // 실제 드로어블 리소스
        initialActions = listOf(
            ActionItem(displayText = "네일 기본 견적", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "beauty_nail_quote_sheet"),
            ActionItem(displayText = "헤어 시술 상담", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "beauty_hair_quote_sheet"),
            ActionItem(displayText = "최신 포트폴리오 업로드", actionType = ActionType.UPLOAD_DATA, uploadDataType = "beauty_portfolio"),
        )
    ),
    SeriesInfo(
        id = "FOOD_DELIVERY01",
        displayName = "배달음식 전문",
        imageUrl = null,
        iconResId = R.drawable.image02_ic, // 실제 드로어블 리소스
        initialActions = listOf(
            ActionItem(displayText = "대표 메뉴 주문", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "food_order_main_menu"),
            ActionItem(displayText = "리뷰 남기기", actionType = ActionType.SHOW_INFO_DIALOG),
        )
    ),
    SeriesInfo(
        id = "PET_CARE02", // ID는 고유해야 합니다.
        displayName = "반려동물 케어",
        imageUrl = null,
        iconResId = R.drawable.image03_ic, // 실제 드로어블 리소스
        initialActions = listOf(
            ActionItem(displayText = "호텔 예약", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "pet_hotel_booking"),
            ActionItem(displayText = "미용 신청", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "pet_grooming_request"),
        )
    ),
    SeriesInfo(
        id = "EDUCATION03",
        displayName = "온라인 교육",
        imageUrl = null,
        iconResId = R.drawable.image05_ic, // 실제 드로어블 리소스
        initialActions = listOf(
            ActionItem(displayText = "강의 수강", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "edu_online_lecture"),
            ActionItem(displayText = "자료실", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "edu_learning_materials"),
        )
    ),
    SeriesInfo(
        id = "SHOPPING04",
        displayName = "온라인 쇼핑",
        imageUrl = null,
        iconResId = R.drawable.image05_ic, // 실제 드로어블 리소스
        initialActions = listOf(
            ActionItem(displayText = "상품 검색", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "shop_search_category"),
            ActionItem(displayText = "장바구니", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "shop_cart_payment"),
        )
    ),
    SeriesInfo(
        id = "TRAVEL05",
        displayName = "여행 상품",
        imageUrl = null,
        iconResId = R.drawable.ic_launcher_round, // 실제 드로어블 리소스
        initialActions = listOf(
            ActionItem(displayText = "항공권 검색", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "travel_flight_search"),
            ActionItem(displayText = "호텔 예약", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "travel_hotel_booking"),
        )
    ),
    SeriesInfo(
        id = "REAL_ESTATE06",
        displayName = "부동산 정보",
        imageUrl = null,
        iconResId = R.drawable.image02_ic, // 실제 드로어블 리소스
        initialActions = listOf(
            ActionItem(displayText = "매물 검색", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "estate_property_search"),
            ActionItem(displayText = "시세 확인", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "estate_market_price"),
        )
    ),
    // ...
    SeriesInfo(
        id = "FITNESS07",
        displayName = "피트니스/운동",
        imageUrl = null,
        iconResId = R.drawable.image03_ic, // 실제 드로어블 리소스
        initialActions = listOf(
            ActionItem(displayText = "운동 프로그램", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "fitness_program_view"),
            ActionItem(displayText = "트레이너 찾기", actionType = ActionType.NAVIGATE_SUB_DOMINANT, targetRoute = "fitness_trainer_search"),
        ) // <--- Potential missing closing parenthesis or comma here
    ) // <--- This closes SeriesInfo
)








