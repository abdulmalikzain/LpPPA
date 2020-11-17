package com.example.lpppa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lpppa.R;
import com.example.lpppa.activity.DetailDataActivity;
import com.example.lpppa.activity.DownloadUUActivity;
import com.example.lpppa.models.ItemList;
import com.example.lpppa.models.UndangUndang;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterUU extends RecyclerView.Adapter<AdapterUU.MyUUViewHolder> {
    private List<UndangUndang> itemLists;
    private Context context;

    public AdapterUU(Context context, List<UndangUndang> arraylist) {
        this.itemLists = arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterUU.MyUUViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_uu, parent, false);
        return new AdapterUU.MyUUViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUU.MyUUViewHolder holder, int position) {
        UndangUndang itemList = itemLists.get(position);
        holder.Uu.setText(itemList.getUu());
        holder.link.setText(itemList.getLink());
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class MyUUViewHolder extends RecyclerView.ViewHolder {
        private TextView Uu, link;
        private Button btndownload;
        private int post;
        public MyUUViewHolder(View itemView) {
            super(itemView);

            Uu  = itemView.findViewById(R.id.tv_listnamauu);
            link  = itemView.findViewById(R.id.tv_listlink);
            btndownload = itemView.findViewById(R.id.btn_listdownloaduu);
            btndownload.setOnClickListener(v -> {
                post = getAdapterPosition();
                Intent intent = new Intent(context, DownloadUUActivity.class);
                intent.putExtra("link", link.getText().toString().trim());
                intent.putExtra("uu", Uu.getText().toString());
                context.startActivity(intent);
            });
        }
    }
}
