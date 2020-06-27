package com.pengdst.amikomcare.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.pengdst.amikomcare.datas.daos.ObatDao;
import com.pengdst.amikomcare.datas.daos.PasienDao;
import com.pengdst.amikomcare.datas.models.AntrianModel;
import com.pengdst.amikomcare.datas.models.DiagnosaModel;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.datas.models.ObatModel;
import com.pengdst.amikomcare.datas.models.PasienModel;
import com.pengdst.amikomcare.datas.models.PeriksaModel;
import com.pengdst.amikomcare.datas.states.AntrianListState;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.adapters.GridAutofitLayoutManager;
import com.pengdst.amikomcare.ui.adapters.KeluhanAdapter;
import com.pengdst.amikomcare.ui.viewmodels.PeriksaViewModel;
import com.pengdst.amikomcare.datas.states.PeriksaState;
import com.pengdst.amikomcare.utils.DateUtil;

import java.util.List;
import java.util.Objects;

import static com.pengdst.amikomcare.datas.constants.ResultKt.RESULT_ERROR;
import static com.pengdst.amikomcare.datas.constants.ResultKt.RESULT_SUCCESS;

public class PeriksaFragment extends BaseMainFragment {

    private static final String TAG = "PeriksaFragment";

    private FragmentPeriksaBinding binding;

    private List<PasienModel> pasiens;
    private List<ObatModel> obats;

    private AntrianModel antrian;
    private String idDokter;
    private String idMahasiswa;

    private void observeViewModel() {

        periksaViewModel.observeAntrianList().observe(getViewLifecycleOwner(), new Observer<AntrianListState>() {
            @Override
            public void onChanged(AntrianListState antrianListState) {
                Log.e(TAG, "onChanged() called with: antrianListViewState = [" + antrianListState + "]");
                if (antrianListState.getStatus() == RESULT_SUCCESS){
                    longToast(antrianListState.getMessage());
                }
                else if (antrianListState.getStatus() == RESULT_ERROR){
                    longToast(antrianListState.getMessage());
                }
            }
        });

        periksaViewModel.observePeriksa().observe(getViewLifecycleOwner(), new Observer<PeriksaState>() {
            @Override
            public void onChanged(PeriksaState periksaState) {
                if (periksaState.getStatus() == RESULT_SUCCESS){
                    Objects.requireNonNull(periksaState.getData());
                    pasiens = periksaState.getData().getPasien();

                    pasienDao.replaceAll(pasiens);
                }
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

                periksaViewModel.updatePeriksa(periksaModel);

                periksaViewModel.deleteAntrian(antrian.getId());

                requireActivity().onBackPressed();
            }
        });
    }

    private void setupComponent() {

        binding.textDate.setText(DateUtil.INSTANCE.getDate());
        binding.tvNamaPasien.setText(Objects.requireNonNull(Objects.requireNonNull(antrian.getMahasiswa()).getNama()));
        binding.tvNimPasien.setText(antrian.getMahasiswa().getNim());

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
        binding.rvKeluhan.setAdapter(keluhanAdapter);

    }

    private void initBinding(View view) {
        binding = FragmentPeriksaBinding.bind(view);
    }

    private void initVariable() {

        antrian = PeriksaFragmentArgs.fromBundle(requireArguments()).getPasien();
        keluhanAdapter = new KeluhanAdapter();
        keluhanAdapter.setList(Objects.requireNonNull(antrian.getKeluhan()));

        pasienDao = new PasienDao();
        obatDao = new ObatDao();

        pasiens = pasienDao.select();
        obats = obatDao.select();

        idDokter = SessionDokter.init(getContext()).getId();
        idMahasiswa = Objects.requireNonNull(antrian.getMahasiswa()).getId();

    }

    private void fetchViewModel() {

        DokterModel dokter = sessionDokter.getDokter();

        periksaViewModel.fetchPeriksaList();
        periksaViewModel.fetchPeriksa(dokter);

        periksaViewModel.fetchObatList(idDokter, idMahasiswa);

        periksaViewModel.fetchListPasien();
        periksaViewModel.fetchPasien(SessionDokter.init(getContext()).getId(), Objects.requireNonNull(Objects.requireNonNull(antrian.getMahasiswa()).getId()));

    }

    private void initViewModel() {

        periksaViewModel = new ViewModelProvider(this).get(PeriksaViewModel.class);
        sessionDokter = SessionDokter.init(getContext());

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