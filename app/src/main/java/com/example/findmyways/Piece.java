package com.example.findmyways;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public class Piece {

    /**
     * Classe piece qui est defini par un nom et par 4 image pour chaque mur dde la piece
     */

    private String nom;
    private String ImgNord;
    private String ImgSud;
    private String ImgEst;
    private String ImgOuest;
    private double Meteo;

    public Piece(String nom, String ImgNord, String ImgSud, String ImgEst, String ImgOuest, double Meteo){
        this.nom = nom;
        this.ImgNord = ImgNord;
        this.ImgSud = ImgSud;
        this.ImgEst = ImgEst;
        this.ImgOuest = ImgOuest;
        this.Meteo = Meteo;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "nom='" + nom + '\'' +
                ", ImgNord=" + ImgNord +
                ", ImgSud=" + ImgSud +
                ", ImgEst=" + ImgEst +
                ", ImgOuest=" + ImgOuest +
                ", Meteo="+ Meteo +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImgEst() {
        return ImgEst;
    }

    public void setImgEst(String imgEst) {
        ImgEst = imgEst;
    }

    public String getImgSud() {
        return ImgSud;
    }

    public void setImgSud(String imgSud) {
        ImgSud = imgSud;
    }

    public String getImgOuest() {
        return ImgOuest;
    }

    public void setImgOuest(String imgOuest) {
        ImgOuest = imgOuest;
    }

    public String getImgNord() {
        return ImgNord;
    }

    public void setImgNord(String imgNord) {
        ImgNord = imgNord;
    }

    public double getMeteo() {
        return Meteo;
    }

    public void setMeteo(double meteo) {
        Meteo = meteo;
    }

}
