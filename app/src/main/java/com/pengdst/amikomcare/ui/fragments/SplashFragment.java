package com.pengdst.amikomcare.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.preferences.SessionUtil;

public class SplashFragment extends Fragment {
    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.d("TEST", "onSharedPreferenceChanged");
            if (SessionUtil.init(getContext()).getBoolean("login")) {
                navigateTo(R.id.action_splashFragment_to_homeFragment);
            } else {
                navigateTo(R.id.action_splashFragment_to_loginFragment);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(listener);
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("", "Splash");

        if (SessionUtil.init(getContext()).getBoolean("login")){
            navigateTo(R.id.action_splashFragment_to_homeFragment);
        }else {
            navigateTo(R.id.action_splashFragment_to_loginFragment);
        }
    }

    private void navigateTo(int target) {
        if (getParentFragment() != null) {
            NavHostFragment.findNavController(getParentFragment()).navigate(target);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

//        SessionUtil.init(getContext()).unregister(this);
    }
}