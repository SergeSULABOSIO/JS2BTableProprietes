/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sources;

import Sources.Ecouteurs.ProprieteAdapter;
import Sources.Ecouteurs.ProprieteEvent;
import Sources.UI.JS2BPanelPropriete;
import java.awt.Font;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;

/**
 *
 * @author Gateway
 */
public class Principale extends javax.swing.JFrame {

    public ImageIcon iconedefaut = null;
    public JS2BPanelPropriete panProp = null;
    private GroupeClasse Gclasse = null;
    private static String BT_TERMINER = "Terminer";
    private static String BT_ANNULER = "Annuler";
    private static String BT_QUITTER = "Quitter";

    public Principale() {
        initComponents();
        iconedefaut = new ImageIcon(getClass().getResource("/IMG/Infos02.png"));
        panProp = new JS2BPanelPropriete(iconedefaut, "New PERSONNE", true);
        if (panProp != null) {
            Gclasse = new GroupeClasse(panProp, false, false, "SIGN01212450505504") {

                @Override
                public void construire() {
                    construireTout();
                }
            };
        }

        //Ecouteur des boutons
        ecouterPropriete();
    }

    private void construireTout() {
        panProp.viderListe();
        //je charge les champs relatives à la classe
        Gclasse.init();
        //Je charge les boutons de conclusion de ce groupe d'enregistrements
        panProp.AjouterSeparateur("ACTION FINAL");
        panProp.AjouterBouton((new ICONES_S2B().BTRECHERCHE_ICONE_01), BT_TERMINER);
        panProp.AjouterBouton((new ICONES_S2B().BTRECHERCHE_ICONE_01), BT_ANNULER);
        panProp.AjouterBouton((new ICONES_S2B().BTRECHERCHE_ICONE_01), BT_QUITTER);
    }

    private void ecouterPropriete() {
        panProp.addProprieteListener(new ProprieteAdapter() {

            @Override
            public void proprieteValidee(ProprieteEvent event) {
                //Ecouter classe
                if (event.getNomBouton().equals(GroupeClasse.BT_IMPORTER_CLASSE)) {
                    Gclasse.Importer();
                }
                if (event.getNomBouton().equals(GroupeClasse.BT_ENREGISTRER_CLASSE)) {
                    Gclasse.Enregistrer();
                }
                if (event.getNomBouton().equals(GroupeClasse.BT_ANNULER_CLASSE)) {
                    Gclasse.Annuler();
                }
                //Autres écouteur
                if (event.getNomBouton().equals("Fermer") || event.getNomBouton().equals("Quitter")) {
                    //jTabbedPane1.removeAll();
                    jTabbedPane1.remove(panProp);
                } else {
                    //System.out.println();
                    //System.out.println("-----------------------");
                    //System.out.println("Evenement capturé :");
                    //System.out.println("Bouton = " + event.getNomBouton());
                    //System.out.println("Serial = " + event.getId());
                    //System.out.println("Nom = " + event.getNom());
                    //System.out.println("Liste des propriétés :");   //+event.getListePro());
                    //for (PROPRIETE prop : event.getListePro()) {
                    //System.out.println(" * " + prop.toString());
                    //}
                }
            }
        });
    }

    public void chargerData() {
        String nomPhoto = (new Date().getTime()) + "";//Photos/Capture"+nomPhoto+".jpg

        CHAMP_LOCAL champ00 = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "Message", "msg", null, "", PROPRIETE.TYPE_SAISIE_TEXTE_MULTIPLES);
        //Photo
        //Exemple de chemin : "F:\\Projets\\S2BFEES\\Photos\\Capture1413282797604.jpg"
        //CHAMP_LOCAL champ000 = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "Photo", "photo", null, "F:\\Projets\\S2BFEES\\Photos\\Capture1413282797604.jpg", PROPRIETE.TYPE_PHOTO);
        Vector villesC = new Vector();
        Vector villeSelected = new Vector();
        villesC.add("KINSHASA");
        villesC.add("KISANGANI");
        villesC.add("LUBUMBASHI");
        villesC.add("GOMA");
        villesC.add("MATADI");
        villesC.add("BOMA");
        villesC.add("KINDU");
        villeSelected.add(villesC.firstElement());

