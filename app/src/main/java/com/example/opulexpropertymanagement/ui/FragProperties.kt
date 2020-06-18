package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragPropertiesBinding
import com.example.opulexpropertymanagement.databinding.ItemPropertyBinding
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.ReasonForLogin
import com.example.opulexpropertymanagement.ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.view_models.PropertiesVM
import com.example.opulexpropertymanagement.view_models.UserVM
import kotlinx.android.synthetic.main.frag_properties.*

class FragProperties: OXFragment(), AdapterRVProperties.ARVInterface {
    lateinit var mBinding: FragPropertiesBinding
    val propertiesVM: PropertiesVM by viewModels()
    val userVM: UserVM by activityViewModels()
    val navController by lazy { this.findNavController() }
    val args by lazy { arguments?.let { FragPropertiesArgs.fromBundle(it) } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.frag_properties, container, false
        )
        mBinding.lifecycleOwner = this
        propertiesVM.properties.observe(viewLifecycleOwner, Observer {
            recyclerview_1.adapter?.notifyDataSetChanged()
        })
        userVM.user.observe(viewLifecycleOwner, Observer {
            if (it==null) {
                val directions = FragPropertiesDirections.actionFragPropertiesToFragLogin(ReasonForLoginInt = ReasonForLogin.Properties.ordinal)
                navController.navigate(directions)
            }
        })
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        recyclerview_1.layoutManager = LinearLayoutManager(requireActivity())
        recyclerview_1.adapter = AdapterRVProperties(this, R.layout.item_property)
        btn_add_property.setOnClickListener {
            val x = propertiesVM.properties.value ?: arrayListOf()
            x.add(Property(propertyaddress = "aaaaaaaaaa"))
            propertiesVM.properties.value = x
        }
    }

    override fun getRecyclerDataSize(): Int {
        return propertiesVM.properties.value?.size ?: 0
    }

    override fun bindRecyclerItemView(binding: ItemPropertyBinding, i: Int) {
        binding.property = propertiesVM.properties.value?.get(i) ?: Property()
    }
}