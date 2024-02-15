package com.crystal.todayprice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crystal.todayprice.R
import com.crystal.todayprice.data.Review
import com.crystal.todayprice.databinding.ItemUserReviewBinding
import com.crystal.todayprice.util.OnItemReviewListener

class UserReviewAdapter(
    private val onItemReviewListener: OnItemReviewListener
): RecyclerView.Adapter<UserReviewAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val onItemReviewListener: OnItemReviewListener,
        private val binding: ItemUserReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.item = review

            binding.countTextView.setOnClickListener {
                onItemReviewListener.onLikeClick(review)
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
            .inflate(R.layout.item_user_review, parent, false)
        val binding = ItemUserReviewBinding.bind(view)
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

    fun updateReview(position: Int, newReview: Review) {
        val list = getList().mapIndexed { index, review ->
            if (position == index) newReview else review
        }
        submitList(list)
    }

    fun deleteReview(position: Int) {
        val list = getList().filterIndexed  { index, _ -> index != position }
        submitList(list)
    }

}