package com.pengdst.amikomcare.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentHomeBinding;
import com.pengdst.amikomcare.datas.models.AntrianModel;
import com.pengdst.amikomcare.datas.models.PeriksaModel;
import com.pengdst.amikomcare.listeners.RecyclerViewCallback;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.adapters.AntrianAdapter;
import com.pengdst.amikomcare.ui.fragments.HomeFragmentDirections.ActionHomeFragmentToPeriksaFragment;
import com.pengdst.amikomcare.ui.viewmodels.LoginViewModel;
import com.pengdst.amikomcare.ui.viewmodels.MahasiswaViewModel;
import com.pengdst.amikomcare.ui.viewstates.AntrianListViewState;
import com.pengdst.amikomcare.utils.DateUtil;
import com.pengdst.amikomcare.utils.GoogleSignInUtil;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment implements RecyclerViewCallback {

    private FragmentHomeBinding binding;
    private MahasiswaViewModel viewModel;
    private AntrianAdapter adapter;

    private SessionDokter session;
    private Fragment parentFragment;

    private LoginViewModel loginViewModel;

    private GoogleSignInUtil signInUtil;

    private void logout() {
        session.logout();
        loginViewModel.logout();
        signInUtil.signOut();
    }

    @SuppressWarnings("ConstantConditions")
    private void initVariable() {

        parentFragment = getParentFragment();
        session = SessionDokter.init(getContext());
        signInUtil = new GoogleSignInUtil().init(getActivity());

    }

    private void setupViewComponent() {
        binding.textDate.setText(DateUtil.INSTANCE.getDate());
        binding.tvNamaDokter.setText(session.getNama());

        Glide.with(binding.imageProfilePic)
                .load(session.getPhoto())
                .error(R.drawable.dummy_photo)
                .into(binding.imageProfilePic);
    }

    private void setupBinding(View view) {
        binding = FragmentHomeBinding.bind(view);
    }

    private void setupListener() {
        binding.btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(R.id.action_homeFragment_to_profileFragment);
            }
        });

        binding.btStatistik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigate(R.id.action_homeFragment_to_statistikDokterFragment);
            }
        });

        binding.btPasien.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.action_homeFragment_to_listPasienFragment);
            }
        });

        binding.btProfile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                logout();
                navigate(R.id.action_homeFragment_to_loginFragment);
                return false;
            }
        });
    }

    private void navigate(int target) {
        NavHostFragment.findNavController(parentFragment).navigate(target);
    }

    private void observeViewModel() {
        viewModel.observeAntrianList().observe(getViewLifecycleOwner(), new Observer<AntrianListViewState>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onChanged(AntrianListViewState antrianListViewState) {
                if (antrianListViewState.isSucces()){
                    adapter.setList(antrianListViewState.getData());
                    binding.rvAntrian.setAdapter(adapter);
                }
            }
        });
    }

    private void setupRecyclerView() {

        binding.rvAntrian.setAdapter(adapter);

    }

    private void fetchViewModel() {
        viewModel.fetchAntrianList();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MahasiswaViewModel.class);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void initAdapter() {
        adapter = new AntrianAdapter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();

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
        setupListener();

    }

    @Override
    public void onItemClick(@NotNull View view, @NotNull AntrianModel antrian) {
        if (view.getId() == R.id.container_item) {
            ActionHomeFragmentToPeriksaFragment action = HomeFragmentDirections.actionHomeFragmentToPeriksaFragment();
            action.setPasien(antrian);
            action.setPeriksa(new PeriksaModel());

            Navigation.findNavController(view).navigate(action);
        }
    }
}