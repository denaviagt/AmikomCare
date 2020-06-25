package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

abstract class BaseFirebaseViewModel: ViewModel() {
    protected val NODE_LOGIN = "login"
    protected val NODE_PERIKSA = "periksa"
    protected val NODE_DATA = "data"

    protected val NODE_DOKTER = "dokter"
    protected val NODE_MAHASISWA = "mahasiswa"
    protected val NODE_ANTRIAN = "antrian"
    protected val NODE_OBAT = "obat"
    protected val NODE_SPESIALIS = "spesialis"

    protected val db = FirebaseDatabase.getInstance()

    protected val dbLogin = db.getReference(NODE_LOGIN)
    protected val dbPeriksa = db.getReference(NODE_PERIKSA)
    protected val dbData = db.getReference(NODE_DATA)
}