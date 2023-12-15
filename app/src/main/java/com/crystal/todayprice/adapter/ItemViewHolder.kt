package com.crystal.todayprice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crystal.todayprice.R
import com.crystal.todayprice.data.NecessaryPrice
import com.crystal.todayprice.databinding.PriceItemBinding
import com.crystal.todayprice.util.TextUtil

class ItemViewHolder(
    private val onClick: (NecessaryPrice) -> Unit,
    private val binding: PriceItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NecessaryPrice?) {
        item?.let { item ->

//            Glide.with(binding.root)
//                .load("https://")
//                .centerCrop()
//                .into(binding.itemImageView)


            binding.nameTextView.text = item.itemName
            binding.priceTextView.text = TextUtil.priceFormat(binding.root.context, item.itemPrice)

            binding.nameTextView.setOnClickListener {
                onClick(item)
            }
        }
    }

    companion object {
        fun create(
            favorite: (NecessaryPrice) -> Unit,
            parent: ViewGroup
        ): ItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.price_item, parent,false)
            val binding = PriceItemBinding.bind(view)
            return ItemViewHolder(favorite, binding)
        }
    }
}