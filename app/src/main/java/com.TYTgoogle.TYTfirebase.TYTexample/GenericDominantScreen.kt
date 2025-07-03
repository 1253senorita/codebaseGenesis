package com.TYTgoogle.TYTfirebase.TYTexample



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.TYTgoogle.TYTfirebase.TYTexample.data.ActionItem // ActionItem 임포트
import com.TYTgoogle.TYTfirebase.TYTexample.data.ActionType // ActionType 임포트
import com.TYTgoogle.TYTfirebase.TYTexample.data.SeriesInfo // SeriesInfo 임포트

@Composable
fun GenericDominantScreen(
    seriesInfo: SeriesInfo, // SeriesInfo 객체를 직접 받음
    l1ScreenRoute: String, // L1 화면 경로는 일단 유지 (필요 없다면 제거 가능)
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "${seriesInfo.displayName} - Dominant Screen",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // initialActions에 정의된 버튼들을 동적으로 생성
        if (seriesInfo.initialActions.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f), // 남은 공간을 채우도록
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(seriesInfo.initialActions) { actionItem ->
                    ActionButton( // 각 ActionItem을 위한 버튼 컴포저블
                        actionItem = actionItem,
                        navController = navController
                    )
                }
            }
        } else {
            Text(
                text = "설정된 액션이 없습니다.",
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally) // 중앙 정렬
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 기존 L1 화면으로 가는 버튼 (필요하다면 유지 또는 initialActions에 통합)
        ElevatedButton(onClick = { navController.navigate(l1ScreenRoute) }) {
            Text("L1 기능 화면으로 이동 (${seriesInfo.id})")
        }
    }
}

// ActionItem을 받아 버튼을 생성하는 별도의 Composable 함수
@Composable
private fun ActionButton(
    actionItem: ActionItem,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            when (actionItem.actionType) {
                ActionType.NAVIGATE_SUB_DOMINANT -> {
                    actionItem.targetRoute?.let { route ->
                        navController.navigate(route)
                    }
                }
                ActionType.UPLOAD_DATA -> {
                    // TODO: 데이터 업로드 로직 구현 (ViewModel 호출 등)
                    // 예: navController.navigate("upload_screen/${actionItem.uploadDataType}") 또는 ViewModel 함수 호출
                    println("데이터 업로드 요청: ${actionItem.uploadDataType}") // 임시 로그
                }
                ActionType.SHOW_INFO_DIALOG -> {
                    // TODO: 정보 다이얼로그 표시 로직 구현
                    println("정보 다이얼로그 표시 요청") // 임시 로그
                }
                // ... 기타 actionType에 대한 처리
            }
        },
        modifier = modifier.fillMaxWidth()
    ) {
        Text(actionItem.displayText)
    }
}