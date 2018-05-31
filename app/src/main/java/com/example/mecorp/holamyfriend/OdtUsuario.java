package com.example.mecorp.holamyfriend;

import java.io.Serializable;

/**
 * Created by Mecorp on 27/11/2017.
 */

public class OdtUsuario implements Serializable {
    private String correo, contraseña, telefono, nnombre, apellidoss, comidafav, ingredientefav, bebidafav;
    private String uState, uFoodType, foto;

    public OdtUsuario(){}



    public OdtUsuario(String correo, String contraseña, String telefono, String nnombre, String apellidoss, String comidafav, String ingredientefav, String bebidafav, String uState, String uFoodType) {
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.nnombre = nnombre;
        this.apellidoss = apellidoss;
        this.comidafav = comidafav;
        this.ingredientefav = ingredientefav;
        this.bebidafav = bebidafav;
        this.uState = uState;
        this.uFoodType = uFoodType;
    }

    /*public OdtUsuario(String correo, String contraseña, String telefono, String nnombre, String apellidoss, boolean uState){
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.nnombre = nnombre;
        this.apellidoss = apellidoss;
        this.uState = uState;
    }*/

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNnombre() {
        return nnombre;
    }

    public void setNnombre(String nnombre) {
        this.nnombre = nnombre;
    }

    public String getApellidoss() {
        return apellidoss;
    }

    public void setApellidoss(String apellidoss) {
        this.apellidoss = apellidoss;
    }

    public String getComidafav() {
        return comidafav;
    }

    public void setComidafav(String comidafav) {
        this.comidafav = comidafav;
    }

    public String getIngredientefav() {
        return ingredientefav;
    }

    public void setIngredientefav(String ingredientefav) {
        this.ingredientefav = ingredientefav;
    }

    public String getBebidafav() {
        return bebidafav;
    }

    public void setBebidafav(String bebidafav) {
        this.bebidafav = bebidafav;
    }

    public String isuState() {
        return uState;
    }

    public void setuState(String uState) {
        this.uState = uState;
    }

    public String isuFoodType() {
        return uFoodType;
    }

    public void setuFoodType(String uFoodType) {
        this.uFoodType = uFoodType;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Email/Usuario = " + correo +
                "\nContraseña = " + contraseña +
                "\nTeléfono = " + telefono +
                "\nNombre = " + nnombre +
                "\nApellidos = " + apellidoss +
                "\nComida Favorita = " + comidafav +
                "\nIngrediente Favorito = " + ingredientefav +
                "\nBebida Favorita" + bebidafav +
                "\n¿Soltero? = " + uState +
                "\n¿Picante? = " + uFoodType;
    }
}
