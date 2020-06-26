package com.pengdst.amikomcare.ui.pages.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.datas.daos.ObatDao;
import com.pengdst.amikomcare.datas.daos.PasienDao;
import com.pengdst.amikomcare.ui.adapters.KeluhanAdapter;
import com.pengdst.amikomcare.ui.pages.viewmodels.PeriksaViewModel;

// FIXME: 26/06/2020 Remove Unused Variable
public class ListPasienFragment extends BaseMainFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_pasien, container, false);
    }
}