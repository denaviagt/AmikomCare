package com.pengdst.amikomcare.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentPeriksaBinding;
import com.pengdst.amikomcare.datas.daos.ObatDao;
import com.pengdst.amikomcare.datas.daos.PasienDao;
import com.pengdst.amikomcare.datas.models.AntrianModel;
import com.pengdst.amikomcare.datas.models.DiagnosaModel;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.datas.models.ObatModel;
import com.pengdst.amikomcare.datas.models.PasienModel;
import com.pengdst.amikomcare.datas.models.PeriksaModel;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.adapters.GridAutofitLayoutManager;
import com.pengdst.amikomcare.ui.adapters.KeluhanAdapter;
import com.pengdst.amikomcare.ui.viewmodels.PasienViewModel;
import com.pengdst.amikomcare.ui.viewmodels.PeriksaViewModel;
import com.pengdst.amikomcare.datas.viewstates.ObatListViewState;
import com.pengdst.amikomcare.datas.viewstates.PasienListViewState;
import com.pengdst.amikomcare.datas.viewstates.PasienViewState;
import com.pengdst.amikomcare.datas.viewstates.PeriksaViewState;
import com.pengdst.amikomcare.utils.DateUtil;

import java.util.List;
import java.util.Objects;

public class PeriksaFragment extends BaseMainFragment {

    private static final String TAG = "PeriksaFragment";

    private PasienDao pasienDao;
    private ObatDao obatDao;

    private List<PasienModel> pasiens;
    private List<ObatModel> obats;

    private FragmentPeriksaBinding binding;
    private KeluhanAdapter adapter;

    private PeriksaViewModel viewModelPeriksa;
    private PasienViewModel viewModelPasien;

    private AntrianModel antrian;
    private String idDokter;
    private String idMahasiswa;

    private void observeViewModel() {

        viewModelPeriksa.observeObatList().observe(getViewLifecycleOwner(), new Observer<ObatListViewState>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(ObatListViewState obatListViewState) {
                if (obatListViewState.isSucces()){
                    binding.etObat.setText(obatListViewState.getData()+"");
                }
            }
        });

        viewModelPeriksa.observePeriksa().observe(getViewLifecycleOwner(), new Observer<PeriksaViewState>() {
            @Override
            public void onChanged(PeriksaViewState periksaViewState) {
                if (periksaViewState.isSucces()){
                    Objects.requireNonNull(periksaViewState.getData());
                    pasiens = periksaViewState.getData().getPasien();

                    pasienDao.replaceAll(pasiens);
                }
            }
        });

        viewModelPasien.observeSinglePasien().observe(getViewLifecycleOwner(), new Observer<PasienViewState>() {
            @Override
            public void onChanged(PasienViewState pasienViewState) {
                if (pasienViewState.isSucces()){
                    DiagnosaModel diagnosa = Objects.requireNonNull(pasienViewState.getData()).getDiagnosa();
                    binding.etPenyakit.setText(Objects.requireNonNull(diagnosa).getPenyakit());
                    binding.etCatatan.setText(diagnosa.getPenyakit());
                }
            }
        });

        viewModelPasien.observePasienList().observe(getViewLifecycleOwner(), new Observer<PasienListViewState>() {
            @Override
            public void onChanged(PasienListViewState pasienListViewState) {
                binding.imageProfilePic.setWillNotDraw(pasienListViewState.getLoading());
                binding.imageProfilePic.setEnabled(pasienListViewState.getLoading());
            }
        });
    }

    private void setupListener() {
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DokterModel dokter = SessionDokter.init(getContext()).getDokter();
                String idPeriksa = dokter.getId();
                List<String> keluhan = antrian.getKeluhan();
                String catatan = binding.etCatatan.getText().toString();
                String penyakit = binding.etPenyakit.getText().toString();
                String obat = binding.etObat.getText().toString();
                obatDao.replace(new ObatModel("0",obat, "3x1", "Sebelum Makan"));
                String pasienId = antrian.getMahasiswa() != null ? antrian.getMahasiswa().getId() : "0";

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

                pasienDao.replace(pasienModel);

                periksaModel.setPasien(pasienDao.select());

                viewModelPeriksa.updatePeriksa(periksaModel);

                requireActivity().onBackPressed();
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
        // FIXME: 26/06/2020 Premature Grid Autofit
        GridAutofitLayoutManager gridAutofitLayoutManager = new GridAutofitLayoutManager(requireContext(), 48);
        @SuppressWarnings("unused") StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        gridAutofitLayoutManager.requestLayout();

        binding.rvKeluhan.setLayoutManager(gridAutofitLayoutManager);
        binding.rvKeluhan.setHasFixedSize(true);
        binding.rvKeluhan.setAdapter(adapter);

    }

    private void initBinding(View view) {
        binding = FragmentPeriksaBinding.bind(view);
    }

    private void initVariable() {

        antrian = PeriksaFragmentArgs.fromBundle(requireArguments()).getPasien();
        adapter = new KeluhanAdapter();
        adapter.setList(Objects.requireNonNull(antrian.getKeluhan()));

        pasienDao = new PasienDao();
        obatDao = new ObatDao();

        pasiens = pasienDao.select();
        obats = obatDao.select();

        idDokter = SessionDokter.init(getContext()).getId();
        idMahasiswa = Objects.requireNonNull(antrian.getMahasiswa()).getId();

    }

    private void fetchViewModel() {

        DokterModel dokter = SessionDokter.init(getContext()).getDokter();

        viewModelPeriksa.fetchPeriksaList();
        viewModelPeriksa.fetchPeriksa(dokter);

        viewModelPeriksa.fetchObatList(idDokter, idMahasiswa);

        viewModelPasien.fetchListPasien();
        viewModelPasien.fetchPasien(SessionDokter.init(getContext()).getId(), Objects.requireNonNull(Objects.requireNonNull(antrian.getMahasiswa()).getId()));

    }

    private void initViewModel() {

        viewModelPeriksa = new ViewModelProvider(this).get(PeriksaViewModel.class);
        viewModelPasien = new ViewModelProvider(this).get(PasienViewModel.class);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initBinding(inflater.inflate(R.layout.fragment_periksa, container, false));
        initViewModel();
        fetchViewModel();

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupComponent();
        setupRecyclerView();
        setupListener();
        observeViewModel();

    }
}