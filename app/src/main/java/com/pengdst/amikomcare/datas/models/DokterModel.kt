package com.pengdst.amikomcare.datas.models

data class DokterModel (
        var id: String? = "0",
        var nama: String? = null,
        var nip: String? = "00.00.00",
        var email: String? = "proto@amikom.ac.id",
        var password: String? = null,
        var spesialis: String? = "Umum",
        var jenisKelamin: String? = "Laki-laki",
        var photo: String? = "")