package com.pengdst.amikomcare.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentPeriksaBinding;
import com.pengdst.amikomcare.datas.models.AntrianModel;
import com.pengdst.amikomcare.ui.adapters.KeluhanAdapter;

import java.util.Objects;

public class PeriksaFragment extends Fragment {
    FragmentPeriksaBinding binding;
    KeluhanAdapter adapter;


    AntrianModel pasien;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();

    }

    private void initVariable() {
        pasien = PeriksaFragmentArgs.fromBundle(requireArguments()).getPasien();
        adapter = new KeluhanAdapter();
        adapter.setList(Objects.requireNonNull(pasien.getKeluhan()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initBinding(inflater.inflate(R.layout.fragment_periksa, container, false));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupComponent();
        setupRecyclerView();

    }

    private void setupComponent() {
    }

    private void setupRecyclerView() {
        binding.rvKeluhan.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false));
        binding.rvKeluhan.setHasFixedSize(true);
        binding.rvKeluhan.setAdapter(adapter);
    }

    private void initBinding(View view) {
        binding = FragmentPeriksaBinding.bind(view);
    }
}