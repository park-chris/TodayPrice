package com.crystal.todayprice.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.crystal.todayprice.data.ViewType
import androidx.viewbinding.ViewBinding
import com.crystal.todayprice.databinding.EmptyItemBinding

object ViewHolderGenerator {

    fun get(
        parent: ViewGroup,
        viewType: Int
    ): ListItemViewHolder<*> {
        return when (viewType) {
            ViewType.NEWS.ordinal -> NewsViewHolder(parent.toBinding())
            ViewType.MARKET.ordinal -> MarketViewHolder(parent.toBinding())
            ViewType.HORIZONTAL.ordinal -> HorizontalViewHolder(parent.toBinding())
            else -> ItemViewHolder(parent.toBinding())
        }
    }

    class ItemViewHolder(binding: EmptyItemBinding) : ListItemViewHolder<EmptyItemBinding>(binding)

    private inline fun <reified V: ViewBinding> ViewGroup.toBinding() : V {
        return V::class.java.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, LayoutInflater.from(context), this, false) as V
    }


}