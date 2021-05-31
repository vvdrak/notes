package ru.gross.notes.utils

import android.content.Context
import android.content.Intent

/**
 * Описывает утилитарный объект, позволяющий делиться контентом с другими приложениями.
 * @author gross_va
 */
object ShareUtils {

    /**
     * Оттправляет текстовое содержимое в другие приложения.
     * @param context Контекст приложения.
     * @param text Текст, отправляемый в другие приложения.
     */
    @JvmStatic
    fun shareText(context: Context, text: String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        context.startActivity(
            Intent.createChooser(sendIntent, null)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}