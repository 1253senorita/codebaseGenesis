package com.TYTgoogle.TYTfirebase.TYTexample // 패키지 이름은 실제 프로젝트에 맞게!

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

// LoginScreen import (실제 경로에 맞게 확인하세요)
// LoginScreen.kt 파일이 com.TYTgoogle.TYTfirebase.TYTexample.ui 패키지 내에 있다고 가정합니다.
import com.TYTgoogle.TYTfirebase.TYTexample.ui.LoginScreen

// 화면 라우트 정의
object LoginRoute {
    const val route = "login"
}

object MajorRoute {
    const val routeTemplate = "movies/{userEmail}" // 사용자 이메일을 전달받기 위한 템플릿
    fun createRoute(userEmail: String?) = "movies/${userEmail ?: "Guest"}" // 실제 경로 생성 함수
    const val USER_EMAIL_ARG = "userEmail" // MoviesScreen에서 받을 인자 이름
}

// 메인 영화 목록 화면 (예시)
@Composable
fun MajorScreen(userEmail: String?, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Welcome to AppNavigation.kt  in  Major 의 메이져 스크린 과  49 라인, ${userEmail ?: "Guest"}!")
        Spacer(modifier = Modifier.height(16.dp))
        // 여기에 영화 목록이나 다른 콘텐츠 UI가 들어갈 수 있습니다.
        Button(onClick = onLogout) {
            Text("Logout 메이져 스크린 53 라인")
        }
    }
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier, // <--- 추가
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel, // <--- 추가
    auth: FirebaseAuth,
    startDestination: Any, // <--- 추가 (타입-세이프 Route 객체로 받는 것이 이상적)
    showSnackBar: (String) -> Unit
) {
    // 앱 시작 시 사용자가 이미 로그인했는지 확인하여 시작 지점 결정
    // 실제로는 ViewModel에서 이 로직을 처리하고 초기 라우트를 결정하는 것이 좋습니다.
    val startDestination = if (auth.currentUser != null) {
        MajorRoute.createRoute(auth.currentUser?.email)
    } else {
        LoginRoute.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(LoginRoute.route) {
            // LoginScreen 호출 시 auth, onLoginSuccess, showSnackBar 등을 전달합니다.
            // onNavigateToSignUp은 FirebaseUI를 사용하면 LoginScreen 내부에서
            // 직접 처리되거나 필요 없을 수 있습니다.
            LoginScreen(
                auth = auth, // LoginScreen이 FirebaseAuth 인스턴스를 직접 사용한다면 전달
                onLoginSuccess = { firebaseUser: FirebaseUser ->
                    // 로그인 성공! FirebaseUser 객체를 받았습니다.
                    val userEmailForRoute = firebaseUser.email // 또는 displayName 등 필요한 정보

                    // MoviesScreen으로 이동하고, 경로에 사용자 이메일 포함
                    navController.navigate(MajorRoute.createRoute(userEmailForRoute)) {
                        // 로그인 화면은 백스택에서 제거 (뒤로 가기 시 다시 안 보이게)
                        popUpTo(LoginRoute.route) { inclusive = true }
                        // 이미 MoviesScreen이 백스택에 있다면 새로 만들지 않고 기존 것을 사용
                        launchSingleTop = true
                    }
                    // 스낵바 표시는 LoginScreen 내부의 onSignInResult에서도 이미 처리될 수 있으므로
                    // 여기서는 중복 호출을 피하거나, LoginScreen 내부 호출을 제거하고 여기서만 할 수 있습니다.
                    // 현재 LoginScreen.kt의 onSignInResult에서 스낵바를 보여주고 있으므로,
                    // 여기서는 추가적인 스낵바 호출이 필요 없을 수 있습니다.
                    // showSnackBar("Navigating to Movies screen...") // 필요하다면 추가
                },
                 onNavigateToSignUp = { navController.navigate("signup_route_if_needed") }, // 만약 별도 가입 화면이 있다면
                showSnackBar = showSnackBar, // LoginScreen 내부에서 스낵바를 사용하도록 전달
            )
        }

        composable(
            route = MajorRoute.routeTemplate, // 경로 템플릿 사용
            arguments = listOf(
                navArgument(MajorRoute.USER_EMAIL_ARG) {
                    type = NavType.StringType
                    nullable = true // 사용자 이메일이 없을 수도 있음을 고려
                },
            ), // 인자 정의
        ) { backStackEntry ->
            // 전달받은 사용자 이메일 가져오기
            val userEmail = backStackEntry.arguments?.getString(MajorRoute.USER_EMAIL_ARG)
            MajorScreen(
                userEmail = userEmail,
                onLogout = {
                    auth.signOut() // Firebase 로그아웃
                    // 로그아웃 후 로그인 화면으로 이동하고 이전 백스택 모두 제거
                    navController.navigate(LoginRoute.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                    showSnackBar("Logged out successfully.")
                },
            )
        }

        // 만약 SignUpScreen으로의 명시적인 라우팅이 필요하다면 여기에 추가:
        // composable("signup_route_if_needed") {
        //     SignUpScreen(...)
        // }
    }
}