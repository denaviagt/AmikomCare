package com.pengdst.amikomcare.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.pengdst.amikomcare.ui.viewmodels.HomeViewModel;
import com.pengdst.amikomcare.datas.states.AntrianListState;
import com.pengdst.amikomcare.utils.DateUtil;
import com.pengdst.amikomcare.utils.GoogleSignInUtil;

import org.jetbrains.annotations.NotNull;

import static com.pengdst.amikomcare.datas.constants.ResultKt.RESULT_ERROR;
import static com.pengdst.amikomcare.datas.constants.ResultKt.RESULT_SUCCESS;

public class HomeFragment extends BaseMainFragment implements RecyclerViewCallback {

    private FragmentHomeBinding binding;

    private void logout() {
        sessionDokter.logout();
        signInUtil.signOut();
        homeViewModel.logout();
    }

    @SuppressWarnings("ConstantConditions")
    private void initVariable() {

        sessionDokter = SessionDokter.init(getContext());
        signInUtil = new GoogleSignInUtil().init(getActivity());

    }

    private void setupViewComponent() {
        binding.textDate.setText(DateUtil.INSTANCE.getDate());
        binding.tvNamaDokter.setText(sessionDokter.getNama());

        Glide.with(binding.imageProfilePic)
                .load(sessionDokter.getPhoto())
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
        NavHostFragment.findNavController(requireParentFragment()).navigate(target);
    }

    private void observeViewModel() {
        homeViewModel.observeAntrianList().observe(getViewLifecycleOwner(), new Observer<AntrianListState>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onChanged(AntrianListState antrianListState) {

                if (antrianListState.getStatus() == RESULT_SUCCESS){
                    antrianAdapter.setList(antrianListState.getData());
                    binding.rvAntrian.setAdapter(antrianAdapter);
                }
                else if (antrianListState.getStatus() == RESULT_ERROR){
                    shortToast(antrianListState.getMessage());
                }

            }
        });
    }

    private void setupRecyclerView() {

        binding.rvAntrian.setAdapter(antrianAdapter);

    }

    private void fetchViewModel() {
        homeViewModel.fetchAntrianList();
    }

    private void initViewModel() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void initAdapter() {
        antrianAdapter = new AntrianAdapter(this);
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