package ru.gross.notes.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Адаптер для отображения списка в [RecyclerView], использующий механизм привязки данных.
 * @param T Тип отображаемых данных.
 * @param VH Тип наследника [DataBoundViewHolder].
 * @author gross_va
 */
abstract class DataBoundRecyclerAdapter<T, VH : DataBoundViewHolder<out ViewDataBinding>> :
    ListAdapter<T, VH> {
    var itemClickListener: ((View, T) -> Unit)? = null

    constructor(diffCallback: DiffUtil.ItemCallback<T>) : super(diffCallback)
    constructor(config: AsyncDifferConfig<T>) : super(config)
}

/**
 * ViewHolder для адаптеров списков использующий привязку данных.
 * @param V Тип сгенерированного класса привязки.
 * @author gross_va
 */
abstract class DataBoundViewHolder<V : ViewDataBinding>(val binding: V) :
    RecyclerView.ViewHolder(binding.root)


/**
 * Выполняет раздувание макета для элементов списковых адаптеров.
 * @param V Тип сгенерированного класса привязки.
 * @author gross_va
 */
fun <V : ViewDataBinding> inflateChild(
    parent: ViewGroup,
    @LayoutRes layoutRes: Int
): V = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutRes, parent, false)