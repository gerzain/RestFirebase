package com.example.irving.cuponesapp.model;

/**
 * Created by Irving on 27/03/2017.
 */

public class CuponFirebase
{

    private int codigo;
    private String fecha;
    private String imagen;
    private String promocion;

    public CuponFirebase()
    {

    }

    public CuponFirebase(int cod, String date, String url_imagen, String promocion)
    {
        this.codigo=cod;
        this.fecha=date;
        this.imagen=url_imagen;
        this.promocion=promocion;
    }



    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPromocion() {
        return promocion;
    }

    public void setPromocion(String promocion) {
        this.promocion = promocion;
    }

}
