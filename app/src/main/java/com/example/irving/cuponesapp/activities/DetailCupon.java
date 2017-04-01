package com.example.irving.cuponesapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.irving.cuponesapp.R;
import com.example.irving.cuponesapp.model.Cupon;

import butterknife.ButterKnife;

public class DetailCupon extends AppCompatActivity
{

    public static final String VIEW_NAME_HEADER_IMAGE = "imagen_compartida";
    private TextView cod_cupo;
    private TextView promo_descripcion;
    private TextView fecha_vencimiento;
    private ImageView foto_promo;

    private String idPromo;
    private int codigo;
    private String fecha;
    private String promo;
    private String img;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        setContentView(R.layout.activity_detail_cupon);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        cod_cupo=(TextView)findViewById(R.id.codigo_promocion);
        promo_descripcion=(TextView)findViewById(R.id.tv_nombre_promocion);
        fecha_vencimiento=(TextView)findViewById(R.id.fecha_promocion);
        foto_promo=(ImageView)findViewById(R.id.foto_promocion);
        if(extras!=null)
        {

            codigo=extras.getInt("cod");
            promo=extras.getString("promo");
            fecha=extras.getString("fecha");
            img=extras.getString("img");
            cod_cupo.setText(String.valueOf(codigo));
            promo_descripcion.setText(promo);
            fecha_vencimiento.setText(fecha);
            Glide.with(DetailCupon.this)
                    .load(img)
                    .centerCrop()
                    .into(foto_promo);
        }



    }



}
