package com.example.feature.material.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import com.example.helper.normalDateFormat
import kotlinx.android.synthetic.main.item_view_slide_menu.view.*
import java.util.*

class MaterialReceiptListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): MaterialReceiptListViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.item_view_material_receipt_list,
                parent,
                false
            )
            return MaterialReceiptListViewHolder(
                view
            )
        }
    }

    fun bind(item: MaterialReceiptScanInformationEntity) {
        itemView.run {
            val date = (item.scan?.cargoCLSDate ?: Date()).normalDateFormat()
            tv.text = "${item.receiptNo} --- $date"
        }
    }
}