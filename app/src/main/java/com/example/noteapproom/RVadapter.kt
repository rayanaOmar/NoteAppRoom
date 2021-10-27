package com.example.noteapproom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapproom.Room.Note
import com.example.noteapproom.databinding.NoteRowBinding

class RVadapter (
    private val activity: MainActivity,
    private val items: List<Note>): RecyclerView.Adapter<RVadapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: NoteRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVadapter.ItemViewHolder {
        return ItemViewHolder(
            NoteRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RVadapter.ItemViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            tvNote.text = item.noteText


            ibEditNote.setOnClickListener {
                activity.raiseDialog(item.id)
            }
            ibDeleteNote.setOnClickListener {
                activity.deleteNote(item.id)
            }
        }
    }

    override fun getItemCount() = items.size
}