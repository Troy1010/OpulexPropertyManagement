package com.example.opulexpropertymanagement.aa.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.aa.view_models.MaintenancesVM
import com.example.opulexpropertymanagement.aa.ui.extras.AdapterRVMaintenances
import com.example.opulexpropertymanagement.aa.ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.ItemMaintenanceBinding
import com.example.opulexpropertymanagement.models.Maintenance
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.easyToast
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_properties.view.*

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
                navController.navigate(R.id.action_maintenancesFrag_to_maintenanceDetailsFrag)
            } else {
                easyToast(requireActivity(), "Add Property Failed")
            }
        })
        maintenancesVM.maintenances.observe(viewLifecycleOwner, Observer {
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
        val maintenance = try {
            maintenancesVM.maintenances.value!![i]
        } catch (e: Exception) {
            logz("WARNING: CAUGHT AN UNTYPED EXCEPTION:$e\n${e.stackTrace}\n  .. When you know the type, add it in")
            Maintenance()
        }
        binding.maintenance = maintenance
        binding.btnTrash.setOnClickListener {
            AlertDialog.Builder(activity)
                .setTitle("Delete Property")
                .setMessage("Are you sure you want to delete this property?\nThis action cannot be undone.")
                .setPositiveButton("Delete") { _, _ ->

                    maintenancesVM.repo.removeMaintenance(maintenancesVM.propertyID, maintenance)
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .create().show()
        }
        binding.root.setOnClickListener {
            navController.navigate(R.id.action_maintenancesFrag_to_maintenanceDetailsFrag)
        }
    }
}