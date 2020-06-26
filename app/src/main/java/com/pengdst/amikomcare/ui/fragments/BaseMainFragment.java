package com.pengdst.amikomcare.ui.fragments;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

@SuppressWarnings("ALL")
public abstract class BaseMainFragment extends Fragment {

    protected void longToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void shortToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
