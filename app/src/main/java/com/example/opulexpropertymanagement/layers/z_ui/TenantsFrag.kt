package com.example.opulexpropertymanagement.layers.z_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.layers.data_layer.TenantsRepo
import com.example.opulexpropertymanagement.layers.z_ui.extras.AdapterRVTenant
import com.example.opulexpropertymanagement.layers.z_ui.inheritables.OXFragment
import com.example.opulexpropertymanagement.databinding.ItemTenantBinding
import com.example.opulexpropertymanagement.models.Tenant
import com.example.opulexpropertymanagement.util.easyPicasso
import com.example.opulexpropertymanagement.util.onlyNew
import com.example.tmcommonkotlin.logz
import kotlinx.android.synthetic.main.frag_tenants.*
import kotlinx.android.synthetic.main.item_tenant.view.*

class TenantsFrag : OXFragment(), AdapterRVTenant.ARVInterface {
    lateinit var tenants: List<Tenant>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tenants = emptyList()
        setupObservers()
        return inflater.inflate(R.layout.frag_tenants, container, false)
    }

    private fun setupObservers() {
        TenantsRepo.streamGetTenantsResult.onlyNew(viewLifecycleOwner).observe(viewLifecycleOwner, Observer {
            logz("getting tenants:$it")
            tenants = it
            recyclerview_tenants.adapter?.notifyDataSetChanged()
        })
    }

    override fun onStart() {
        super.onStart()
        TenantsRepo.getTenants()
        setupViews()
    }

    private fun setupViews() {
        recyclerview_tenants.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerview_tenants.adapter = AdapterRVTenant(this, R.layout.item_tenant)
    }

    override fun getRecyclerDataSize(): Int {
        return tenants.size
    }

    override fun bindRecyclerItemView(binding: ItemTenantBinding, i: Int) {
        binding.tenantName = tenants[i].name
        binding.includibleTenant.imageview1.easyPicasso(tenants[i].imageUrlTask)
        binding.root.includible_tenant.setOnClickListener {
            val directions = TenantsFragDirections.actionTenantsFragToTenantDetailsFrag(tenants[i])
            findNavController().navigate(directions)
        }
    }
}