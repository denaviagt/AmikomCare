package com.pengdst.amikomcare.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentProfileBinding;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.preferences.SessionUtil;

public class ProfileFragment extends Fragment {
    private String TAG = "ProfileFragment";
    FragmentProfileBinding binding;
    private Fragment parentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
    }

    private void initVariable() {
        parentFragment = getParentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        binding = FragmentProfileBinding.bind(view);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewComponent();
        setupListener();

    }

    private void setupViewComponent() {
        binding.tvNamaDokter.setText(SessionDokter.init(getContext()).getNama());
        binding.tvSpesialis.setText(SessionDokter.init(getContext()).getSpesialis());

        Glide.with(binding.imagePhoto)
                .load(SessionDokter.init(getContext()).getPhoto())
                .error(R.drawable.dummy_photo)
                .into(binding.imagePhoto);

    }

    private void setupListener() {
        binding.btAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(R.id.action_profileFragment_to_editProfileFragment);
            }
        });
    }

    private void navigateTo(int target) {
        NavHostFragment.findNavController(parentFragment).navigate(target);
    }
}