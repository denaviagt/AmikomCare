package com.pengdst.amikomcare.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentHomeBinding;
import com.pengdst.amikomcare.datas.models.AntrianModel;
import com.pengdst.amikomcare.ui.adapters.AntrianAdapter;
import com.pengdst.amikomcare.ui.viewmodels.MahasiswaViewModel;

import java.util.List;

public class HomeFragment extends Fragment {
    private MahasiswaViewModel viewModel;
    private AntrianAdapter adapter;
    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

//    public static HomeFragment newInstance(String param1, String param2) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupBinding(view);
        initAdapter();
        initViewModel();
        fetchViewModel();

        return binding.getRoot();
    }

    private void setupBinding(View view) {
        binding = FragmentHomeBinding.bind(view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        observeViewModel();

        setupListener(view);
    }

    private void setupListener(View view) {
        binding.btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_homeFragment_to_profileFragment);
            }
        });

        binding.btStatistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_homeFragment_to_statistikDokterFragment);
            }
        });

        binding.btPasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_homeFragment_to_listPasienFragment);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getLiveDataAntrian().observe(getViewLifecycleOwner(), new Observer<List<AntrianModel>>() {
            @Override
            public void onChanged(List<AntrianModel> antrianModels) {
                adapter.setList(antrianModels);
            }
        });
    }

    private void setupRecyclerView() {

        binding.rvAntrian.setAdapter(adapter);

    }

    private void fetchViewModel() {
        viewModel.fetchAntrian();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MahasiswaViewModel.class);
    }

    private void initAdapter() {
        adapter = new AntrianAdapter();
    }

}