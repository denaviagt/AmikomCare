package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PeriksaModel(
        var id: String? = null,
        var dokter: DokterModel? = null,
        var pasien: List<PasienModel>? = null
) : Parcelable