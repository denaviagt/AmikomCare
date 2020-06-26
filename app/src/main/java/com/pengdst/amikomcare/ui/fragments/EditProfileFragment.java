package com.pengdst.amikomcare.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
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
import com.pengdst.amikomcare.datas.viewstates.DokterViewState;

import java.util.Objects;

import static com.pengdst.amikomcare.preferences.SessionDokter.KEY_NAMA;
import static com.pengdst.amikomcare.preferences.SessionDokter.KEY_SPESIALIS;

public class EditProfileFragment extends BaseMainFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "EditProfileFragment";

    private FragmentEditProfileBinding binding;
    private SessionDokter session;
    private DokterViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = SessionDokter.init(getContext());
        session.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        session.unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
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
        observeViewModel();

    }

    private void observeViewModel() {
        viewModel.observeDokter().observe(getViewLifecycleOwner(), new Observer<DokterViewState>() {
            @Override
            public void onChanged(DokterViewState dokterViewState) {
                if (dokterViewState.getSuccess()) {
                    requireActivity().onBackPressed();
                    shortToast("Success update: "+ Objects.requireNonNull(dokterViewState.getData()).getNama());
                }
                else {
                    Log.e(TAG, "Update Error: "+dokterViewState.getError());
                }
            }
        });
    }

    private void setupListener() {
        binding.btSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                session.setNama(binding.etNama.getText().toString());
                session.setEmail(binding.etEmail.getText().toString());
                session.setSpesialis(binding.etSpesialis.getText().toString());

                DokterModel dokter = session.getDokter();

                viewModel.updateDokter(dokter);

            }
        });
    }

    private void setupComponent() {
        binding.tvNamaUser.setText(session.getNama());
        binding.tvSpesialisUser.setText(session.getSpesialis());

        binding.etNama.setText(SessionUtil.init(getContext()).getString(KEY_NAMA));
        binding.etEmail.setText(SessionUtil.init(getContext()).getString(SessionDokter.KEY_EMAIL));
        binding.etSpesialis.setText(SessionUtil.init(getContext()).getString(SessionDokter.KEY_SPESIALIS));

        Glide.with(binding.imageProfilePic)
                .load(session.getPhoto())
                .error(R.drawable.dummy_photo)
                .into(binding.imageProfilePic);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case KEY_NAMA:
                binding.tvNamaUser.setText(session.getNama());
                break;
            case KEY_SPESIALIS:
                binding.tvSpesialisUser.setText(session.getSpesialis());
                break;
        }
    }
}