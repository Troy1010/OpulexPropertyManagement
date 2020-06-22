package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.PropertiesVM
import com.example.opulexpropertymanagement.ab_view_models.PropertyDetailsVM
import com.example.opulexpropertymanagement.ac_ui.extras.BottomDialogForPhoto
import com.example.opulexpropertymanagement.ac_ui.extras.PropertyDetailsVMFactory
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragPropertyDetailsBinding
import com.example.opulexpropertymanagement.util.easyPicasso
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

    private fun setupView() {
        mBinding.root.includible_property_image.imageview_1.easyPicasso(propertyDetailsVM.property?.imageUrlTask)
        mBinding.root.includible_tenant.imageview_1.easyPicasso(propertyDetailsVM.tenant.value?.imageUrlTask)
        mBinding.root.includible_tenant.setOnClickListener {
            navController.navigate(R.id.action_fragPropertyDetails_to_tenantAddFrag)
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

    private fun setupObservers() {

    }

    private fun setupClickListeners() {
    }
}