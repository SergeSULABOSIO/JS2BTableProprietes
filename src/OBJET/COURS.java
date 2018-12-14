/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package OBJET;

/**
 *
 * @author Gateway
 */
public class COURS {
    private int id;
    private String nom;
    private int maxima;

    public COURS(int id, String nom, int maxima) {
        this.id = id;
        this.nom = nom;
        this.maxima = maxima;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getMaxima() {
        return maxima;
    }

    public void setMaxima(int maxima) {
        this.maxima = maxima;
    }

    @Override
    public String toString() {
        return +id + ", " + nom+", "+ maxima;
    }

    
    
}
