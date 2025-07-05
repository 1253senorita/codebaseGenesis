package com.pdfmoduletyt.tytpdfmodule



import android.content.Context
import android.graphics.pdf.PdfDocument
import android.graphics.Paint
import android.graphics.Color
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PdfHandler {

    fun createSamplePdf(context: Context, fileName: String = "sample.pdf"): File? {
        val pdfFile = File(context.cacheDir, fileName)

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
        }
        canvas.drawText("Hello, PDF from com.pdfmoduletyt.tytpdfmodule!", 10f, 25f, paint) // << 텍스트 내용 예시 변경
        pdfDocument.finishPage(page)

        return try {
            FileOutputStream(pdfFile).use { fos ->
                pdfDocument.writeTo(fos)
            }
            pdfDocument.close()
            pdfFile
        } catch (e: IOException) {
            e.printStackTrace()
            pdfDocument.close()
            null
        }
    }

    fun getExistingPdf(context: Context, fileName: String): File? {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) file else null
    }
}