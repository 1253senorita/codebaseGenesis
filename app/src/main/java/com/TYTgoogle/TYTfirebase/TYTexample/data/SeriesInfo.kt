// 파일 경로: C:/Users/55341/TeminaterTT/codelab-dataconnect-android/app/src/main/java/com/TYTgoogle/TYTfirebase/TYTexample/data/SeriesInfo.kt
package com.TYTgoogle.TYTfirebase.TYTexample.data

// ActionItem과 ActionType은 SeriesInfo와 같은 파일에 두거나, 별도의 파일로 분리할 수도 있습니다.
// 같은 파일에 둘 경우, import 없이 바로 사용 가능합니다.
enum class ActionType {
    NAVIGATE_SUB_DOMINANT,
    UPLOAD_DATA,
    SHOW_INFO_DIALOG
    // ... 기타 필요한 액션 타입
}

data class ActionItem(
    val displayText: String,
    val actionType: ActionType,
    val targetRoute: String? = null,
    val uploadDataType: String? = null
    // 필요에 따라 더 많은 정보 추가
)

data class SeriesInfo(
    val id: String,
    val displayName: String,
    val imageUrl: String? = null,
    val iconResId: Int? = null,
    val initialActions: List<ActionItem> = emptyList() // 새로운 필드 추가!
)