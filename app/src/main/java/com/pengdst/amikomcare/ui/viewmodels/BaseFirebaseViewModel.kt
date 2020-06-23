package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

abstract class BaseFirebaseViewModel: ViewModel() {
    protected val NODE_LOGIN = "login"
    protected val NODE_PERIKSA = "periksa"

    protected val dbLogin = FirebaseDatabase.getInstance().getReference(NODE_LOGIN)
    protected val dbPeriksa = FirebaseDatabase.getInstance().getReference(NODE_PERIKSA)
}