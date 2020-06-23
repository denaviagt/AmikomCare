package com.pengdst.amikomcare.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pengdst.amikomcare.R
import com.pengdst.amikomcare.databinding.ItemKeluhanBinding

class KeluhanAdapter: RecyclerView.Adapter<KeluhanAdapter.ViewHolder>() {

    lateinit var binding: ItemKeluhanBinding

    private var list = mutableListOf<String>()

    fun setList(listKeluhan: List<String>) {
        list = listKeluhan as MutableList<String>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_keluhan, parent, false)

        binding = ItemKeluhanBinding.bind(view)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.itemKeluhan.text = list[position]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}