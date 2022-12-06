package com.example.findmyways;

import java.util.ArrayList;

public class Batiment {

    /**
     * Class batiment qui est defini par un nom et une liste de pieces
     */

    private String nom;
    private ArrayList<Piece> pieces;

    public Batiment(String nom,ArrayList<Piece> pieces ) {
        this.nom = nom;
        this.pieces = pieces;
    }

    @Override
    public String toString() {
        return "Batiment{" +
                "nom='" + nom + '\'' +
                ", pieces=" + pieces +
                '}';
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
