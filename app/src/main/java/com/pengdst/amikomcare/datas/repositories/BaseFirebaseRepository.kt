@file:Suppress("unused")

package com.pengdst.amikomcare.datas.repositories

import com.google.firebase.database.FirebaseDatabase

@Suppress("PropertyName", "MemberVisibilityCanBePrivate", "SpellCheckingInspection")
abstract class BaseFirebaseRepository {
    protected val NODE_LOGIN = "set"
    protected val NODE_PERIKSA = "periksa"
    protected val NODE_DATA = "data"

    protected val NODE_DOKTER = "dokter"
    protected val NODE_MAHASISWA = "mahasiswa"
    protected val NODE_ANTRIAN = "antrian"
    protected val NODE_PASIEN = "pasien"
    protected val NODE_DIAGNOSA = "diagnosa"
    protected val NODE_OBAT = "obat"

    protected val db = FirebaseDatabase.getInstance()

    protected val dbLogin = db.getReference(NODE_LOGIN)
    protected val dbPeriksa = db.getReference(NODE_PERIKSA)
    protected val dbData = db.getReference(NODE_DATA)

}