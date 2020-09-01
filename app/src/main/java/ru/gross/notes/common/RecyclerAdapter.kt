package ru.gross.notes.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.*

/**
 * Адаптер для отображения списка в [RecyclerView].
 * @param T Тип отображаемых данных.
 * @param VH Тип наследника [RecyclerView.ViewHolder].
 * @author gross_va
 */
@Suppress("LeakingThis", "MemberVisibilityCanBePrivate")
abstract class RecyclerAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {
    var itemClickListener: ((View, T?) -> Unit)? = null
    private val listDiffer: AsyncListDiffer<T>

    constructor(diffCallback: DiffUtil.ItemCallback<T>) {
        listDiffer = AsyncListDiffer(this, diffCallback)
    }

    constructor(config: AsyncDifferConfig<T>) {
        listDiffer = AsyncListDiffer(AdapterListUpdateCallback(this), config)
    }

    /**
     * Возвращает количество элементов в адаптере
     *
     * @return Количество элементов в адаптере
     */
    override fun getItemCount(): Int =
        listDiffer.currentList.size

    /**
     * Возвращает тип представления элемента в указанной позиции
     *
     * Для уникальной идентификации типов представлений используйте идентфикатор ресурса представления
     *
     * @param position Позиция
     * @return Тип представления
     */
    override fun getItemViewType(position: Int): Int = 0

    /**
     * Устанавливает список элементов
     *
     * @param list Данные, которые необходимо отобразить
     */
    fun submitList(list: List<T>?) =
        listDiffer.submitList(list)

    /**
     * Возвращает элемент списка по его позиции
     *
     * @param position Позиция элемента, должна быть >= 0 и &lt; [getItemCount]
     * @return Элемент списка
     */
    protected fun getItem(position: Int): T? =
        listDiffer.currentList[position]

    /**
     * Возвращает текущий набор данных
     *
     * @return Набор данных
     */
    val currentList: MutableList<T>
        get() = listDiffer.currentList
}

/**
 * Адаптер для отображения списка в [RecyclerView], использующий механизм привязки данных.
 * @param T Тип отображаемых данных.
 * @param VH Тип наследника [DataBoundViewHolder].
 * @author gross_va
 */
abstract class DataBoundRecyclerAdapter<T, VH : DataBoundViewHolder<out ViewDataBinding>> :
    RecyclerAdapter<T, VH> {

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