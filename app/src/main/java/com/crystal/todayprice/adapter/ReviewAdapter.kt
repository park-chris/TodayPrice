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
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.databinding.ItemPriceBinding
import com.crystal.todayprice.databinding.ItemReviewBinding
import com.crystal.todayprice.util.OnItemReviewListener

class ReviewAdapter(
    private val onItemReviewListener: OnItemReviewListener
): RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val onItemReviewListener: OnItemReviewListener,
        private val binding: ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.item = review

            binding.countTextView.setOnClickListener {
                review.likeState = !review.likeState
                onItemReviewListener.onLikeClick(review)
                Log.e("TestLog", "like ${review.likeState}")
            }
            binding.menuButton.setOnClickListener {
                onItemReviewListener.onMenuClick(review)
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        val binding = ItemReviewBinding.bind(view)
        return ViewHolder(onItemReviewListener, binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun submitList(items: List<Review>) {
        differ.submitList(items)
    }

    fun getList(): List<Review> {
        return differ.currentList
    }

}