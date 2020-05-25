package com.example.feature.material.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ImageListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<ImageItem> = mutableListOf()

    var onItemClickListener: OnItemClickListener<ImageItem>? = null

    fun update(data: List<ImageItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DATA_VIEW_TYPE -> ImageListViewHolder.create(parent)
            else -> AddImageListViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageListViewHolder -> {
                val item = data[position]
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    onItemClickListener?.onClickItem(
                        item,
                        position
                    )
                }
            }
            else -> {
                (holder as? AddImageListViewHolder)?.bind(null)
                holder.itemView.setOnClickListener {
                    onItemClickListener?.onClickItem(
                        ImageItem(),
                        position
                    )
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) ADD_VIEW_TYPE else DATA_VIEW_TYPE
    }

    companion object {
        const val DATA_VIEW_TYPE = 1
        const val ADD_VIEW_TYPE = 2
    }
}