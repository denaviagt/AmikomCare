package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

abstract class BaseFirebaseViewModel: ViewModel() {
    private val NODE_LOGIN = "login"
    private val NODE_PERIKSA = "periksa"
    private val NODE_DATA = "data"

    protected val NODE_DOKTER = "dokter"

    private val db = FirebaseDatabase.getInstance()

    protected val dbLogin = db.getReference(NODE_LOGIN)
    protected val dbPeriksa = db.getReference(NODE_PERIKSA)
    protected val dbData = db.getReference(NODE_DATA)
}