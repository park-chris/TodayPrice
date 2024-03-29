package com.crystal.todayprice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crystal.todayprice.R
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.databinding.ItemPriceBinding

class ItemAdapter(
    private val onClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val onClick: (Item) -> Unit,
        private val binding: ItemPriceBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item?) {
            item?.let {
                binding.item = it
                binding.layout.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
    private fun create(
        onClick: (Item) -> Unit,
        parent: ViewGroup
    ): ItemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_price, parent, false)
        val binding = ItemPriceBinding.bind(view)
        return ViewHolder(onClick, binding)
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.itemId == newItem.itemId
        }
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtil)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return create(onClick, parent)

    }

    override fun getItemCount() = differ.currentList.size

    fun submitList(items: List<Item>) {
        differ.submitList(items)
    }

}