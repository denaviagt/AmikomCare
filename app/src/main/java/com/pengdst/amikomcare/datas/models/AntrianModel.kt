package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AntrianModel(
        var id: String? = null,
        var mahasiswa: MahasiswaModel? = null,
        var keluhan: List<String>? = null
) : Parcelable