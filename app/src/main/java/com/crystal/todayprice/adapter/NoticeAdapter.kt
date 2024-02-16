package com.crystal.todayprice.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crystal.todayprice.R
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.databinding.ItemNoticeBinding
import com.crystal.todayprice.databinding.ItemPriceBinding
import com.crystal.todayprice.databinding.ItemReviewBinding
import com.crystal.todayprice.util.CommonUtil.Companion.dpToPx
import com.crystal.todayprice.util.OnItemReviewListener

class NoticeAdapter(
    private val onClick: (Notice) -> Unit
): RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemNoticeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notice: Notice) {
            binding.item = notice


            binding.imageView.setOnClickListener {
                onClick(notice)
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Notice>() {
        override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notice, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = 120.dpToPx(parent.context)
        val binding = ItemNoticeBinding.bind(view)
        return ViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun submitList(items: List<Notice>) {
        differ.submitList(items)
    }

}