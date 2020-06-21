package com.pengdst.amikomcare.datas.models;

public class MahasiswaModel {
    String id = "0";
    String nim = "00.00.00";
    String passwordNim = "0";
    String nama = "Proto Mahasiswa";
    String email = "proto@students.amikom.ac.id";
    String passwordEmail = "proto";
    String jenisKelamin = "Laki-laki";
    int usia = 20;
    String photo = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPasswordNim() {
        return passwordNim;
    }

    public void setPasswordNim(String passwordNim) {
        this.passwordNim = passwordNim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordEmail() {
        return passwordEmail;
    }

    public void setPasswordEmail(String passwordEmail) {
        this.passwordEmail = passwordEmail;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
