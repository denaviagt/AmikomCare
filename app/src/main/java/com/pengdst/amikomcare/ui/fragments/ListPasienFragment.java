package com.pengdst.amikomcare.ui.fragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentListPasienBinding;
import com.pengdst.amikomcare.datas.models.AntrianModel;
import com.pengdst.amikomcare.datas.states.PasienListState;
import com.pengdst.amikomcare.listeners.RecyclerViewCallback;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.adapters.AntrianAdapter;
import com.pengdst.amikomcare.ui.adapters.PasienAdapter;
import com.pengdst.amikomcare.ui.viewmodels.PasienViewModel;

import org.jetbrains.annotations.NotNull;

import static com.pengdst.amikomcare.datas.constants.ResultKt.RESULT_SUCCESS;

// FIXME: 26/06/2020 Remove Unused Variable
public class ListPasienFragment extends BaseMainFragment {
    @SuppressWarnings("unused")
    private static final String TAG = "ListPasienFragment";

    FragmentListPasienBinding binding;

    private void initVariable() {
        sessionDokter = SessionDokter.init(getContext());
    }

    private void initAdapter() {
        pasienAdapter = new PasienAdapter();
    }

    private void setupRecyclerView() {
        binding.recyclerView.setAdapter(pasienAdapter);
    }

    private void initViewModel() {
        pasienViewModel = new ViewModelProvider(this).get(PasienViewModel.class);
    }

    private void fetchViewModel() {
        pasienViewModel.fetchPasienList(sessionDokter.getId());
    }

    private void observeViewModel() {
        pasienViewModel.getObservePasienList().observe(getViewLifecycleOwner(), new Observer<PasienListState>() {
            @Override
            public void onChanged(PasienListState pasienListState) {
                if (pasienListState.getStatus() == RESULT_SUCCESS){
                    pasienAdapter.setList(pasienListState.getData());
                }
            }
        });
    }

    private void setupBinding(View view) {
        binding = FragmentListPasienBinding.bind(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_list_pasien, container, false);
        setupBinding(view);
        initAdapter();
        initViewModel();
        fetchViewModel();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeViewModel();
        setupRecyclerView();

    }

}