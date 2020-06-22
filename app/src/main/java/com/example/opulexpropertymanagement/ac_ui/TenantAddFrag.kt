package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.GlobalVM
import com.example.opulexpropertymanagement.ab_view_models.PropertiesVM
import com.example.opulexpropertymanagement.ab_view_models.PropertyAddVM
import com.example.opulexpropertymanagement.ab_view_models.TenantAddVM
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.FragTenantAddBinding
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.PropertyStatus
import com.example.tmcommonkotlin.easyToast
import kotlinx.android.synthetic.main.frag_tenant_add.view.*

class TenantAddFrag: OXFragment() {
    lateinit var mBinding: FragTenantAddBinding
    val tenantAddVM: TenantAddVM by viewModels()
    val navController by lazy { this.findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_tenant_add, container, false)
        mBinding.lifecycleOwner = this
        mBinding.tenantAddVM = tenantAddVM
        setupOnFocusChangedListeners()
        setupClickListeners()
        setupObservers()
        return mBinding.root
    }

    private fun setupObservers() {
    }

    private fun setupClickListeners() {
        mBinding.root.btn_add.setOnClickListener {
                val user = GlobalVM.user.value
                val image = tenantAddVM.image.value
                if ((user != null) && (image != null)) {
                    tenantAddVM.repo.addProperty(
                        streetAddress = mBinding.textinputAddress.textinput.text.toString(),
                        city = mBinding.textinputCity.textinput.text.toString(),
                        country = mBinding.textinputCountry.textinput.text.toString(),
                        mortgageInfo = "",
                        state = mBinding.textinputState.textinput.text.toString(),
                        purchasePrice = mBinding.textinputPrice.textinput.text.toString(),
                        statusID = PropertyStatus.Unavailable.id // TODO
                    ), user, image)} else if (user == null) {
                    easyToast(requireActivity(), "Login required")
                } else if (image == null) {
                    easyToast(requireActivity(), "Image required")
                }
            }
        }
    }

    private fun setupOnFocusChangedListeners() {
    }
}