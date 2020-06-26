package com.pengdst.amikomcare.ui.pages.fragments;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.pengdst.amikomcare.datas.daos.ObatDao;
import com.pengdst.amikomcare.datas.daos.PasienDao;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.adapters.AntrianAdapter;
import com.pengdst.amikomcare.ui.adapters.KeluhanAdapter;
import com.pengdst.amikomcare.ui.pages.viewmodels.EditProfileViewModel;
import com.pengdst.amikomcare.ui.pages.viewmodels.HomeViewModel;
import com.pengdst.amikomcare.ui.pages.viewmodels.PeriksaViewModel;
import com.pengdst.amikomcare.utils.GoogleSignInUtil;

abstract class BaseMainFragment extends Fragment {

    SessionDokter sessionPeriksa = null;

    protected EditProfileViewModel editProfileViewModel = null;
    protected PeriksaViewModel periksaViewModel = null;
    protected HomeViewModel homeViewModel = null;

    protected GoogleSignInUtil signInUtil = null;

    protected AntrianAdapter antrianAdapter = null;
    protected KeluhanAdapter keluhanAdapter = null;

    protected PasienDao pasienDao = null;
    protected ObatDao obatDao = null;


    protected void longToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void shortToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
