package com.pengdst.amikomcare.datas.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MahasiswaModel(
        var id: String? = "0",
        var nim: String? = "00.00.00",
        var passwordNim: String? = "0",
        var nama: String? = "Proto Mahasiswa",
        var email: String? = "proto@students.amikom.ac.id",
        var passwordEmail: String? = "proto",
        var jenisKelamin: String? = "Laki-laki",
        var usia: Int = 20,
        var photo: String? = ""
) : Parcelable