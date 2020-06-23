package com.example.opulexpropertymanagement.ac_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.ab_view_models.MaintenancesVM
import com.example.opulexpropertymanagement.ac_ui.extras.AdapterRVMaintenances
import com.example.opulexpropertymanagement.ac_ui.extras.MaintenancesVMFactory
import com.example.opulexpropertymanagement.ac_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.ItemMaintenanceBinding
import com.example.opulexpropertymanagement.models.Maintenance
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_properties.view.*

// This is a silly hack to share a fragment-scoped ViewModel.
// I prefer not to make an activityViewModel() because it's essentially a memory leak.
var MaintenancesStoreOwner: ViewModelStoreOwner? = null
class MaintenancesFrag: OXFragment(), AdapterRVMaintenances.ARVInterface {
    lateinit var fragView: View
    val args by lazy { requireArguments().let { MaintenancesFragArgs.fromBundle(it) } }
    val maintenancesVM: MaintenancesVM by viewModels({ PropertyDetailsStoreOwner!!})
    val navController by lazy { findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // stealing frag_properties to re-use code
        fragView = inflater.inflate(R.layout.frag_properties, container, false)
        setupObservers()
        return fragView
    }

    private fun setupObservers() {
        maintenancesVM.repo.streamAddMaintenanceResult.onlyNew(viewLifecycleOwner).observe(viewLifecycleOwner, Observer {
            if (it) {
                MaintenancesStoreOwner = this
                navController.navigate(R.id.action_maintenancesFrag_to_maintenanceDetailsFrag)
            } else {
                easyToast(requireActivity(), "Add Property Failed")
            }
        })
        maintenancesVM.maintenances.observe(viewLifecycleOwner, Observer {
            logz("Got new maintenance List: $it")
            fragView.recyclerview_1.adapter?.notifyDataSetChanged()
        })
    }

    override fun onStart() {
        super.onStart()
        setupViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        fragView.btn_add_property.setOnClickListener {
            val newMaintenance = Maintenance()
            maintenancesVM.selectedMaintenance.value = newMaintenance
            maintenancesVM.repo.addMaintenance(maintenancesVM.propertyID, newMaintenance)
        }
    }

    private fun setupViews() {
        fragView.btn_add_property.text = "Add Maintenance Request"
        fragView.recyclerview_1.layoutManager = LinearLayoutManager(activity)
        fragView.recyclerview_1.adapter = AdapterRVMaintenances(this, R.layout.item_maintenance)
    }

    override fun getRecyclerDataSize(): Int {
        return maintenancesVM.maintenances.value?.size?:0
    }

    override fun bindRecyclerItemView(binding: ItemMaintenanceBinding, i: Int) {
        logz("does this list match:${maintenancesVM.maintenances.value}")
        try {
            binding.maintenance = maintenancesVM.maintenances.value!![i]
        } catch (e: Exception) {
            logz("WARNING: CAUGHT AN UNTYPED EXCEPTION:$e\n${e.stackTrace}\n  .. When you know the type, add it in")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MaintenancesStoreOwner = null
    }
}