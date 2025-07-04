

package com.TYTgoogle.TYTfirebase.TYTexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.* // Material 3 import
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.TYTgoogle.TYTfirebase.TYTexample.ui.theme.FirebaseDataConnectTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.Locale

// AppNavigation.kt 또는 다른 파일에서 이들을 import 해야 합니다.
// (실제 프로젝트 구조에 맞게 경로 수정 필요)
import com.TYTgoogle.TYTfirebase.TYTexample.ui.LoginScreen // 실제 LoginScreen 경로
//import com.TYTgoogle.TYTfirebase.TYTexample.GenericL1Screen
import com.TYTgoogle.TYTfirebase.TYTexample.GenericDominantScreen
import com.TYTgoogle.TYTfirebase.TYTexample.GenericDominantScreen

// 아래와 같이 실제 파일 위치에 맞게 수정해야 합니다.
import com.TYTgoogle.TYTfirebase.TYTexample.MajorScreen // 예시 경로
import com.TYTgoogle.TYTfirebase.TYTexample.GenericL1Screen // 예시 경로
import com.TYTgoogle.TYTfirebase.TYTexample.GenericDominantScreen // 예시 경로

import com.TYTgoogle.TYTfirebase.TYTexample.LoginRoute
//import com.TYTgoogle.TYTfirebase.TYTexample.MajorScreen
import com.TYTgoogle.TYTfirebase.TYTexample.Routes
import com.TYTgoogle.TYTfirebase.TYTexample.allSeriesData // allSeriesData 임포트

