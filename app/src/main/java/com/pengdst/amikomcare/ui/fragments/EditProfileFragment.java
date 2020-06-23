package com.pengdst.amikomcare.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentEditProfileBinding;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.preferences.SessionUtil;
import com.pengdst.amikomcare.ui.viewmodels.DokterViewModel;

public class EditProfileFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    FragmentEditProfileBinding binding;
    SessionDokter session;
    DokterViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = SessionDokter.init(getContext());
        session.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        session.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_edit_profile, container, false);
        setupBinding(view);
        initViewModel();

        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(DokterViewModel.class);
    }

    private void setupBinding(View view) {
        binding = FragmentEditProfileBinding.bind(view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupComponent();
        setupListener();

    }

    private void setupListener() {
        binding.btSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionDokter.init(getContext()).setNama(binding.etNama.getText().toString());
                SessionDokter.init(getContext()).setEmail(binding.etEmail.getText().toString());
                SessionDokter.init(getContext()).setSpesialis(binding.etSpesialis.getText().toString());

                getActivity().onBackPressed();
            }
        });
    }

    private void navigateTo(int target) {
        NavHostFragment.findNavController(getParentFragment()).navigate(target);
    }

    private void setupComponent() {
        binding.tvNamaUser.setText(session.getNama());
        binding.tvSpesialisUser.setText(session.getSpesialis());

        binding.etNama.setText(SessionUtil.init(getContext()).getString(SessionDokter.KEY_NAMA));
        binding.etEmail.setText(SessionUtil.init(getContext()).getString(SessionDokter.KEY_EMAIL));
        binding.etSpesialis.setText(SessionUtil.init(getContext()).getString(SessionDokter.KEY_SPESIALIS));

        Glide.with(binding.imageProfilePic)
                .load(session.getPhoto())
                .error(R.drawable.dummy_photo)
                .into(binding.imageProfilePic);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        binding.tvNamaUser.setText(session.getNama());
        binding.tvSpesialisUser.setText(session.getSpesialis());

        DokterModel dokter = session.getDokter();

        viewModel.updateDokter(dokter);
    }
}