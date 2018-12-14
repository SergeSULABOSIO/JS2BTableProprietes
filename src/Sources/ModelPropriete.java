package Sources;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Serge SULA BOSIO
 */
public class ModelPropriete extends AbstractTableModel {

    

    //Objets non immuables
    Vector<PROPRIETE> listeProprietes = new Vector<PROPRIETE>();
    

    //Objets immuables
    public String[] enteteValeur = {"", " "};

    public ModelPropriete() {
        super();
    }
    
    public void vider(){
        this.listeProprietes.removeAllElements();
    }

    public void initialiser() {//Cette méthode est sensée actualiser la liste des propriétés

        fireTableDataChanged();
    }
    
    public boolean AjouterPropriete(PROPRIETE pro){
        if(!listeProprietes.contains(pro)){
            return listeProprietes.add(pro);
        }else{
            return false;
        }
    }

    public Vector<PROPRIETE> getListeProprietes() {
        return listeProprietes;
    }

    public void setListeProprietes(Vector<PROPRIETE> listeProprietes) {
        this.listeProprietes = listeProprietes;
    }
    
    

    @Override
    public int getRowCount() {
        return listeProprietes.size();
    }

    @Override
    public int getColumnCount() {
        return enteteValeur.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Object getValueAt(int idIndex, int columnIndex) {
        if(columnIndex == 0){
            return listeProprietes.elementAt(idIndex).getNom();
        }else{
            return listeProprietes.elementAt(idIndex).getValeurSelectionne();
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        PROPRIETE proSelected = listeProprietes.elementAt(rowIndex);
        proSelected.setValeurSelectionne(aValue);
        fireTableDataChanged();
    }

    public String getColumnName(int columnIndex) {
        return enteteValeur[columnIndex];
    }

    public Class getColumnClass(int columnIndex) {
        return PROPRIETE.class;
    }
    
    

}
