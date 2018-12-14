/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sources.Ecouteurs;

import Sources.PROPRIETE;
import java.util.Vector;

/**
 *
 * @author user
 */
public class ProprieteEvent {
    
    private int id;
    private String nom;
    private String nomBouton;
    private Vector<PROPRIETE> listePro;

    public ProprieteEvent(int id, String nom, String nomBouton, Vector<PROPRIETE> listePro) {
        this.id = id;
        this.nom = nom;
        this.nomBouton = nomBouton;
        this.listePro = listePro;
    }

    public String getNomBouton() {
        return nomBouton;
    }

    public void setNomBouton(String nomBouton) {
        this.nomBouton = nomBouton;
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

    public Vector<PROPRIETE> getListePro() {
        return listePro;
    }

    public void setListePro(Vector<PROPRIETE> listePro) {
        this.listePro = listePro;
    }

    
}
