package Sources.Choixmultiple;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Serge SULA BOSIO
 */
public class ModelChoixMultiple extends AbstractTableModel {

    //Objets non immuables
    Vector listeValeurs = new Vector();
    Vector<Boolean> listeChoix = new Vector<Boolean>();

    //Objets résultats de la recherche
    Vector listeValeursResultats = new Vector();
    Vector<Boolean> listeChoixResultats = new Vector<Boolean>();

    //Objets sélectionnés
    Vector listeValeursSelectionnees = new Vector();

    //Objets immuables
    public String[] entetes = {"[.]", "Valeurs"};

    private JList UIListeElementsSelected = null;
    private JLabel Afficheur = null;

    public ModelChoixMultiple(JList listeOfSeleted, JLabel Afficheur) {
        super();
        this.UIListeElementsSelected = listeOfSeleted;
        this.Afficheur = Afficheur;
    }

    public Vector getListeElementsSelectionnes() {
        return listeValeursSelectionnees;
    }

    public void setListeElementsSelectionnes(Vector liste) {
        this.listeValeursSelectionnees = liste;
        fireTableDataChanged();
    }

    public void setListeElements(Vector liste) {
        this.listeValeurs = liste;
        fireTableDataChanged();
    }

    public void vider() {
        this.listeValeurs.removeAllElements();
        this.listeChoix.removeAllElements();
        this.listeValeursSelectionnees.removeAllElements();
        affiche();
        fireTableDataChanged();
    }

    public void viderResultat() {
        this.listeValeursResultats.removeAllElements();
        this.listeChoixResultats.removeAllElements();
        affiche();
        fireTableDataChanged();
    }

    public void initialiser(String motcle) {
        viderResultat();
        for (int i = 0; i < this.listeValeurs.size(); i++) {
            String valeurExistante = this.listeValeurs.elementAt(i) + "";
            if (valeurExistante.toLowerCase().contains(motcle.toLowerCase())) {
                AjouterResultatRechercher(valeurExistante, this.listeChoix.elementAt(i));
            }
        }
        actualiserValeurSelectionnees();
        fireTableDataChanged();
    }

    public void AjouterResultatRechercher(String valeur, boolean choix) {
        if (!this.listeValeursResultats.contains(valeur)) {
            this.listeValeursResultats.add(valeur);
            this.listeChoixResultats.add(choix);
        }
    }

    public boolean AjouterValeur(Object valeur, boolean choix) {
        if (!listeValeurs.contains(valeur)) {
            listeChoix.add(choix);
            listeValeurs.add(valeur);
//            System.out.println(valeur+", "+choix);
            fireTableDataChanged();
            return true;
        } else {
            return false;
        }
    }

    public Object getChoix(int index) {
        Object o = null;
        if (listeValeurs.size() > index) {
            return listeValeurs.elementAt(index);
        }
        return o;
    }

    public boolean EnleverValeur(Object valeur) {
        if (listeValeurs.contains(valeur)) {
            int index = listeValeurs.indexOf(valeur);
            listeValeurs.removeElementAt(index);
            listeChoix.removeElementAt(index);
//            System.out.println(valeur+", "+choix);
            fireTableDataChanged();
            return true;
        } else {
            return false;
        }
    }

    public Vector getListeValeurs() {
        return listeValeursResultats;
    }

    public Vector<Boolean> getListeChoix() {
        return listeChoixResultats;
    }

    public void setListeChoix(Vector<Boolean> listeChoix) {
        this.listeChoixResultats = listeChoix;
    }

    public void setListeValeur(Vector listeVal) {
        this.listeValeursResultats = listeVal;
    }

    @Override
    public int getRowCount() {
        return listeValeursResultats.size();
    }

    @Override
    public int getColumnCount() {
        return entetes.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object getValueAt(int idIndex, int columnIndex) {
        if (idIndex < listeChoixResultats.size()) {
            if (columnIndex == 0) {
                return listeChoixResultats.elementAt(idIndex);
            } else {
                return listeValeursResultats.elementAt(idIndex);
            }
        } else {
            return null;
        }

    }

    public void selectionnerTout(boolean ok) {
        for (int i = 0; i < this.listeChoixResultats.size(); i++) {
            this.listeChoixResultats.set(i, ok);
        }
        actualiserListeValeurs();
        fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            Boolean valeur = (Boolean) aValue;
//            System.out.println("Nouvelle valeur = " + valeur);
            this.listeChoixResultats.setElementAt(valeur, rowIndex);
        } else {
            this.listeValeursResultats.setElementAt(aValue, rowIndex);
        }
        actualiserListeValeurs();
        fireTableDataChanged();
    }

    private void actualiserListeValeurs() {
        for (int i = 0; i < this.listeValeursResultats.size(); i++) {
            String valSelected = this.listeValeursResultats.elementAt(i) + "";
            boolean choixSelected = this.listeChoixResultats.elementAt(i);
            int indexToUpdate = this.listeValeurs.indexOf(valSelected);

            this.listeValeurs.setElementAt(valSelected, indexToUpdate);
            this.listeChoix.setElementAt(choixSelected, indexToUpdate);
        }
        actualiserValeurSelectionnees();
    }

    private void actualiserValeurSelectionnees() {
        //je commence par vider la liste des valeurs sélectionnées
        this.listeValeursSelectionnees.removeAllElements();
        //Je vais chercher les élements qui ont été sélectionnés par l'utilisateur
//        System.out.println("Actualisation de la liste d'élements séléctionnés");
        for (int i = 0; i < this.listeChoix.size(); i++) {
            if (this.listeChoix.elementAt(i) == true) {
                String valeurSelected = this.listeValeurs.elementAt(i) + "";
                if (!this.listeValeursSelectionnees.contains(valeurSelected)) {
                    this.listeValeursSelectionnees.addElement(valeurSelected);
                }
            }
        }
        affiche();
        if (UIListeElementsSelected != null) {
            UIListeElementsSelected.setListData(this.listeValeursSelectionnees);
        }
//        System.out.println("Elements = "+listeValeursSelectionnees.size());
    }

    private void affiche() {
        Afficheur.setText(this.listeValeursSelectionnees.size() + " Séléction(s)/" + this.listeValeursResultats.size() + " trouvé(s).");
    }

    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

    public Class getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Boolean.class;
        } else {
            return Object.class;
        }
    }

}
