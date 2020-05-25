package com.example.feature.material.presentation.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import com.example.feature.material.presentation.OnItemClickListener

class MaterialReceiptListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<MaterialReceiptScanInformationEntity> = mutableListOf()

    var onItemClickListener: OnItemClickListener<MaterialReceiptScanInformationEntity>?= null

    fun update(data: List<MaterialReceiptScanInformationEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialReceiptListViewHolder {
        return MaterialReceiptListViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        (holder as? MaterialReceiptListViewHolder)?.bind(item)
        holder.itemView.setOnClickListener { onItemClickListener?.onClickItem(item, position) }
    }
}