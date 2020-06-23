package com.pengdst.amikomcare.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentPeriksaBinding;
import com.pengdst.amikomcare.datas.models.AntrianModel;
import com.pengdst.amikomcare.ui.adapters.GridAutofitLayoutManager;
import com.pengdst.amikomcare.ui.adapters.KeluhanAdapter;
import com.pengdst.amikomcare.ui.viewmodels.PeriksaViewModel;

import java.util.Objects;

public class PeriksaFragment extends Fragment {
    String TAG = "PeriksaFragment";

    FragmentPeriksaBinding binding;
    KeluhanAdapter adapter;

    PeriksaViewModel viewModel;

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
        initViewModel();

        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PeriksaViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupComponent();
        setupRecyclerView();
        setupListener();

    }

    private void setupListener() {
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.fetch();
            }
        });
    }

    private void setupComponent() {
        binding.tvNamaPasien.setText(pasien.getMahasiswa().getNama());
        binding.tvJenisKelamin.setText(pasien.getMahasiswa().getJenisKelamin());


        Glide.with(binding.imageProfilePic)
                .load(pasien.getMahasiswa().getPhoto())
                .error(R.drawable.dummy_photo)
                .into(binding.imageProfilePic);
    }

    private void setupRecyclerView() {
        GridAutofitLayoutManager gridAutofitLayoutManager = new GridAutofitLayoutManager(getContext(), 48);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        gridAutofitLayoutManager.requestLayout();

        int spanSize = gridAutofitLayoutManager.getSpanCount();
        binding.rvKeluhan.setLayoutManager(gridAutofitLayoutManager);
        binding.rvKeluhan.setHasFixedSize(true);
        binding.rvKeluhan.setAdapter(adapter);

        Log.e(TAG, "setupRecyclerView: "+spanSize);
    }

    private void initBinding(View view) {
        binding = FragmentPeriksaBinding.bind(view);
    }
}