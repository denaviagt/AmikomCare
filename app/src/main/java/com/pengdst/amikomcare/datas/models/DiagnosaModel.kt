package com.pengdst.amikomcare.datas.models

data class DiagnosaModel(
        val keluhan: List<String>? = null,
        val penyakit: String? = null,
        val obat: List<ObatModel>? = null,
        val catatan: String? = null
)