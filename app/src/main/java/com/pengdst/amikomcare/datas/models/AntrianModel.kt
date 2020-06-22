package com.pengdst.amikomcare.datas.models

data class AntrianModel(
        var noAntrian: Int? = null,
        var mahasiswa: MahasiswaModel? = null,
        var keluhan: List<String>? = null
)