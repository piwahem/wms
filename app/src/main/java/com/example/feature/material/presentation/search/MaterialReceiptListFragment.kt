package com.example.feature.material.presentation.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.base.Error
import com.example.feature.NavigateViewModel
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import com.example.feature.material.presentation.*
import kotlinx.android.synthetic.main.fragment_material_receipt_list.*

class MaterialReceiptListFragment : Fragment() {

    private lateinit var factory: MaterialReceiptListViewModelFactory
    private lateinit var viewModel: MaterialReceiptListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MaterialReceiptListAdapter
    private lateinit var navigateViewModel: NavigateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = MaterialReceiptListViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(MaterialReceiptListViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(viewLifecycleOwner, Observer {

            progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE
            adapter.update(it.receipts ?: emptyList())
            it.error?.run {
                when (this) {
                    is Error.NetworkError -> {
                    }
                    else -> {
                    }
                }
            }
        })
        navigateViewModel = ViewModelProvider(requireActivity()).get(NavigateViewModel::class.java)
        progressBar.visibility = View.GONE

        adapter = MaterialReceiptListAdapter()
        adapter?.onItemClickListener = object :
            OnItemClickListener<MaterialReceiptScanInformationEntity> {
            override fun onClickItem(
                item: MaterialReceiptScanInformationEntity,
                position: Int
            ) {
                navigateViewModel.materialSelected.value = item
            }
        }
        recyclerView = rv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                viewModel.search(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        if (savedInstanceState == null) {
            viewModel.getList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(
            R.layout.fragment_material_receipt_list,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}