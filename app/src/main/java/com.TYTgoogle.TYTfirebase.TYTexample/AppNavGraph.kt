package com.TYTgoogle.TYTfirebase.TYTexample

//import MajorScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.TYTgoogle.TYTfirebase.TYTexample.LoginRoute.route
import com.TYTgoogle.TYTfirebase.TYTexample.ui.LoginScreen


@Composable
fun AppNavGraph(navController: NavHostController, /* 필요한 다른 파라미터들 */) {
    NavHost(navController = navController, startDestination = route) { // 또는 다른 시작점
        composable(LoginRoute.route) {

        }
        composable(
            route = MajorRoute.routeTemplate,
            arguments = listOf(navArgument(MajorRoute.USER_EMAIL_ARG) { type = NavType.StringType; nullable = true })
        ) { backStackEntry ->
            val userEmail = backStackEntry.arguments?.getString(MajorRoute.USER_EMAIL_ARG)
            //MajorScreen(userEmail = userEmail, navController = navController)
        }

        // allSeriesData를 기반으로 동적 라우트 생성 (예시)
        allSeriesData.forEach { seriesInfo ->
            val (dominantRoute, l1ScreenRoute) = Routes.seriesRoutes[seriesInfo.id]
                ?: return@forEach // 안전하게 처리

            // Dominant Screen 라우 pd트 (예시, 실제 구현 필요)
            composable(dominantRoute) {
                // 예시: GenericDominantScreen(seriesInfo = seriesInfo, navController = navController)
                Text("Dominant Screen for ${seriesInfo.displayName}") // 실제 화면으로 대체
            }

            // L1 Screen 라우트
            composable(l1ScreenRoute) {
                GenericL1Screen(seriesName = seriesInfo.displayName, navController = navController)
            }
        }

        // 고정 버튼들이 사용하는 라우트 정의
        composable("my_custom_feature_route") {
            // MyCustomFeatureScreen(navController = navController)
            Text("My Custom Feature Screen") // 실제 화면으로 대체
        }
        composable("app_info_route") {
            // AppInfoScreen(navController = navController)
            Text("App Info Screen") // 실제 화면으로 대체
        }
        composable("customer_support_route") {
            // CustomerSupportScreen(navController = navController)
            Text("Customer Support Screen") // 실제 화면으로 대체
        }

        // PDF 뷰어 화면 라우트 (1번 방식 - Composable 사용 시)
        composable("pdfView/{pdfPath}") { backStackEntry ->
            val pdfPath = backStackEntry.arguments?.getString("pdfPath")
            if (pdfPath != null) {
                // PdfViewScreen(pdfFile = File(Uri.decode(pdfPath))) // 경로 인코딩/디코딩 방식에 따라 조정
                Text("PDF Viewer for $pdfPath") // 실제 PdfViewScreen Composable로 대체
            } else {
                Text("Error: PDF path not found")
                // 또는 오류 처리 로직
            }
        }

        // SimpleTextActivity를 Compose 화면으로 만든다면 추가
        // composable("simple_text_screen_route") {
        //    SimpleTextScreen(navController = navController)
        // }
    }
}
