package com.example.feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.feature.material.presentation.OnItemClickListener
import com.example.feature.material.presentation.SlideMenuAdapter
import com.example.feature.material.presentation.SlideMenuItem
import com.example.feature.material.presentation.SlideMenuItemId
import kotlinx.android.synthetic.main.view_content_menu.*

class SlideMenuFragment : Fragment() {

    private lateinit var rvMaterialWarehouse: RecyclerView
    private lateinit var materialWarehouseSlideMenuAdapter: SlideMenuAdapter

    private lateinit var rvFinishedGoodWarehouse: RecyclerView
    private lateinit var finishGoodWarehouseSlideMenuAdapter: SlideMenuAdapter

    private lateinit var rvSetting: RecyclerView
    private lateinit var settingSlideMenuAdapter: SlideMenuAdapter

    private lateinit var navigateViewModel: NavigateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateViewModel = ViewModelProvider(requireActivity()).get(NavigateViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_slide_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSlideMenu()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setUpSlideMenu() {
        setUpMaterialWarehouse()
        setUpFinishGoodWarehouse()
        setUpSetting()
    }

    private fun setUpMaterialWarehouse() {
        rvMaterialWarehouse = rvMaterialWarehouse_
        materialWarehouseSlideMenuAdapter = SlideMenuAdapter()
        rvMaterialWarehouse.layoutManager = LinearLayoutManager(requireContext())
        rvMaterialWarehouse.adapter = materialWarehouseSlideMenuAdapter
        materialWarehouseSlideMenuAdapter.update(
            mutableListOf(
                SlideMenuItem(
                    SlideMenuItemId.QR_MATERIAL_RECEIPT_WITH_PACKAGE,
                    getString(R.string.label_material_receipt_with_package)
                ),
                SlideMenuItem(
                    SlideMenuItemId.QR_QC_MATERIAL,
                    getString(R.string.label_qc_material)
                ),
                SlideMenuItem(
                    SlideMenuItemId.QR_QC_SKIN_GRADE,
                    getString(R.string.label_qc_skin_grade)
                ),
                SlideMenuItem(
                    SlideMenuItemId.QR_LOADING_MATERIAL_INTO_RACK,
                    getString(R.string.label_qr_loading_material_into_rack)
                ),
                SlideMenuItem(
                    SlideMenuItemId.QR_FIXING_RECEIPT_QUANTITY,
                    getString(R.string.label_qr_fixing_receipt_quantity)
                ),
                SlideMenuItem(
                    SlideMenuItemId.QR_PICKING_MATERIAL_WITH_PACKAGE,
                    getString(R.string.label_qr_picking_material_with_package)
                ),
                SlideMenuItem(
                    SlideMenuItemId.QR_PICKING_MATERIAL_BY_DELIVERY_ORDER,
                    getString(R.string.label_qr_picking_material_by_delivery_order)
                ),
                SlideMenuItem(
                    SlideMenuItemId.QR_MATERIAL_RECEIPT_BY_OTHER_CASE,
                    getString(R.string.label_qr_material_by_order_case)
                ),
                SlideMenuItem(
                    SlideMenuItemId.QR_MATERIAL_CHECKING,
                    getString(R.string.label_qr_material_checking)
                ),
                SlideMenuItem(
                    SlideMenuItemId.SEARCHING_MATERIAL,
                    getString(R.string.label_searching_material)
                )
            )
        )
        tvTitleMaterialWarehouse.setOnClickListener {
            val isVisible = rvMaterialWarehouse.visibility == View.VISIBLE
            val currentState = if (isVisible) View.GONE else View.VISIBLE
            rvMaterialWarehouse.visibility = currentState
        }
        materialWarehouseSlideMenuAdapter.onItemClickListener =
            object : OnItemClickListener<SlideMenuItem> {
                override fun onClickItem(item: SlideMenuItem, position: Int) {
//                    navigateToPage(item.id)
//                    toggleMenu()

                    navigateViewModel.selected.value = item
                }
            }
    }

    private fun setUpFinishGoodWarehouse() {
        rvFinishedGoodWarehouse = rvFinishedGoodWarehouse_
        rvFinishedGoodWarehouse.visibility = View.GONE
        finishGoodWarehouseSlideMenuAdapter =
            SlideMenuAdapter()
        rvFinishedGoodWarehouse.layoutManager = LinearLayoutManager(requireContext())
        rvFinishedGoodWarehouse.adapter = finishGoodWarehouseSlideMenuAdapter
        finishGoodWarehouseSlideMenuAdapter.update(
            mutableListOf(
                SlideMenuItem(
                    SlideMenuItemId.NOTHING,
                    SlideMenuItemId.NOTHING
                )
            )
        )
        tvFinishedGoodWarehouse.setOnClickListener {
            val isVisible = rvFinishedGoodWarehouse.visibility == View.VISIBLE
            val currentState = if (isVisible) View.GONE else View.VISIBLE
            rvFinishedGoodWarehouse.visibility = currentState
        }
    }

    private fun setUpSetting() {
        rvSetting = rvSetting_
        rvSetting.visibility = View.GONE
        settingSlideMenuAdapter = SlideMenuAdapter()
        rvSetting.layoutManager = LinearLayoutManager(requireContext())
        rvSetting.adapter = settingSlideMenuAdapter
        settingSlideMenuAdapter.update(
            mutableListOf(
                SlideMenuItem(
                    SlideMenuItemId.SIGN_OUT,
                    getString(R.string.label_sign_out)
                )
            )
        )
        tvSetting.setOnClickListener {
            val isVisible = rvSetting.visibility == View.VISIBLE
            val currentState = if (isVisible) View.GONE else View.VISIBLE
            rvSetting.visibility = currentState
        }
        settingSlideMenuAdapter.onItemClickListener =
            object : OnItemClickListener<SlideMenuItem> {
                override fun onClickItem(item: SlideMenuItem, position: Int) {
                    if (item.id == SlideMenuItemId.SIGN_OUT) {
                        Toast.makeText(requireContext(), "We sign out...", Toast.LENGTH_LONG)
                            .show()
//                        viewModel.signOut()
                        navigateViewModel.selected.value = item
                    }
//                    toggleMenu()
                }
            }
    }
}