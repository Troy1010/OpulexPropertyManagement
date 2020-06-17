package com.example.opulexpropertymanagement.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.opulexpropertymanagement.R
import com.example.opulexpropertymanagement.databinding.ItemPropertyBinding
import com.example.opulexpropertymanagement.models.Property

class AdapterRVProperties(
    var arvImplementer: ARVInterface,
    val item_layout: Int
) : RecyclerView.Adapter<AdapterRVProperties.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPropertyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRVProperties.ViewHolder =
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

    override fun onBindViewHolder(holder: AdapterRVProperties.ViewHolder, position: Int) {
        arvImplementer.bindRecyclerItemView(holder.binding, position)
    }

    interface ARVInterface {
        fun getRecyclerDataSize(): Int
        fun bindRecyclerItemView(binding: ItemPropertyBinding, i: Int)
    }

}