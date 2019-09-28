/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sources.UI;

import Sources.Choixmultiple.ModelChoixMultiple;
import Sources.ICONES_S2B;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author user
 */
public class DialChoixMultiple extends javax.swing.JDialog {

    /**
     * Creates new form DialChoixMultiple
     */
    public ModelChoixMultiple modelChoix = null;
    public JTable table = null;
    public PanelValeur2 pan = null;
    public PanelValeur3 panSelected = null;
    private Dimension ecran = Toolkit.getDefaultToolkit().getScreenSize();
    private int X = (int) ecran.getWidth() / 3;
    private int Y = (int) ecran.getHeight() / 4;

    public static final int MODE_CHOIX = 0;
    public static final int MODE_AJOUT_DATES = 1;
    public static final int MODE_AJOUT_AN = 2;

    public int mode = MODE_CHOIX;

    public DialChoixMultiple(java.awt.Frame parent, boolean modal, int mode) {
        super(parent, modal);
        initComponents();
        modelChoix = new ModelChoixMultiple(jList1, LabelInfo);
        pan = new PanelValeur2();
        panSelected = new PanelValeur3();
        construireTable();
        this.setBounds(X, Y, 410, 430);
//        chargerDataExemple();
        rechercher();

        this.mode = mode;
        activerMode();
    }

    public void setMode(int mode) {
        this.mode = mode;
        activerMode();
    }

    private void activer(boolean ok0, boolean ok1, boolean ok2, boolean ok3) {
        jS2bTextField1.setEnabled(ok0);
        Aj01.setEnabled(ok1);
        Enl1.setEnabled(ok1);

        Aj02.setEnabled(ok2);
        Enl2.setEnabled(ok2);
        jDateChooser1.setEnabled(ok3);
        if (ok3 == true) {
            jDateChooser1.setDate(new Date());
        }
    }

    private void activerMode() {
        if (this.mode == MODE_CHOIX) {
            activer(true, false, false, false);
        } else if (this.mode == MODE_AJOUT_AN) {
            activer(true, true, false, false);
        } else if (this.mode == MODE_AJOUT_DATES) {
            activer(false, false, true, true);
        }
    }

    public void setImage(ImageIcon icone) {
        this.pan.setImage(icone);
        this.panSelected.setImage(icone);
    }

    public void vider() {
        modelChoix.vider();
        modelChoix.viderResultat();
    }

    public void AjouterValeur(Object valeur, boolean choix) {
        modelChoix.AjouterValeur(valeur, choix);
    }

    public void setValeursCherchables(Vector liste, Vector listeSelected) {
        if (this.modelChoix != null) {
            for (int i = 0; i < liste.size(); i++) {
                Object OListe = liste.elementAt(i);
                if (listeSelected.contains(OListe)) {
                    modelChoix.AjouterValeur(OListe, true);
                } else {
                    modelChoix.AjouterValeur(OListe, false);
                }
            }
            modelChoix.setListeElementsSelectionnes(listeSelected);
            rechercher();
        }
    }

    public void setValeursSelectionnees(Vector liste) {
        if (this.modelChoix != null) {
            modelChoix.setListeElementsSelectionnes(liste);
        }
    }

    public Vector getValeursSelectionnees() {
        if (this.modelChoix != null) {
            Vector liste = modelChoix.getListeElementsSelectionnes();
            if (liste != null) {
                return liste;
            }
        }
        return null;
    }

    private void chargerDataExemple() {
        setImage((new ICONES_S2B().PISTE_ICONE_01));
        for (int i = 0; i < 10; i++) {
            modelChoix.AjouterValeur("SERGE SULA BOSIO " + i, false);
            modelChoix.AjouterValeur("ELEMBO ENDJINGA JEAN-PAUL " + i, false);
            modelChoix.AjouterValeur("MUKENGUE NZIK FIDELE " + i, true);
            modelChoix.AjouterValeur("MUTA KANKUNGWALA " + i, true);
        }
    }

