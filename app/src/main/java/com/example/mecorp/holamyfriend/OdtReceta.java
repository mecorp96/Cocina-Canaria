package com.example.mecorp.holamyfriend;

import java.io.Serializable;

/**
 * Created by Meciro on 07/12/2017.
 */

public class OdtReceta implements Serializable{
    private String nombreRecycler;
    private String descripcionRecycler;
    private String ingredientes;
    private String cocinar;
    private String imageRecycler;

    public OdtReceta() {
    }

    public OdtReceta(String nombreRecycler, String descripcionRecycler, String imageRecycler, String ingredientes, String cocinar) {
        this.nombreRecycler = nombreRecycler;
        this.descripcionRecycler = descripcionRecycler;
        this.imageRecycler = imageRecycler;
        this.ingredientes = ingredientes;
        this.cocinar = cocinar;
    }

    public String getNombreRecycler() {
        return nombreRecycler;
    }

    public void setNombreRecycler(String nombreRecycler) {
        this.nombreRecycler = nombreRecycler;
    }

    public String getDescripcionRecycler() {
        return descripcionRecycler;
    }

    public void setDescripcionRecycler(String descripcionRecycler) {
        this.descripcionRecycler = descripcionRecycler;
    }

    public String getImageRecycler() {
        return imageRecycler;
    }

    public void setImageRecycler(String imageRecycler) {
        this.imageRecycler = imageRecycler;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getCocinar() {
        return cocinar;
    }

    public void setCocinar(String cocinar) {
        this.cocinar = cocinar;
    }

    @Override
    public String toString() {
        return "OdtReceta{" +
                "nombreRecycler='" + nombreRecycler + '\'' +
                ", descripcionRecycler='" + descripcionRecycler + '\'' +
                ", ingredientes='" + ingredientes + '\'' +
                ", cocinar='" + cocinar + '\'' +
                ", imageRecycler=" + imageRecycler +
                '}';
    }
}
