package com.crystal.todayprice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crystal.todayprice.R
import com.crystal.todayprice.data.Market
import com.crystal.todayprice.databinding.ItemMarketBinding

class MarketAdapter(
    private val onClick: (Market) -> Unit
) : RecyclerView.Adapter<MarketAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val onClick: (Market) -> Unit,
        private val binding: ItemMarketBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(market: Market?) {
            market?.let {
                binding.item = market

                binding.layout.setOnClickListener {
                    onClick(market)
                }

                binding.marketImageView.clipToOutline = true

            }
        }
    }
    private fun create(
        onClick: (Market) -> Unit,
        parent: ViewGroup
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_market, parent, false)
        val binding = ItemMarketBinding.bind(view)
        return ViewHolder(onClick, binding)
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Market>() {
        override fun areItemsTheSame(
            oldItem: Market,
            newItem: Market
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Market,
            newItem: Market
        ): Boolean {
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

    fun submitList(markets: List<Market>) {
        differ.submitList(markets)
    }
}