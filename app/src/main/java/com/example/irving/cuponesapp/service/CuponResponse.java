package com.example.irving.cuponesapp.service;

import com.example.irving.cuponesapp.model.CuponFirebase;

import java.util.ArrayList;

/**
 * Created by Irving on 27/03/2017.
 */

public class CuponResponse
{
    private ArrayList<CuponFirebase> cuponFirebases=
            new ArrayList<CuponFirebase>();

    public ArrayList<CuponFirebase> getCuponFirebases()
    {
        return cuponFirebases;
    }

    public void setCuponFirebases(ArrayList<CuponFirebase> cuponFirebases)
    {
        this.cuponFirebases=cuponFirebases;
    }
}
