package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ObatModel(
        var id: String? = null,
        var nama: String? = null,
        var dosisMinum: String? = null,
        var aturanMinum: String? = null
) : Parcelable