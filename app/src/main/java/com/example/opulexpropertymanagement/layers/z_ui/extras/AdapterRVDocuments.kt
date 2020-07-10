package com.example.opulexpropertymanagement.layers.z_ui.extras

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.opulexpropertymanagement.databinding.ItemDocumentBinding


class AdapterRVDocuments(
    var arvImplementer: ARVInterface,
    val item_layout: Int
) : RecyclerView.Adapter<AdapterRVDocuments.ViewHolder>() {
    inner class ViewHolder(val binding: ItemDocumentBinding) : RecyclerView.ViewHolder(binding.root)

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
        fun bindRecyclerItemView(binding: ItemDocumentBinding, i: Int)
    }

}