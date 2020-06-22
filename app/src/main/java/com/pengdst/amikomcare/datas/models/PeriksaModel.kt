package com.pengdst.amikomcare.datas.models

data class PeriksaModel(
    val dokter: DokterModel?,
    val pasien: List<PasienModel>?
)