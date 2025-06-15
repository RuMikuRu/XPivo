package com.example.xpivo.feature.articles_page.detail_article_page

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.xpivo.core.view_model.BaseViewModel
import com.example.xpivo.core.view_model.Lce
import com.example.xpivo.data.repository.article_repository.IArticlesRepository
import com.example.xpivo.data.response.DetailArticleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailArticlePageViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: IArticlesRepository
) : BaseViewModel(context) {
    private val _detailArticleState = MutableStateFlow<Lce<DetailArticleResponse?>>(Lce.Ready(null))
    val detailArticleState = _detailArticleState.asStateFlow()

    fun loadDetailArticle(id: Int) {
        launchSafely {
            _detailArticleState.value = Lce.Loading
            try {
                val detailArticle = repository.getDetailArticle(id)
                _detailArticleState.value = Lce.Content(detailArticle)
            } catch (e: Exception) {
                _detailArticleState.value = Lce.Error(e)
                sendError(e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveArticleToPdf(detail: DetailArticleResponse) {
        launchSafely {
            val pdf = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4
            val page = pdf.startPage(pageInfo)
            val canvas: Canvas = page.canvas
            val paint = Paint().apply { textSize = 14f }

            var y = 40f
            fun drawText(label: String, value: String) {
                canvas.drawText("$label: $value", 40f, y, paint)
                y += 25
            }

            drawText("Заголовок", detail.title)
            drawText("Автор", detail.authorUsername)
            drawText("Дата", detail.createdAt)
            drawText("Статус", detail.status)
            drawText("Описание", detail.description)
            y += 20
            canvas.drawText("Содержание:", 40f, y, paint)
            y += 25

            // Разбиение длинного текста на строки
            val bodyLines = detail.body.chunked(80) // каждые 80 символов
            for (line in bodyLines) {
                if (y > 800) break
                canvas.drawText(line, 40f, y, paint)
                y += 20
            }

            y += 25
            canvas.drawText("Теги: ${detail.tags.joinToString(", ")}", 40f, y, paint)

            pdf.finishPage(page)

            // Сохранение в Загрузки
            val filename = "article_${detail.id}_${System.currentTimeMillis()}.pdf"
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, filename)
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            try {
                uri?.let {
                    resolver.openOutputStream(it)?.use { stream ->
                        pdf.writeTo(stream)
                    }
                    Toast.makeText(context, "Статья сохранена в Загрузки", Toast.LENGTH_LONG).show()
                } ?: Toast.makeText(context, "Ошибка сохранения", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                pdf.close()
            }
        }
    }
}