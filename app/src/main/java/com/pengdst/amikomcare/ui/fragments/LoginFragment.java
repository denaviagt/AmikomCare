package com.pengdst.amikomcare.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentLoginBinding;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.listeners.LoginCallback;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.preferences.SessionUtil;
import com.pengdst.amikomcare.ui.viewmodels.DokterViewModel;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener, LoginCallback {

    private FragmentLoginBinding binding;

    private DokterViewModel viewModel;

    private Fragment parentFragment;

    private SessionDokter session;

    InputMethodManager imm;

    private static final String TAG = "LoginFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (session.isLogin()){
            navigate(R.id.action_loginFragment_to_homeFragment);
        }

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setupBinding(view);
        initViewModel();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e(TAG, "onViewCreated: ");

        observeViewModel();
        setupListener();

    }

    private void login(String email, String password) {
        viewModel.setCallback(this);

        viewModel.login(
                email,
                password
        );
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(DokterViewModel.class);
    }

    private void initVariable() {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        parentFragment = getParentFragment();
        session = SessionDokter.init(getContext());
    }

    private void setupBinding(View view) {
        binding = FragmentLoginBinding.bind(view);
    }

    private void observeViewModel() {

    }

    private void setupListener() {
        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(
                        binding.etEmailUser.getText().toString(),
                        binding.etPassword.getText().toString()
                );
            }
        });
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (session.isLogin()){
            navigate(R.id.action_loginFragment_to_homeFragment);
        }
    }

    public void navigate(int target) {
        NavController navController = NavHostFragment.findNavController(parentFragment);
        navController.navigate(target);
    }

    @Override
    public void onSuccess(@NotNull DokterModel dokter) {
        NavController navController = NavHostFragment.findNavController(parentFragment);
        if (navController.getCurrentDestination().getId() == R.id.loginFragment) {
            Log.d(TAG, "onDataChange: "+dokter.toString());
            session.login(dokter);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            navigate(R.id.action_loginFragment_to_homeFragment);
        }
    }
}