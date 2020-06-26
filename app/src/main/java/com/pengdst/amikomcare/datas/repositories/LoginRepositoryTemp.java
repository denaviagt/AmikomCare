package com.pengdst.amikomcare.datas.repositories;

import com.pengdst.amikomcare.datas.datasources.LoginDataSource;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of set status and user credentials information.
 */
@SuppressWarnings("unused")
public class LoginRepositoryTemp extends BaseFirebaseRepository {

    private static volatile LoginRepositoryTemp instance;

    private final LoginDataSource dataSource;

//    private LoggedInUser user = null;

    private LoginRepositoryTemp(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepositoryTemp getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepositoryTemp(dataSource);
        }
        return instance;
    }

//    public boolean isLoggedIn() {
//        return user != null;
//    }

    public void logout() {
//        user = null;
        dataSource.logout();
    }

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
}