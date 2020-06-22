package com.example.opulexpropertymanagement.ac_ui.extras

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.opulexpropertymanagement.databinding.ItemTenantBinding

class AdapterRVTenant(
        var arvImplementer: ARVInterface,
        val item_layout: Int
    ) : RecyclerView.Adapter<AdapterRVTenant.ViewHolder>() {
        inner class ViewHolder(val binding: ItemTenantBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    item_layout,
                    parent,
                    false
                )
            )

        override fun getItemCount(): Int {
            return arvImplementer.getRecyclerDataSize()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            arvImplementer.bindRecyclerItemView(holder.binding, position)
        }

        interface ARVInterface {
            fun getRecyclerDataSize(): Int
            fun bindRecyclerItemView(binding: ItemTenantBinding, i: Int)
        }

    }