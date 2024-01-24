package com.crystal.todayprice.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.crystal.todayprice.R
import java.text.NumberFormat
import java.util.Locale

@BindingAdapter("visible")
fun View.setVisible(isShow: Boolean) {
    isVisible = isShow
}

@BindingAdapter("imageUrl")
fun ImageView.setImage(imageUrl: String?) {
    if (!imageUrl.isNullOrBlank()) {
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade(300))
            .error(R.drawable.no_picture)
            .into(this)
    }
}

@BindingAdapter("moneyText")
fun TextView.setMoneyText(money: Long?) {
    text = resources.getString(R.string.format_money, NumberFormat.getInstance(Locale.KOREA).format( money ?: 0))
}

@BindingAdapter("descriptionText")
fun TextView.setDescriptionText(description: String?) {
    text = if (description != null && description.isEmpty()) {
        resources.getString(R.string.market_empty_description)
    } else {
        description
    }
}