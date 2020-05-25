package com.example.feature.material.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_view_material_receipt_image_list.view.*

class ImageListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): ImageListViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.item_view_material_receipt_image_list,
                parent,
                false
            )
            return ImageListViewHolder(view)
        }
    }

    fun bind(item: ImageItem) {
        itemView.run {
            Glide.with(context).load(android.R.drawable.gallery_thumb).placeholder(item.testImage).into(iv)
        }
    }
}