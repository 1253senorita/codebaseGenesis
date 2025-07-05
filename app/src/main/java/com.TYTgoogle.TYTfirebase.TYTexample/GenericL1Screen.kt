package com.TYTgoogle.TYTfirebase.TYTexample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun GenericL1Screen(
    seriesName: String, // 어떤 시리즈의 L1 화면인지 식별
    navController: NavHostController, // 뒤로 가기 등의 네비게이션을 위해 필요
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image8_base), // L1 스크린용 배경 이미지 (새 이미지 또는 기존 이미지 재활용)
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("$seriesName - L1 기능 스크린", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            // L1 화면의 구체적인 내용 (예시: Card 안에 정보 표시)
            // 이 부분은 실제 L1 화면의 요구사항에 맞게 커스터마이징합니다.
            androidx.compose.material3.Card(
                // Card 임포트가 명시적으로 필요할 수 있음
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("이것은 $seriesName 시리즈의 L1 화면입니다.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("여기에 해당 시리즈의 L1 레벨 상세 기능 또는 정보가 표시됩니다.")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.popBackStack() }, // 이전 화면 (Dominant Screen)으로 돌아가기
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp,
                ),
            ) {
                Text("뒤로 가기 (${seriesName} Dominant로)")
            }
        }
    }
}





@Composable
fun GenericDominantScreen(
    seriesName: String,
    l1ScreenRoute: String,
    navController: NavHostController,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.image10_base), // 실제 이미지 리소스로 변경하세요.
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("$seriesName 도미넌트 스크린 (L0)", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(l1ScreenRoute) },
                shape = RoundedCornerShape(12.dp), // 둥근 모서리 적용
                elevation = ButtonDefaults.buttonElevation(
                    // 그림자 효과 적용
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp,
                    disabledElevation = 0.dp,
                ),
            ) {
                Text("$seriesName - L1 기능 가기")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(12.dp), // 둥근 모서리 적용
                elevation = ButtonDefaults.buttonElevation(
                    // 그림자 효과 적용
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp,
                    disabledElevation = 0.dp,
                ),
            ) {
                Text("뒤로 가기 (Main Hub로)")
            }
        }
    }
}
