package dev.sukhrob.offline_dictionary.adapter

import android.database.Cursor
import androidx.recyclerview.widget.DiffUtil

class WordDiffUtil(
    private val oldList: Cursor?,
    private val newList: Cursor
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        if (oldList == null) {
            return -1
        }
        return oldList.count
    }

    override fun getNewListSize(): Int = newList.count

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        oldList?.moveToPosition(oldItemPosition)
        newList.moveToPosition(newItemPosition)
        return oldList?.getInt(oldList.getColumnIndexOrThrow("id")) == newList.getInt(newList.getColumnIndexOrThrow("id"))
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        oldList?.moveToPosition(oldItemPosition)
        newList.moveToPosition(newItemPosition)
        return oldList?.getInt(oldList.getColumnIndexOrThrow("id")) == newList.getInt(newList.getColumnIndexOrThrow("id"))
                && oldList.getInt(oldList.getColumnIndexOrThrow("isBookmark")) == newList.getInt(newList.getColumnIndexOrThrow("isBookmark"))
    }
}