        CHAMP_LOCAL champ0000 = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "Ville cibles", "villes", villesC, villeSelected, PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE);
        Vector lNoms = new Vector();
        CHAMP_LOCAL champ0000A = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "Noms", "noms", null, lNoms, PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE_ALPHA_NUMERIQUE);
        Vector lDates = new Vector();
        CHAMP_LOCAL champ0000B = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "Dates", "dates", null, lDates, PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE_DATE);

        CHAMP_LOCAL champ01 = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "NOM", "nom", null, "", PROPRIETE.TYPE_SAISIE_TEXTE);
        CHAMP_LOCAL champ02 = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "POST-NOM", "postnom", null, "", PROPRIETE.TYPE_SAISIE_TEXTE);
        CHAMP_LOCAL champ03 = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "AGE", "age", null, "", PROPRIETE.TYPE_SAISIE_NUMBRE);
        CHAMP_LOCAL champ04 = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "ENTRE LE", "dateEnreg", null, (new Date()), PROPRIETE.TYPE_CHOIX_DATE);
        CHAMP_LOCAL champ05 = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "ET LE", "dateEnreg", null, (new Date()), PROPRIETE.TYPE_CHOIX_DATE);
        Vector Sexe = new Vector();
        Sexe.addElement("TOUS LES SEXES");
        Sexe.addElement("MASCULAIN");
        Sexe.addElement("FEMININ");
        CHAMP_LOCAL champ06 = new CHAMP_LOCAL(new ICONES_S2B().PISTE_ICONE_01, "SEXE", "sexe", Sexe, Sexe.firstElement(), PROPRIETE.TYPE_CHOIX_LISTE);

        Vector classe = new Vector();
        classe.addElement("TOUTES LES CLASSE");
        classe.addElement("CP");
        classe.addElement("CM1");
        classe.addElement("CM2");
        classe.addElement("GS");
        classe.addElement("MS");
        CHAMP_ETRANGER champ07 = new CHAMP_ETRANGER(new ICONES_S2B().PISTE_ICONE_01, "CLASSE", "idClasse", classe, classe.firstElement(), PROPRIETE.TYPE_OBJET);

        Vector eleves = new Vector();
        eleves.addElement("TOUS LES ELEVES");
        eleves.addElement("1, SULA BOSIO Serge");
        eleves.addElement("2, MUTA KANKUNGWALA Christian");
        eleves.addElement("3, SULA ESUA Yannick");
        eleves.addElement("4, IKEKA LOPOKO Modeste");
        eleves.addElement("5, KALALA KAZADI Kally");
        eleves.addElement("6, ELEMBO ENDJINGA Jean-paul");
        eleves.addElement("7, MUKENGE NZIK Fidele");
        CHAMP_ETRANGER champ08 = new CHAMP_ETRANGER(new ICONES_S2B().PISTE_ICONE_01, "ELEVE", "idEleve", eleves, eleves.firstElement(), PROPRIETE.TYPE_OBJET);

        //je vide d'abord mon modele par mesure de précaution !
        panProp.viderListe();
        //Pour l'instant je charge ce modèle manuellement. Mais normalement ça devra se faire à l'aide d'une boucle
//        panProp.AjouterSeparateur("ACTION FINAL");
        panProp.AjouterBouton((new ICONES_S2B().BTRECHERCHE_ICONE_01), "Fermer");
        panProp.AjouterBouton((new ICONES_S2B().BTRECHERCHE_ICONE_01), "Charger");
        panProp.AjouterSeparateur("UTILISATEUR");
        panProp.AjouterPropriete(champ01, 0);
        panProp.AjouterPropriete(champ02, 0);
        panProp.AjouterPropriete(champ03, 0);
        panProp.AjouterPropriete(champ04, 0);
        panProp.AjouterPropriete(champ05, 0);
        panProp.AjouterSeparateur("PISTE");
        panProp.AjouterPropriete(champ06, 0);
        panProp.AjouterPropriete(champ07, 0);
        panProp.AjouterPropriete(champ08, 0);
        panProp.AjouterPropriete(champ00, 0);
        //panProp.AjouterPropriete(champ000, 0);
        panProp.AjouterPropriete(champ0000, 0);
        panProp.AjouterPropriete(champ0000A, 0);
        panProp.AjouterPropriete(champ0000B, 0);
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
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 307, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Construire");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Constr. dynamique");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Exemple de texte à modifier");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        chargerData();
        panProp.chargerPanel(jTabbedPane1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        chargerBase();
        construireTout();
        panProp.chargerPanel(jTabbedPane1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Principale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principale().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
