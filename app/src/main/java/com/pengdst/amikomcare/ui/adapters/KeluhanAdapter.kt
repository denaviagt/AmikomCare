package com.pengdst.amikomcare.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pengdst.amikomcare.R
import com.pengdst.amikomcare.databinding.ItemKeluhanBinding

class KeluhanAdapter: RecyclerView.Adapter<KeluhanAdapter.ViewHolder>() {

    @Suppress("PrivatePropertyName")
    private val TYPE_FULL = 0
    @Suppress("PrivatePropertyName")
    private val TYPE_HALF = 1
    @Suppress("PrivatePropertyName")
    private val TYPE_QUARTER = 2

    private lateinit var binding: ItemKeluhanBinding

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

    override fun getItemViewType(position: Int): Int {

        when (position % 8) {
            0, 5 -> return TYPE_HALF
            1, 2, 4, 6 -> return TYPE_QUARTER
        }

        return TYPE_FULL
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}