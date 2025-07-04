package com.TYTgoogle.TYTfirebase.TYTexample // 패키지 선언은 파일 상단에 있어야 합니다.

// AndroidX 및 Jetpack Compose UI 관련
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement // 개별 임포트
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size // ButtonDefaults.IconSize 등 사용 시 필요
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp // 사용되는지 확인 필요
import androidx.compose.material.icons.filled.AccountCircle // 사용되는지 확인 필요
// import androidx.compose.material.icons.filled.ExitToApp // automirrored와 중복 가능성
import androidx.compose.material.icons.filled.Info
//import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Settings // 사용되는지 확인 필요
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card // GenericL1Screen에서 사용
import androidx.compose.material3.CardDefaults // GenericL1Screen에서 사용
import androidx.compose.material3.ExperimentalMaterial3Api // 사용되는 Composable이 Experimental인지 확인
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton // 사용되는지 확인 필요
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold // 사용되는지 확인 필요
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar // 사용되는지 확인 필요
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector // 사용되는지 확인 필요 (Icons에서 가져오는 경우 명시적 임포트 불필요)
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext // MajorScreen에서 사용
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Navigation 관련
import androidx.navigation.NavHostController
import androidx.navigation.NavType // 사용되는지 확인 필요
import androidx.navigation.compose.NavHost // 사용되는지 확인 필요
import androidx.navigation.compose.composable // 사용되는지 확인 필요
import androidx.navigation.compose.rememberNavController // 사용되는지 확인 필요
import androidx.navigation.navArgument // 사용되는지 확인 필요

// 프로젝트 내부 데이터 클래스 및 리소스
import com.TYTgoogle.TYTfirebase.TYTexample.R
import com.TYTgoogle.TYTfirebase.TYTexample.data.ActionItem // 사용되는지 확인 필요
import com.TYTgoogle.TYTfirebase.TYTexample.data.ActionType // 사용되는지 확인 필요
import com.TYTgoogle.TYTfirebase.TYTexample.data.SeriesInfo

// Firebase 관련 (필요한 경우)
import com.google.firebase.auth.FirebaseAuth // 사용되는지 확인 필요

// Android 시스템 관련
import android.content.Intent // MajorScreen에서 PDF 뷰어 호출 시 사용

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




    @Composable
    fun MajorScreen(
        userEmail: String?,
        navController: NavHostController,
    ) {
        val context = LocalContext.current // Context 가져오기

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.image9_base),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // LazyColumn으로 allSeriesData 기반 버튼들 표시
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val chunkedSeriesData = allSeriesData.chunked(2)
                    items(items = chunkedSeriesData) { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            rowItems.forEach { seriesInfo ->
                                val (dominantRoute, _) = Routes.seriesRoutes[seriesInfo.id]!!
                                Button(
                                    onClick = { navController.navigate(dominantRoute) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 4.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = ButtonDefaults.buttonElevation(
                                        defaultElevation = 6.dp,
                                        pressedElevation = 2.dp,
                                        disabledElevation = 0.dp,
                                    ),
                                ) {
                                    Text(seriesInfo.displayName)
                                }
                            }
                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                // --- 기존 고정 버튼들 ---
                Button(
                    onClick = { navController.navigate("my_custom_feature_route") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Special Feature",
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("나의 특별 기능")
                }

                Button(
                    onClick = { navController.navigate("app_info_route") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "App Info",
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("앱 정보 보기")
                }

                OutlinedButton(
                    onClick = { navController.navigate("customer_support_route") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("고객 지원")
                }
                // --- 여기까지 고정 버튼 추가 ---

                // --- "간단한 텍스트 화면 테스트" 버튼 수정 ---
                Button(
                    onClick = {
                        // "PDF 생성 및 보기" 버튼과 동일한 방식으로 Intent 사용
                        val intent = Intent(context, SimpleTextActivity::class.java)
                        // SimpleTextActivity는 특별한 데이터를 넘길 필요 없으므로 putExtra는 생략
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp), // 일관성을 위해 추가 (선택 사항)
                ) {
                    // 아이콘은 선택 사항입니다. 원하시면 추가하세요.
                    // Icon(imageVector = Icons.Filled.TextFields, contentDescription = "Simple Text")
                    // Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("간단한 텍스트 화면 테스트")
                }
                // --- 여기까지 "간단한 텍스트 화면 테스트" 버튼 ---




            }
        }
    }




@Composable
fun GenericL1Screen(
    seriesName: String, // 어떤 시리즈의 L1 화면인지 식별
    navController: NavHostController, // 뒤로 가기 등의 네비게이션을 위해 필요
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image8_base), // L1 스크린용 배경 이미지 (새 이미지 또는 기존 이미지 재활용)
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("$seriesName - L1 기능 스크린", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            // L1 화면의 구체적인 내용 (예시: Card 안에 정보 표시)
            // 이 부분은 실제 L1 화면의 요구사항에 맞게 커스터마이징합니다.
            androidx.compose.material3.Card(
                // Card 임포트가 명시적으로 필요할 수 있음
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("이것은 $seriesName 시리즈의 L1 화면입니다.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("여기에 해당 시리즈의 L1 레벨 상세 기능 또는 정보가 표시됩니다.")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.popBackStack() }, // 이전 화면 (Dominant Screen)으로 돌아가기
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp,
                ),
            ) {
                Text("뒤로 가기 (${seriesName} Dominant로)")
            }
        }
    }
}





@Composable
fun GenericDominantScreen(
    seriesName: String,
    l1ScreenRoute: String,
    navController: NavHostController,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image10_base), // 실제 이미지 리소스로 변경하세요.
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("$seriesName 도미넌트 스크린 (L0)", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(l1ScreenRoute) },
                shape = RoundedCornerShape(12.dp), // 둥근 모서리 적용
                elevation = ButtonDefaults.buttonElevation(
                    // 그림자 효과 적용
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp,
                    disabledElevation = 0.dp,
                ),
            ) {
                Text("$seriesName - L1 기능 가기")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(12.dp), // 둥근 모서리 적용
                elevation = ButtonDefaults.buttonElevation(
                    // 그림자 효과 적용
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp,
                    disabledElevation = 0.dp,
                ),
            ) {
                Text("뒤로 가기 (Main Hub로)")
            }
        }
    }
}

