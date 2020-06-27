package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.aa_repo.TenantsRepo
import com.example.opulexpropertymanagement.ab_view_models.MaintenancesVM
import com.example.opulexpropertymanagement.ab_view_models.TenantDetailsVM
import com.example.opulexpropertymanagement.ab_view_models.getTenantVM
import com.example.opulexpropertymanagement.ac_ui.activities.ActivityHostInterface
import com.example.opulexpropertymanagement.ac_ui.extras.AdapterRVDocuments
import com.example.opulexpropertymanagement.ac_ui.extras.BottomDialogForPhoto
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragTenantDetailsBinding
import com.example.opulexpropertymanagement.databinding.ItemDocumentBinding
import com.example.opulexpropertymanagement.models.Document
import com.example.opulexpropertymanagement.util.easyPicasso
import com.example.tmcommonkotlin.TMRecyclerViewAdapter
import com.example.tmcommonkotlin.logv
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_property_details.view.*
import kotlinx.android.synthetic.main.item_document.view.*
class TenantDetailsFrag : OXFragment(), AdapterRVDocuments.ARVInterface {
    lateinit var mBinding: FragTenantDetailsBinding
    val tenantDetailsVM: TenantDetailsVM by lazy { getTenantVM(navController) }
    val navController by lazy { this.findNavController() }
    val args by lazy { arguments?.let { TenantDetailsFragArgs.fromBundle(it) } }
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
        logz("TenantDetailsFrag`tenantDetailsVM:$tenantDetailsVM")
        tenantDetailsVM.tenant.value = args?.tenant
        return mBinding.root
    }

    private fun setupClickListeners() {
        mBinding.fab.setOnClickListener {
            val bottomDialog = BottomDialogForPhoto(requireActivity(), "Add Document") { uri, _ ->
                val tenantID = tenantDetailsVM.tenant.value?.id!!
                if ((tenantID!=null) && (uri!=null)) {
                    tenantDetailsVM.documentsRepo.addDocument(tenantID, uri, "New Document")
                }
            }
            bottomDialog.show(requireActivity().supportFragmentManager, "ZipZoopTheBottomDialog")
        }
    }

    private fun setupObservers() {
        tenantDetailsVM.tenant.observe(viewLifecycleOwner, Observer {
            mBinding.includibleTenantImage.imageview1.easyPicasso(tenantDetailsVM.tenant.value?.imageUrlTask)
        })
        tenantDetailsVM.documents.observe(viewLifecycleOwner, Observer {
            mBinding.recyclerview1.adapter?.notifyDataSetChanged()
        })
    }

    private fun setupView() {
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