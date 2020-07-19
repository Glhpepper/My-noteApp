package com.example.mynotesapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.CustomOnItemClickListener
import com.example.mynotesapp.NoteAddUpdateActivity
import com.example.mynotesapp.R
import com.example.mynotesapp.entity.Note
import kotlinx.android.synthetic.main.item_note.view.*
import java.util.ArrayList

class NoteAdapter(private val activity: Activity) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var listNote = ArrayList<Note>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listNote.clear()
            }
            this.listNote.addAll(listNotes)

            notifyDataSetChanged()
        }

    fun addItem(note: Note) {
        this.listNote.add(note)
        notifyItemInserted(this.listNote.size - 1)
    }

    fun updateItem(position: Int, note: Note) {
        this.listNote[position] = note
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int) {
        this.listNote.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNote.size)
    }

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: Note) {
            with(itemView){
                tv_title.text = note.title
                tv_date.text = note.date
                tv_description.text = note.description
                card_item.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                  override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, NoteAddUpdateActivity::class.java)
                        intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
                        intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                        activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
                    }
                }))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = listNote.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNote[position])
    }

}