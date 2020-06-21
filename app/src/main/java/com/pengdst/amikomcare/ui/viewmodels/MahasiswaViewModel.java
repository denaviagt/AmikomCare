package com.pengdst.amikomcare.ui.viewmodels;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.preferences.SessionUtil;

public class MahasiswaViewModel {
    private final String logedIn = "login";
    private final String NODE_LOGIN = "login";
    private final String NODE_DOKTER = "dokter";

    SessionUtil sessionUtil;
    DokterModel dokter;

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference(NODE_LOGIN);
    private DatabaseReference dbDokter = db.child(NODE_DOKTER);
}
