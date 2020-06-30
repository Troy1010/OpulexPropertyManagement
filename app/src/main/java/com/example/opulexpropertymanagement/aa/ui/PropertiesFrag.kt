package com.example.opulexpropertymanagement.aa.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.FragPropertiesBinding
import com.example.opulexpropertymanagement.databinding.ItemPropertyBinding
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.aa.ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.aa.view_models.PropertiesVM
import com.example.opulexpropertymanagement.aa.ui.extras.AdapterRVProperties
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_properties.*

// This is a silly hack to share a fragment-scoped ViewModel.
// I prefer not to make an activityViewModel() because it's essentially a memory leak.
var PropertiesStoreOwner: ViewModelStoreOwner? = null
class PropertiesFrag: OXFragment(), AdapterRVProperties.ARVInterface {
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
        setupObservers()
//        logz("activityViewModels:${activityViewModels<ViewModel>()}")
//        logz("activityViewModels:${requireActivity()}")
        return mBinding.root
    }

    private fun setupObservers() {
        propertiesVM.properties.observe(viewLifecycleOwner, Observer {
            recyclerview_1.adapter?.notifyDataSetChanged()
        })
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
        binding.root.includible_property_image.imageview_1.easyPicasso(property.imageUrlTask)
        binding.root.setOnClickListener {
            PropertiesStoreOwner = this
            val directions = PropertiesFragDirections.actionFragPropertiesToFragPropertyDetails(property)
            navController.navigate(directions)
        }
        binding.root.btn_trash.setOnClickListener {
            AlertDialog.Builder(activity)
                .setTitle("Delete Property")
                .setMessage("Are you sure you want to delete this property?\nThis action cannot be undone.")
                .setPositiveButton("Delete") { _, _ ->
                    propertiesVM.repo.removeProperty(property.id)
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .create().show()
        }
    }

    override fun onDestroy() {
        logz("PropertiesFrag`onDestroy")
        super.onDestroy()
        PropertiesStoreOwner = null
    }
}