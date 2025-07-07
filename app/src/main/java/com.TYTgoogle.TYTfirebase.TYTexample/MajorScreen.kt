package com.TYTgoogle.TYTfirebase.TYTexample

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun MajorScreen(
    userEmail: String?,
    navController: NavHostController,
) {
    val context = LocalContext.current // Context 가져오기

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.image9_base),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // LazyColumn으로 allSeriesData 기반 버튼들 표시
            LazyColumn(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                val chunkedSeriesData = allSeriesData.chunked(2)
                items(items = chunkedSeriesData) { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        rowItems.forEach { seriesInfo ->
                            val (dominantRoute, _) = Routes.seriesRoutes[seriesInfo.id]!!
                            Button(
                                onClick = { navController.navigate(dominantRoute) },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 2.dp,
                                    disabledElevation = 0.dp,
                                ),
                            ) {
                                Text(seriesInfo.displayName)
                            }
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            // --- 기존 고정 버튼들 ---
            Button(
                onClick = { navController.navigate("my_custom_feature_route") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Special Feature",
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("나의 특별 기능")
            }





            Button(
                onClick = { navController.navigate("app_info_route") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "App Info",
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("앱 정보 보기")
            }




            OutlinedButton(
                onClick = { navController.navigate("customer_support_route") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text("고객 지원")
            }
            // --- 여기까지 고정 버튼 추가 ---

            // --- "간단한 텍스트 화면 테스트" 버튼 수정 ---
            Button(
                onClick = {
                    // "PDF 생성 및 보기" 버튼과 동일한 방식으로 Intent 사용
                    val intent = Intent(context, SimpleTextActivity::class.java)
                    // SimpleTextActivity는 특별한 데이터를 넘길 필요 없으므로 putExtra는 생략
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp), // 일관성을 위해 추가 (선택 사항)
            ) {
                // 아이콘은 선택 사항입니다. 원하시면 추가하세요.
                // Icon(imageVector = Icons.Filled.TextFields, contentDescription = "Simple Text")
                // Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("간단한 텍스트 화면 테스트")
            }
            // --- 여기까지 "간단한 텍스트 화면 테스트" 버튼 ---





            // --- 새로운 웹 페이지 링크 버튼 ---
            Button(
                onClick = {
                    // 이동할 웹 페이지 주소
                    val webPageUrl = "https://www.google.com" // 여기에 원하는 웹 주소를 입력하세요.
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webPageUrl))
                    // Intent를 처리할 수 있는 앱이 있는지 확인 (선택 사항이지만 권장)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        // 웹 브라우저가 없는 경우 사용자에게 알림 (예: Toast 메시지)
                        // Toast.makeText(context, "웹 브라우저를 찾을 수 없습니다.", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            ) {
                Text(
                    text = "google으로 이동하기 🔗",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
            // --- 여기까지 새로운 웹 페이지 링크 버튼 ---






            // --- 새로운 웹 페이지 링크 버튼 ---
            Button(
                onClick = {
                    // 이동할 웹 페이지 주소
                    val webPageUrl = "https://www.bing.com/" // 여기에 원하는 웹 주소를 입력하세요.
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webPageUrl))
                    // Intent를 처리할 수 있는 앱이 있는지 확인 (선택 사항이지만 권장)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        // 웹 브라우저가 없는 경우 사용자에게 알림 (예: Toast 메시지)
                        // Toast.makeText(context, "웹 브라우저를 찾을 수 없습니다.", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
            ) {
                Text(
                    text = "Bing으로 이동하기 🔗",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
            // --- 여기까지 새로운 웹 페이지 링크 버튼 ---






        }
    }
}

