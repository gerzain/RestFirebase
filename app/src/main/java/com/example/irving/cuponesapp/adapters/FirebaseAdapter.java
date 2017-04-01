package com.example.irving.cuponesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.irving.cuponesapp.R;
import com.example.irving.cuponesapp.model.Cupon;
import com.example.irving.cuponesapp.model.CuponFirebase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Irving on 28/03/2017.
 */

public class FirebaseAdapter extends RecyclerView.Adapter<FirebaseAdapter.ViewHolder>
{

    private List<CuponFirebase> cuponFirebase;
    private Context context;
    private ClickFirebase onClickItem;

    public FirebaseAdapter(List<CuponFirebase> cuponFirebase,ClickFirebase onClickItem) {
        this.cuponFirebase = cuponFirebase;
        this.onClickItem=onClickItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cupon,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CuponFirebase Firebase=cuponFirebase.get(position);
        holder.nombre_promo.setText(Firebase.getPromocion());
        Glide.with(holder.itemView.getContext())
                .load(Firebase.getImagen())
                .centerCrop()
                .into(holder.foto_promo);
        holder.setOnItemClickListener(Firebase,onClickItem);

    }

    @Override
    public int getItemCount()
    {
        return cuponFirebase.size();
    }
    public void setDataset(List<CuponFirebase> dataset)
    {
        this.cuponFirebase=dataset;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.fotoImageView)
        ImageView foto_promo;
        @BindView(R.id.tituloTextView)
        TextView nombre_promo;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setOnItemClickListener(final CuponFirebase c, final ClickFirebase onFirebae) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    onFirebae.onItemClick(c);
                }
            });
        }
    }


}
