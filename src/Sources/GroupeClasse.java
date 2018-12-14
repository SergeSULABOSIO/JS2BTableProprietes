/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sources;

import OBJET.CLASSE;
import OBJET.COURS;
import Sources.UI.JS2BPanelPropriete;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Gateway
 */
public abstract class GroupeClasse {

    //Compteur d'enregistrement au cas où il y a possibilité d'ajouter plusieurs enregistrement
    public static int nbEnreg = 0;

    public static String BT_ENREGISTRER_CLASSE = "Enregistrer classe";
    public static String BT_IMPORTER_CLASSE = "Importer classe";
    public static String BT_ANNULER_CLASSE = "Annuler classe";

    //Proprietes
    CHAMP_LOCAL champAncien = null;
    CHAMP_LOCAL champNom = null;
    CHAMP_LOCAL champCapacite = null;

    //Objets temporaires
    private Vector<CLASSE> OlisteClasse = new Vector<CLASSE>();//En réalité cette liste d'objet devra être remplie par la base de données
    private Vector listeClasses = new Vector();   //Liste des references pour selection d'objet
    private CLASSE Oclasse = new CLASSE(-1, "", 0);    //Objet cours temporaire
    private Object OclasseImporte = null;    //Référence importée

    //Objets obligatoires
    public JS2BPanelPropriete panProp;
    private static boolean isImportable;
    private static boolean isAjoutable;
    private String titre = "CLASSE";
    private String signature;

    public GroupeClasse(JS2BPanelPropriete panProp, boolean isImportable, boolean isAjoutable, String signature) {
        this.panProp = panProp;
        GroupeClasse.isImportable = isImportable;
        GroupeClasse.isAjoutable = isAjoutable;
        this.signature = signature;
        chargerGroupeObjets();
    }

    private static void incrementerEnreg() {
        if (isAjoutable == true) {
            nbEnreg++;
        } else {
            nbEnreg = 1;
        }
    }

    private static void viderEnreg() {
        nbEnreg = 0;
    }

