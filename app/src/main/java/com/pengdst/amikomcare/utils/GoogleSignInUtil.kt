package com.pengdst.amikomcare.utils

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.pengdst.amikomcare.datas.constants.ApiConstant

class GoogleSignInUtil {

    companion object {
        fun init(activity: Activity): GoogleSignInUtil { return GoogleSignInUtil().init(activity) }
        const val RC_SIGN_IN: Int = 1
    }

    var currentUser: FirebaseUser? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private lateinit var signInClient: GoogleSignInClient
    private lateinit var activity: Activity
    var result: GoogleSignInResult? = null
    lateinit var task: Task<GoogleSignInAccount>
    var account: GoogleSignInAccount? = null

    fun init(activity: Activity) : GoogleSignInUtil {

        this.auth = FirebaseAuth.getInstance()
        this.activity = activity
        this.currentUser = auth.currentUser
        buildGoogleSignIn()

        return this
    }

    private fun buildGoogleSignIn() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(ApiConstant.default_web_client_id)
                .requestEmail()
                .build()

        signInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun getSignInIntent(): Intent {
        return signInClient.signInIntent
    }

    fun prepareHandleResult(data: Intent?){
        task = GoogleSignIn.getSignedInAccountFromIntent(data)
        result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        account = completedTask.getResult(ApiException::class.java)
    }

    fun signInWithGoogle(idToken: String?): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
    }

    /*//    4. SignOut*/
    fun signOut(){
        auth.signOut()
        signInClient.signOut()
    }

}