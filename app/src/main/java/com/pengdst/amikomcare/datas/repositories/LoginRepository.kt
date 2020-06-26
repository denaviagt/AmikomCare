package com.pengdst.amikomcare.datas.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.datasources.LoginDataSource
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.ui.viewstates.LoginViewState

@Suppress("unused")
class LoginRepository//    private LoggedInUser user = null;
constructor(private var dataSource: LoginDataSource? = null) : BaseFirebaseRepository() {

    // FIXME: 26/06/2020 Try use Datasource
    @Volatile
    private var instance: LoginRepository? = null

    val loginViewState = MutableLiveData<LoginViewState>()

    //    private LoggedInUser user = null;

    fun getInstance(dataSource: LoginDataSource?): LoginRepository? {
        if (instance == null) {
            this.instance = LoginRepository(dataSource)
        }
        return instance
    }

//    public boolean isLoggedIn() {
//        return user != null;
//    }

    //    public boolean isLoggedIn() {
    //        return user != null;
    //    }

//    private void setLoggedInUser(LoggedInUser user) {
//        this.user = user;
//        // If user credentials will be cached in local storage, it is recommended it be encrypted
//        // @see https://developer.android.com/training/articles/keystore
//    }
//
//    public Result<LoggedInUser> set(String username, String password) {
//        // handle set
//        Result<LoggedInUser> result = dataSource.set(username, password);
//        if (result instanceof Result.Success) {
//            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
//        }
//        return result;
//    }

    private val dbDokter = dbLogin.child(NODE_DOKTER)

    fun observeUser(): MutableLiveData<LoginViewState> {
        return loginViewState
    }

    fun logout() {
        loginViewState.value = null
//        user = null;
        dataSource!!.logout()
    }

    fun signIn(email: String, password: String) {

        loginViewState.value = LoginViewState(loading = true)

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                loginViewState.value = loginViewState.value?.copy(isSucces = false, error = error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                var loginViewStateTemp = loginViewState.value?.copy()

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if ((dokter?.email?.equals(email) == true) && (dokter.password.equals(password))) {
                        dokter.id = dokterSnapshots.key

                        loginViewStateTemp = loginViewState.value?.copy(data = dokter, isSucces = true, message = "Sukses Login dengan Email: ${dokter.email}")
                    }
                }

                loginViewState.value = loginViewStateTemp?.copy(loading = false)
            }

        })
    }

    fun signIn(email: String) {

        loginViewState.value = LoginViewState(loading = true)

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                loginViewState.value = loginViewState.value?.copy(isSucces = false, error = error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                var loginViewStateTemp = loginViewState.value?.copy()

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if (dokter?.email?.equals(email) == true) {
                        dokter.id = dokterSnapshots.key

                        loginViewStateTemp = loginViewState.value?.copy(data = dokter, isSucces = true, message = "Sukses Login dengan Email Google: ${dokter.email}")
                    }
                }

                loginViewState.value = loginViewStateTemp?.copy(loading = false)

            }

        })
    }
}