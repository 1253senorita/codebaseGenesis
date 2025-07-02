package com.TYTgoogle.TYTfirebase.TYTexample.data





/**
 * 각 시리즈의 정보를 담는 데이터 클래스입니다.
 *
 * @property id 각 시리즈를 고유하게 식별하는 ID (예: "AA", "AB"). 내부 로직 및 라우팅에 사용됩니다.
 * @property displayName 사용자 인터페이스에 표시될 시리즈의 이름 (예: "첫 번째 특별 시리즈", "사용자 분석 대시보드").
 * @property imageUrl 시리즈를 나타내는 이미지의 URL (선택 사항).
 * @property iconResId 시리즈를 나타내는 아이콘의 드로어블 리소스 ID (선택 사항).
 */
data class SeriesInfo(
    val id: String,
    val displayName: String,
    val imageUrl: String? = null, // 이미지 URL은 선택적이므로 nullable String으로 하고 기본값을 null로 설정
    val iconResId: Int? = null    // 아이콘 리소스 ID도 선택적이므로 nullable Int로 하고 기본값을 null로 설정
)