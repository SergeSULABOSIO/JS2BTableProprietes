/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sources.UI;


import Sources.Ecouteurs.ProprieteEvent;
import Sources.Ecouteurs.ProprieteListener;
import Sources.EditeurTablePropriete;
import Sources.ModelPropriete;
import Sources.PROPRIETE;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Thread.sleep;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Gateway
 */
public class JS2BPanelPropriete extends javax.swing.JPanel {

    /**
     * Creates new form JS2BPanelPropriete
     */
    private boolean isModifier = false;
    public ModelPropriete model = new ModelPropriete();
    private Vector<PROPRIETE> listePro = new Vector<PROPRIETE>();
    public String nomTable;
    private EditeurTablePropriete ed1 = null;
    private EditeurTablePropriete ed2 = null;
    public PanelTransition transition = new PanelTransition();
    private JS2BPanelPropriete moi = null;
    private ImageIcon icone = null;
    private final EventListenerList listeners = new EventListenerList();
    

    public JS2BPanelPropriete(ImageIcon icone, String nomTable, boolean isModifier) {
        initComponents();
        this.nomTable = nomTable;
        this.transition.setNomTable(this.nomTable);
        this.moi = this;
        this.isModifier = isModifier;
        this.icone = icone;
    }

    public boolean isIsModifier() {
        return isModifier;
    }

    public void setIsModifier(boolean isModifier) {
        this.isModifier = isModifier;
    }
    
    private void removeAllPanelProprietes(JTabbedPane tabbed){
        for(Component c: tabbed.getComponents()){
            if(c instanceof JS2BPanelPropriete){
                try{
                    tabbed.remove(c);
                }catch(Exception e){
                    e.printStackTrace();
                }
                
            }
        }
    }

    public void chargerPanel(final JTabbedPane tabbed) {
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    if (tabbed != null) {
                        removeAllPanelProprietes(tabbed);
                        construireTable();
                        //System.out.println("|| ** On attent 100 millis sécondes...");
                        sleep(100);
                        if (moi != null) {
                            tabbed.add("Propriétés - " + nomTable, moi);
                            tabbed.setSelectedComponent(moi);
                            int idexMoi = tabbed.getComponentZOrder(moi);
                            tabbed.setIconAt(idexMoi, icone);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

        };
        th.start();

    }

    public void AjouterPropriete(PROPRIETE prop, int mode) {
        if (!listePro.contains(prop)) {
            //Les objets ne doivent être visualisés que sur JtexArea
            if (mode == 2 && prop.getType() == PROPRIETE.TYPE_OBJET) {
                prop.setType(PROPRIETE.TYPE_SAISIE_TEXTE_MULTIPLES);
            }
            listePro.add(prop);
            model.setListeProprietes(listePro);
        }
    }

    public void actualiser() {
        model.initialiser();
    }

    public void AjouterPropriete(PROPRIETE prop) {
        if (!listePro.contains(prop)) {
            listePro.add(prop);
            model.setListeProprietes(listePro);
        }
        actualiser();
    }

    public void AjouterBouton(ImageIcon image, String texte) {
        PROPRIETE proBouton = new PROPRIETE(image, PROPRIETE.Prefixe_BT + texte, texte, null, null, PROPRIETE.TYPE_BOUTON_ACTUALISER);
        if (!listePro.contains(proBouton)) {
            listePro.add(proBouton);
            model.setListeProprietes(listePro);
        }
        actualiser();
    }

    public void AjouterSeparateur(String texte) {
        PROPRIETE proSeperateur = new PROPRIETE(null, PROPRIETE.Prefixe_SP + texte, texte, null, null, PROPRIETE.TYPE_SEPARATEUR);
        listePro.add(proSeperateur);
        model.setListeProprietes(listePro);
        actualiser();
    }

    public ModelPropriete getModel() {
        return model;
    }

    public void setModel(ModelPropriete model) {
        this.model = model;
    }

    public Vector<PROPRIETE> getListePro() {
        return listePro;
    }
    
    public PROPRIETE getPropriete(String nom) {
        for(PROPRIETE prop: listePro){
            if(nom.equals(prop.getNom())){
                return prop;
            }
        }
        return null;
    }
    
    public void setListePro(Vector<PROPRIETE> listePro) {
        this.listePro = listePro;
    }

    public String getNomTable() {
        return nomTable;
    }

    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
        transition.setNomTable(this.nomTable);
    }

    private void chargerEditeurEtRendu() {
        ed1 = new EditeurTablePropriete(model.getListeProprietes(), this, isModifier);
        ed2 = new EditeurTablePropriete(model.getListeProprietes(), this, isModifier);

        tableauPrincipal.getColumnModel().getColumn(0).setCellRenderer(new EditeurTablePropriete(this, isModifier));
        tableauPrincipal.getColumnModel().getColumn(1).setCellEditor(ed1);
        tableauPrincipal.getColumnModel().getColumn(1).setCellRenderer(ed2);

        tableauPrincipal.getColumn(model.enteteValeur[0]).setPreferredWidth(150);
        tableauPrincipal.getColumn(model.enteteValeur[1]).setPreferredWidth(300);
    }

    public void viderListe() {
        model.vider();
    }

    public synchronized void construireTable() {
        //System.out.println("|| ** Construction de la table des Proprietes");
        tableauPrincipal = new JTable(model);
        tableauPrincipal.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
//                System.out.println("Ecouter TABLE = Saisie !");
            }

            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println("Ecouter TABLE = enfoncé !");
            }

            @Override
            public void keyReleased(KeyEvent e) {
//                System.out.println("Ecouter TABLE = relaché !");
            }
        });
        chargerEditeurEtRendu();
        tableauPrincipal.setRowHeight(25);
        jScrollPane1.setViewportView(tableauPrincipal);
        //System.out.println("|| ** FIN de la Construction de la table des Proprietes");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableauPrincipal = new javax.swing.JTable();

        tableauPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableauPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tableauPrincipal;
    // End of variables declaration//GEN-END:variables

    //Méthodes pour la gestion des écouteurs d'actions
    public void setActualisation(String nomBouton) {
        fireProprieteChanged(ed1.getSerial(), nomTable, nomBouton, model.getListeProprietes());
    }

    public void addProprieteListener(ProprieteListener listener) {
        listeners.add(ProprieteListener.class, listener);
    }

    public void removeProprieteListener(ProprieteListener listener) {
        listeners.remove(ProprieteListener.class, listener);
    }

    public ProprieteListener[] getProprieteListener() {
        return listeners.getListeners(ProprieteListener.class);
    }

    protected void fireProprieteChanged(int id, String nom, String nomBouton, Vector<PROPRIETE> listePro) {
        ProprieteEvent event = null;
        //System.out.println("fire !");
        for (ProprieteListener listener : getProprieteListener()) {
            if (event == null) {
                event = new ProprieteEvent(id, nom, nomBouton, listePro);
            }
            listener.proprieteValidee(event);
        }
    }

}
