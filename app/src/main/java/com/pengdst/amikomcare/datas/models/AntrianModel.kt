package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AntrianModel(
        var id: String? = null,
        var dokter: DokterModel? = null,
        var mahasiswa: MahasiswaModel? = null,
        var keluhan: List<String>? = null
) : Parcelable