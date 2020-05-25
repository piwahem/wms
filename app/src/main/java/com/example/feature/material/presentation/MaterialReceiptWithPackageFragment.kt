package com.example.feature.material.presentation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.example.base.Result
import com.example.extension.showDialog
import com.example.feature.NavigateViewModel
import com.example.feature.material.data.MaterialMapper
import com.example.feature.material.data.MaterialRepository
import com.example.feature.material.data.local.MaterialDatabase
import com.example.feature.material.data.local.MaterialLocalDataSource
import com.example.feature.material.data.remote.MaterialRemoteContract
import com.example.feature.material.domain.GenerateReceiptNoNumber
import com.example.feature.material.domain.GetReceiptDetail
import com.example.feature.material.domain.MaterialReceiptScanInformationEntity
import com.example.feature.material.domain.SubmitReceiptInformation
import com.example.helper.AppSettingHelper
import com.example.helper.DefaultCameraHelper
import com.example.helper.normalDateFormat
import kotlinx.android.synthetic.main.fragment_material.*
import permissions.dispatcher.*

@RuntimePermissions
class MaterialReceiptWithPackageFragment :
    Fragment(),
    DefaultCameraHelper.OnDefaultCameraCaptureListener {

    private lateinit var factory: MaterialReceiptViewModelFactory
    private lateinit var viewModel: MaterialReceiptViewModel
    private lateinit var cameraHelper: DefaultCameraHelper
    private lateinit var navigateViewModel: NavigateViewModel

    private lateinit var rv: RecyclerView
    private lateinit var imageListAdapter: ImageListAdapter

    private val db = MaterialDatabase.getInstance(Any())
    private val localDataSource = MaterialLocalDataSource(
        db,
        MaterialMapper()
    )
    private val remoteDataSource = object : MaterialRemoteContract {
        override suspend fun registerReceiptInformation(
            userId: String,
            warehouseId: String,
            info: MaterialReceiptScanInformationEntity
        ): Result<MaterialReceiptScanInformationEntity, Error> {
            TODO("Not yet implemented")
        }
    }
    private val repository = MaterialRepository(localDataSource, remoteDataSource)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO Improve with dagger 2
        factory = MaterialReceiptViewModelFactory(
            GenerateReceiptNoNumber(repository),
            SubmitReceiptInformation(repository),
            GetReceiptDetail(repository)
        )
        viewModel = ViewModelProvider(this, factory).get(MaterialReceiptViewModel::class.java)
        cameraHelper = DefaultCameraHelper(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigateViewModel = ViewModelProvider(requireActivity()).get(NavigateViewModel::class.java)
        navigateViewModel.actionGuideFromComputer.observe(viewLifecycleOwner, Observer {
            viewModel.handleActionGuideFromPC(it)
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {

            progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE
            //TODO improve binding function
            it.materialReceipt?.let { receipt ->

                lnReceipt.visibility = View.VISIBLE
                lnScanInfo.visibility = if (receipt.scan == null) {
                    if (lnScanInfo.visibility == View.VISIBLE) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                } else {
                    View.VISIBLE
                }

                receipt?.let {
                    rowReceipt?.setText(it.receiptNo)
                }

                val scan = receipt.scan
                scan?.let { scan ->
                    rowItemName.setText(scan.itemName)
                    edtInputNumber.setText(scan.numberOfMaterial.toString())

                    rowQCScan?.setText(receipt.qcScan?.toString())
                    rowDevice?.setText(receipt.device?.toString())
                    rowYourScan?.setText(receipt.yourScan?.toString())
                    rowMode?.setText(receipt.mode)

                    rowScanId.setText(scan.scanId)
                    rowDono.setText(scan.dono)
                    rowAo.setText(scan.ao)
                    rowPono.setText(scan.pono)
                    rowCTNQty.setText(scan.ctnQty?.toString())
                    rowSupplier.setText(scan.supplier)

                    rowCargoDate.setText(scan.cargoCLSDate.normalDateFormat())
                    rowItemCD.setText(scan.itemCD)
                    rowColorWay.setText(scan.colorWay)
                    rowSOSOrigin.setText(scan.sosOrigin)
                    rowPackageNo.setText(scan.packageNo)

                    btnCamera.visibility = View.VISIBLE
                    btnOk.visibility = View.VISIBLE
                    lnAdditionalInfo.visibility = View.VISIBLE
                }
            }

            it.error?.run {
                when (this) {
                    is Error.NetworkError -> {
                    }
                    else -> {
                    }
                }
            }

            it.isScanning?.run {
                val txt = if (this) {
                    rowAddMaterialInSameBox.visibility = View.VISIBLE
                    getString(R.string.label_finished)
                } else {
                    rowAddMaterialInSameBox.visibility = View.VISIBLE
                    getString(R.string.label_box_scan)
                }
                rowBoxScan.text = txt
            }
            it.isAllowEdit?.run {
                if (!this) {
                    btnCamera.visibility = View.GONE
                    btnOk.visibility = View.GONE
                    (edtInputNumber.parent as? View)?.isEnabled = false
                    edtInputNumber.isEnabled = false
                } else {
                    btnCamera.visibility = View.GONE
                    btnOk.visibility = View.VISIBLE
                    (edtInputNumber.parent as? View)?.isEnabled = true
                    edtInputNumber.isEnabled = true
                }
            }

            it.pseudoData?.let { pseudoData ->
                viewModel.submitReceiptInformation(pseudoData)
            }
            it.isShowOk?.run {
                if (this) {
                    btnOk.visibility = View.VISIBLE
                } else {
                    btnOk.visibility = View.GONE
                }
            }
            it.actionGuideFromComputer?.run {
                val message = when (this) {
                    R.id.action_receive_scan -> {
                        viewModel.testToReceivePseudoData()
                        "Phone receive scan information"
                    }
                    else -> {
                        ""
                    }
                }
            }
            it.images?.run {
                imageListAdapter.update(it.images)
            }
        })

        lnScanInfo.visibility = View.GONE
        progressBar.visibility = View.GONE
        btnCamera.visibility = View.GONE
        btnOk.visibility = View.GONE
        iv.visibility = View.GONE
        lnAdditionalInfo.visibility = View.GONE

        rowBoxScan.setOnClickListener {
            viewModel.boxScanAction()
        }

        btnOk.setOnClickListener {
            val txt = edtInputNumber.text?.toString()
            val numberOfMaterial = if (txt.isNullOrEmpty()) {
                1
            } else {
                txt.toInt()
            }
            viewModel.submitReceiptInformation(numberOfMaterial)
        }

        val receiptNoNumber = arguments?.getString(RECEIPT_NO_NUMBER, "") ?: ""
        if (receiptNoNumber.isNotEmpty()) {
            viewModel.getReceiptDetail(receiptNoNumber)
        } else {
            if (savedInstanceState == null) {
                viewModel.generateReceiptNoNumber()
            }
        }

        rowReceipt.setOnClickListener {
            navigateViewModel.selected.value = (SlideMenuItem(
                SlideMenuItemId.QR_MATERIAL_RECEIPT_WITH_PACKAGE_LIST,
                ""
            ))
        }

        rowAddMaterialInSameBox.setOnClickListener {
            viewModel.toggleEditMode()
        }

        rv = rvImage
        imageListAdapter = ImageListAdapter()
        rv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = imageListAdapter
        imageListAdapter.onItemClickListener = object : OnItemClickListener<ImageItem> {
            override fun onClickItem(item: ImageItem, position: Int) {
                if (position == imageListAdapter.itemCount - 1) {
                    openDefaultCameraWithPermissionCheck()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(
            R.layout.fragment_material,
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
        cameraHelper.onActivityResult(requestCode, resultCode, data)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onCaptureDefaultCamera(uri: Uri) {
        viewModel.addImage(ImageItem())
    }

    @NeedsPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun openDefaultCamera() {
        cameraHelper.startDefaultCamera()
    }

    @OnShowRationale(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showRationaleForCamera(request: PermissionRequest) {
        request.proceed()
    }

    @OnPermissionDenied(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onCameraDenied() {
        context?.let {
            it.showDialog(
                getString(R.string.label_title),
                getString(R.string.message_response_deny_camera_permission)
            )
        }
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onCameraNeverAskAgain() {
        context?.let {
            it.showDialog(
                getString(R.string.label_title),
                getString(R.string.message_response_never_ask_again_camera_permission)
            ){
                openAppSetting()
            }
        }
    }

    private fun openAppSetting() {
        context?.let {
            val helper = AppSettingHelper(it)
            helper.appDetailsSettings()
        }
    }

    companion object {
        const val RECEIPT_NO_NUMBER = "RECEIPT_NO_NUMBER"

        fun newInstance(receiptNoNumber: String?): MaterialReceiptWithPackageFragment {
            return MaterialReceiptWithPackageFragment().apply {
                arguments = Bundle().apply {
                    putString(RECEIPT_NO_NUMBER, receiptNoNumber)
                }
            }
        }
    }
}