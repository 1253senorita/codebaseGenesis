package com.TYTgoogle.TYTfirebase.TYTexample

import android.os.Bundle
// import android.util.Log // 현재 사용 안 함
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
// ... 다른 Compose 관련 import들 ...
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel // viewModel() import 확인
import com.TYTgoogle.TYTfirebase.TYTexample.ui.theme.FirebaseDataConnectTheme
import com.google.firebase.auth.FirebaseAuth
// import com.google.firebase.auth.FirebaseUser // auth 객체는 AppNavigation으로 전달
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch



// --- 라우트 객체 import ---
// AppNavigation 내부에서 시작 지점을 결정하므로, MainActivity에서 직접 라우트 객체를
// startDestination으로 전달할 필요는 없어졌습니다.
// 다만, MainViewModel에서 이 라우트 타입을 참조할 수 있으므로 import는 유지할 수 있습니다.
// import com.TYTgoogle.TYTfirebase.TYTexample.ui.routes.LoginRoute // AppNavigation이 내부적으로 사용
// import com.TYTgoogle.TYTfirebase.TYTexample.ui.routes.MainHubRoute // AppNavigation이 내부적으로 사용 또는 ViewModel이 참조 가능

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = Firebase.auth
        // auth.useEmulator("10.0.2.2", 9099)

        setContent {
            FirebaseDataConnectTheme {
                // MainViewModel 인스턴스 생성
                val mainViewModel: MainViewModel = viewModel()
                // ViewModel로부터 스플래시 화면 가시성 및 현재 사용자 상태 구독
                val isSplashVisible by mainViewModel.isSplashVisible.collectAsState()
                // val currentUser by mainViewModel.currentUser.collectAsState() // AppNavigation이 auth를 직접 사용

                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    splashScreen.setKeepOnScreenCondition {
                        isSplashVisible
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                ) { paddingValues ->
                    // AppNavigation 호출 변경:
                    // - startDestination 인자 제거
                    // - mainViewModel은 AppNavigation에서 현재 직접 사용하지 않지만,
                    //   만약 AppNavigation이 ViewModel의 상태를 참조해야 한다면 전달 유지
                    AppNavigation(
                        modifier = Modifier.padding(paddingValues),
                        auth = auth, // AppNavigation이 auth 객체를 직접 사용하여 시작 지점 결정
                        // mainViewModel = mainViewModel, // AppNavigation이 ViewModel을 직접 사용한다면 전달
                        showSnackBar = { message ->
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(message)
                            }
                        }
                        // NavController는 AppNavigation 내부에서 rememberNavController()로 생성하거나,
                        // 여기서 생성해서 전달할 수도 있습니다. 현재 AppNavigation은 내부 생성 방식입니다.
                    )
                }
            }
        }
    }
}