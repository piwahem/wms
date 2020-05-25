package com.example.feature.material.presentation

interface OnItemClickListener<T> {
    fun onClickItem(item: T, position: Int)
}