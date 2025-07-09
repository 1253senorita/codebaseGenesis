package com.TYTgoogle.TYTfirebase.TYTexample // 패키지 이름은 실제 프로젝트에 맞게

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // LazyColumn의 items 사용을 위해
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.TYTgoogle.TYTfirebase.TYTexample.data.SeriesInfo // SeriesInfo 실제 경로로 수정 필요
import com.TYTgoogle.TYTfirebase.TYTexample.R // 실제 R 클래스 경로로 수정 필요
import com.TYTgoogle.TYTfirebase.TYTexample.Routes // 실제 Routes 객체 경로로 수정 필요
import com.TYTgoogle.TYTfirebase.TYTexample.LoginRoute // 실제 LoginRoute 객체 경로로 수정 필요
// GenericDominantScreen, GenericL1Screen, allSeriesData 가 다른 파일에 정의되어 있다면 import 필요
// 예시:
// import com.TYTgoogle.TYTfirebase.TYTexample.ui.screens.GenericDominantScreen
// import com.TYTgoogle.TYTfirebase.TYTexample.ui.screens.GenericL1Screen
// import com.TYTgoogle.TYTfirebase.TYTexample.data.allSeriesData // ViewModel 등을 통해 주입받는 것을 권장

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

// 아래의 예시 데이터/객체/함수들은 실제 프로젝트의 정의를 사용하고, 이 부분은 삭제하거나 주석 처리합니다.
// 만약 이 파일에서만 사용되는 임시 데이터라면 그대로 두어도 되지만,
// 다른 파일에 이미 동일한 이름의 정의가 있다면 충돌합니다.
/*
val allSeriesData = listOf(SeriesInfo("s1", "시리즈 1"), SeriesInfo("s2", "시리즈 2")) // 실제 데이터 소스 사용 권장
object Routes {
    val seriesRoutes = mapOf(
        "s1" to ("dominant_s1" to "l1_s1"),
        "s2" to ("dominant_s2" to "l1_s2")
    )
}
object LoginRoute {
    const val route = "login"
}
@Composable fun GenericDominantScreen(seriesName: String, l1ScreenRoute: String, navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Text("Dominant: $seriesName", style = MaterialTheme.typography.headlineMedium)
    }
}
@Composable fun GenericL1Screen(seriesName: String, navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Text("L1: $seriesName", style = MaterialTheme.typography.headlineSmall)
    }
}
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MajorScreen(
    userEmail: String?,
    mainAppNavController: NavHostController,
    // ViewModel이나 다른 방식으로 allSeriesData를 주입받는 것을 권장
    // 예: seriesViewModel: SeriesViewModel = hiltViewModel()
    // val allSeriesData by seriesViewModel.allSeries.collectAsState()
    // 여기서는 전역 변수 allSeriesData를 사용한다고 가정 (실제 프로젝트에서는 수정 필요)
) {
    val context = LocalContext.current
    val nestedNavController = rememberNavController()
    val auth = Firebase.auth
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("메인 허브") },
                actions = {
                    if (userEmail != null) {
                        Text(
                            text = "Welcome, $userEmail!",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .align(Alignment.CenterVertically),
                        )
                    }
                    IconButton(onClick = {
                        nestedNavController.navigate("nested_home") {
                            popUpTo(nestedNavController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(Icons.Filled.Home, contentDescription = "홈")
                    }
                    IconButton(onClick = { nestedNavController.navigate("app_info_route_nested") { launchSingleTop = true } }) {
                        Icon(Icons.Filled.Info, contentDescription = "앱 정보1")
                    }
                    IconButton(onClick = { nestedNavController.navigate("app_info_route_nested") { launchSingleTop = true } }) {
                        Icon(Icons.Filled.Info, contentDescription = "앱 정보2")
                    }


                    IconButton(onClick = { nestedNavController.navigate("app_info_route_nested") { launchSingleTop = true } }) {
                        Icon(Icons.Filled.Info, contentDescription = "앱 정보3")
                    }



                    IconButton(onClick = { mainAppNavController.navigate("app_settings_from_main") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "전체 앱 설정")
                    }
                    IconButton(onClick = {
                        auth.signOut()
                        // LoginRoute.route는 올바르게 import 또는 정의되어야 함
                        mainAppNavController.navigate(LoginRoute.route) {
                            popUpTo(mainAppNavController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                        coroutineScope.launch { snackbarHostState.showSnackbar("Logged out.") }
                    }) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "로그아웃")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Image(
                // R.drawable.image9_base는 올바르게 import 또는 정의되어야 함
                painter = painterResource(id = R.drawable.image9_base),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.3f,
            )

            NavHost(
                navController = nestedNavController,
                startDestination = "series_selection_or_dashboard",
                modifier = Modifier.fillMaxSize(),
            ) {
                composable("series_selection_or_dashboard") {
                    // allSeriesData는 올바르게 import 또는 주입되어야 함
                    SeriesSelectionScreen(
                        allSeriesData = allSeriesData, // 전역 또는 주입된 allSeriesData 사용
                        onSeriesClick = { seriesId ->
                            // Routes.seriesRoutes는 올바르게 import 또는 정의되어야 함
                            val (dominantRoute, _) = Routes.seriesRoutes[seriesId]
                                ?: return@SeriesSelectionScreen // seriesId에 해당하는 경로가 없을 경우 방어 코드
                            nestedNavController.navigate(dominantRoute)
                        },
                    )
                }

                // allSeriesData는 올바르게 import 또는 주입되어야 함
                allSeriesData.forEach { seriesInfo ->
                    // Routes.seriesRoutes는 올바르게 import 또는 정의되어야 함
                    val (dominantRoute, l1ScreenRoute) = Routes.seriesRoutes[seriesInfo.id]
                        ?: return@forEach // seriesInfo.id에 해당하는 경로가 없을 경우 방어 코드

                    composable(dominantRoute) {
                        // GenericDominantScreen은 올바르게 import 또는 정의되어야 함
                        GenericDominantScreen(
                            seriesName = seriesInfo.displayName,
                            l1ScreenRoute = l1ScreenRoute,
                            navController = nestedNavController,
                        )
                    }
                    composable(l1ScreenRoute) {
                        // GenericL1Screen은 올바르게 import 또는 정의되어야 함
                        GenericL1Screen(
                            seriesName = seriesInfo.displayName,
                            navController = nestedNavController,
                        )
                    }
                }

                composable("my_custom_feature_route_nested") { MyCustomFeatureScreen(navController = nestedNavController) }
                composable("app_info_route_nested") { AppInfoScreen(navController = nestedNavController) }
                composable("customer_support_route_nested") { CustomerSupportScreen(navController = nestedNavController) }
                composable("nested_home") {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text("메인 허브에 오신 것을 환영합니다!", style = MaterialTheme.typography.headlineSmall)
                        Text("시리즈를 선택하거나 상단 메뉴를 통해 원하는 기능으로 이동하세요.", style = MaterialTheme.typography.bodyLarge)
                        // 내용을 길게 만들어 스크롤 테스트 (선택적)
                        // (1..20).forEach { Text("아이템 $it", modifier = Modifier.padding(4.dp)) } // androidx.compose.foundation.gestures.forEach 사용시 제거 또는 수정
                    }
                }
            }
        }
    }
}





@Composable
fun SeriesSelectionScreen(
    allSeriesData: List<SeriesInfo>, // SeriesInfo는 올바르게 import 되어야 함
    onSeriesClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(allSeriesData) { series -> // SeriesInfo의 series.id, series.displayName 사용
            Button(
                onClick = { onSeriesClick(series.id) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(series.displayName)
            }
        }
        // 내용을 길게 만들어 스크롤 테스트
        items(30) { index -> // 파라미터 이름 명시 (예: index)
            Text("스크롤 아이템 $index", modifier = Modifier.padding(8.dp))
        }
    }
}

// 아래 컴포저블들은 실제 구현이 필요합니다.
// (CustomerSupportScreen, AppInfoScreen, MyCustomFeatureScreen)
// GenericDominantScreen, GenericL1Screen 도 실제 구현이 다른 파일에 있거나 여기에 있어야 합니다.

@Composable
fun CustomerSupportScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Text("고객 지원 화면", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun AppInfoScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Text("앱 정보 화면", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun MyCustomFeatureScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Text("특별 기능 화면", style = MaterialTheme.typography.headlineMedium)
    }
}

// 만약 GenericDominantScreen과 GenericL1Screen이 이 파일에 정의되어야 한다면:

