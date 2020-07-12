package com.example.opulexpropertymanagement.layers.z_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.App
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.layers.view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.layers.z_ui.extras.AdapterRVDocuments
import com.example.opulexpropertymanagement.layers.z_ui.extras.BottomDialogForPhoto
import com.example.opulexpropertymanagement.layers.z_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragTenantDetailsBinding
import com.example.opulexpropertymanagement.databinding.ItemDocumentBinding
import com.example.opulexpropertymanagement.util.easyPicasso
import com.example.opulexpropertymanagement.util.vmFactoryFactory
import kotlinx.android.synthetic.main.frag_tenant_details.view.*
import kotlinx.android.synthetic.main.includible_rounded_image.view.*
import kotlinx.android.synthetic.main.item_document.view.*

class TenantDetailsFrag : OXFragment(), AdapterRVDocuments.ARVInterface {
    lateinit var mBinding: FragTenantDetailsBinding
    val args by lazy { arguments?.let { TenantDetailsFragArgs.fromBundle(it) } }
    val tenantDetailsVM: TenantDetailsVM by activityViewModels { vmFactoryFactory { TenantDetailsVM(args!!.tenant, App.appComponent.getRepo()) } }
    val navController by lazy { this.findNavController() }
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
                if (uri!=null) {
                    tenantDetailsVM.addDocument(uri, "New Document")
                }
            }
            bottomDialog.show(requireActivity().supportFragmentManager, "ZipZoopTheBottomDialog")
        }
    }

    private fun setupObservers() {
        tenantDetailsVM.documents.observe(viewLifecycleOwner, Observer {
            mBinding.recyclerview1.adapter?.notifyDataSetChanged()
        })
    }

    private fun setupView() {
        mBinding.root.includible_tenant_image.imageview_rounded_image.easyPicasso(tenantDetailsVM.tenant.imageUrlTask)
        mBinding.recyclerview1.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mBinding.recyclerview1.adapter = AdapterRVDocuments(this, R.layout.item_document)
    }

    override fun getRecyclerDataSize(): Int {
        return tenantDetailsVM.documents.value?.size ?: 0
    }

    override fun bindRecyclerItemView(binding: ItemDocumentBinding, i: Int) {
        val document = tenantDetailsVM.documents.value?.get(i)!!
        binding.document = document
        binding.root.imageview_document.easyPicasso(document.imageUrlTask)
        binding.root.setOnClickListener {
            val directions = TenantDetailsFragDirections.actionTenantDetailsFragToDocumentDetailsFrag(document)
            navController.navigate(directions)
        }
    }
}