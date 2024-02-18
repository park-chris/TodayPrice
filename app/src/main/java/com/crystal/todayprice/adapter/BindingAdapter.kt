package com.crystal.todayprice.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.crystal.todayprice.R
import com.crystal.todayprice.component.UserDataManager
import com.crystal.todayprice.data.ListItem
import com.crystal.todayprice.data.Price
import com.crystal.todayprice.data.User
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
            .skipMemoryCache(true)
            .dontAnimate()
            .error(R.drawable.img_no_picture)
            .transition(DrawableTransitionOptions.withCrossFade(300))
            .into(this)
    }
    this.clipToOutline = true
}

@BindingAdapter("itemImageUrl")
fun ImageView.setImage(itemId: Int?) {
    val url = "https://firebasestorage.googleapis.com/v0/b/today-price-94264.appspot.com/o/item%2F${itemId}.webp?alt=media"
    Glide.with(this)
        .load(url)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade(300))
        .error(R.drawable.img_no_picture)
        .into(this)
    this.clipToOutline = true
}

@BindingAdapter("moneyText")
fun TextView.setMoneyText(money: Int?) {
    text = if (money == 0) {
        resources.getString(R.string.no_stock)
    } else {
        resources.getString(R.string.format_money, NumberFormat.getInstance(Locale.KOREA).format( money ?: 0))
    }
}

@BindingAdapter("descriptionText")
fun TextView.setDescriptionText(description: String?) {
    text = if (description != null && description.isEmpty()) {
        resources.getString(R.string.market_empty_description)
    } else {
        description
    }
}

@BindingAdapter("countText")
fun TextView.setCountText(count: Int?) {
    text = count?.toString() ?: "0"
}

@BindingAdapter("headerText")
fun TextView.setHeaderText(name: String?) {
    text = if  (name != null) {
        resources.getString(R.string.welcome_user, name)
    } else {
        resources.getText(R.string.require_login)
    }
}

@BindingAdapter("buttonTextView")
fun Button.setButtonTextView(user: User?) {
    text = if (user != null) {
        resources.getString(R.string.profile)
    } else {
        resources.getString(R.string.login)
    }
}

@BindingAdapter("isVisible")
fun TextView.setIsVisible(user: User?) {
    visibility = if (user != null) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("heartDrawable")
fun TextView.setHeartDrawable(users: List<String>) {
    val userId = UserDataManager.getInstance().user?.id
    val drawableResId = if (userId != null && users.contains(userId)) {
        R.drawable.ic_fill_heart
    } else {
        R.drawable.ic_empty_heart
    }
    setCompoundDrawablesRelativeWithIntrinsicBounds(drawableResId, 0, 0, 0)
}

@BindingAdapter("blockUsers", "content")
fun TextView.setContentText(blockUsers: List<String>, content: String) {
    val userId = UserDataManager.getInstance().user?.id
    val isBlocked = userId != null && blockUsers.contains(userId)
    if (isBlocked) {
        text = resources.getString(R.string.user_is_blocked)
        setTextColor(ContextCompat.getColor(context, R.color.highlight))
    } else {
        text = content
        setTextColor(ContextCompat.getColor(context, R.color.text))
    }
}

@BindingAdapter("menuDrawable")
fun ImageButton.setMenuDrawable(reviewId: String) {
    val userId = UserDataManager.getInstance().user?.id
    val drawableResId = if (userId != null && reviewId == userId) {
        R.drawable.ic_close
    } else {
        R.drawable.ic_see_more
    }
    setImageResource(drawableResId)
}

@BindingAdapter("surveyDate")
fun TextView.setSurveyDate(price: Price?) {
    text = resources.getString(R.string.price_info, price?.surveyDate ?: "")
}

@BindingAdapter("answerState")
fun TextView.setAnswerState(answer: String) {
    if (answer.isNotEmpty()) {
        text = resources.getString(R.string.complete_answer)
        background = ContextCompat.getDrawable(rootView.context, R.drawable.bg_state_true)
    } else {
        text =  resources.getString(R.string.incomplete_answer)
        background = ContextCompat.getDrawable(rootView.context, R.drawable.bg_state_false)
    }
}

@BindingAdapter("answerText")
fun TextView.setAnswerText(answer: String) {
    if (answer.isNotEmpty()) {
        text = answer
    } else {
        text =  resources.getString(R.string.incomplete_answer_text)
        setTextColor(ContextCompat.getColor(rootView.context,  R.color.hint))
    }
}

@BindingAdapter("heartDrawable")
fun ImageButton.setHeartDrawable(isFavorite: Boolean) {
    val drawableResId = if (isFavorite) {
        R.drawable.ic_fill_heart
    } else {
        R.drawable.ic_empty_heart
    }
    setImageResource(drawableResId)
}


