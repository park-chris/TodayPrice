package com.crystal.todayprice.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.crystal.todayprice.data.NecessaryPrice

class ItemAdapter(
    private val favorite: (NecessaryPrice) -> Unit
): PagingDataAdapter<NecessaryPrice, ItemViewHolder> (diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<NecessaryPrice>() {
            override fun areItemsTheSame(
                oldItem: NecessaryPrice,
                newItem: NecessaryPrice
            ): Boolean {
                return oldItem.itemId == newItem.itemId
            }

            override fun areContentsTheSame(
                oldItem: NecessaryPrice,
                newItem: NecessaryPrice
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(favorite, parent)
    }
}