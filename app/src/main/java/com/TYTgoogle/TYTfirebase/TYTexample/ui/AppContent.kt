package com.TYTgoogle.TYTfirebase.TYTexample.ui // AppContent.kt의 패키지 확인

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// import androidx.compose.ui.unit.dp // dp는 현재 직접 사용되지 않음 (필요 시 유지)
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.TYTgoogle.TYTfirebase.TYTexample.MainViewModel
import com.TYTgoogle.TYTfirebase.TYTexample.ui.theme.FirebaseDataConnectTheme

// Route 객체 import - 실제 정의된 위치에 따라 경로 확인 필요
// 가정: Route들은 com.TYTgoogle.TYTfirebase.TYTexample.ui.routes 패키지에 정의되어 있음
import com.TYTgoogle.TYTfirebase.TYTexample.ui.routes.LoginRoute
import com.TYTgoogle.TYTfirebase.TYTexample.ui.routes.SignUpRoute
import com.TYTgoogle.TYTfirebase.TYTexample.ui.routes.MoviesRoute // MoviesRoute도 routes 패키지에 있다고 가정
import com.TYTgoogle.TYTfirebase.TYTexample.ui.routes.MovieDetailRoute // MovieDetailRoute도 routes 패키지에 있다고 가정

// 화면 Composable import - 실제 정의된 위치에 따라 경로 확인 필요
// 가정: 화면들은 com.TYTgoogle.TYTfirebase.TYTexample.ui 패키지 바로 아래에 있음
import com.TYTgoogle.TYTfirebase.TYTexample.ui.LoginScreen
import com.TYTgoogle.TYTfirebase.TYTexample.ui.SignUpScreen // SignUpScreen도 NavHost에 추가할 경우 필요
// import com.TYTgoogle.TYTfirebase.TYTexample.ui.MoviesScreen // 실제 MoviesScreen Composable이 있다면 import
// import com.TYTgoogle.TYTfirebase.TYTexample.ui.MovieDetailScreen // 실제 MovieDetailScreen Composable이 있다면 import

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun AppContent(
    mainViewModel: MainViewModel,
    auth: FirebaseAuth
) {
    val isSplashVisible by mainViewModel.isSplashVisible.collectAsState()
    val currentUser by mainViewModel.currentUser.collectAsState()

    FirebaseDataConnectTheme {
        val navController = rememberNavController()
        val snackBarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        if (isSplashVisible) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading...") // 실제 스플래시 UI로 대체 권장
            }
        } else {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { SnackbarHost(snackBarHostState) }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = if (currentUser != null) MoviesRoute else LoginRoute,
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding) // consumeWindowInsets는 deprecated 될 수 있으므로 최신 API 확인
                ) {
                    composable<LoginRoute> {
                        LoginScreen( // com.TYTgoogle.TYTfirebase.TYTexample.ui.LoginScreen 호출
                            auth = auth,
                            onLoginSuccess = {
                                navController.navigate(MoviesRoute) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            },
                            onNavigateToSignUp = {
                                navController.navigate(SignUpRoute)
                            },
                            showSnackBar = { message -> // <<--- 누락되었던 파라미터 추가
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar(message)
                                }
                            }
                        )
                    }

                    // SignUpRoute도 NavHost에 추가하는 것이 일반적입니다.
                    composable<SignUpRoute> {
                        SignUpScreen( // com.TYTgoogle.TYTfirebase.TYTexample.ui.SignUpScreen 호출
                            auth = auth,
                            onSignUpSuccess = {
                                // 회원가입 성공 후 로그인 화면으로 이동 또는 자동 로그인 처리
                                navController.navigate(LoginRoute) {
                                    popUpTo(SignUpRoute) { inclusive = true } // SignUp 화면은 스택에서 제거
                                    launchSingleTop = true
                                }
                                coroutineScope.launch { // 스낵바 표시
                                    snackBarHostState.showSnackbar("Sign up successful! Please log in.")
                                }
                            },
                            onNavigateToLogin = {
                                navController.popBackStack() // 이전 화면 (아마도 로그인 화면)으로 이동
                            },
                            showSnackBar = { message ->
                                coroutineScope.launch {
                                    snackBarHostState.showSnackbar(message)
                                }
                            }
                        )
                    }

                    composable<MoviesRoute> {
                        // 실제 MoviesScreen Composable로 대체해야 합니다.
                        // 예: MoviesScreen(mainViewModel = mainViewModel, navController = navController, showSnackBar = { ... })
                        if (currentUser != null) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Movies Screen Placeholder - User: ${currentUser?.email}")
                                // 여기에 로그아웃 버튼 등을 추가할 수 있습니다.
                                Button(onClick = {
                                    auth.signOut() // Firebase 로그아웃
                                    // 로그아웃 후 로그인 화면으로 이동하는 로직은 LaunchedEffect에서 처리될 수 있음
                                }) {
                                    Text("Log Out")
                                }
                            }
                        } else {
                            // 이 경우는 startDestination 로직 또는 LaunchedEffect에 의해
                            // 로그인 화면으로 리디렉션 되어야 하므로, 일반적으로 여기에 도달하지 않거나
                            // 도달하더라도 즉시 로그인 화면으로 보내야 합니다.
                            Log.d("NavHost", "MoviesRoute reached but currentUser is null. Redirecting to Login.")
                            // LaunchedEffect가 이 상황을 처리하도록 두거나, 여기서 즉시 navigate 할 수 있지만 중복될 수 있음
                            // navController.navigate(LoginRoute) {
                            // popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                            // launchSingleTop = true
                            // }
                        }
                    }

                    composable<MovieDetailRoute> { backStackEntry ->
                        // 실제 MovieDetailScreen Composable로 대체해야 합니다.
                        // 예: MovieDetailScreen(movieId = movieDetailArgs.movieId, onNavigateBack = { navController.popBackStack() })
                        if (currentUser != null) {
                            val movieDetailArgs = backStackEntry.toRoute<MovieDetailRoute>()
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Movie Detail Screen for ID: ${movieDetailArgs.movieId}")
                            }
                        }
                        // 사용자가 null인 경우에 대한 처리는 MoviesRoute와 유사하게 고려
                    }
                }

                LaunchedEffect(currentUser, navController, isSplashVisible) {
                    if (!isSplashVisible) {
                        val currentRouteObject = try {
                            navController.currentBackStackEntry?.toRoute<Any?>()
                        } catch (e: Exception) {
                            // toRoute 변환 실패 시 (예: 아직 경로가 준비되지 않은 극초기 상태)
                            Log.w("AppContentNavEffect", "Failed to get current route object: ${e.message}")
                            null
                        }

                        Log.d("AppContentNavEffect", "User: ${currentUser?.email}, Current Route: $currentRouteObject, Splash: $isSplashVisible")

                        if (currentUser != null) { // 로그인 된 상태
                            // 현재 화면이 Login 또는 SignUp 화면이라면 Movies 화면으로 이동
                            if (currentRouteObject is LoginRoute || currentRouteObject is SignUpRoute) {
                                Log.d("AppContentNavEffect", "User logged in. Navigating to Movies from $currentRouteObject")
                                navController.navigate(MoviesRoute) {
                                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                                    launchSingleTop = true
                                }
                            } else if (currentRouteObject == null && navController.currentDestination?.route != MoviesRoute.toString()) {
                                // 앱 시작 시 사용자가 이미 로그인되어 있고, 아직 MoviesRoute가 아닌 경우 (startDestination 로직 보완)
                                Log.d("AppContentNavEffect", "User logged in (initial/other). Ensuring navigation to Movies.")
                                navController.navigate(MoviesRoute) {
                                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        } else { // 로그아웃 된 상태 또는 초기 상태 (currentUser가 null)
                            // 현재 화면이 Login 또는 SignUp 화면이 *아니라면* Login 화면으로 이동
                            if (currentRouteObject !is LoginRoute && currentRouteObject !is SignUpRoute) {
                                Log.d("AppContentNavEffect", "User not logged in. Navigating to Login from $currentRouteObject")
                                navController.navigate(LoginRoute) {
                                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                            // currentRouteObject가 null이고 로그아웃 상태인 경우는 startDestination이 LoginRoute로 잘 처리할 것임
                        }
                    }
                }
            }
        }
    }
}