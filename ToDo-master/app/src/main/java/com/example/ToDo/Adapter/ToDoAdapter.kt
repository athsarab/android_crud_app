package com.example.ToDo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ToDo.Models.Note
import com.example.ToDo.R

class ToDoAdapter(private val context: Context, val listener: NotesClickListener) : RecyclerView.Adapter<ToDoAdapter.NoteViewHolder>() {

    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true

        holder.note.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.notesLayout.setCardBackgroundColor(context.resources.getColor(randomColor()))

        holder.notesLayout.setOnClickListener {
            listener.onItemClicked(notesList[holder.adapterPosition])
        }

        holder.deleteButton.setOnClickListener {
            val noteToDelete = notesList[holder.adapterPosition]
            notesList.removeAt(holder.adapterPosition)
            fullList.remove(noteToDelete)
            notifyDataSetChanged()
            listener.onDeleteButtonClicked(noteToDelete)
        }
    }

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)

        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        notesList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true || item.note?.lowercase()?.contains(search.lowercase()) == true) {
                notesList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    private fun randomColor(): Int {
        val list = listOf(
            R.color.NoteColor1,
            R.color.NoteColor2,
            R.color.NoteColor3,
            R.color.NoteColor4,
            R.color.NoteColor5,
            R.color.NoteColor6
        )

        return list.random()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notesLayout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.TVtitle)
        val note = itemView.findViewById<TextView>(R.id.TVnote)
        val date = itemView.findViewById<TextView>(R.id.TVdate)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.delete_button)
    }

    interface NotesClickListener {
        fun onItemClicked(note: Note)
        fun onDeleteButtonClicked(note: Note)
    }
}
