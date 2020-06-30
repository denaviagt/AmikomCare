package com.pengdst.amikomcare.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentStatistikDokterBinding;
import com.pengdst.amikomcare.datas.daos.PasienDao;
import com.pengdst.amikomcare.datas.models.PasienModel;
import com.pengdst.amikomcare.datas.states.PasienListState;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.viewmodels.StatistikViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.pengdst.amikomcare.datas.constants.ResultKt.RESULT_SUCCESS;

public class StatistikDokterFragment extends BaseMainFragment {
    @SuppressWarnings("unused")
    private static final String TAG = "StatistikDokterFragment";

    Map<Float, String> dataPenyakit = new HashMap<>();

    FragmentStatistikDokterBinding binding;

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
                    Map<String, List<PasienModel>> filter = pasienDao.filterBy();

                    for (Map.Entry<String, List<PasienModel>> pasiens : filter.entrySet()) {
                        dataPenyakit.put((float) pasiens.getValue().size(), pasiens.getKey());
                    }

                    Log.e(TAG, "onChanged() called with: pasienListState = [" + dataPenyakit + "]");

                    setupChart(dataPenyakit);

                    String pasienSize = String.valueOf(pasienDao.select().size());
                    binding.tvTotalPasien.setText(pasienSize);
                }
            }
        });
    }

    private void lineBarChart(){
        /*Line Chart*/

//        binding.chartStatistic.setDragEnabled(true);
//        binding.chartStatistic.setScaleEnabled(false);

//        ArrayList<Entry> yValues = new ArrayList<>();
//
//        yValues.add(new Entry(0, 60f));
//        yValues.add(new Entry(1, 30f));
//        yValues.add(new Entry(2, 100f));
//
//        LineDataSet set = new LineDataSet(yValues, "Data set 1");
//
//        set.setFillAlpha(110);
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(set);
//
//        LineData lineData = new LineData(dataSets);

        /*Bar Chart*/
//        binding.chartStatistic.setDrawBarShadow(true);
//        binding.chartStatistic.setDrawValueAboveBar(true);
//        binding.chartStatistic.setMaxVisibleValueCount(50);
//        binding.chartStatistic.setPinchZoom(false);
//        binding.chartStatistic.setDrawGridBackground(true);
//
//        ArrayList<BarEntry> yValues = new ArrayList<>();
//
//        yValues.add(new BarEntry(0, 60f));
//        yValues.add(new BarEntry(1, 30f));
//        yValues.add(new BarEntry(2, 100f));
//
//        BarDataSet set = new BarDataSet(yValues, "Data set 1");
//        set.setColors(ColorTemplate.MATERIAL_COLORS);
//
//        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//        dataSets.add(set);
//
//        BarData barData = new BarData(dataSets);
//        barData.setBarWidth(0.9f);
//
//        binding.chartStatistic.setData(barData);
    }

    private void setupChart(Map<Float, String> dataPenyakit) {
        binding.chartStatistic.setUsePercentValues(true);
        binding.chartStatistic.getDescription();
        binding.chartStatistic.setExtraOffsets(5, 10, 5, 5);

        binding.chartStatistic.animateY(1000, Easing.Linear);

        binding.chartStatistic.setDragDecelerationFrictionCoef(0.95f);
        binding.chartStatistic.setDrawHoleEnabled(true);
        binding.chartStatistic.setHoleColor(Color.WHITE);
        binding.chartStatistic.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(32f, "Diabetes"));
        yValues.add(new PieEntry(12f, "Flue"));
        yValues.add(new PieEntry(44f, "Batuk"));

        Description description = new Description();
        description.setText("This is Hobbies");
        description.setTextSize(14);

        binding.chartStatistic.setDescription(description);

        PieDataSet dataSet = new PieDataSet(yValues, "Hobbies");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData pieData = new PieData();
        pieData.setDataSet(dataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.BLACK);

        binding.chartStatistic.setData(pieData);
    }


    private void setupComponent() {
//        binding.chartStatistic.setOnChartGestureListener(new );
//        binding.chartStatistic.setOnChartValueSelectedListener(new );
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
        observeViewModel();

    }
}