package com.example.lpppa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lpppa.R;
import com.example.lpppa.activity.DetailPenyidikActivity;
import com.example.lpppa.models.Penyidik;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPenyidik extends RecyclerView.Adapter<AdapterPenyidik.MyPenyidikViewHolder> {
    private List<Penyidik> my_penyidik;
    private Context context;
    private String urlImagedefault = "https://drive.google.com/uc?export=view&id=1x2a7NJnvUZUFdXOeLb_jP0UM0GbdahIF";

    public AdapterPenyidik(Context context, List<Penyidik> arraylist) {
        this.my_penyidik = arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPenyidik.MyPenyidikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_itempenyidik, parent, false);

        return new AdapterPenyidik.MyPenyidikViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPenyidik.MyPenyidikViewHolder holder, int position) {
        Penyidik penyidik = my_penyidik.get(position);
        holder.tvNrp.setText(penyidik.getNrp());
        holder.tvPangkat.setText(penyidik.getPangkat());
        holder.tvPenyidik.setText(penyidik.getNama());

            Picasso.get()
                    .load(penyidik.getFoto())
                    .error(R.drawable.user_police)
                    .into(holder.civFoto);

    }

    @Override
    public int getItemCount() {
        return my_penyidik.size();
    }

    public class MyPenyidikViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPenyidik, tvNrp, tvPangkat;
        private LinearLayout llItemPenyidik;
        private CircleImageView civFoto;
        private int post;
        public MyPenyidikViewHolder(View itemView) {
            super(itemView);

            tvPenyidik  = itemView.findViewById(R.id.tv_ipPenyidik);
            tvNrp  = itemView.findViewById(R.id.tv_ipNrp);
            tvPangkat  = itemView.findViewById(R.id.tv_ipPangkat);
            civFoto = itemView.findViewById(R.id.civ_ipFoto);
            llItemPenyidik = itemView.findViewById(R.id.llitempenyidik);
            llItemPenyidik.setOnClickListener(v -> {
                post = getAdapterPosition();
                Intent intent = new Intent(context, DetailPenyidikActivity.class);
                intent.putExtra("nrp", tvNrp.getText().toString().trim());
                context.startActivity(intent);
            });
        }
    }
}
