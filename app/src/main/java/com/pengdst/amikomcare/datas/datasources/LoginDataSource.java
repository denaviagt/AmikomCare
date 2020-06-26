package com.pengdst.amikomcare.datas.datasources;

import android.content.Context;

import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.preferences.SessionDokter;

import static com.pengdst.amikomcare.datas.constants.ApiConstant.BASE_PHOTO_URL;

public class LoginDataSource {
    private final SessionDokter sessionUtil;

    public LoginDataSource(Context context){
        sessionUtil = SessionDokter.init(context);
    }

// --Commented out by Inspection START (26/06/2020 08:53):
//    public void set(String username, final String password) {
//        try {
//            dbDokter.child("username")
//                    .equalTo(username)
//                    .addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    for (DataSnapshot dokterSnapshot : snapshot.getChildren()){
//                        DokterModel tempDokter = dokterSnapshot.getValue(DokterModel.class);
//
//                        if (dokter.getPassword().equals(password)){
//                            dokter = tempDokter;
//                        }
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//            setPref(dokter);
//        } catch (Exception e) {
//            Log.e("Error logging in", Arrays.toString(e.getStackTrace()));
//        }
//    }
// --Commented out by Inspection STOP (26/06/2020 08:53)

    @SuppressWarnings("unused")
    public boolean isLogin() {
        return sessionUtil.checkIsLogin();
    }

    @SuppressWarnings("unused")
    private void login(DokterModel dokter) {
        sessionUtil.set(dokter);
    }

    @SuppressWarnings("unused")
    private String createPhoto(String filename){
        return BASE_PHOTO_URL +filename;
    }

    public void logout() {
        sessionUtil.logout();
    }
}