// TopAppBar 상태를 위한 데이터 클래스
data class TopAppBarState(
    val title: String = "", // 기본적으로 제목 없음 (로그인 화면 등에서 TopAppBar 숨김 처리 위함)
    val showActions: Boolean = false,
)

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = Firebase.auth
        val initialAppLocale = Locale.getDefault() // 다국어 지원용 (현재 코드에서는 직접 사용 안함)

        setContent {
            FirebaseDataConnectTheme {
                val mainViewModel: MainViewModel = viewModel()
                val isSplashVisible by mainViewModel.isSplashVisible.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                val navController = rememberNavController()

                var topAppBarState by remember { mutableStateOf(TopAppBarState()) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                // 현재 라우트에 따라 TopAppBar 상태 업데이트
                LaunchedEffect(navBackStackEntry) {
                    val currentRoute = navBackStackEntry?.destination?.route
                    val currentLabel = navBackStackEntry?.destination?.label?.toString()

                    topAppBarState = when {
                        currentRoute == LoginRoute.route -> TopAppBarState(title = "", showActions = false) // 로그인 화면에서는 TopAppBar 숨김
                        currentRoute?.startsWith(MajorRoute.routeTemplate.substringBefore("/{")) == true -> TopAppBarState(title = "메인 허브", showActions = true)
                        Routes.seriesRoutes.any { it.value.first == currentRoute || it.value.second == currentRoute } -> {

                            val seriesEntry = Routes.seriesRoutes.entries.find { it.value.first == currentRoute || it.value.second == currentRoute }

                            val seriesInfo = allSeriesData.find { it.id == seriesEntry?.key }

                            TopAppBarState(title = seriesInfo?.displayName ?: (currentLabel ?: "시리즈 화면"), showActions = false)
                        }
                        // 프로필 또는 설정 화면 라우트 추가 가능
                        currentRoute == "profile_route" -> TopAppBarState(title = "내 정보", showActions = false)
                        currentRoute == "settings_route" -> TopAppBarState(title = "설정", showActions = false)
                        else -> TopAppBarState(title = currentLabel ?: "", showActions = false)
                    }
                }

                // 스플래시 화면 표시 조건
                LaunchedEffect(Unit) {
                    splashScreen.setKeepOnScreenCondition { isSplashVisible }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        // 제목이 있을 때만 TopAppBar 표시 (로그인 화면 등에서 숨기기 위함)
                        if (topAppBarState.title.isNotBlank()) {
                            TopAppBar(
                                title = { Text(topAppBarState.title) },
                                actions = {
                                    if (topAppBarState.showActions) {
                                        // 현재 라우트가 MajorScreen과 관련된 경우에만 액션 표시
                                        if (navController.currentDestination?.route?.startsWith(MajorRoute.routeTemplate.substringBefore("/{")) == true) {
                                            val currentUserEmail = auth.currentUser?.email
                                            Text(
                                                text = "Welcome, ${currentUserEmail ?: "Guest"}!",
                                                style = MaterialTheme.typography.bodySmall,
                                                modifier = Modifier.padding(end = 8.dp)
                                            )
                                            IconButton(onClick = { navController.navigate("profile_route") }) {
                                                Icon(Icons.Filled.AccountCircle, contentDescription = "내 정보")
                                            }
                                            IconButton(onClick = {
                                                auth.signOut()
                                                navController.navigate(LoginRoute.route) {
                                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                                    launchSingleTop = true
                                                }
                                                coroutineScope.launch { snackbarHostState.showSnackbar("Logged out.") }
                                            }) {
                                                Icon(Icons.Filled.ExitToApp, contentDescription = "로그아웃")
                                            }
                                            IconButton(onClick = { navController.navigate("settings_route") }) {
                                                Icon(Icons.Filled.Settings, contentDescription = "설정")
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                ) { paddingValues ->
                    // NavHost를 직접 정의하여 네비게이션 그래프 설정
                    NavHost(
                        navController = navController,
                        startDestination = if (auth.currentUser != null) MajorRoute.createRoute(auth.currentUser?.email) else LoginRoute.route,
                        modifier = Modifier.padding(paddingValues) // Scaffold로부터 받은 패딩 적용
                    ) {
                        // 로그인 화면
                        composable(LoginRoute.route) {
                            LoginScreen( // 실제 LoginScreen Composable
                                auth = auth,
                                onLoginSuccess = { firebaseUser ->
                                    navController.navigate(MajorRoute.createRoute(firebaseUser.email)) {
                                        popUpTo(LoginRoute.route) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                                onNavigateToSignUp = {
                                    // TODO: 회원가입 화면으로 네비게이션 로직 구현
                                    coroutineScope.launch { snackbarHostState.showSnackbar("회원가입 화면으로 이동 (구현 필요)") }
                                },
                                showSnackBar = { message ->
                                    coroutineScope.launch { snackbarHostState.showSnackbar(message) }
                                }
                            )
                        }

                        // 메인 화면 (MajorScreen)
                        composable(
                            route = MajorRoute.routeTemplate,
                            arguments = listOf(navArgument(MajorRoute.USER_EMAIL_ARG) {
                                type = NavType.StringType
                                nullable = true
                            })
                        ) { backStackEntry ->
                            val userEmail = backStackEntry.arguments?.getString(MajorRoute.USER_EMAIL_ARG)
                            MajorScreen( // onLogout 파라미터가 없는 버전
                                userEmail = userEmail,
                                navController = navController
                            )
                        }

                        // 동적으로 생성된 dominantRoute 시리즈 SeriesInfo 라우트들
                        Routes.seriesRoutes.forEach { (seriesId, routesPair) ->
                            val (dominantRoute, l1ScreenRoute) = routesPair
                            val seriesInfo = allSeriesData.find { it.id == seriesId }

                            composable(dominantRoute) {
                                GenericDominantScreen(
                                    seriesName = seriesInfo?.displayName ?: seriesId,
                                    l1ScreenRoute = l1ScreenRoute,
                                    navController = navController
                                )
                            }
                            composable(l1ScreenRoute) {
                                GenericL1Screen(
                                    seriesName = seriesInfo?.displayName ?: seriesId,
                                    navController = navController
                                )
                            }
                        }

                        // 프로필 화면 라우트 (임시)
                        composable("profile_route") {
                            // TODO: 실제 ProfileScreen 컴포저블로 교체
                            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                                Text("프로필 화면 (구현 필요)", style = MaterialTheme.typography.headlineMedium)
                            }
                        }

                        // 설정 화면 라우트 (임시)
                        composable("settings_route") {
                            // TODO: 실제 SettingsScreen 컴포저블로 교체
                            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                                Text("설정 화면 (구현 필요)", style = MaterialTheme.typography.headlineMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}


