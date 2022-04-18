package com.iamcheng5.roompractice_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class WordListAdapter(val itemClickListener:ItemClickListener) : ListAdapter<Word, WordListAdapter.WordViewHolder>(WORD_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val wordViewHolder =WordViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_word_item, parent, false))
        wordViewHolder.btnDelete.setOnClickListener {
            itemClickListener.onDeleteClick(getItem(wordViewHolder.adapterPosition))
        }
        return wordViewHolder

    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = getItem(position)
        holder.bind(word.word)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvWord: TextView = itemView.findViewById<TextView>(R.id.wordItemRec_tv_word)
        val btnDelete: Button = itemView.findViewById<Button>(R.id.wordItemRec_btn_delete)
        fun bind(text: String?) {
            tvWord.text = text
        }
    }

    companion object {

        private val WORD_DIFF: DiffUtil.ItemCallback<Word> =
            object : DiffUtil.ItemCallback<Word>() {
                override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                    return oldItem.word == newItem.word
                }

                override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                    return oldItem.word == newItem.word
                }

            }
    }

    interface ItemClickListener{
        fun onDeleteClick(word:Word)
    }

}