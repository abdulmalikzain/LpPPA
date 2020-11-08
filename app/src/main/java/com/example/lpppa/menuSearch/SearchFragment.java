
package com.example.lpppa.menuSearch;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.lpppa.R;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SearchFragment extends Fragment {

    private ImageView ivCari;
    private int choosenYear = 2020;
    private TextView tv_tahun;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ivCari = view.findViewById(R.id.iv_cari);
        tv_tahun = view.findViewById(R.id.tv_tahun);

        tv_tahun.setOnClickListener(v -> {
            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(), new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {
                    tv_tahun.setText(Integer.toString(selectedYear));

                    choosenYear = selectedYear;
                }
            }, choosenYear, 0);

            builder.showYearOnly()
                    .setYearRange(2010, 2030)
                    .build()
                    .show();
        });
        return view;
    }


}
