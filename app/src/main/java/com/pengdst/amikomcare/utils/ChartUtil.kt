package com.pengdst.amikomcare.utils

import android.graphics.Color
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*

object ChartUtil {

    fun createPieData(groups: MutableSet<MutableMap.MutableEntry<String, Float>>, label: String): PieData {

        val yValues = ArrayList<PieEntry>()

        for ((groupKey, groupValue) in groups) {
            yValues.add(PieEntry(groupValue, groupKey))
        }

        val dataSet = PieDataSet(yValues, label)
        dataSet.sliceSpace = 9f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val pieData = PieData()
        pieData.dataSet = dataSet
        pieData.setValueTextSize(10f)
        pieData.setValueTextColor(Color.WHITE)

        return pieData
    }

    fun createLineData(@Suppress("SpellCheckingInspection") datas: MutableSet<MutableMap.MutableEntry<Float, Float>>, label: String): LineData {

        val yValues: ArrayList<Entry> = ArrayList()

        for ((xValue, yValue) in datas) {
            yValues.add(Entry(xValue, yValue))
        }

        val set = LineDataSet(yValues, label)

        set.fillAlpha = 110

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(set)

        return LineData(dataSets)
    }

    fun createBarData(groups: MutableSet<MutableMap.MutableEntry<Float, Float>>, label: String): BarData {

        val yValues = ArrayList<BarEntry>()

        for ((xValue, yValue) in groups) {
            yValues.add(BarEntry(yValue, xValue))
        }

        val set = BarDataSet(yValues, label)
        set.colors = ColorTemplate.MATERIAL_COLORS.asList()

        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(set)

        val barData = BarData(dataSets)
        barData.barWidth = 0.9f

        return barData
    }
}