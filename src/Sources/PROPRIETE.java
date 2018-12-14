/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sources;

import java.util.Vector;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class PROPRIETE {
    public static String Prefixe_BT = "BT_";//Le bouton
    public static String Prefixe_SP = "SP_";//Le séparateur
    public ImageIcon icone;
    public static final int TYPE_BOUTON_ACTUALISER = -1;
    public static final int TYPE_SAISIE_TEXTE = 0;
    public static final int TYPE_SAISIE_NUMBRE = 1;
    public static final int TYPE_CHOIX_LISTE = 2;
    public static final int TYPE_CHOIX_BOOLEN = 3;
    public static final int TYPE_CHOIX_DATE = 4;
    public static final int TYPE_OBJET = 5;
    public static final int TYPE_SAISIE_TEXTE_MULTIPLES = 6;
    public static final int TYPE_PHOTO = 7;
    public static final int TYPE_CHOIX_LISTE_MULTIPLE = 8;
    public static final int TYPE_CHOIX_LISTE_MULTIPLE_ALPHA_NUMERIQUE = 9;
    public static final int TYPE_CHOIX_LISTE_MULTIPLE_DATE = 10;
    public static final int TYPE_SAISIE_MOT_DE_PASSE = 11;
    public static final int TYPE_SEPARATEUR = 12;
    
    public String nom;                                      //Le nom qui s'affichera dans la prémière colonne.
    public String codeSql;                                  //Le nom du champ tel qu'il est écrit dans la table au sein de la base de données.
    public Vector valeurs;                                  //La liste des valeurs possible.
    public Object valeurSelectionne;                        //La valeur qui a été séléctionnée par l'utilisateur.
    public int type;                                        //Le type ou la nature de cette propriété.
    
    
    

    public PROPRIETE() {
        
    }

    public PROPRIETE(ImageIcon icone, String nom, String codeSql, Vector valeurs, Object valeurSelectionne, int type) {
        this.icone = icone;
        this.nom = nom;
        this.codeSql = codeSql;
        if(valeurs == null){
            this.valeurs = new Vector();
        }else{
            this.valeurs = valeurs;
        }
        if(valeurSelectionne == null){
            this.valeurSelectionne = "";
        }else{
            this.valeurSelectionne = valeurSelectionne;
        }
        this.type = type;
    }

    public ImageIcon getIcone() {
        return icone;
    }

    public void setIcone(ImageIcon icone) {
        this.icone = icone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeSql() {
        return codeSql;
    }

    public void setCodeSql(String codeSql) {
        this.codeSql = codeSql;
    }

    public Vector getValeurs() {
        return valeurs;
    }

    public void setValeurs(Vector valeurs) {
        this.valeurs = valeurs;
    }

    public Object getValeurSelectionne() {
        return valeurSelectionne;
    }

    public void setValeurSelectionne(Object valeurSelectionne) {
        this.valeurSelectionne = valeurSelectionne;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PROPRIETE{" + "nom=" + nom + ", codeSql=" + codeSql + ", valeurSelectionne=" + valeurSelectionne + ", type=" + type + '}';
    }

    
    
    public static void main(String[] a){
        
    }
    
}