    public void Importer() {
        OclasseImporte = champAncien.getValeurSelectionne();
        if (((OclasseImporte + "").trim()).length() != 0) {
            int idclss = Integer.parseInt((OclasseImporte + "").split(",")[0].trim());
            for (CLASSE clss : OlisteClasse) {
                if (clss.getId() == idclss) {
                    Oclasse = clss;
                    titre = "CLASSE (Importée !) @" + nbEnreg;
                    break;
                }
            }
            construire();
        } else {
            JOptionPane.showMessageDialog(null, "Impossible d'importer !\nVous n'avez rien séléctionné", this.titre + " - Erreur !", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Annuler() {
        int rep = JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir annuler ce " + this.titre.toUpperCase() + " ?", this.titre + " - Question", JOptionPane.YES_NO_OPTION);
        if (rep == 0) {
            OclasseImporte = null;
            Oclasse = new CLASSE(-1, "", 0);
            viderEnreg();
            titre = "CLASSE";
            construire();
        }
    }

    public void Enregistrer() {
        String Snom = champNom.getValeurSelectionne() + "";
        String Scap = champCapacite.getValeurSelectionne() + "".trim();
        try {
            int cap = Integer.parseInt(Scap);
            if (isAjoutable == false) {
                System.out.println(Oclasse.getId());
                if (Oclasse.getId() == -1) {//C'est que c'est nouveau
                    //C'est ici que l'on doit normalement materialiser l'enregistrement
                    Oclasse.setId((new Date()).getSeconds());
                    Oclasse.setNom(Snom);
                    Oclasse.setCapacite(cap);
                    incrementerEnreg();
                    titre = "CLASSE (Enregistré !) @" + nbEnreg;
                    //C'EST ICI QUE JE VAIS ENREGISTRE DANS LA BASE
                } else { //On ne fait que remplacer ce qui est actuellement
                    Oclasse.setNom(Snom);
                    Oclasse.setCapacite(cap);
                    titre = "CLASSE (Enregistré !) @" + nbEnreg;
                    //C'EST ICI QUE JE VAIS ENREGISTRE DANS LA BASE
                }
            } else {
                //C'est ici que l'on doit normalement materialiser l'enregistrement
                Oclasse.setId((new Date()).getSeconds());
                Oclasse.setNom(Snom);
                Oclasse.setCapacite(cap);
                incrementerEnreg();
                titre = "CLASSE (Enregistré !) @" + nbEnreg;
                //C'EST ICI QUE JE VAIS ENREGISTRE DANS LA BASE
            }
            construire();
            JOptionPane.showMessageDialog(null, "Félicitation !\nVous venez d'enregistrer " + Oclasse.getNom() + ", Capacité " + Oclasse.getCapacite() + "(" + Oclasse.getId() + ").", this.titre + " - Félicitation !", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Impossible d'enregistrer !\nVérifier correctement vos entrées", this.titre + " - Erreur !", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chargerGroupeObjets() {
        OlisteClasse.removeAllElements();
        //Les objets que je charge ici devront normalement venir de la base de données
        for (int i = 1; i < 500; i++) {
            OlisteClasse.add(new CLASSE(i, "CLASSE" + i, i * 20));
        }
    }

    public void init() {
        listeClasses.removeAllElements();
        listeClasses.add("");
        for (CLASSE cl : OlisteClasse) {
            listeClasses.add(cl.getId() + ", " + cl.getNom() + ", Cap=" + cl.getCapacite());
        }
        //initialisation des champs
        champAncien = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "Anciens", "anciens", listeClasses, OclasseImporte, PROPRIETE.TYPE_OBJET);
        champNom = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "NOM", "nom", null, Oclasse.getNom(), PROPRIETE.TYPE_SAISIE_TEXTE);
        champCapacite = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "CAPACITE", "maxima", null, Oclasse.getCapacite(), PROPRIETE.TYPE_SAISIE_TEXTE);

        panProp.AjouterSeparateur(this.titre);
        if (isImportable == true) {
            panProp.AjouterPropriete(champAncien, 0);
            panProp.AjouterBouton((new ICONES_S2B().BTRECHERCHE_ICONE_01), GroupeClasse.BT_IMPORTER_CLASSE);
        }
        panProp.AjouterPropriete(champNom, 0);
        panProp.AjouterPropriete(champCapacite, 0);
        panProp.AjouterBouton((new ICONES_S2B().BTRECHERCHE_ICONE_01), GroupeClasse.BT_ENREGISTRER_CLASSE);
        panProp.AjouterBouton((new ICONES_S2B().BTRECHERCHE_ICONE_01), GroupeClasse.BT_ANNULER_CLASSE);

    }

    public CHAMP_LOCAL getChampAncien() {
        return champAncien;
    }

    public void setChampAncien(CHAMP_LOCAL champAncien) {
        this.champAncien = champAncien;
    }

    public CHAMP_LOCAL getChampNom() {
        return champNom;
    }

    public void setChampNom(CHAMP_LOCAL champNom) {
        this.champNom = champNom;
    }

    public static String getBT_ENREGISTRER_CLASSE() {
        return BT_ENREGISTRER_CLASSE;
    }

    public static void setBT_ENREGISTRER_CLASSE(String BT_ENREGISTRER_CLASSE) {
        GroupeClasse.BT_ENREGISTRER_CLASSE = BT_ENREGISTRER_CLASSE;
    }

    public static String getBT_IMPORTER_CLASSE() {
        return BT_IMPORTER_CLASSE;
    }

    public static void setBT_IMPORTER_CLASSE(String BT_IMPORTER_CLASSE) {
        GroupeClasse.BT_IMPORTER_CLASSE = BT_IMPORTER_CLASSE;
    }

    public static String getBT_ANNULER_CLASSE() {
        return BT_ANNULER_CLASSE;
    }

    public static void setBT_ANNULER_CLASSE(String BT_ANNULER_CLASSE) {
        GroupeClasse.BT_ANNULER_CLASSE = BT_ANNULER_CLASSE;
    }

    public CHAMP_LOCAL getChampCapacite() {
        return champCapacite;
    }

    public void setChampCapacite(CHAMP_LOCAL champCapacite) {
        this.champCapacite = champCapacite;
    }

    public Vector<CLASSE> getOlisteClasse() {
        return OlisteClasse;
    }

    public void setOlisteClasse(Vector<CLASSE> OlisteClasse) {
        this.OlisteClasse = OlisteClasse;
    }

    public Vector getListeClasses() {
        return listeClasses;
    }

    public void setListeClasses(Vector listeClasses) {
        this.listeClasses = listeClasses;
    }

    public CLASSE getOclasse() {
        return Oclasse;
    }

    public void setOclasse(CLASSE Oclasse) {
        this.Oclasse = Oclasse;
    }

    public Object getOclasseImporte() {
        return OclasseImporte;
    }

    public void setOclasseImporte(Object OclasseImporte) {
        this.OclasseImporte = OclasseImporte;
    }

    public JS2BPanelPropriete getPanProp() {
        return panProp;
    }

    public void setPanProp(JS2BPanelPropriete panProp) {
        this.panProp = panProp;
    }

    public boolean isIsImportable() {
        return isImportable;
    }

    public void setIsImportable(boolean isImportable) {
        this.isImportable = isImportable;
    }

    public boolean isIsAjoutable() {
        return isAjoutable;
    }

    public void setIsAjoutable(boolean isAjoutable) {
        this.isAjoutable = isAjoutable;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public abstract void construire();

}
