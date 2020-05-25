package com.example.feature.material.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import kotlinx.android.synthetic.main.item_view_slide_menu.view.*

class SlideMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): SlideMenuViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.item_view_slide_menu,
                parent,
                false
            )
            return SlideMenuViewHolder(view)
        }
    }

    fun bind(item: SlideMenuItem) {
        val color = when (item.id) {
            SlideMenuItemId.QR_PICKING_MATERIAL_BY_DELIVERY_ORDER,
            SlideMenuItemId.QR_MATERIAL_RECEIPT_BY_OTHER_CASE,
            SlideMenuItemId.QR_MATERIAL_CHECKING,
            SlideMenuItemId.SEARCHING_MATERIAL -> android.R.color.black
            else -> android.R.color.black
        }
        itemView.run {
            tv.setTextColor(ContextCompat.getColor(context, color))
            tv.text = item.name
        }
    }
}