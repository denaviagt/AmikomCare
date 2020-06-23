package com.pengdst.amikomcare.datas.models

data class PeriksaModel(
        var id: String? = null,
        var dokter: DokterModel? = DokterModel(),
        var pasien: List<PasienModel>? = null
)