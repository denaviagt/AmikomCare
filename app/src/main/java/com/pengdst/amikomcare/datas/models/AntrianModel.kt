package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AntrianModel(
        var noAntrian: Int? = null,
        var mahasiswa: MahasiswaModel? = null,
        var keluhan: List<String>? = null
) : Parcelable