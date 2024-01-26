package com.crystal.todayprice.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.crystal.todayprice.data.ViewType
import androidx.viewbinding.ViewBinding
import com.crystal.todayprice.component.OnItemListItemListener
import com.crystal.todayprice.databinding.ItemEmptyBinding

object ViewHolderGenerator {

    fun get(
        parent: ViewGroup,
        viewType: Int,
        onItemClickListener: OnItemListItemListener
    ): ListItemViewHolder<*> {
        return when (viewType) {
            ViewType.NEWS.ordinal -> NewsViewHolder(parent.toBinding(), onItemClickListener)
            ViewType.MARKET.ordinal -> MarketViewHolder(parent.toBinding(), onItemClickListener)
            ViewType.HORIZONTAL.ordinal -> HorizontalViewHolder(parent.toBinding(), onItemClickListener)
            ViewType.VIEW_PAGER.ordinal -> ViewPagerViewHolder(parent.toBinding(), onItemClickListener)
            ViewType.NOTICE.ordinal -> NoticeViewHolder(parent.toBinding(), onItemClickListener)
            else -> ItemViewHolder(parent.toBinding())
        }
    }

    class ItemViewHolder(binding: ItemEmptyBinding) : ListItemViewHolder<ItemEmptyBinding>(binding)

    private inline fun <reified V: ViewBinding> ViewGroup.toBinding() : V {
        return V::class.java.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, LayoutInflater.from(context), this, false) as V
    }


}