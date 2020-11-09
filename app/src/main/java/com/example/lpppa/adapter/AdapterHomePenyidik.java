package com.example.lpppa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lpppa.R;
import com.example.lpppa.activity.DetailPenyidikActivity;
import com.example.lpppa.models.Penyidik;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterHomePenyidik extends RecyclerView.Adapter<AdapterHomePenyidik.MyPenyidikViewHolder> {
    private List<Penyidik> my_penyidik;
    private Context context;

    public AdapterHomePenyidik(Context context, List<Penyidik> arraylist) {
        this.my_penyidik = arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterHomePenyidik.MyPenyidikViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_homepenyidik, parent, false);

        return new AdapterHomePenyidik.MyPenyidikViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHomePenyidik.MyPenyidikViewHolder holder, int position) {
        Penyidik penyidik = my_penyidik.get(position);
        holder.tvPangkat.setText(penyidik.getPangkat());
        holder.tvPenyidik.setText(penyidik.getNama());
//        Picasso.get()
//                .load(penyidik.getFoto())
//                .error(R.drawable.user_police)
//                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return my_penyidik.size();
    }

    public class MyPenyidikViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPenyidik,  tvPangkat, tvNrp;
        private int post;
        private ImageView imageView;
        public MyPenyidikViewHolder(View itemView) {
            super(itemView);

            tvPenyidik  = itemView.findViewById(R.id.tv_listhome_nama);
            imageView = itemView.findViewById(R.id.iv_listhome_penyidik);
            tvPangkat  = itemView.findViewById(R.id.tv_listhome_pangkat);
            LinearLayout llItemPenyidik = itemView.findViewById(R.id.ll_listhome_penyidik);
            llItemPenyidik.setOnClickListener(v -> {
                post = getAdapterPosition();
                Intent intent = new Intent(context, DetailPenyidikActivity.class);
                intent.putExtra("nrp", tvNrp.getText().toString().trim());
                context.startActivity(intent);
            });
        }
    }
}
