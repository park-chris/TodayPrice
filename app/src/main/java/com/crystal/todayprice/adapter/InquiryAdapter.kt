package com.crystal.todayprice.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Transformation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.crystal.todayprice.R
import com.crystal.todayprice.data.Inquiry
import com.crystal.todayprice.data.Item
import com.crystal.todayprice.data.Notice
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.databinding.ItemInquiryBinding
import com.crystal.todayprice.databinding.ItemNoticeBinding
import com.crystal.todayprice.databinding.ItemPriceBinding
import com.crystal.todayprice.databinding.ItemReviewBinding
import com.crystal.todayprice.util.CommonUtil.Companion.dpToPx
import com.crystal.todayprice.util.OnItemReviewListener
import com.crystal.todayprice.util.ToggleAnimation

class InquiryAdapter(
    private val onClick: (Inquiry) -> Unit
): RecyclerView.Adapter<InquiryAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemInquiryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(inquiry: Inquiry) {
            binding.item = inquiry
            var isExpanded = false

            binding.root.setOnClickListener {
                val show = toggleLayout(!isExpanded, it, binding.contentLayout)
                isExpanded = show
            }
            binding.removeButton.setOnClickListener {
                onClick(inquiry)
            }
        }

        private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
            if (isExpanded) {
                ToggleAnimation.expand(layoutExpand)
            } else {
                ToggleAnimation.collapse(layoutExpand)
            }
            return isExpanded
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Inquiry>() {
        override fun areItemsTheSame(oldItem: Inquiry, newItem: Inquiry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Inquiry, newItem: Inquiry): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inquiry, parent, false)
        val binding = ItemInquiryBinding.bind(view)
        return ViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun submitList(items: List<Inquiry>) {
        differ.submitList(items)
    }


    fun getList(): List<Inquiry> {
        return differ.currentList
    }
    fun deleteInquiry(position: Int) {
        val list = getList().filterIndexed  { index, _ -> index != position }
        submitList(list)
    }

}