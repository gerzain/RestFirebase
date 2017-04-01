package com.example.irving.cuponesapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.irving.cuponesapp.R;
import com.example.irving.cuponesapp.adapters.ClickFirebase;
import com.example.irving.cuponesapp.adapters.CuponAdapter;
import com.example.irving.cuponesapp.service.CuponResponse;
import com.example.irving.cuponesapp.adapters.FirebaseAdapter;
import com.example.irving.cuponesapp.adapters.OnClickItem;
import com.example.irving.cuponesapp.model.Cupon;
import com.example.irving.cuponesapp.model.CuponFirebase;
import com.example.irving.cuponesapp.service.Api;
import com.example.irving.cuponesapp.service.ItemListResponseTypeAdapter;
import com.example.irving.cuponesapp.utils.Network;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnClickItem ,ClickFirebase {

    private RecyclerView cupones;
    private String TAG=MainActivity.class.getSimpleName();
    private ArrayList<Cupon> cupons;
    private ArrayList<CuponFirebase> cuponesFirebase;
    private CuponAdapter cuponAdapter;
    private FirebaseAdapter adapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cupones=(RecyclerView)findViewById(R.id.reciclador_cupon);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        cupones.setHasFixedSize(true);
        cupons=new ArrayList<>();
        cupones.setLayoutManager(layoutManager);
        cupones.setAdapter(cuponAdapter);
        cuponAdapter=new CuponAdapter(this);
        adapter=new FirebaseAdapter(cuponesFirebase,this);
        cuponesFirebase=new ArrayList<>();

            //new ObtenerDatos().execute();

          new FirebaseCupon().execute();
      //  DataVolley();
    }

    public void DataVolley()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url="https://cupones-36d3e.firebaseio.com/cupones.json";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                    Log.i(TAG,"Response"+response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);

                    String codigo= jsonObject.optString("codigo");
                    String fecha=jsonObject.optString("fecha");
                    String promocion=jsonObject.optString("promocion");
                    String imagen=jsonObject.optString("imagen");
                    GsonBuilder builder = new GsonBuilder();
                    Gson mGson = builder.create();
                    CuponFirebase cuponFirebase=mGson.fromJson(response,CuponFirebase.class);

                   // cuponFirebase.setCodigo(codigo);
                    cuponFirebase.setImagen(imagen);
                    cuponFirebase.setFecha(fecha);
                    cuponFirebase.setPromocion(promocion);

                    cuponesFirebase.add(cuponFirebase);
                    //adapter=new FirebaseAdapter(cuponesFirebase,getApplicationContext());
                    cupones.setAdapter(adapter);






                    //Log.i(TAG,codigo+""+ "  "+fecha+" "+promocion+" "+imagen);

                } catch (JSONException e)

                {

                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
            requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(Cupon cupon)
    {
        Log.d(TAG,"Click");
        Intent intent=new Intent(getApplicationContext(),DetailCupon.class);
        Bundle extras = new Bundle();
        extras.putString("id",cupon.getId());
        extras.putString("promo",cupon.getPromocion());
        extras.putString("cod",cupon.getCodigo());
        extras.putString("fecha",cupon.getFecha());
        extras.putString("img",cupon.getImagen());
       // intent.putExtras(extras);
        //startActivity(intent);
    }

    @Override
    public void onItemClick(CuponFirebase cuponFirebase) {
        Intent intent=new Intent(getApplicationContext(),DetailCupon.class);
        Bundle extras = new Bundle();
        extras.putString("promo",cuponFirebase.getPromocion());
        extras.putInt("cod",cuponFirebase.getCodigo());
        extras.putString("fecha",cuponFirebase.getFecha());
        extras.putString("img",cuponFirebase.getImagen());
        intent.putExtras(extras);
        startActivity(intent);

        Log.d(TAG,"ClickFirebase");
    }

    private class FirebaseCupon extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids)
        {
            Gson gson=new GsonBuilder()
                    .registerTypeAdapter(CuponResponse.class,new ItemListResponseTypeAdapter())
                    .create();

            final String url= "https://cupones-36d3e.firebaseio.com/";
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            Api servicio=retrofit.create(Api.class);
            Call<CuponResponse > call=servicio.getCuponFirebase();

            call.enqueue(new Callback<CuponResponse>()
            {
                @Override
                public void onResponse(Call<CuponResponse> call, Response<CuponResponse> response)
                {
                    if(response.isSuccessful())
                    {
                        int code_response=response.code();
                        //Toast.makeText(getApplicationContext(),String.valueOf(code_response),Toast.LENGTH_LONG).show();
                        switch (response.code())
                        {
                            case 200:

                                CuponResponse respuesta=response.body();

                                ArrayList<CuponFirebase> items=respuesta.getCuponFirebases();

                                Log.i(TAG,items.toString());
                                //FirebaseAdapter firebaseAdapter=new FirebaseAdapter(items,this);
                                adapter.setDataset(items);
                                cupones.setAdapter(adapter);


                                break;

                            case 401:
                                break;

                        }

                        Log.i(TAG,String.valueOf(code_response));


                    }
                }

                @Override
                public void onFailure(Call<CuponResponse> call, Throwable t)
                {
                    //Snackbar.make(findViewById(R.id.rootview), t.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.e(TAG,t.getLocalizedMessage());
                }
            });

            return null;
        }
    }

    private   class ObtenerDatos extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            cuponAdapter.notifyDataSetChanged();

        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, null, null, true);
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            /***
             * Verificamos la existencia de conexion
             */
            if(!Network.isConnected(getApplicationContext()))
            {
                Snackbar.make(findViewById(R.id.rootview), "No hay conexion a Internet", Snackbar.LENGTH_LONG).show();


            }
            else
            {

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        .create();
                final String ulr = "http://192.168.1.72:5000/api/";
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ulr)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                Api servicio = retrofit.create(Api.class);
                final Call<List<Cupon>> respuesta = servicio.getCupones();
                respuesta.enqueue(new Callback<List<Cupon>>()

                {
                    @Override
                    public void onResponse(Call<List<Cupon>> call, Response<List<Cupon>> response)
                    {
                        try
                        {

                            //int code_response=response.code();
                            //Toast.makeText(getApplicationContext(),String.valueOf(code_response),Toast.LENGTH_LONG).show();
                            for (Cupon cupon :response.body())
                            {

                                cupons.add(cupon);
                            }

                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    cuponAdapter.setDataset(cupons);
                                    cupones.setAdapter(cuponAdapter);
                                }
                            });
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Cupon>> call, Throwable t)
                    {
                        Snackbar.make(findViewById(R.id.rootview), t.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });


            }/**  Termina comprobacion si existe conexion */

            return null;
        }
    }
}
