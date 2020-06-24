package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ObatModel(
        val id: String? = null,
        val nama: String? = null,
        val dosisMinum: String? = null,
        val aturanMinum: String? = null
) : Parcelable