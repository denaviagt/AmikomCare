package com.pengdst.amikomcare.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pengdst.amikomcare.R
import com.pengdst.amikomcare.databinding.ItemAntrianBinding
import com.pengdst.amikomcare.datas.models.AntrianModel
import com.pengdst.amikomcare.ui.listeners.RecyclerViewCallback

class AntrianAdapter(private var listener: RecyclerViewCallback) : RecyclerView.Adapter<AntrianAdapter.ViewHolder>() {

    @Suppress("PropertyName")
    val TAG = "AntrianAdapter"

    var list = mutableListOf<AntrianModel>()
        set(models) {
            field = models
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_antrian, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            listener.onItemClick(it!!, list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemAntrianBinding.bind(itemView)

        fun bind(antrian: AntrianModel) {
            binding.itemTvNoAntri.text = antrian.id.toString()
            binding.itemTvNamaPasien.text = antrian.mahasiswa?.nama.toString()
            binding.itemKeluhan.text = antrian.keluhan.toString()
        }
    }
}
