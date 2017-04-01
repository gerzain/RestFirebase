package com.example.irving.cuponesapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.irving.cuponesapp.R;
import com.example.irving.cuponesapp.model.Cupon;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Irving on 21/03/2017.
 */
public class CuponAdapter extends RecyclerView.Adapter<CuponAdapter.ViewHolder>
{

    private List<Cupon> cupons;
    private OnClickItem onClickItem;

    public CuponAdapter(OnClickItem onClickItem)
    {
        this.cupons=new ArrayList<>();
        this.onClickItem = onClickItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cupon,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Cupon cupon=cupons.get(position);
        holder.nombre_promo.setText(cupon.getPromocion());
        Glide.with(holder.itemView.getContext())
                .load(cupon.getImagen())
                .centerCrop()
                .into(holder.foto_promo);
        holder.setOnItemClickListener(cupon,onClickItem);


    }
    public void setDataset(List<Cupon> dataset)
    {
        this.cupons=dataset;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return cupons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.fotoImageView)
        ImageView foto_promo;
        @BindView(R.id.tituloTextView)
        TextView nombre_promo;
        /*public  TextView promocion;
        public ImageView foto_promo;*/

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);

            // promocion=(TextView) itemView.findViewById(R.id.promocion);
            //foto_promo=(ImageView)itemView.findViewById(R.id.miniatura_cupon);

        }
        public void setOnItemClickListener(final Cupon c, final OnClickItem onItemClickListener)
        {
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    onItemClickListener.onItemClick(c);
                }
            });
        }


    }

}
