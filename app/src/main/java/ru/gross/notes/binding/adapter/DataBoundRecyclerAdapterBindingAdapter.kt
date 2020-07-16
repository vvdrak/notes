package ru.gross.notes.binding.adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ru.gross.notes.common.DataBoundRecyclerAdapter
import ru.gross.notes.common.DataBoundViewHolder

/**
 * Адаптер привязки, позволяющий указать данные для адаптера через xml разметку.
 * @param view [RecyclerView], в котором нужно отобразить данные.
 * @param items Данные.
 * @param <T> Тип данных.
 * @author gross_va
 */
@BindingAdapter(value = ["itemsSource"])
fun <T> setItemsSource(view: RecyclerView, items: List<T>?) =
    getAdapter<T>(view).submitList(items)

private fun <T> getAdapter(view: RecyclerView): DataBoundRecyclerAdapter<T, DataBoundViewHolder<in ViewDataBinding>> =
    view.adapter.let {
        @Suppress("UNCHECKED_CAST")
        if (it is DataBoundRecyclerAdapter<*, *>) it as DataBoundRecyclerAdapter<T, DataBoundViewHolder<in ViewDataBinding>> else throw IllegalStateException(
            "Для использования привязки необходимо использовать адаптер DataBoundRecyclerAdapter." +
                    "Убедитесь что супер-класс используемого адаптера -- DataBoundRecyclerAdapter и что для RecyclerView был вызван метод setAdapter()"
        )
    }