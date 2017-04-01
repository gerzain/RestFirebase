package com.example.irving.cuponesapp.service;

import com.example.irving.cuponesapp.model.CuponFirebase;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Irving on 29/03/2017.
 */

public class ItemListResponseTypeAdapter extends TypeAdapter {
    @Override
    public void write(JsonWriter out, Object value) throws IOException
    {

    }

    @Override
    public CuponResponse read(JsonReader in) throws IOException
    {

        final CuponResponse response=new CuponResponse();
        ArrayList<CuponFirebase> itemList=new ArrayList<CuponFirebase>();
        in.beginObject();
        while (in.hasNext())
        {
            CuponFirebase cupon=redadItem(in);
            itemList.add(cupon);

        }
        in.endObject();
        response.setCuponFirebases(itemList);
        return response;
    }

    public CuponFirebase redadItem(JsonReader reader)throws IOException
    {
        CuponFirebase cuponFirebase=new CuponFirebase();
        reader.nextName();
        reader.beginObject();
        while (reader.hasNext())
        {
            String next=reader.nextName();

            switch (next)
            {
                case "codigo":
                    cuponFirebase.setCodigo(reader.nextInt());
                    break;
                case "fecha":
                    cuponFirebase.setFecha(reader.nextString());
                    break;
                case "imagen":
                    cuponFirebase.setImagen(reader.nextString());
                    break;

                case "promocion":
                    cuponFirebase.setPromocion(reader.nextString());
                    break;

            }


        } //Termina while
        reader.endObject();
        return  cuponFirebase;
    }
}
