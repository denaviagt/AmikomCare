package com.pengdst.amikomcare.preferences;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.pengdst.amikomcare.datas.models.DokterModel;

public class SessionDokter {
    SessionUtil session;
    private Context context;
    private final String base_url = "https://firebasestorage.googleapis.com/v0/b/amikomcare.appspot.com/o/";
//    private String token = "786dd46d-e693-4942-99f6-64408818938d";

    public static final String KEY_ID = "id",
            KEY_NAMA = "nama",
            KEY_SPESIALIS = "spesialis",
            KEY_EMAIL = "email",
            KEY_JENIS_KELAMIN = "jenis_kelamin",
            KEY_NIP = "nip",
            KEY_PHOTO = "photo",
            KEY_PASSWORD = AccountManager.KEY_PASSWORD,
            KEY_LOGIN = "login";

    public SessionDokter(Context context) {
        this.context = context;
        session = SessionUtil.init(context);
    }

    public static SessionDokter init(Context context){
        return new SessionDokter(context);
    }

    public String getPhoto(){
        return createPhoto(SessionUtil.init(this.context).getString(KEY_PHOTO));
    }

    public String getSpesialis(){
        String spesialis = "Dokter "+session.getString(KEY_SPESIALIS);

        return spesialis;
    }

    public String getNama(){
        String nama = "Dokter "+session.getString(KEY_NAMA);

        return nama;
    }

    public String getId(){
        String nama = session.getString(KEY_ID);

        return nama;
    }

    public SessionDokter setNama(String nama){
        session.set(KEY_NAMA, nama);
        return this;
    }

    public SessionDokter setEmail(String email){
        session.set(KEY_EMAIL, email);
        return this;
    }

    public SessionDokter setSpesialis(String spesialis){
        session.set(KEY_SPESIALIS, spesialis);
        return this;
    }

    public SessionDokter login(DokterModel dokter){
        session.set(KEY_ID, dokter.getId())
            .set(KEY_NIP, dokter.getNip())
            .set(KEY_NAMA, dokter.getNama())
            .set(KEY_SPESIALIS, dokter.getSpesialis())
            .set(KEY_EMAIL, dokter.getEmail())
            .set(KEY_PASSWORD, dokter.getPassword())
            .set(KEY_JENIS_KELAMIN, dokter.getJenisKelamin())
            .set(KEY_PHOTO, dokter.getPhoto())
            .set(KEY_LOGIN, true);
        return this;
    }

    public void logout(){
        session.clear();
        session.set(KEY_LOGIN, false);
    }

    public DokterModel getDokter(){

        DokterModel dokter = new DokterModel();

        dokter.setId(session.getString(KEY_ID));
        dokter.setNip(session.getString(KEY_NIP));
        dokter.setNama(session.getString(KEY_NAMA));
        dokter.setSpesialis(session.getString(KEY_SPESIALIS));
        dokter.setEmail(session.getString(KEY_EMAIL));
        dokter.setPassword(session.getString(KEY_PASSWORD));
        dokter.setJenisKelamin(session.getString(KEY_JENIS_KELAMIN));
        dokter.setPhoto(session.getString(KEY_PHOTO));

        return dokter;

    }

    private String createPhoto(String filename){
        return base_url+filename;
    }

    public void register(SharedPreferences.OnSharedPreferenceChangeListener listener){
        session.register(listener);
    }

    public void unregister(SharedPreferences.OnSharedPreferenceChangeListener listener){
        session.unregister(listener);
    }

    public boolean isLogin() {
        return session.getBoolean(KEY_LOGIN);
    }
}
