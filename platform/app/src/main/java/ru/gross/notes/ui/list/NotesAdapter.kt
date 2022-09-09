package ru.gross.notes.ui.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.gross.notes.R
import ru.gross.notes.binding.DataBoundRecyclerAdapter
import ru.gross.notes.binding.DataBoundViewHolder
import ru.gross.notes.binding.inflateChild
import ru.gross.notes.databinding.NoteItemLayoutBinding
import java.util.*

/**
 * Описывает адаптер для отображения заметок.
 * @author gross_va
 */
internal class NotesAdapter : DataBoundRecyclerAdapter<NoteView, NoteViewHolder>(ITEM_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(inflateChild(parent, viewType))

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) =
        holder.bindTo(requireItem(position), itemClickListener)

    override fun getItemViewType(position: Int): Int = R.layout.note_item_layout

    private fun requireItem(position: Int) = requireNotNull(getItem(position))

    private companion object {
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

internal class NoteViewHolder(binding: NoteItemLayoutBinding) :
    DataBoundViewHolder<NoteItemLayoutBinding>(binding) {

    fun bindTo(note: NoteView, l: ((View, NoteView) -> Unit)?) = with(binding) {
        item = note
        root.setOnClickListener { l?.invoke(binding.root, note) }
    }
}