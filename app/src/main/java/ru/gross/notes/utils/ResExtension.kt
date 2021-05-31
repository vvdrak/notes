package ru.gross.notes.utils

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.AndroidViewModel

/**
 * Описывает функции расширения для работы с ресурсами проекта.
 * @author gross_va
 */
private object ResExtension {
    /**
     * Возвращает строку из ресурсов проекта ассоциированную с [resId].
     * @param context Контекст.
     * @param resId Идентификатор строкового ресурса.
     */
    @JvmStatic
    fun stringResource(context: Context, @StringRes resId: Int) =
        context.getString(resId)

    /**
     * Возвращает строку из ресурсов проекта ассоциированную с [resId].
     * @param context Контекст.
     * @param resId Идентификатор строкового ресурса.
     */
    @JvmStatic
    fun stringResource(context: Context, @StringRes resId: Int, vararg args: Any) =
        context.getString(resId, *args)

    /**
     * Возвращает изображение из ресурсов проекта ассоциированное с [resId].
     * @param context Контекст.
     * @param resId Идентификатор графического ресурса.
     */
    @JvmStatic
    fun drawableResource(context: Context, @DrawableRes resId: Int) =
        AppCompatResources.getDrawable(context, resId)
}

/**
 * Возвращает изображение из ресурсов проекта ассоциированную с [resId].
 */
fun Context.drawableResource(@DrawableRes resId: Int) =
    ResExtension.drawableResource(this, resId)

/**
 * Возвращает строку из ресурсов проекта ассоциированную с [resId].
 */
fun Context.stringResource(@StringRes resId: Int) =
    ResExtension.stringResource(this, resId)

/**
 * Возвращает строку из ресурсов проекта ассоциированную с [resId].
 */
fun Context.stringResource(@StringRes resId: Int, vararg args: Any) =
    ResExtension.stringResource(this, resId, *args)

/**
 * Возвращает строку из ресурсов проекта ассоциированное с [resId].
 */
fun AndroidViewModel.stringResource(@StringRes resId: Int) =
    ResExtension.stringResource(getApplication(), resId)
