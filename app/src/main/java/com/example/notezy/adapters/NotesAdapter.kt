package com.example.notezy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notezy.models.Notes
import com.example.notezy.R

class NotesAdapter(private val notesList : MutableList<Notes>)
    : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private lateinit var mListener: onItemClickListener

        inner class NotesViewHolder(itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView){
            var tvTitle :TextView
            init {
                tvTitle = itemView.findViewById(R.id.tvTitle)

                itemView.setOnClickListener {
                    clickListener.onClick(adapterPosition)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.note_layout,parent,false)
        return NotesViewHolder(inflater, mListener)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.tvTitle.text = notesList[position].title.toString()
    }

    interface onItemClickListener{

        fun onClick(position : Int)
    }

    fun setItemClickListener(clickListener : onItemClickListener){
        mListener = clickListener
    }
}