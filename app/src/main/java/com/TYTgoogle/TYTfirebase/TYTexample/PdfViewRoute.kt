package com.TYTgoogle.TYTfirebase.TYTexample

// app/src/main/java/com/TYTgoogle/TYTfirebase/TYTexample/AppNavigation.kt


// ... (기존 imports) ...
// PdfViewScreen composable 함수 import
import android.net.Uri


object PdfViewRoute {
    const val routeTemplate = "pdfView/{pdfPath}"
    fun createRoute(pdfPath: String) = "pdfView/${Uri.encode(pdfPath)}"
    const val PDF_PATH_ARG = "pdfPath"
}

// NavHost 내부에 추가될 composable
// composable(
//     route = PdfViewRoute.routeTemplate,
//     arguments = listOf(navArgument(PdfViewRoute.PDF_PATH_ARG) { type = NavType.StringType })
// ) { backStackEntry ->
//     val pdfPath = backStackEntry.arguments?.getString(PdfViewRoute.PDF_PATH_ARG)
//     if (pdfPath != null) {
//         PdfViewScreen(pdfFile = File(Uri.decode(pdfPath)))
//     } else {
//         Text("PDF 파일을 찾을 수 없습니다.")
//     }
// }