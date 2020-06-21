package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragPropertiesBinding
import com.example.opulexpropertymanagement.databinding.ItemPropertyBinding
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.ab_view_models.PropertiesVM
import com.example.opulexpropertymanagement.ac_ui.extras.AdapterRVProperties
import com.example.tmcommonkotlin.logz
import com.google.gson.Gson
import kotlinx.android.synthetic.main.frag_properties.*

// This is a silly hack to share a fragment-scoped ViewModel.
// I prefer not to make an activityViewModel() because it's essentially a memory leak.
var PropertiesStoreOwner: ViewModelStoreOwner? = null
class Properties: OXFragment(), AdapterRVProperties.ARVInterface {
    lateinit var mBinding: FragPropertiesBinding
    val propertiesVM: PropertiesVM by viewModels()
    val navController by lazy { this.findNavController() }

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
        recyclerview_1.adapter =
            AdapterRVProperties(
                this,
                R.layout.item_property
            )
        btn_add_property.setOnClickListener {
            navController.navigate(R.id.action_fragProperties_to_fragPropertyAdd)
        }
    }

    override fun getRecyclerDataSize(): Int {
        return propertiesVM.properties.value?.size ?: 0
    }

    override fun bindRecyclerItemView(binding: ItemPropertyBinding, i: Int) {
        val property = propertiesVM.properties.value?.get(i) ?: Property()
        binding.property = property
        binding.root.setOnClickListener {
            PropertiesStoreOwner = this
            val directions = PropertiesDirections.actionFragPropertiesToFragPropertyDetails(property)
            navController.navigate(directions)
        }
    }
}