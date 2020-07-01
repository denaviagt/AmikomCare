package com.pengdst.amikomcare.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pengdst.amikomcare.R
import com.pengdst.amikomcare.databinding.ItemPasienBinding
import com.pengdst.amikomcare.datas.models.PasienModel

class PasienAdapter : RecyclerView.Adapter<PasienAdapter.ViewModel>() {

    @Suppress("PropertyName")
    val TAG = "AntrianAdapter"

    var list = mutableListOf<PasienModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pasien, parent, false)

        return ViewModel(view)
    }

    override fun onBindViewHolder(holder: ViewModel, position: Int) {

        holder.bind(list[position])

        holder.itemView.setOnClickListener {
//            listener.onItemClick(it!!, list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ItemPasienBinding.bind(itemView)

        fun bind(pasien: PasienModel) {
            binding.itemTvIdPasien.text = pasien.id.toString()
            binding.itemTvNamaPasien.text = pasien.mahasiswa?.nama.toString()
            binding.itemSakit.text = pasien.diagnosa?.penyakit.toString()
        }
    }
}