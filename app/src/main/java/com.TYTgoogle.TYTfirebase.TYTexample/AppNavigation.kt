
package com.TYTgoogle.TYTfirebase.TYTexample // 패키지 선언은 파일 상단에 있어야 합니다.

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // 와일드카드 임포트 대신 개별 임포트 권장
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape // RoundedCornerShape 임포트
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults // ButtonDefaults 임포트
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.TYTgoogle.TYTfirebase.TYTexample.data.ActionItem
import com.TYTgoogle.TYTfirebase.TYTexample.data.ActionType
// ... (다른 필요한 임포트들)
import com.TYTgoogle.TYTfirebase.TYTexample.data.SeriesInfo // SeriesInfo 임포트 가정
import com.google.firebase.auth.FirebaseAuth

// import com.google.firebase.auth.FirebaseAuth // 사용되지 않으면 제거 가능
// import com.google.firebase.auth.FirebaseUser // 사용되지 않으면 제거 가능
// import com.TYTgoogle.TYTfirebase.TYTexample.ui.LoginScreen // 사용되지 않으면 제거 가능


// 전역 변수 또는 객체들은 여기에 위치 (예: allSeriesData, Routes 등)
// 예시로 allSeriesData와 Routes가 이 파일 또는 다른 파일에 정의되어 있다고 가정합니다.
// object Routes { /* ... */ }
// val allSeriesData: List<SeriesInfo> = /* ... */

// ... (AppNavigation.kt 파일의 상단 import 및 다른 Composable 함수들)











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

// AppNavigation.kt 파일 내 또는 별도의 Data 파일에 정의 가능
val allSeriesData: List<SeriesInfo> = listOf(
    SeriesInfo(
        id = "BEAUTY01",
        displayName = "뷰티 (네일/헤어)", // <-- "도미넌트 스크린의 이름" (표시용)
        imageUrl = "...",
        iconResId = R.drawable.image01_ic,
        initialActions = listOf( // <-- 여러 개의 ActionItem 객체들을 리스트로 전달
            ActionItem(
                displayText = "네일 기본 견적", // 첫 번째 아이템의 이름
                actionType = ActionType.NAVIGATE_SUB_DOMINANT,
                targetRoute = "beauty_nail_quote_sheet"
            ),
            ActionItem(
                displayText = "헤어 시술 상담", // 두 번째 아이템의 이름
                actionType = ActionType.NAVIGATE_SUB_DOMINANT,
                targetRoute = "beauty_hair_quote_sheet"
            ),
            ActionItem(
                displayText = "최신 포트폴리오 업로드", // 세 번째 아이템의 이름
                actionType = ActionType.UPLOAD_DATA,
                uploadDataType = "beauty_portfolio"
            )
            // ... 필요하다면 더 많은 ActionItem 추가 가능
        )
    ),
    SeriesInfo(
        id = "FOOD_DELIVERY01",
        displayName = "배달음식 전문", // <-- 다른 도미넌트 스크린의 이름
        initialActions = listOf(
            ActionItem(
                displayText = "대표 메뉴 주문",
                actionType = ActionType.NAVIGATE_SUB_DOMINANT,
                targetRoute = "food_order_main_menu"
            ),
            ActionItem(
                displayText = "리뷰 남기기",
                actionType = ActionType.SHOW_INFO_DIALOG // 예시로 다른 액션 타입
            )
        )
    )
    // ... 다른 SeriesInfo 객체들 ...
)





// import 문은 필요한 것들만 남깁니다. Scaffold, TopAppBar, Icons 관련 import는 제거될 수 있습니다.
// 예: import androidx.compose.material3.Scaffold // 제거
//    import androidx.compose.material3.TopAppBar // 제거
//    import androidx.compose.material.icons.Icons // MainActivity에서 사용
//    import androidx.compose.material.icons.filled.* // MainActivity에서 사용
@Composable
fun MajorScreen(
    userEmail: String?, // 이 정보는 여전히 화면 내 다른 곳에서 사용될 수 있습니다.
    // onLogout: () -> Unit, // MainActivity의 TopAppBar에서 처리하므로 제거
    navController: NavHostController, // 네비게이션은 계속 필요
) {
    // Scaffold와 TopAppBar가 제거되었습니다.
    // innerPadding도 제거되었습니다.
    Box(
        modifier = Modifier
            .fillMaxSize()
        // .padding(innerPadding) // 제거: MainActivity의 Scaffold가 패딩 관리
    ) {
        Image(
            painter = painterResource(id = R.drawable.image9_base), // 실제 이미지 리소스로 변경하세요.
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // 컨텐츠 자체의 내부 패딩은 유지
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // "Welcome" 메시지는 MainActivity의 TopAppBar로 옮겼다고 가정하고 여기서 제거하거나,
            // 화면 본문에 별도로 표시하고 싶다면 유지할 수 있습니다.
            // 예를 들어, 사용자 이름만 간단히 표시할 수도 있습니다.
            // Text(
            //     text = "Current User: ${userEmail ?: "Guest"}",
            //     fontSize = 18.sp, // TopAppBar와 다른 스타일로
            //     fontWeight = FontWeight.SemiBold
            // )
            // Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f), // 남은 공간을 채우도록
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                val chunkedSeriesData = allSeriesData.chunked(2) // allSeriesData 사용
                items(items = chunkedSeriesData) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        rowItems.forEach { seriesInfo ->
                            // Routes 객체 사용
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
                        // 행에 아이템이 하나만 있을 경우 빈 공간 채우기
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f).padding(horizontal = 4.dp))
                        }
                    }
                }
            }
            // 기존 하단 로그아웃 버튼은 MainActivity의 TopAppBar로 이동했으므로 여기서는 제거
        }
    }
}



// ... (AppNavigation.kt 파일의 기존 코드 상단)

// MajorScreen, GenericDominantScreen 등의 Composable 함수들...

// 여기에 GenericL1Screen Composable 함수를 추가합니다.
@Composable
fun GenericL1Screen(
    seriesName: String, // 어떤 시리즈의 L1 화면인지 식별
    navController: NavHostController // 뒤로 가기 등의 네비게이션을 위해 필요
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image8_base), // L1 스크린용 배경 이미지 (새 이미지 또는 기존 이미지 재활용)
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("$seriesName - L1 기능 스크린", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            // L1 화면의 구체적인 내용 (예시: Card 안에 정보 표시)
            // 이 부분은 실제 L1 화면의 요구사항에 맞게 커스터마이징합니다.
            androidx.compose.material3.Card( // Card 임포트가 명시적으로 필요할 수 있음
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
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
                    pressedElevation = 2.dp
                )
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

