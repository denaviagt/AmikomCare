package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PasienModel(
        var id: String? = null,
        var mahasiswa: MahasiswaModel? = null,
        var diagnosa: DiagnosaModel? = null
) : Parcelable