    private void chargerEditeurEtRendu() {
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        table.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                pan.setEcouterSelection(isSelected, row);
                pan.setNom(value + "");
                return pan;
            }
        });
        //Les dimentions des colonnes de mon tableau
        table.getColumn(modelChoix.entetes[0]).setPreferredWidth(30);
        table.getColumn(modelChoix.entetes[1]).setPreferredWidth(900);
        //rendu de la liste d'élements sélectionnés par l'utilisateur
        jList1.setCellRenderer(new ListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                panSelected.setEcouterSelection(isSelected, index);
                panSelected.setNom(value + "");
                panSelected.setPreferredSize(new Dimension(panSelected.getWidth(), 25));
                return panSelected;
            }
        });
    }

    private void construireTable() {
        table = new JTable(modelChoix);
        table.addKeyListener(new KeyListener() {

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
        table.setRowHeight(25);
        jScrollPane1.setViewportView(table);

    }

    private void rechercher() {
        Thread th = new Thread() {

            @Override
            public void run() {
                modelChoix.initialiser(jS2bTextField1.getText());
            }
        };
        th.start();
    }
    
    private String getValeur(int valeur){
        String text = "";
        if(valeur < 10){
            text = "0"+(valeur);
        }else{
            text = ""+(valeur);
        }
        return text;
    }
    
    
    public String getSDate(Date date) {
        String dateS = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.FRENCH);    //Ce qui était avant : dd/MM/yyyy
        dateS = formatter.format(date);
        return dateS;
    }

    private void AjouterChoix() {
        Object valeur = null;
        if(mode == MODE_AJOUT_AN){
            valeur = jS2bTextField1.getText().trim();
        }
        if (mode == MODE_AJOUT_DATES) {
            Date dateSelected = jDateChooser1.getDate();
            valeur = getSDate(dateSelected);
        }
        System.out.println("Valeur à ajouter = " + valeur);
        jS2bTextField1.setText("");
        modelChoix.AjouterValeur(valeur, true);
        modelChoix.initialiser("");
    }

    private void EnleverChoix() {
        int ligne = table.getSelectedRow();
        if (ligne != -1) {
            Object oSelected = modelChoix.getChoix(ligne);
            if(oSelected != null){
                modelChoix.EnleverValeur(oSelected);
                modelChoix.initialiser("");
            }
        }
    }

    public void fermer() {
        System.out.println("");
        int index = 1;
        for (Object oSelected : this.getValeursSelectionnees()) {
            System.out.println(index + ") " + oSelected);
            index++;
        }
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jS2bTextField1 = new UI.JS2bTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        LabelInfo = new javax.swing.JLabel();
        Aj01 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        Aj02 = new javax.swing.JButton();
        Enl1 = new javax.swing.JButton();
        Enl2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Liste à choix multiple");
        setResizable(false);

        jTabbedPane1.addTab("Résultats de la récherche", jScrollPane1);

        jList1.setToolTipText("Liste d'élements sélectionnés");
        jScrollPane2.setViewportView(jList1);

        jTabbedPane1.addTab("Elements sélectionnés", jScrollPane2);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jS2bTextField1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICONES/BTRECHERCHE/rech01.png"))); // NOI18N
        jS2bTextField1.setTextInitial("Mot clé");
        jS2bTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jS2bTextField1KeyReleased(evt);
            }
        });

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jCheckBox1.setText("Séléctionner tout");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        LabelInfo.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        LabelInfo.setForeground(new java.awt.Color(255, 51, 51));
        LabelInfo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LabelInfo.setText("Prêt.");

        Aj01.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Aj01.setText("Ajouter");
        Aj01.setEnabled(false);
        Aj01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Aj01ActionPerformed(evt);
            }
        });

        jDateChooser1.setDateFormatString("EEE dd MMM yyyy à HH:mm");
        jDateChooser1.setEnabled(false);
        jDateChooser1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        Aj02.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Aj02.setText("Ajouter");
        Aj02.setEnabled(false);
        Aj02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Aj02ActionPerformed(evt);
            }
        });

        Enl1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Enl1.setText("Enlever");
        Enl1.setEnabled(false);
        Enl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Enl1ActionPerformed(evt);
            }
        });

        Enl2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Enl2.setText("Enlever");
        Enl2.setEnabled(false);
        Enl2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Enl2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jS2bTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Aj02, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Aj01))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Enl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Enl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jS2bTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Aj01)
                    .addComponent(Enl1))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Aj02)
                        .addComponent(Enl2)))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelInfo)
                    .addComponent(jCheckBox1))
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        fermer();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        modelChoix.selectionnerTout(jCheckBox1.isSelected());
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jS2bTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jS2bTextField1KeyReleased
        // TODO add your handling code here:
        rechercher();
    }//GEN-LAST:event_jS2bTextField1KeyReleased

    private void Aj01ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aj01ActionPerformed
        // TODO add your handling code here:
        AjouterChoix();
    }//GEN-LAST:event_Aj01ActionPerformed

    private void Enl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Enl1ActionPerformed
        // TODO add your handling code here:
        EnleverChoix();
    }//GEN-LAST:event_Enl1ActionPerformed

    private void Aj02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Aj02ActionPerformed
        // TODO add your handling code here:
        AjouterChoix();
    }//GEN-LAST:event_Aj02ActionPerformed

    private void Enl2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Enl2ActionPerformed
        // TODO add your handling code here:
        EnleverChoix();
    }//GEN-LAST:event_Enl2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialChoixMultiple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialChoixMultiple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialChoixMultiple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialChoixMultiple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialChoixMultiple dialog = new DialChoixMultiple(new javax.swing.JFrame(), true, DialChoixMultiple.MODE_AJOUT_DATES);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aj01;
    private javax.swing.JButton Aj02;
    private javax.swing.JButton Enl1;
    private javax.swing.JButton Enl2;
    public javax.swing.JLabel LabelInfo;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    public javax.swing.JList jList1;
    private UI.JS2bTextField jS2bTextField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
