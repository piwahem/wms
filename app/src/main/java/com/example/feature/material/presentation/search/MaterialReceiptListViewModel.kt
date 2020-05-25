package com.example.feature.material.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.Error
import com.example.feature.material.data.MaterialMapper
import com.example.feature.material.data.local.MaterialDatabase
import com.example.feature.material.data.local.MaterialLocalDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MaterialReceiptListViewModel(
) : ViewModel() {

    var viewState: MutableLiveData<MaterialReceiptListViewState> = MutableLiveData()
    //TODO Improve with dagger 2 and injecting by use case
    private val db = MaterialDatabase.getInstance(Any())
    private val localDataSource = MaterialLocalDataSource(db, MaterialMapper())

    init {
        val viewState = MaterialReceiptListViewState()
        this.viewState.value = viewState
    }

    fun getList() {
        viewModelScope.launch {
            viewState.value = viewState.value?.copy(
                isLoading = true,
                receipts = null,
                error = null
            )
            delay(100)
            val locals = localDataSource.getMaterials()
            locals.handleResult({
                viewState.value = viewState.value?.copy(
                    isLoading = false,
                    receipts = it,
                    error = null
                )
            }, {
                viewState.value = viewState.value?.copy(
                    isLoading = false,
                    receipts = null,
                    error = Error.RandomError(Throwable("Get list fail..."))
                )
            })
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            val locals = localDataSource.getMaterials()
            locals.handleResult({
                val searchResult = it.filter { it.receiptNo?.contains(query, true) }
                viewState.value = viewState.value?.copy(
                    isLoading = false,
                    receipts = searchResult,
                    error = null
                )
            }, {
                viewState.value = viewState.value?.copy(
                    isLoading = false,
                    receipts = null,
                    error = Error.RandomError(Throwable("Get list fail..."))
                )
            })
        }
    }
}