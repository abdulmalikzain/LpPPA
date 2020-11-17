package com.example.lpppa.activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.lpppa.R;
import com.example.lpppa.adapter.AdapterListTahun;
import com.example.lpppa.api.ApiService;
import com.example.lpppa.api.RetrofitClient;
import com.example.lpppa.models.ListTahun;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KdrtActivity extends AppCompatActivity {

    private LineChart lineChart;
    private AVLoadingIndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kdrt);
        lineChart = findViewById(R.id.chart_bar);
        indicatorView = findViewById(R.id.av_kdrt);

        lineChart.setVisibility(View.INVISIBLE);
        getData();
    }

    private void getData(){
        ApiService mApiService = RetrofitClient.getRetroPenyidik();
        mApiService.getPenyidik("read","indexsheet").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray jsonArray  = object.optJSONArray("indexsheet");

                    ArrayList<Entry> yValues = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        float indexSheet = Float.parseFloat(jsonObject.optString("sheetnames"));
                        float kdrt = Float.parseFloat(jsonObject.optString("kdrt"));

                        yValues.add(new Entry(indexSheet,kdrt));
                    }
                    linechartx(yValues);

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void linechartx(ArrayList<Entry> yValues){
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setVisibility(View.VISIBLE);
        indicatorView.hide();
        LineDataSet dataSet = new LineDataSet(yValues, "Data Set");
        dataSet.setFillAlpha(110);
        dataSet.setColor(Color.RED);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextColor(Color.BLUE);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
    }
}