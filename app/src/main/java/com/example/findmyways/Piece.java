package com.example.findmyways;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public class Piece {

    /**
     * Classe piece qui est defini par un nom et par 4 image pour chaque mur dde la piece
     */

    private String nom;
    private Bitmap ImgNord;
    private Bitmap ImgSud;
    private Bitmap ImgEst;
    private Bitmap ImgOuest;
    private double Meteo;

    public Piece(String nom, Bitmap ImgNord, Bitmap ImgSud, Bitmap ImgEst, Bitmap ImgOuest, double Meteo){
        this.nom = nom;
        this.ImgNord = ImgNord;
        this.ImgSud = ImgSud;
        this.ImgEst = ImgEst;
        this.ImgOuest = ImgOuest;
        this.Meteo = Meteo;
    }

    public double getMeteo() {
        return Meteo;
    }

    public void setMeteo(double meteo) {
        Meteo = meteo;
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

    public Bitmap getImgEst() {
        return ImgEst;
    }

    public void setImgEst(Bitmap imgEst) {
        ImgEst = imgEst;
    }

    public Bitmap getImgSud() {
        return ImgSud;
    }

    public void setImgSud(Bitmap imgSud) {
        ImgSud = imgSud;
    }

    public Bitmap getImgOuest() {
        return ImgOuest;
    }

    public void setImgOuest(Bitmap imgOuest) {
        ImgOuest = imgOuest;
    }

    public Bitmap getImgNord() {
        return ImgNord;
    }

    public void setImgNord(Bitmap imgNord) {
        ImgNord = imgNord;
    }
}
