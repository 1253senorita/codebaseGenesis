package com.TYTgoogle.TYTfirebase.TYTexample

// ... (기존 import들은 대부분 유지, MajorScreen import 경로 확인)
//import MajorScreen // MajorScreen.kt의 MajorScreen Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
// MainActivity의 TopAppBar에서 사용하던 아이콘들은 MajorScreen으로 옮겨가므로 여기서 제거 가능
// import androidx.compose.material.icons.Icons
// import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// import androidx.compose.ui.unit.dp // Modifier.padding(paddingValues) 외 직접 사용 없으면 제거 가능
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
// import java.util.Locale // 현재 미사용

import com.TYTgoogle.TYTfirebase.TYTexample.ui.LoginScreen
import com.TYTgoogle.TYTfirebase.TYTexample.LoginRoute
import com.TYTgoogle.TYTfirebase.TYTexample.Routes // MajorRoute 접근 등

// TopAppBarState는 이제 MainActivity에서 TopAppBar를 거의 사용하지 않으므로,
// Login 화면 등에서 TopAppBar를 숨길지 여부만 판단하는 용도로 단순화될 수 있습니다.
// 혹은 아예 제거하고, 각 화면이 자체적으로 Scaffold와 TopAppBar를 관리하도록 할 수도 있습니다.
// 여기서는 Login 화면에서 TopAppBar가 없다는 것을 명시하기 위해 남겨둡니다.
data class TopAppBarConfig( // 이름 변경 (선택적)
    val showTopAppBar: Boolean = false, // 기본적으로 TopAppBar 숨김
    val title: String = "" // Login 등에서는 사용 안 함
)

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = Firebase.auth

        setContent {
            FirebaseDataConnectTheme {
                val mainViewModel: MainViewModel = viewModel()
                val isSplashVisible by mainViewModel.isSplashVisible.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                val mainAppNavController = rememberNavController()

                var topAppBarConfig by remember { mutableStateOf(TopAppBarConfig()) }
                val navBackStackEntry by mainAppNavController.currentBackStackEntryAsState()

                LaunchedEffect(navBackStackEntry) {
                    val currentRoute = navBackStackEntry?.destination?.route
                    topAppBarConfig = when { // when (value) 가 아닌 when {} 형태로 사용
                        currentRoute == LoginRoute.route -> TopAppBarConfig(showTopAppBar = false)
                        // MajorScreen 진입 시 MainActivity의 TopAppBar는 표시하지 않음
                        // MajorScreen이 자체 TopAppBar를 가짐
                        (currentRoute?.startsWith(MajorRoute.routeTemplate.substringBefore("/{")) ?: false) ->
                            TopAppBarConfig(showTopAppBar = false) // 여기서 TopAppBar를 안 보이게 설정
                        // 다른 최상위 화면에서 TopAppBar가 필요하다면 여기에 조건 추가
                        currentRoute == "app_profile_route" -> TopAppBarConfig(showTopAppBar = true, title = "앱 프로필")
                        currentRoute == "app_settings_route" -> TopAppBarConfig(showTopAppBar = true, title = "앱 설정")
                        else -> TopAppBarConfig(showTopAppBar = false) // 기본적으로 숨김
                    }
                }


                LaunchedEffect(Unit) {
                    splashScreen.setKeepOnScreenCondition { isSplashVisible }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        // MainActivity의 TopAppBar는 이제 MajorScreen에서는 표시되지 않음.
                        // Login 화면에서는 원래부터 없었고, 다른 최상위 화면에서 필요시에만 표시.
                        if (topAppBarConfig.showTopAppBar && topAppBarConfig.title.isNotBlank()) {
                            TopAppBar(
                                title = { Text(topAppBarConfig.title) }
                                // actions는 여기서 관리하지 않음. 각 화면이 필요시 자체 TopAppBar에 구현
                            )
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = mainAppNavController,
                        startDestination = if (auth.currentUser != null) MajorRoute.createRoute(auth.currentUser?.email) else LoginRoute.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(LoginRoute.route) {
                            LoginScreen(
                                auth = auth,
                                onLoginSuccess = { firebaseUser ->
                                    mainAppNavController.navigate(MajorRoute.createRoute(firebaseUser.email)) {
                                        popUpTo(LoginRoute.route) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                                onNavigateToSignUp = {
                                    coroutineScope.launch { snackbarHostState.showSnackbar("회원가입 화면으로 이동 (구현 필요)") }
                                },
                                showSnackBar = { message ->
                                    coroutineScope.launch { snackbarHostState.showSnackbar(message) }
                                }
                            )
                        }

                        composable(
                            route = MajorRoute.routeTemplate,
                            arguments = listOf(navArgument(MajorRoute.USER_EMAIL_ARG) {
                                type = NavType.StringType
                                nullable = true
                            })
                        ) { backStackEntry ->
                            val userEmail = backStackEntry.arguments?.getString(MajorRoute.USER_EMAIL_ARG)
                            MajorScreen(
                                userEmail = userEmail,
                                mainAppNavController = mainAppNavController
                            )
                        }

                        composable("app_profile_route") {
                            // 이 화면은 자체 Scaffold와 TopAppBar를 가질 수도 있고,
                            // MainActivity의 TopAppBar를 사용할 수도 있습니다. (현재는 MainActivity의 것 사용)
                            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) { // paddingValues 대신 직접 지정
                                Text("앱 프로필 화면 (최상위)", style = MaterialTheme.typography.headlineMedium)
                            }
                        }
                        composable("app_settings_route") { // MainActivity의 TopAppBar 사용
                            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                                Text("앱 설정 화면 (최상위)", style = MaterialTheme.typography.headlineMedium)
                            }
                        }
                        // "app_settings_from_main" 라우트가 필요하다면 여기서 정의
                        composable("app_settings_from_main") {
                            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                                Text("MajorScreen에서 호출된 전체 앱 설정", style = MaterialTheme.typography.headlineMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

