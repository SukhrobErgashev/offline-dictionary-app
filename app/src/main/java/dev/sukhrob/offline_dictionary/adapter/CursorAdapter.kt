package dev.sukhrob.offline_dictionary.adapter

import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.sukhrob.offline_dictionary.R
import dev.sukhrob.offline_dictionary.data.model.Word
import dev.sukhrob.offline_dictionary.databinding.ItemWordListBinding

class CursorAdapter : RecyclerView.Adapter<CursorAdapter.Holder>() {
    private var cursor: Cursor? = null
    private var query: String? = null

    var listener: ((Word) -> Unit)? = null
    var bookmarkListener: ((Word) -> Unit)? = null

    fun submitCursor(cursor: Cursor, query: String? = null) {
        this.cursor = cursor
        this.query = query
        notifyDataSetChanged()
    }

//    fun submitCursor(newCursor: Cursor) {
//        val toDoDiffUtil = WordDiffUtil(cursor, newCursor)
//        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
//        cursor = newCursor
//        toDoDiffResult.dispatchUpdatesTo(this)
//    }

    inner class Holder(private val binding: ItemWordListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            cursor?.let {
                it.moveToPosition(adapterPosition)
                binding.apply {
                    word.text = it.getString(it.getColumnIndexOrThrow("word"))
                    definition.text = it.getString(it.getColumnIndexOrThrow("definition"))
                    if (it.getInt(it.getColumnIndexOrThrow("isBookmark")) == 0) {
                        bookmark.setImageResource(R.drawable.ic_bookmark_border)
                    } else {
                        bookmark.setImageResource(R.drawable.ic_bookmark)
                    }
                }
                val word = Word(
                    it.getInt(it.getColumnIndexOrThrow("id")),
                    it.getString(it.getColumnIndexOrThrow("word")),
                    it.getString(it.getColumnIndexOrThrow("wordtype")),
                    it.getString(it.getColumnIndexOrThrow("definition")),
                    it.getInt(it.getColumnIndexOrThrow("isBookmark"))
                )
                binding.root.setOnClickListener {
                    listener?.invoke(word)
                }
                binding.bookmark.setOnClickListener {
                    bookmarkListener?.invoke(word)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(ItemWordListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind()

    override fun getItemCount(): Int {
        cursor?.let { return it.count }
        return 0
    }
}