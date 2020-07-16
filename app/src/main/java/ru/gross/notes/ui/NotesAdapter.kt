package ru.gross.notes.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.gross.notes.R
import ru.gross.notes.common.DataBoundRecyclerAdapter
import ru.gross.notes.common.DataBoundViewHolder
import ru.gross.notes.common.inflateChild
import ru.gross.notes.databinding.NoteItemLayoutBinding
import ru.gross.notes.model.Note
import java.util.*

/**
 * Описывает адаптер для отображения заметок.
 * @author gross_va
 */
class NotesAdapter : DataBoundRecyclerAdapter<Note, NoteViewHolder>(ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder =
        NoteViewHolder(inflateChild(parent, viewType))

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) =
        holder.bindTo(getItem(position), itemClickListener)

    override fun getItemViewType(position: Int): Int = R.layout.note_item_layout

    companion object {
        @JvmField
        val ITEM_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                Objects.equals(oldItem.id, newItem.id)
        }
    }
}

class NoteViewHolder(binding: NoteItemLayoutBinding) :
    DataBoundViewHolder<NoteItemLayoutBinding>(binding) {

    fun bindTo(item: Note?, l: ((View, Note?) -> Unit)?) {
        binding.item = item
        binding.root.setOnClickListener { l?.invoke(binding.root, item) }
    }
}