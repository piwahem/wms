package com.example.feature.material.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.R


class AddImageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): AddImageListViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.item_view_material_receipt_add_image,
                parent,
                false
            )
            return AddImageListViewHolder(view)
        }
    }

    fun bind(item: ImageItem?) {
    }
}