package com.pengdst.amikomcare.listeners

import android.view.View
import com.pengdst.amikomcare.datas.models.AntrianModel

interface RecyclerViewCallback {
    fun onItemClick(view: View, antrian: AntrianModel)
}
