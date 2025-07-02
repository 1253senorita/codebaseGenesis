
package com.TYTgoogle.TYTfirebase.TYTexample // 패키지 선언은 파일 상단에 있어야 합니다.

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.* // 와일드카드 임포트 대신 개별 임포트 권장
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape // RoundedCornerShape 임포트
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults // ButtonDefaults 임포트
import androidx.compose.material3.Text
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












@Composable
fun AppNavigation(
    // AppNavigation 함수 시그니처는 기존 파일의 것을 따릅니다.
    modifier: Modifier = Modifier,
    auth: FirebaseAuth, // MainActivity로부터 전달받는 auth 객체
    showSnackBar: (String) -> Unit,
) {
    val navController = rememberNavController()

    // 로그인 상태에 따라 시작 지점 결정
    val startDestination = if (auth.currentUser != null) {
        MajorRoute.createRoute(auth.currentUser?.email)
    } else {
        LoginRoute.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier, // MainActivity에서 전달된 modifier 적용
    ) {
        // 로그인 화면
        composable(LoginRoute.route) {
            // LoginScreen 호출 (LoginScreen Composable은 다른 파일에 정의되어 있다고 가정)
            // 예시: LoginScreen(navController = navController, onLoginSuccess = { ... })
            // 실제 LoginScreen의 파라미터에 맞게 호출해야 합니다.
            // 여기서는 임시로 Text를 배치합니다. 실제 LoginScreen 구현으로 대체되어야 합니다.
            com.TYTgoogle.TYTfirebase.TYTexample.ui.LoginScreen(
                // LoginScreen의 실제 경로로 수정
                auth = auth,
                onLoginSuccess = { firebaseUser ->
                    val userEmailForRoute = firebaseUser.email
                    navController.navigate(MajorRoute.createRoute(userEmailForRoute)) {
                        popUpTo(LoginRoute.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToSignUp = { /* 회원가입 로직 */ },
                showSnackBar = showSnackBar,
            )
        }

        // 메인 화면 (MajorScreen)
        composable(
            route = MajorRoute.routeTemplate,
            arguments = listOf(
                navArgument(MajorRoute.USER_EMAIL_ARG) {
                    type = NavType.StringType
                    nullable = true
                },
            ),
        ) { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString(MajorRoute.USER_EMAIL_ARG)
            MajorScreen(
                userEmail = userEmail,
                onLogout = {
                    auth.signOut()
                    navController.navigate(LoginRoute.route) {
                        popUpTo(MajorRoute.createRoute(userEmail)) {
                            inclusive = true
                        } // 현재 화면까지 popUp
                        launchSingleTop = true
                    }
                    showSnackBar("Logged out successfully.")
                },
                navController = navController,
            )
        }

        // 동적으로 생성된 시리즈 라우트들
        Routes.seriesRoutes.forEach { (seriesId, routesPair) ->
            val (dominantRoute, l1ScreenRoute) = routesPair

            // 각 시리즈의 Dominant Screen (L0)
            composable(dominantRoute) {
                // seriesId 대신 seriesInfo 객체 또는 displayName을 전달할 수 있으면 더 좋습니다.
                // 여기서는 seriesId (예: "AA")를 seriesName으로 사용합니다.
                // 만약 displayName이 필요하면 allSeriesData에서 찾아야 합니다.
                val seriesInfo = allSeriesData.find { it.id == seriesId }
                GenericDominantScreen(
                    seriesName = seriesInfo?.displayName ?: seriesId, // displayName 사용, 없으면 id 사용
                    l1ScreenRoute = l1ScreenRoute,
                    navController = navController,
                )
            }

            // 각 시리즈의 L1 Screen 등록 (이 부분이 추가/수정됩니다)
            composable(l1ScreenRoute) {
                val seriesInfo = allSeriesData.find { it.id == seriesId }
                GenericL1Screen(
                    seriesName = seriesInfo?.displayName ?: seriesId, // displayName 사용, 없으면 id 사용
                    navController = navController,
                )
            }
        }
    }
}






object Routes {
    val seriesRoutes: Map<String, Pair<String, String>> = allSeriesData.associate { seriesInfo ->
        seriesInfo.id to createSeriesRoutes(seriesInfo.id)
    }
}




object LoginRoute {
    const val route = "login"
}

object MajorRoute {
    const val routeTemplate = "major/{userEmail}"
    fun createRoute(userEmail: String?) = "major/${userEmail ?: "Guest"}"
    const val USER_EMAIL_ARG = "userEmail"
}

fun createSeriesRoutes(seriesId: String): Pair<String, String> {
    val dominantRoute = "${seriesId.lowercase()}_dominant"
    val l1ScreenRoute = "${seriesId.lowercase()}_l1_screen"
    return Pair(dominantRoute, l1ScreenRoute)
}

val allSeriesData: List<SeriesInfo> = ('A'..'Z')
    .flatMap { firstChar ->
        ('A'..'Z').map { secondChar ->
            val seriesId = "$firstChar$secondChar"
            val displayName = when (seriesId) {
                "AA" -> "첫 번째 특별 시리즈"
                "AB" -> "사용자 분석 대시보드"
                else -> "$seriesId 시리즈"
            }
            SeriesInfo(id = seriesId, displayName = displayName)
        }
    }
    .take(50)











@Composable
fun MajorScreen(
    userEmail: String?,
    onLogout: () -> Unit,
    navController: NavHostController,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image9_base), // 실제 이미지 리소스로 변경하세요.
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
            Text(
                "Welcome to MajorScreen, ${userEmail ?: "Guest"}!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                val chunkedSeriesData = allSeriesData.chunked(2)
                items(chunkedSeriesData.size) { rowIndex ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        chunkedSeriesData[rowIndex].forEach { seriesInfo ->
                            val (dominantRoute, _) = Routes.seriesRoutes[seriesInfo.id]!!
                            Button(
                                onClick = { navController.navigate(dominantRoute) },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp),
                                shape = RoundedCornerShape(12.dp), // 둥근 모서리 적용 (예: 12dp)
                                elevation = ButtonDefaults.buttonElevation(
                                    // 그림자 효과 적용
                                    defaultElevation = 6.dp, // 기본 그림자
                                    pressedElevation = 2.dp,  // 눌렸을 때 그림자
                                    disabledElevation = 0.dp, // 비활성화 시 그림자
                                ),
                            ) {
                                Text(seriesInfo.displayName)
                            }
                        }
                        if (chunkedSeriesData[rowIndex].size == 1) {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp),
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLogout,
                shape = RoundedCornerShape(12.dp), // 둥근 모서리 적용 (시리즈 버튼과 동일하게)
                elevation = ButtonDefaults.buttonElevation(
                    // 그림자 효과 적용
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp,
                    disabledElevation = 0.dp,
                ),
            ) {
                Text("Logout")
            }
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
