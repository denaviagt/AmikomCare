package com.pengdst.amikomcare.datas.models

data class DiagnosaModel(
        val keluhan: List<String>?,
        val penyakit: String?,
        val obat: List<ObatModel>?,
        val catatan: String?
)