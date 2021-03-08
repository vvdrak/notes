package ru.gross.notes.ui.list

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import ru.gross.notes.R
import ru.gross.notes.common.DataBoundRecyclerAdapter
import ru.gross.notes.common.DataBoundViewHolder
import ru.gross.notes.common.inflateChild
import ru.gross.notes.databinding.NoteItemLayoutBinding
import java.util.*

/**
 * Описывает адаптер для отображения заметок.
 * @author gross_va
 */
class NotesAdapter : DataBoundRecyclerAdapter<NoteView, NoteViewHolder>(ITEM_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(inflateChild(parent, viewType))

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) =
        holder.bindTo(getItem(position), itemClickListener)

    override fun getItemViewType(position: Int): Int = R.layout.note_item_layout

    companion object {
        @JvmField
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<NoteView>() {
            override fun areItemsTheSame(oldItem: NoteView, newItem: NoteView): Boolean =
                Objects.equals(oldItem.id, newItem.id)

            override fun areContentsTheSame(oldItem: NoteView, newItem: NoteView): Boolean =
                Objects.equals(oldItem.content, newItem.content) &&
                        Objects.equals(oldItem.title, newItem.title)
        }
    }
}

class NoteViewHolder(binding: NoteItemLayoutBinding) :
    DataBoundViewHolder<NoteItemLayoutBinding>(binding) {

    fun bindTo(note: NoteView?, l: ((View, NoteView?) -> Unit)?) = with(binding) {
        item = note
        ViewCompat.setTransitionName(rootPane, item?.id)
        root.setOnClickListener { l?.invoke(binding.root, item) }
    }
}