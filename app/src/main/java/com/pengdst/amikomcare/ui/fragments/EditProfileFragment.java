package com.pengdst.amikomcare.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentEditProfileBinding;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.datas.preferences.SessionDokter;
import com.pengdst.amikomcare.utils.SessionUtil;
import com.pengdst.amikomcare.ui.viewmodels.EditProfileViewModel;
import com.pengdst.amikomcare.datas.viewstates.DokterState;

import java.util.Objects;

import static com.pengdst.amikomcare.datas.constants.ResultKt.RESULT_ERROR;
import static com.pengdst.amikomcare.datas.constants.ResultKt.RESULT_SUCCESS;
import static com.pengdst.amikomcare.datas.preferences.SessionDokter.KEY_NAMA;
import static com.pengdst.amikomcare.datas.preferences.SessionDokter.KEY_SPESIALIS;

public class EditProfileFragment extends BaseMainFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private FragmentEditProfileBinding binding;

    private void initViewModel() {
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
    }

    private void setupBinding(View view) {
        binding = FragmentEditProfileBinding.bind(view);
    }

    private void observeViewModel() {

        editProfileViewModel.observeDokter().observe(getViewLifecycleOwner(), new Observer<DokterState>() {
            @Override
            public void onChanged(DokterState dokterState) {
                if (dokterState.getStatus() == RESULT_SUCCESS) {
                    requireActivity().onBackPressed();
                    shortToast("Success update: "+ Objects.requireNonNull(dokterState.getData()).getNama());
                }
                else if (dokterState.getStatus() == RESULT_ERROR){
                    shortToast("Update Failed: "+ dokterState.getMessage());
                }
            }
        });

    }

    private void setupListener() {
        binding.btSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionDokter.setNama(binding.etNama.getText().toString());
                sessionDokter.setEmail(binding.etEmail.getText().toString());
                sessionDokter.setSpesialis(binding.etSpesialis.getText().toString());

                DokterModel dokter = sessionDokter.getDokter();

                editProfileViewModel.updateDokter(dokter);

            }
        });
    }

    private void setupComponent() {
        binding.tvNamaUser.setText(sessionDokter.getNama());
        binding.tvSpesialisUser.setText(sessionDokter.getSpesialis());

        binding.etNama.setText(SessionUtil.init(getContext()).getString(KEY_NAMA));
        binding.etEmail.setText(SessionUtil.init(getContext()).getString(SessionDokter.KEY_EMAIL));
        binding.etSpesialis.setText(SessionUtil.init(getContext()).getString(SessionDokter.KEY_SPESIALIS));

        Glide.with(binding.imageProfilePic)
                .load(sessionDokter.getPhoto())
                .error(R.drawable.dummy_photo)
                .into(binding.imageProfilePic);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionDokter = SessionDokter.init(getContext());
        sessionDokter.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        setupBinding(view);
        initViewModel();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupComponent();
        setupListener();
        observeViewModel();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case KEY_NAMA:
                binding.tvNamaUser.setText(sessionDokter.getNama());
                break;
            case KEY_SPESIALIS:
                binding.tvSpesialisUser.setText(sessionDokter.getSpesialis());
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        sessionDokter.unregister(this);
    }
}