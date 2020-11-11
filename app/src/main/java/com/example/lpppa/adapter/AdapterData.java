package com.example.lpppa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lpppa.R;
import com.example.lpppa.activity.DetailDataActivity;
import com.example.lpppa.activity.DetailPenyidikActivity;
import com.example.lpppa.models.ItemList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterData extends RecyclerView.Adapter<AdapterData.MyDataViewHolder> {
    private List<ItemList> itemLists;
    private Context context;

    public AdapterData(Context context, List<ItemList> arraylist) {
        this.itemLists = arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterData.MyDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_data, parent, false);

        return new AdapterData.MyDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterData.MyDataViewHolder holder, int position) {
        ItemList itemList = itemLists.get(position);
        holder.tvNolp.setText(itemList.getNoLp());
        holder.tvPerkembangan.setText(itemList.getPerkembangan());
        holder.tvNamaPelapor.setText(itemList.getNamapelapor());
        holder.tvNamaPenyidik.setText(itemList.getNamapenyidik());
        holder.tvTahun.setText(itemList.getTahun());
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class MyDataViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPerkembangan, tvNolp, tvNamaPelapor, tvNamaPenyidik, tvTahun;
        private LinearLayout llItemData;
        private int post;
        public MyDataViewHolder(View itemView) {
            super(itemView);

            tvPerkembangan  = itemView.findViewById(R.id.tv_perkembangan);
            tvNolp  = itemView.findViewById(R.id.tv_ldnolp);
            tvTahun = itemView.findViewById(R.id.tv_ldtahun);
            tvNamaPelapor  = itemView.findViewById(R.id.tv_ldpelapor);
            tvNamaPenyidik = itemView.findViewById(R.id.tv_ldpenyidik);
            llItemData = itemView.findViewById(R.id.ll_listdata);
            llItemData.setOnClickListener(v -> {
                post = getAdapterPosition();
                Intent intent = new Intent(context, DetailDataActivity.class);
                intent.putExtra("nolp", tvNolp.getText().toString().trim());
                intent.putExtra("tahun", tvTahun.getText().toString().trim());
                context.startActivity(intent);
            });
        }
    }

}
