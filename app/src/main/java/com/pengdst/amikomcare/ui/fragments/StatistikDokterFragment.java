package com.pengdst.amikomcare.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentStatistikDokterBinding;
import com.pengdst.amikomcare.datas.daos.PasienDao;
import com.pengdst.amikomcare.datas.models.PasienModel;
import com.pengdst.amikomcare.datas.states.PasienListState;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.viewmodels.StatistikViewModel;
import com.pengdst.amikomcare.utils.ChartUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static com.pengdst.amikomcare.datas.constants.ResultKt.RESULT_SUCCESS;

public class StatistikDokterFragment extends BaseMainFragment {
    @SuppressWarnings("unused")
    private static final String TAG = "StatistikDokterFragment";

    private Map<String, Float> dataPenyakit = new HashMap<>();

    private FragmentStatistikDokterBinding binding;

    private void initVariavle() {
        sessionDokter = SessionDokter.init(getContext());
        pasienDao = new PasienDao();
    }

    private void fetchViewModel() {
        statistikViewModel.fetchPasienList(sessionDokter.getId());
    }

    private void observeViewModel() {
        statistikViewModel.observePasienList().observe(getViewLifecycleOwner(), new Observer<PasienListState>() {
            @Override
            public void onChanged(PasienListState pasienListState) {
                if (pasienListState.getStatus() == RESULT_SUCCESS){
                    pasienDao.replaceAll(pasienListState.getData());

                    Map<String, List<PasienModel>> filter = pasienDao.filterCategory();

                    filter.forEach(new BiConsumer<String, List<PasienModel>>() {
                        @Override
                        public void accept(String groupCategory, List<PasienModel> data) {
                            dataPenyakit.put(groupCategory, (float) data.size());
                        }
                    });

                    PieData pieData = ChartUtil.INSTANCE.createPieData(dataPenyakit.entrySet(), "");

                    binding.chartStatistic.setData(pieData);

                    String pasienSize = String.valueOf(pasienDao.select().size());
                    binding.tvTotalPasien.setText(pasienSize);
                }
            }
        });
    }

    private void barChart(){
//        binding.chartStatistic.setDrawBarShadow(true);
//        binding.chartStatistic.setDrawValueAboveBar(true);
//        binding.chartStatistic.setMaxVisibleValueCount(50);
//        binding.chartStatistic.setPinchZoom(false);
//        binding.chartStatistic.setDrawGridBackground(true);

//        binding.chartStatistic.setData(barData);
    }

    private void lineChart(){
//        binding.chartStatistic.setDragEnabled(true);
//        binding.chartStatistic.setScaleEnabled(false);

//        binding.chartStatistic.setData(barData);
    }

    private void setupChart() {
        PieChart pieChart = binding.chartStatistic;
        pieChart.getDescription();
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.animateY(1000, Easing.Linear);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);

        Description description = new Description();
        description.setText("Data Penyakit");
        description.setTextSize(22);
    }


    private void setupComponent() {
    }

    private void initBinding(View view) {
        binding = FragmentStatistikDokterBinding.bind(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariavle();
        initViewModel();

    }

    private void initViewModel() {
        statistikViewModel = new ViewModelProvider(this).get(StatistikViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistik_dokter, container, false);
        initBinding(view);
        fetchViewModel();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupComponent();
        setupChart();
        observeViewModel();

    }
}