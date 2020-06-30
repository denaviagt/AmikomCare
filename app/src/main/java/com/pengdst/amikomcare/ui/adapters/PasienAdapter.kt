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

    lateinit var binding: ItemPasienBinding
    private var list = mutableListOf<PasienModel>()

    fun setList(newList: List<PasienModel>?) {
        list = newList as MutableList<PasienModel>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pasien, parent, false)

        binding = ItemPasienBinding.bind(view)

        return ViewModel(binding.root)
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
        fun bind(pasien: PasienModel) {
            binding.itemTvIdPasien.text = pasien.id.toString()
            binding.itemTvNamaPasien.text = pasien.mahasiswa?.nama.toString()
            binding.itemSakit.text = pasien.diagnosa?.penyakit.toString()
        }
    }
}