package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.ac_ui.extras.BottomDialogForPhoto
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragTenantDetailsBinding
import com.example.opulexpropertymanagement.util.easyPicasso
import com.example.tmcommonkotlin.TMRecyclerViewAdapter

class TenantDetailsFrag : OXFragment(), TMRecyclerViewAdapter.Callbacks {
    lateinit var mBinding: FragTenantDetailsBinding
    val tenantDetailsVM: TenantDetailsVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_tenant_details, container, false)
        mBinding.lifecycleOwner = this
        mBinding.tenantDetailsVM = tenantDetailsVM
        setupClickListeners()
        setupObservers()
        setupView()
        return mBinding.root
    }

    private fun setupClickListeners() {
        mBinding.fab.setOnClickListener {
            val bottomDialog = BottomDialogForPhoto(requireActivity(), "Add Document") { uri, _ ->

            }
            bottomDialog.show(requireActivity().supportFragmentManager, "ZipZoopTheBottomDialog")
        }
    }

    private fun setupObservers() {
        tenantDetailsVM.tenant.observe(viewLifecycleOwner, Observer {
            mBinding.includibleTenantImage.imageview1.easyPicasso(tenantDetailsVM.tenant.value?.imageUrlTask)
        })
    }

    private fun setupView() {
        mBinding.recyclerview1.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mBinding.recyclerview1.adapter = TMRecyclerViewAdapter(this, requireActivity(), R.layout.item_document)
    }

    override fun bindRecyclerItemView(view: View, i: Int) {

    }

    override fun getRecyclerDataSize(): Int {
        return 0
    }
}