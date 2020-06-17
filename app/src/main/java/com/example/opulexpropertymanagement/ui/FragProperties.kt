package com.example.opulexpropertymanagement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragLoginBinding
import com.example.opulexpropertymanagement.databinding.FragPropertiesBinding
import com.example.opulexpropertymanagement.databinding.ItemPropertyBinding
import com.example.opulexpropertymanagement.models.Property
import com.example.tmcommonkotlin.TMRecyclerViewAdapter
import kotlinx.android.synthetic.main.frag_properties.*
import kotlinx.android.synthetic.main.item_property.view.*

class FragProperties: Fragment(), AdapterRVProperties.ARVInterface {
    lateinit var mBinding: FragPropertiesBinding
    val propertiesVM: PropertiesVM by viewModels()

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
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        recyclerview_1.layoutManager = LinearLayoutManager(requireActivity())
        recyclerview_1.adapter = AdapterRVProperties(this, R.layout.item_property)
    }

    override fun getRecyclerDataSize(): Int {
        return propertiesVM.properties.value?.size ?: 0
    }

    override fun bindRecyclerItemView(binding: ItemPropertyBinding, i: Int) {
        binding.property = propertiesVM.properties.value?.get(i) ?: Property()
    }
}