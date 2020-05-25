package com.example.feature.material.presentation

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.R

class MaterialRow : ConstraintLayout {

    var tvTitle: TextView? = null
    var tvValue: TextView? = null

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    )
            : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    )
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        var title: CharSequence = ""
        var value: CharSequence = ""
        try {
            attrs?.let {
                val typedArray = context.obtainStyledAttributes(it, R.styleable.MaterialRow, 0, 0)
                val hasRowTitle = typedArray.hasValue(R.styleable.MaterialRow_rowTitle)
                val hasRowValue = typedArray.hasValue(R.styleable.MaterialRow_rowValue)

                title = if (hasRowTitle) {
                    typedArray.getText(R.styleable.MaterialRow_rowTitle)
                } else ""
                value = if (hasRowValue) {
                    typedArray.getText(R.styleable.MaterialRow_rowValue)
                } else ""

                typedArray.recycle()
            }

            LayoutInflater.from(context).inflate(R.layout.view_material_custom_row, this, true)
            tvTitle = findViewById(R.id.tvTitle)
            tvValue = findViewById(R.id.tvValue)

            tvTitle?.text = title
            tvValue?.text = value
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}