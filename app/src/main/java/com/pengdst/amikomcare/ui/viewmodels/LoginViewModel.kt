package com.pengdst.amikomcare.ui.viewmodels

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.ui.viewstates.LoginViewState

class LoginViewModel : ViewModel() {

    companion object {
        @kotlin.jvm.JvmField
        var RC_SIGN_IN: Int = 1
        val TAG = "LoginViewModel"
    }

    private val NODE_LOGIN = "login"
    private val NODE_DOKTER = "dokter"

    val loginViewState = MutableLiveData<LoginViewState>()

    private val db = FirebaseDatabase.getInstance().getReference(NODE_LOGIN)
    private val dbDokter = db.child(NODE_DOKTER)

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun checkCurrentUser(): FirebaseUser? {
        Log.e(TAG, "checkCurrentUser: " + auth.currentUser)
        return auth.currentUser
    }

    fun logout() {
        loginViewState.value = null
        auth.signOut()
    }

    fun login(email: String, password: String) {
        loginViewState.value = LoginViewState(loading = true)

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if ((dokter?.email?.equals(email) == true) && (dokter.password.equals(password))) {
                        dokter.id = dokterSnapshots.key

                        loginViewState.value = loginViewState.value?.copy(data = dokter, isSucces = true)
                    }
                }

                loginViewState.value = loginViewState.value?.copy(loading = false)
            }

        })
    }

    fun signIn(email: String) {

        loginViewState.value = LoginViewState(loading = true)

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if (dokter?.email?.equals(email) == true) {
                        dokter.id = dokterSnapshots.key

                        auth.fetchSignInMethodsForEmail(email)
                        loginViewState.value = loginViewState.value?.copy(data = dokter, isSucces = true)
                    }
                }

                loginViewState.value = loginViewState.value?.copy(loading = false)

            }

        })
    }

    fun firebaseAuthWithGoogle(activity: Activity, idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = auth.getCurrentUser()
                        Log.d(TAG, "signInWithCredential:success $user")
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithCredential:failure", task.exception)
                    }
                }
    }

    fun observeUser(): MutableLiveData<LoginViewState> {
        return loginViewState
    }

}