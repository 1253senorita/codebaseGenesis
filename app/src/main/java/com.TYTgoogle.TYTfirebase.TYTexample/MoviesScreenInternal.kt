package com.TYTgoogle.TYTfirebase.TYTexample

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser

@Composable
fun MoviesScreenInternal(
    // 이름 변경 및 파라미터 조정
    // navController: NavController, // AppNavigation에서 관리하므로 직접 필요 X
    // auth: FirebaseAuth, // ViewModel에서 관리
    user: FirebaseUser?, // ViewModel의 currentUser 사용
    mainViewModel: MainViewModel,
    onNavigateToMovieDetail: (String) -> Unit, // 상세 화면 이동 콜백
    showSnackBar: (String) -> Unit, // 스낵바 표시용
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text("Welcome, ${user?.email ?: "Guest"}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                mainViewModel.signOut() // ViewModel 통해 로그아웃
                Log.d("MoviesScreen", "Sign out clicked.")
                showSnackBar("로그아웃 되었습니다.")
                // 네비게이션은 AppNavigation의 LaunchedEffect(currentUser)가 처리
            },
        ) {
            Text("Sign Out")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onNavigateToMovieDetail("exampleMovie123") // 콜백 호출
            },
        ) {
            Text("View Dummy Movie Detail")
        }
        // 여기에 실제 영화 목록 등을 표시하는 UI 추가 가능
    }
}
