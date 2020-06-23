package com.pengdst.amikomcare.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentLoginBinding;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.listeners.LoginCallback;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.preferences.SessionUtil;
import com.pengdst.amikomcare.ui.viewmodels.LoginViewModel;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener, LoginCallback {
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;
    private SessionUtil sessionUtil;

    public LoginFragment() {
        Log.e(getTag(), "Login");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionUtil = SessionUtil.init(getContext());
        if (sessionUtil.getBoolean("login")){
            navigate(R.id.action_loginFragment_to_homeFragment);
        }

        sessionUtil.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        sessionUtil.unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setupBinding(view);
        initAdapter();
        initViewModel();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeViewModel();
        setupListener();

    }

    private void fetchViewModel(String email, String password) {
        viewModel.fetchDokter(
                email,
                password
        );

        viewModel.session = this;
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void initAdapter() {

    }

    private void setupBinding(View view) {
        binding = FragmentLoginBinding.bind(view);
    }

    private void observeViewModel() {
        viewModel.observeDokter().observe(getViewLifecycleOwner(), new Observer<DokterModel>() {
            @Override
            public void onChanged(DokterModel dokterModel) {
                Log.d("LOGINFRAGMENT", "observe: "+dokterModel.toString());
            }
        });
    }

    private void setupListener() {
        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchViewModel(
                        binding.etEmailUser.getText().toString(),
                        binding.etPassword.getText().toString()
                );
            }
        });
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (SessionUtil.init(getContext()).getBoolean("login")){
            navigate(R.id.action_loginFragment_to_homeFragment);
        }
    }

    public void navigate(int target) {
        NavHostFragment.findNavController(getParentFragment()).navigate(target);
    }

    @Override
    public void onSuccess(@NotNull DokterModel dokter) {
        SessionDokter.init(getContext()).login(dokter);
    }
}