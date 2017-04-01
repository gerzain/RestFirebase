package com.example.irving.cuponesapp.service;

import com.example.irving.cuponesapp.model.Cupon;
import com.example.irving.cuponesapp.model.CuponFirebase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Irving on 21/03/2017.
 */
public interface Api
{
    @GET("cupon")
    Call<List<Cupon> > getCupones();
    @GET("cupon/{id}")
    Call<List<Cupon> >getCupon(@Path("id")int id);
    @GET("cupones.json")
    Call<CuponResponse> getCuponFirebase();


}
