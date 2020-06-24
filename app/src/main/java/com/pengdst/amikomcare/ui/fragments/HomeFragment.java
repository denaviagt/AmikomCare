package com.pengdst.amikomcare.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentHomeBinding;
import com.pengdst.amikomcare.datas.models.AntrianModel;
import com.pengdst.amikomcare.datas.models.PasienModel;
import com.pengdst.amikomcare.datas.models.PeriksaModel;
import com.pengdst.amikomcare.listeners.RecyclerViewCallback;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.adapters.AntrianAdapter;
import com.pengdst.amikomcare.ui.fragments.HomeFragmentDirections.ActionHomeFragmentToPeriksaFragment;
import com.pengdst.amikomcare.ui.viewmodels.MahasiswaViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RecyclerViewCallback {
    private String TAG = "HomeFragment";

    private FragmentHomeBinding binding;
    private MahasiswaViewModel viewModel;
    private AntrianAdapter adapter;

    private SessionDokter session;
    private Fragment parentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();

    }

    private void initVariable() {

        parentFragment = getParentFragment();
        session = SessionDokter.init(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupBinding(view);
        initAdapter();
        initViewModel();
        fetchViewModel();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewComponent();
        setupRecyclerView();
        observeViewModel();
        setupListener(view);

    }

    private void setupViewComponent() {
        binding.tvNamaDokter.setText(session.getNama());

        Glide.with(binding.imageProfilePic)
                .load(session.getPhoto())
                .error(R.drawable.dummy_photo)
                .into(binding.imageProfilePic);
    }

    private void setupBinding(View view) {
        binding = FragmentHomeBinding.bind(view);
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

        binding.btProfile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                session.logout();
                navigate(R.id.action_homeFragment_to_loginFragment);
                return false;
            }
        });
    }

    private void navigate(int target) {
        NavHostFragment.findNavController(parentFragment).navigate(target);
    }

    private void observeViewModel() {
        viewModel.observeAntrian().observe(getViewLifecycleOwner(), new Observer<List<AntrianModel>>() {
            @Override
            public void onChanged(List<AntrianModel> antrianModels) {
                adapter.setList(antrianModels);
                binding.rvAntrian.setAdapter(adapter);
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
        adapter = new AntrianAdapter(this);
    }

    @Override
    public void onItemClick(@NotNull View view, @NotNull AntrianModel antrian) {
        switch (view.getId()){
            case R.id.container_item:
                ActionHomeFragmentToPeriksaFragment action = HomeFragmentDirections.actionHomeFragmentToPeriksaFragment();
                action.setPasien(antrian);
                action.setPeriksa(new PeriksaModel());

                Navigation.findNavController(view).navigate(action);
                break;
            default:
                break;
        }
    }
}