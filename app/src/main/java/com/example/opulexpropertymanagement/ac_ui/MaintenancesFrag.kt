package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.MaintenancesVM
import com.example.opulexpropertymanagement.ac_ui.extras.AdapterRVMaintenances
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.ItemMaintenanceBinding
import kotlinx.android.synthetic.main.frag_properties.*

class MaintenancesFrag: OXFragment(), AdapterRVMaintenances.ARVInterface {
//    lateinit var mBinding: FragPropertiesBinding
    val maintenancesVM: MaintenancesVM by viewModels({ PropertiesStoreOwner!!})
    val navController by lazy { findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // stealing frag_properties to re-use code
        return inflater.inflate(R.layout.frag_properties, container, false)
    }

    override fun onStart() {
        super.onStart()
        setupViews()
    }

    private fun setupViews() {
        btn_add_property.text = "Add Maintenance Request"
        recyclerview_1.layoutManager = LinearLayoutManager(activity)
        recyclerview_1.adapter = AdapterRVMaintenances(this, R.layout.item_maintenance)
    }

    override fun getRecyclerDataSize(): Int {
        return 0
    }

    override fun bindRecyclerItemView(binding: ItemMaintenanceBinding, i: Int) {

    }
}