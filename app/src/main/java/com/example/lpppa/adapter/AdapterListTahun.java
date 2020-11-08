package com.example.lpppa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lpppa.R;
import com.example.lpppa.activity.DataActivity;
import com.example.lpppa.activity.DetailPenyidikActivity;
import com.example.lpppa.models.ListTahun;
import com.example.lpppa.models.Penyidik;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListTahun extends RecyclerView.Adapter<AdapterListTahun.MyListTahunViewHolder> {
    private List<ListTahun> listTahuns;
    private Context context;

    public AdapterListTahun(Context context, List<ListTahun> arraylist) {
        this.listTahuns = arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterListTahun.MyListTahunViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_itemtahun, parent, false);

        return new AdapterListTahun.MyListTahunViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListTahun.MyListTahunViewHolder holder, int position) {
        ListTahun listTahun = listTahuns.get(position);
        holder.tvItemtahun.setText(listTahun.getTahun());
        holder.tvAduan.setText(listTahun.getJenisLaporan());
    }

    @Override
    public int getItemCount() {
        return listTahuns.size();
    }

    public class MyListTahunViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemtahun, tvAduan;
        private LinearLayout llitemtahun;
        private int post;
        public MyListTahunViewHolder(View itemView) {
            super(itemView);
            tvAduan = itemView.findViewById(R.id.tv_itemaduan);
            tvItemtahun  = itemView.findViewById(R.id.tv_itemtahun);
            llitemtahun = itemView.findViewById(R.id.ll_itemtahun);
            llitemtahun.setOnClickListener(v -> {
                post = getAdapterPosition();
                Intent intent = new Intent(context, DataActivity.class);
                intent.putExtra("tahun", tvItemtahun.getText().toString().trim());
                intent.putExtra("jenis", tvAduan.getText().toString().trim());
                context.startActivity(intent);
            });
        }
    }
}
