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
                Objects.equals(oldItem.id, newItem.id)

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                Objects.equals(oldItem.content, newItem.content) &&
                        Objects.equals(oldItem.creationDate, newItem.creationDate) &&
                        Objects.equals(oldItem.title, newItem.title)
        }
    }
}

class NoteViewHolder(binding: NoteItemLayoutBinding) :
    DataBoundViewHolder<NoteItemLayoutBinding>(binding) {

    fun bindTo(note: Note?, l: ((View, Note?) -> Unit)?) = with(binding) {
        item = note
        ViewCompat.setTransitionName(rootPane, item?.id)
        root.setOnClickListener { l?.invoke(binding.root, item) }
    }
}