package com.pengdst.amikomcare.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pengdst.amikomcare.R
import com.pengdst.amikomcare.databinding.ItemAntrianBinding
import com.pengdst.amikomcare.datas.models.AntrianModel
import com.pengdst.amikomcare.listeners.RecyclerViewCallback

class AntrianAdapter(private var listener: RecyclerViewCallback) : RecyclerView.Adapter<AntrianAdapter.ViewModel>() {

    @Suppress("PropertyName")
    val TAG = "AntrianAdapter"

    lateinit var binding: ItemAntrianBinding
    private var list = listOf<AntrianModel>()

    fun setList(models: List<AntrianModel>) {
        list = models
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_antrian, parent, false)

        binding = ItemAntrianBinding.bind(view)

        return ViewModel(binding.root)
    }

    override fun onBindViewHolder(holder: ViewModel, position: Int) {

        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            listener.onItemClick(it!!, list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(antrian: AntrianModel) {
            binding.itemTvNoAntri.text = antrian.id.toString()
            binding.itemTvNamaPasien.text = antrian.mahasiswa?.nama.toString()
            binding.itemKeluhan.text = antrian.keluhan.toString()
        }
    }
}
