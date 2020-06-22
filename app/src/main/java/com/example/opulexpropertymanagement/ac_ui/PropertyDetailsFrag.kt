package com.example.opulexpropertymanagement.ac_ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.aa_repo.PropertyDetailsRepo
import com.example.opulexpropertymanagement.ab_view_models.GlobalVM
import com.example.opulexpropertymanagement.ab_view_models.PropertiesVM
import com.example.opulexpropertymanagement.ab_view_models.PropertyDetailsVM
import com.example.opulexpropertymanagement.ac_ui.extras.BottomDialogForPhoto
import com.example.opulexpropertymanagement.ac_ui.extras.PropertyDetailsVMFactory
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragPropertyDetailsBinding
import com.example.opulexpropertymanagement.models.streamable.RemoveTenantResult
import com.example.opulexpropertymanagement.util.easyPicasso
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_property_details.view.*
import kotlinx.android.synthetic.main.includible_rounded_image.view.imageview_1


class PropertyDetailsFrag: OXFragment() {
    lateinit var mBinding: FragPropertyDetailsBinding
    val args by lazy { arguments?.let { PropertyDetailsFragArgs.fromBundle(it) } }
    val propertiesVM: PropertiesVM by viewModels({ PropertiesStoreOwner!! })
    val propertyIndex by lazy { propertiesVM.properties.value?.indexOf(args?.property)?:0 }
    val propertyDetailsVM: PropertyDetailsVM by viewModels({ this }) { PropertyDetailsVMFactory(propertiesVM.properties, propertyIndex) }
    val navController by lazy { this.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_property_details, container, false)
        mBinding.lifecycleOwner = this
        mBinding.propertyDetailsVM = propertyDetailsVM
        setupClickListeners()
        setupObservers()
        setupView()
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        PropertyDetailsRepo.getTenantByLandlordAndPropertyID(GlobalVM.user.value?.id,
            propertiesVM.properties.value?.get(propertyIndex)?.id
        )
    }

    private fun setupView() {
        mBinding.root.includible_property_image.imageview_1.easyPicasso(propertyDetailsVM.property?.imageUrlTask)
        registerForContextMenu(mBinding.root.includible_tenant)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v==mBinding.root.includible_tenant) {
            activity?.menuInflater?.inflate(R.menu.tenant_context_menu, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.remove_tenant -> {
                val tenantID = propertyDetailsVM.tenant.value?.id
                if (tenantID!=null) {
                    AlertDialog.Builder(activity)
                        .setTitle("Remove Tenant")
                        .setMessage("Are you sure you want to remove this tenant?\nThis action cannot be undone.")
                        .setPositiveButton("Remove") { _, _ ->
                            propertyDetailsVM.tenantsRepo.removeTenant(tenantID)
                        }
                        .setNegativeButton("Cancel") { _, _ -> }
                        .create().show()
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun setupObservers() {
        propertyDetailsVM.tenant.observe(viewLifecycleOwner, Observer {
            logz("observing new tenant:$it")
            mBinding.includibleTenant.imageview1.easyPicasso(propertyDetailsVM.tenant.value?.imageUrlTask)
        })
        propertyDetailsVM.tenantsRepo.streamRemoveTenantResult.onlyNew(viewLifecycleOwner).observe(viewLifecycleOwner, Observer {
            when (it) {
                is RemoveTenantResult.Failure.ApiDoesNotSupportRemovingTenants -> {
                    easyToast(requireActivity(), "Api does not support tenant removal")
                }
            }
        })
    }

    private fun setupClickListeners() {
        mBinding.root.includible_tenant.setOnClickListener {
            if (propertyDetailsVM.tenant.value==null) {
                val directions = PropertyDetailsFragDirections.actionFragPropertyDetailsToTenantAddFrag(propertyDetailsVM.property!!)
                navController.navigate(directions)
            } else {
                val directions = PropertyDetailsFragDirections.actionFragPropertyDetailsToTenantDetailsFrag(propertyDetailsVM.tenant.value!!)
                navController.navigate(directions)
            }
        }
        mBinding.root.includible_property_image.setOnLongClickListener {
            val bottomDialogForPhoto =  BottomDialogForPhoto(requireActivity(), "Replace Property Image") { uri, _ ->
                if (uri!=null) {
                    propertyDetailsVM.property?.setImage(uri)
                        ?.addOnSuccessListener {
                            mBinding.root.includible_property_image.imageview_1.easyPicasso(propertyDetailsVM.property?.imageUrlTask)
                        }
                }
            }
            bottomDialogForPhoto.show(
                requireActivity().supportFragmentManager,
                "bottomSheet2"
            )
            true
        }
    }
}