package com.pengdst.amikomcare.ui.viewmodels

import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.constants.ApiConstant
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.listeners.LoginCallback

class LoginViewModel : ViewModel() {

    companion object {
        @kotlin.jvm.JvmField
        var RC_SIGN_IN: Int = 1
        val TAG = "LoginViewModel"
    }

    public var callback: LoginCallback = object : LoginCallback {
        override fun onSuccess(dokter: DokterModel) {

        }

    }

    private val NODE_LOGIN = "login"
    private val NODE_DOKTER = "dokter"

    val liveDataDokter = MutableLiveData<DokterModel>()

    protected val db = FirebaseDatabase.getInstance().getReference(NODE_LOGIN)
    private val dbDokter = db.child(NODE_DOKTER)

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    fun init() {
        auth = FirebaseAuth.getInstance()
    }

    fun checkCurrentUser() {
        val currentUser = auth.currentUser
    }

    fun configureGoogleSignin(fragment: Fragment) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(ApiConstant.default_web_client_id)
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(fragment.requireActivity(), gso)
    }

    fun signInGoogle(fragment: Fragment) {
        configureGoogleSignin(fragment)
        val signInIntent: Intent = googleSignInClient.signInIntent

        Log.d(TAG, "signInGoogle() called with: activity = ${fragment.onActivityResult(RC_SIGN_IN, 1, signInIntent)}")
        fragment.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun signIn(email: String) {
        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var found = false

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if (dokter?.email?.equals(email) == true) {
                        dokter.id = dokterSnapshots.key

                        liveDataDokter.value = dokter
                        found = true
                    }
                }

                if (found) {
                    callback.onSuccess(liveDataDokter.value!!)
                    Log.e(TAG, "Found User: ${liveDataDokter.value}")
                }
                else {
                    Log.e(TAG, "Wrong Password or Email: ${!found}")
                }
            }

        })
    }

    fun signOut() {
        auth.signOut()
    }

}