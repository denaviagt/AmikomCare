package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiagnosaModel(
        var keluhan: List<String>? = null,
        var penyakit: String? = null,
        var obat: List<ObatModel>? = null,
        var catatan: String? = null
) : Parcelable