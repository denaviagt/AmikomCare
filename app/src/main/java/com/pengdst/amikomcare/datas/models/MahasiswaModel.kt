package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MahasiswaModel(
        var id: String? = null,
        var nim: String? = null,
        var passwordNim: String? = null,
        var nama: String? = null,
        var email: String? = null,
        var passwordEmail: String? = null,
        var jenisKelamin: String? = null,
        var usia: Int? = null,
        var photo: String? = null
) : Parcelable