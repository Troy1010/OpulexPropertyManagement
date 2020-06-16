package com.example.opulexpropertymanagement.ui

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opulexpropertymanagement.R
import com.example.tmcommonkotlin.TMRecyclerViewAdapter
import kotlinx.android.synthetic.main.frag_properties.*

class FragProperties: Fragment(R.layout.frag_properties), TMRecyclerViewAdapter.Callbacks {

    override fun onStart() {
        super.onStart()
        recyclerview_1.layoutManager = LinearLayoutManager(requireActivity())
        recyclerview_1.adapter = TMRecyclerViewAdapter(this, requireActivity(),R.layout.item_property)
    }

    override fun bindRecyclerItemView(view: View, i: Int) {
        TODO("Not yet implemented")
    }

    override fun getRecyclerDataSize(): Int {
        TODO("Not yet implemented")
    }
}