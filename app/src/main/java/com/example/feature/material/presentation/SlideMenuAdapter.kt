package com.example.feature.material.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SlideMenuAdapter : RecyclerView.Adapter<SlideMenuViewHolder>() {

    private var data: List<SlideMenuItem> = mutableListOf()

    var onItemClickListener: OnItemClickListener<SlideMenuItem>?= null

    fun update(data: List<SlideMenuItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideMenuViewHolder {
        return SlideMenuViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SlideMenuViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClickListener?.onClickItem(item, position) }
    }
}