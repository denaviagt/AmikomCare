package com.pengdst.amikomcare.datas.datasources;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.preferences.SessionUtil;

import java.util.Arrays;

public class LoginDataSource {

    private final String logedIn = "login";
    private final String NODE_LOGIN = "login";
    private final String NODE_DOKTER = "dokter";

    SessionUtil sessionUtil;
    DokterModel dokter;

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference(NODE_LOGIN);
    private DatabaseReference dbDokter = db.child(NODE_DOKTER);

    public LoginDataSource(Context context){
        sessionUtil = SessionUtil.init(context);
    }

    public void login(String username, final String password) {
        try {
            dbDokter.child("username")
                    .equalTo(username)
                    .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dokterSnapshot : snapshot.getChildren()){
                        DokterModel tempDokter = dokterSnapshot.getValue(DokterModel.class);

                        if (dokter.getPassword().equals(password)){
                            dokter = tempDokter;
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            setPref(dokter);
        } catch (Exception e) {
            Log.e("Error logging in", Arrays.toString(e.getStackTrace()));
        }
    }

    private void setPref(DokterModel dokter) {
        sessionUtil.set("Nama", dokter.getNama());
        sessionUtil.set(logedIn, true);
    }

    public void logout() {
        sessionUtil.set(logedIn, false);
    }
}