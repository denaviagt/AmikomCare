package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DokterModel (
        var id: String? = null,
        var nama: String? = null,
        var nip: String? = null,
        var email: String? = null,
        var password: String? = null,
        var spesialis: String? = null,
        var jenisKelamin: String? = null,
        var photo: String? = null
) : Parcelable, BaseModel()