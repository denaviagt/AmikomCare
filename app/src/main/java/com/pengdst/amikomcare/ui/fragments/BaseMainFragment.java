package com.pengdst.amikomcare.ui.fragments;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.pengdst.amikomcare.datas.daos.ObatDao;
import com.pengdst.amikomcare.datas.daos.PasienDao;
import com.pengdst.amikomcare.datas.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.adapters.AntrianAdapter;
import com.pengdst.amikomcare.ui.adapters.KeluhanAdapter;
import com.pengdst.amikomcare.ui.adapters.PasienAdapter;
import com.pengdst.amikomcare.ui.viewmodels.EditProfileViewModel;
import com.pengdst.amikomcare.ui.viewmodels.HomeViewModel;
import com.pengdst.amikomcare.ui.viewmodels.PasienViewModel;
import com.pengdst.amikomcare.ui.viewmodels.PeriksaViewModel;
import com.pengdst.amikomcare.ui.viewmodels.StatistikViewModel;
import com.pengdst.amikomcare.utils.GoogleSignInUtil;

abstract class BaseMainFragment extends Fragment {

    protected SessionDokter sessionDokter = null;

    protected EditProfileViewModel editProfileViewModel = null;
    protected PeriksaViewModel periksaViewModel = null;
    protected HomeViewModel homeViewModel = null;
    protected PasienViewModel pasienViewModel = null;
    protected StatistikViewModel statistikViewModel = null;

    protected GoogleSignInUtil signInUtil = null;

    protected AntrianAdapter antrianAdapter = null;
    protected PasienAdapter pasienAdapter = null;
    protected KeluhanAdapter keluhanAdapter = null;

    protected PasienDao pasienDao = null;
    protected ObatDao obatDao = null;

    protected void longToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void shortToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected String toUpperSentence(String sentence){
        return Character.toUpperCase(sentence.charAt(0)) + sentence.substring(1).toLowerCase();
    }
}
