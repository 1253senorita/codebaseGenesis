package com.TYTgoogle.TYTfirebase.TYTexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.TYTgoogle.TYTfirebase.TYTexample.ui.LoginScreen



// --- 기존 라우트 정의 ---
object LoginRoute {
    const val route = "login"
}

object MajorRoute { // 이제 Main Hub Screen 또는 Dashboard 역할
    const val routeTemplate = "major/{userEmail}"
    fun createRoute(userEmail: String?) = "major/${userEmail ?: "Guest"}"
    const val USER_EMAIL_ARG = "userEmail"
}

// --- 20개 시리즈 라우트 정의 시작 (AA ~ AS) ---

// Helper function to create series routes to reduce boilerplate
fun createSeriesRoutes(seriesPrefix: String): Pair<String, String> {
    val dominantRoute = "${seriesPrefix.lowercase()}_dominant"
    val l1ScreenRoute = "${seriesPrefix.lowercase()}_l1_screen"
    return Pair(dominantRoute, l1ScreenRoute)
}

val seriesNames = ('A'..'A') // AA to AS (20 series)
    .flatMap { firstChar -> ('A'..'S').map { secondChar -> "$firstChar$secondChar" } }
    .take(20) // Ensure exactly 20 series

object Routes {
    val seriesRoutes = seriesNames.associateWith { createSeriesRoutes(it) }
}
// --- 20개 시리즈 라우트 정의 끝 ---


// 메인 허브 화면 (기존 MajorScreen 역할)
@Composable
fun MainHubScreen(
    userEmail: String?,
    onLogout: () -> Unit,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Welcome to Main Hub, ${userEmail ?: "Guest"}!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn( // 버튼이 많으므로 LazyColumn 사용
            modifier = Modifier.weight(1f), // 남은 공간을 채우도록
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(Routes.seriesRoutes.size) { index ->
                val seriesName = seriesNames[index]
                val (dominantRoute, _) = Routes.seriesRoutes[seriesName]!!
                Button(
                    onClick = { navController.navigate(dominantRoute) },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("$seriesName 시리즈 가기")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}


// --- 20개 시리즈를 위한 Generic 스크린 Composable 함수들 시작 ---
@Composable
fun GenericDominantScreen(
    seriesName: String,
    l1ScreenRoute: String,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("$seriesName 도미넌트 스크린 (L0)", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate(l1ScreenRoute) }) {
            Text("$seriesName - L1 기능 가기")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("뒤로 가기 (Main Hub로)")
        }
    }
}

@Composable
fun GenericL1Screen(
    seriesName: String,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("$seriesName - L1 기능 스크린", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("이것은 $seriesName 시리즈의 L1 화면입니다.")
                Text("간단한 내용을 표시합니다.")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("뒤로 가기 (${seriesName} Dominant로)")
        }
    }
}
// --- 20개 시리즈를 위한 Generic 스크린 Composable 함수들 끝 ---


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier, // <--- 이 부분을 추가하거나 확인하세요!
    auth: FirebaseAuth,
    showSnackBar: (String) -> Unit
    // MainViewModel 등은 필요시 주입 (현재 예제에서는 직접 사용 안함)
) {
    val navController = rememberNavController()

    val startDestinationRoute = if (auth.currentUser != null) {
        MajorRoute.createRoute(auth.currentUser?.email)
    } else {
        LoginRoute.route
    }

    NavHost(navController = navController, startDestination = startDestinationRoute) {
        // --- 기존 로그인 및 메인 허브 라우트 ---
        composable(LoginRoute.route) {
            LoginScreen(
                auth = auth,
                onLoginSuccess = { firebaseUser: FirebaseUser ->
                    val userEmailForRoute = firebaseUser.email
                    navController.navigate(MajorRoute.createRoute(userEmailForRoute)) {
                        popUpTo(LoginRoute.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToSignUp = { /* TODO */ },
                showSnackBar = showSnackBar,
            )
        }

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
            MainHubScreen( // MajorScreen 대신 MainHubScreen 사용
                userEmail = userEmail,
                navController = navController,
                onLogout = {
                    auth.signOut()
                    navController.navigate(LoginRoute.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                    showSnackBar("Logged out successfully.")
                },
            )
        }

        // --- 20개 시리즈를 NavHost에 동적으로 등록 시작 ---
        Routes.seriesRoutes.forEach { (seriesName, routes) ->
            val (dominantRoute, l1ScreenRoute) = routes

            // 각 시리즈의 Dominant Screen (L0) 등록
            composable(dominantRoute) {
                GenericDominantScreen(
                    seriesName = seriesName,
                    l1ScreenRoute = l1ScreenRoute,
                    navController = navController
                )
            }

            // 각 시리즈의 L1 Screen 등록
            composable(l1ScreenRoute) {
                GenericL1Screen(
                    seriesName = seriesName,
                    navController = navController
                )
            }
        }
        // --- 20개 시리즈를 NavHost에 동적으로 등록 끝 ---
    }
}