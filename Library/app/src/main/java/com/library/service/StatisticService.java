package com.library.service;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.library.service.api.StatisticAPIService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticService {
    public static void graphBookType(PieChart chart) {
        StatisticAPIService.service.graphBookType().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                if (response.body() != null) {
                    ArrayList<PieEntry> pieEntries = new ArrayList<>();
                    response.body().forEach(e -> {
                        JsonObject data = e.getAsJsonObject();
                        pieEntries.add(new PieEntry(data.get("count").getAsFloat(), data.get("category").getAsString()));
                    });

                    PieDataSet dataSet = new PieDataSet(pieEntries, "");
                    dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                    PieData data = new PieData(dataSet);
                    data.setValueTextSize(20);

                    chart.setData(data);

                    chart.getDescription().setEnabled(false);
                    chart.setEntryLabelTextSize(15);
                    chart.setDrawHoleEnabled(false);
                    chart.setMaxHighlightDistance(0);
                    chart.setEntryLabelColor(Color.BLACK);
                    chart.setUsePercentValues(true);

                    Legend legend = chart.getLegend();
                    legend.setTextSize(20);
                    legend.setOrientation(Legend.LegendOrientation.VERTICAL);
                    legend.setYOffset(20f);
                    legend.setTextColor(Color.BLACK);

                    chart.notifyDataSetChanged();
                    chart.invalidate();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {

            }
        });
    }

    public static void graphBookSell(BarChart chart) {
        StatisticAPIService.service.graphBookSell().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                if (response.body() != null) {
                    String[] MONTHS = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                            "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};

                    ArrayList<BarEntry> barEntries = new ArrayList<>();
                    response.body().forEach(e -> {
                        JsonObject data = e.getAsJsonObject();
                        barEntries.add(new BarEntry(data.get("month").getAsInt(), data.get("total").getAsFloat()));
                    });

                    BarDataSet barDataSet = new BarDataSet(barEntries, "Date set 1");

                    BarData data = new BarData(barDataSet);
                    data.setBarWidth(0.9f);
                    data.setValueTextColor(Color.BLACK);
                    chart.setData(data);

                    ValueFormatter formatter = new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return MONTHS[(int) value];
                        }
                    };

                    XAxis xAxis = chart.getXAxis();
                    xAxis.setValueFormatter(formatter);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextColor(Color.BLACK);
                    xAxis.setGranularityEnabled(true);

                    chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(MONTHS));

                    chart.getLegend().setEnabled(false);

                    chart.getDescription().setEnabled(false);
                    chart.setDrawBarShadow(false);
                    chart.setDrawValueAboveBar(true);
                    chart.setAutoScaleMinMaxEnabled(true);

                    chart.getAxisLeft().setTextColor(Color.BLACK);
                    chart.getAxisRight().setTextColor(Color.BLACK);
                    chart.getDescription().setTextColor(Color.BLACK);

                    chart.notifyDataSetChanged();
                    chart.invalidate();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {

            }
        });
    }

    public static void graphBookBorrow(TableLayout tblBookTop) {
        StatisticAPIService.service.graphBookBorrow().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                if (response.body() != null) {
                    Context context = tblBookTop.getContext();
                    response.body().forEach(e -> {
                        JsonObject data = e.getAsJsonObject();
                        TableRow row = new TableRow(context);

                        TextView tvRank = new TextView(context);
                        tvRank.setText(data.get("rank").getAsString());
                        tvRank.setGravity(Gravity.CENTER);
                        tvRank.setTextSize(18);

                        TextView tvTitle = new TextView(context);
                        tvTitle.setText(data.get("title").getAsString());
                        tvTitle.setTextSize(18);

                        TextView tvCount = new TextView(context);
                        tvCount.setText(data.get("count").getAsString());
                        tvCount.setGravity(Gravity.CENTER);
                        tvCount.setTextSize(18);

                        row.addView(tvRank);
                        row.addView(tvTitle);
                        row.addView(tvCount);
                        TableRow.LayoutParams params = new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 10, 0, 10);
                        row.setLayoutParams(params);

                        tblBookTop.addView(row);
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {

            }
        });
    }
}
