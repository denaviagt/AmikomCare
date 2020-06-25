package com.pengdst.amikomcare.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentPeriksaBinding;
import com.pengdst.amikomcare.datas.daos.PasienDAO;
import com.pengdst.amikomcare.datas.models.AntrianModel;
import com.pengdst.amikomcare.datas.models.DiagnosaModel;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.datas.models.MahasiswaModel;
import com.pengdst.amikomcare.datas.models.ObatModel;
import com.pengdst.amikomcare.datas.models.PasienModel;
import com.pengdst.amikomcare.datas.models.PeriksaModel;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.adapters.GridAutofitLayoutManager;
import com.pengdst.amikomcare.ui.adapters.KeluhanAdapter;
import com.pengdst.amikomcare.ui.viewmodels.PeriksaViewModel;
import com.pengdst.amikomcare.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PeriksaFragment extends Fragment {
    String TAG = "PeriksaFragment";

    PasienDAO pasienDAO;

    List<PasienModel> pasiens;
    private List<ObatModel> obats;

    FragmentPeriksaBinding binding;
    KeluhanAdapter adapter;

    PeriksaViewModel viewModel;

    AntrianModel antrian;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();

    }

    private void initVariable() {
        antrian = PeriksaFragmentArgs.fromBundle(requireArguments()).getPasien();
        adapter = new KeluhanAdapter();
        adapter.setList(Objects.requireNonNull(antrian.getKeluhan()));
        pasienDAO = new PasienDAO();
        pasiens = pasienDAO.select();

        Log.e(TAG, "initVariable: "+pasiens.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initBinding(inflater.inflate(R.layout.fragment_periksa, container, false));
        initViewModel();
        fetchViewModel();

        return binding.getRoot();
    }

    private void fetchViewModel() {
        viewModel.fetch();
        viewModel.fetch(SessionDokter.init(getContext()).getDokter());
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PeriksaViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupComponent();
        setupRecyclerView();
        setupListener(view);
        observeViewModel();

    }

    private void observeViewModel() {
        viewModel.observeSingle().observe(getViewLifecycleOwner(), new Observer<PeriksaModel>() {
            @Override
            public void onChanged(PeriksaModel periksaModel) {
                pasiens = periksaModel.getPasien();

                pasienDAO.replaceAll(pasiens);
            }
        });
    }

    private void setupListener(View view) {
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DokterModel dokter = SessionDokter.init(getContext()).getDokter();
                String idPeriksa = dokter.getId();
                List<String> keluhan = antrian.getKeluhan();
                String catatan = binding.etCatatan.getText().toString();
                String penyakit = binding.etPenyakit.getText().toString();
                String obat = binding.etObat.getText().toString();
                String pasienId = antrian.getMahasiswa().getId();

                PeriksaModel periksaModel = new PeriksaModel();
                periksaModel.setId(idPeriksa);
                periksaModel.setDokter(dokter);

                        PasienModel pasienModel = new PasienModel();
                        pasienModel.setId(pasienId);
                        pasienModel.setMahasiswa(antrian.getMahasiswa());

                            DiagnosaModel diagnosa = new DiagnosaModel();
                            diagnosa.setKeluhan(keluhan);
                            diagnosa.setCatatan(catatan);
                            diagnosa.setObat(obats);
                            diagnosa.setPenyakit(penyakit);

                        pasienModel.setDiagnosa(diagnosa);

                    Log.e(TAG, "PasienModelId: "+pasienModel.getId());
                    Log.e(TAG, "Antrian: "+antrian);
                    pasienDAO.replace(pasienModel);

                periksaModel.setPasien(pasienDAO.select());

                viewModel.update(periksaModel);

                getActivity().onBackPressed();
            }
        });
    }

    private void setupComponent() {

        binding.textDate.setText(DateUtil.INSTANCE.getDate());
        binding.tvNamaPasien.setText(Objects.requireNonNull(antrian.getMahasiswa()).getNama());
        binding.tvJenisKelamin.setText(antrian.getMahasiswa().getJenisKelamin());

        Glide.with(binding.imageProfilePic)
                .load(antrian.getMahasiswa().getPhoto())
                .error(R.drawable.dummy_photo)
                .into(binding.imageProfilePic);

    }

    private void setupRecyclerView() {
        //Todo Premature Grid Autofit
        GridAutofitLayoutManager gridAutofitLayoutManager = new GridAutofitLayoutManager(getContext(), 48);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        gridAutofitLayoutManager.requestLayout();

        binding.rvKeluhan.setLayoutManager(gridAutofitLayoutManager);
        binding.rvKeluhan.setHasFixedSize(true);
        binding.rvKeluhan.setAdapter(adapter);

    }

    private void initBinding(View view) {
        binding = FragmentPeriksaBinding.bind(view);
    }
}