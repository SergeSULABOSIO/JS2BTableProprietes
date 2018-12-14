/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sources;

import Sources.AutoCompletion.AutocompleteJComboBox;
import Sources.AutoCompletion.PanelAuto;
import Sources.AutoCompletion.StringSearchable;
import Sources.Choixmultiple.DialChoixMultiple;
import Sources.PhotoCapture.DialPhoto;
import Sources.TextArea.DialZonneTexte;
import Sources.UI.JS2BPanelPropriete;
import UI.JS2bTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Gateway
 */
public class EditeurTablePropriete extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {//KeyListener, PropertyChangeListener, 

    private int tailleExemplaire = 15;
    private Dimension ecran = Toolkit.getDefaultToolkit().getScreenSize();
    //LISTE DES CHAMPS ACONSTRUIRE DYNAMIQUEMENT
    private Vector<PROPRIETE> listePro;

    //lES OBJETS D'EDITION
    //Sélecteur des dates
    private JDateChooser champDate = null;//new JDateChooser(new Date());
    //Champ de saisie texte ligne unique
    private JS2bTextField chamTexte = new JS2bTextField();
    //Champ de mot de passe
    private JPasswordField champMotDePasse = new JPasswordField();
    //Champ de saisie texte lignes multiples
    private DialZonneTexte champTexteMutiple = null;//new DialZonneTexte(null, true);
    //Liste des choix unique d'élements simples
    private JComboBox listeChoix = null;//new JComboBox();
    //Liste des choix unique d'élements long
    private AutocompleteJComboBox listeChoixAuto = null;//new AutocompleteJComboBox();
    private PanelAuto panAuto = null;//new PanelAuto(listeChoixAuto);
    //Liste des choix multiples d'élements court ou long
    private DialChoixMultiple listeChoixMultiple = null;//new DialChoixMultiple(null, true, DialChoixMultiple.MODE_CHOIX);
    //Liste des choix multiples d'élements court ou long - AN
    private DialChoixMultiple listeChoixMultiple_AN = null;//new DialChoixMultiple(null, true, DialChoixMultiple.MODE_AJOUT_AN);
    //Liste des choix multiples d'élements court ou long - DATE
    private DialChoixMultiple listeChoixMultiple_DATE = null;//new DialChoixMultiple(null, true, DialChoixMultiple.MODE_AJOUT_DATES);
    //Prenneur des photos
    private DialPhoto champPhoto = null;

    //LES OBJETS DES RENDUS
    //Le panel qui affiche le nom de la propriété
    private PanelPropriete panPro = null;//new PanelPropriete();
    //Le panel qui affiche la valeur de la propriété
    private PanelValeur panVal = new PanelValeur();
    private PanelSeparateur panSep = new PanelSeparateur();
    //Le bouton actualiser
    private JButton boutonActualiser = new JButton("Actualiser");

    private boolean modifie = false;    //Cette variable empêche le programme d'actualiser les liste plusieurs fois en un seul clic

    private int ligneModifiee = -1;
    private static int serial = 0;

    public static EditeurTablePropriete moi = null;

    private JS2BPanelPropriete js2bPanel;
    private PanelCombo panCombo = new PanelCombo();
    private boolean isModifier = false;

    public EditeurTablePropriete(JS2BPanelPropriete js2bPanel, boolean isModifier) {
        setViderListeChoixMultiple();
        setViderListeChoixMultipleAN();
        setViderListeChoixMultipleDATE();

        this.js2bPanel = js2bPanel;
        listePro = new Vector<PROPRIETE>();

        initiliserBouton();
        moi = this;
        this.isModifier = isModifier;
    }

    private void setViderListeChoixMultiple() {
        try {
            this.listeChoixMultiple.vider();
        } catch (Exception e) {
            return;
        }
    }

    private void setViderListeChoixMultipleAN() {
        try {
            this.listeChoixMultiple_AN.vider();
        } catch (Exception e) {
            return;
        }
    }

    private void setViderListeChoixMultipleDATE() {
        try {
            this.listeChoixMultiple_DATE.vider();
        } catch (Exception e) {
            return;
        }
    }

    public int getSerial() {
        return this.serial;
    }

    public EditeurTablePropriete(Vector<PROPRIETE> listePro, JS2BPanelPropriete js2bPanel, boolean iSModifier) {
        setViderListeChoixMultiple();
        this.js2bPanel = js2bPanel;
        this.listePro = listePro;

    }

    private void initiliserBouton() {
        boutonActualiser.setFont(new java.awt.Font("Tahoma", 1, 11));
        boutonActualiser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (modifie == true) {
                    js2bPanel.setActualisation(e.getActionCommand());
                    serial++;
                    modifie = false;
                }

            }
        });
    }

    private void initialiserLesEcouteurs() {
        try {
            champDate.setDate(new Date());
            champDate.setDateFormatString("dd/MM/yyyy HH:mm:ss");
        } catch (Exception e) {
            return;
        }
    }

    public String getDate(Date date) {
        String dateS = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRENCH);    //ce que je désire mettre maintenant pour gérer aussi l'heure yyyy-MM-dd HH:mm:ss
        dateS = formatter.format(date);
        return dateS;
    }

    @Override
    public Object getCellEditorValue() {
        PROPRIETE proEncours = listePro.elementAt(ligneModifiee);
        if (proEncours != null) {
            if (proEncours.getType() == PROPRIETE.TYPE_SAISIE_TEXTE) {
                return chamTexte.getText();
            }else if (proEncours.getType() == PROPRIETE.TYPE_SAISIE_MOT_DE_PASSE) {
                return champMotDePasse.getText();
            } else if (proEncours.getType() == PROPRIETE.TYPE_SAISIE_TEXTE_MULTIPLES) {
                return champTexteMutiple.getTexte();
            } else if (proEncours.getType() == PROPRIETE.TYPE_BOUTON_ACTUALISER) {
                return boutonActualiser.getText();
            } else if (proEncours.getType() == PROPRIETE.TYPE_PHOTO) {
                return champPhoto.getPhoto();
            } else if (proEncours.getType() == PROPRIETE.TYPE_SAISIE_NUMBRE) {
                return chamTexte.getText();
            } else if (proEncours.getType() == PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE) {
                return listeChoixMultiple.getValeursSelectionnees();
            } else if (proEncours.getType() == PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE_ALPHA_NUMERIQUE) {
                return listeChoixMultiple_AN.getValeursSelectionnees();
            } else if (proEncours.getType() == PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE_DATE) {
                return listeChoixMultiple_DATE.getValeursSelectionnees();
            } else if (proEncours.getType() == PROPRIETE.TYPE_CHOIX_DATE) {
                return champDate.getDate();
            } else if (proEncours.getType() == PROPRIETE.TYPE_CHOIX_LISTE) {
                String val = listeChoix.getSelectedItem() + "";
                if (listeChoix.getSelectedItem().equals(proEncours.getValeurs().firstElement())) {
                    val = "";
                }
                return val;
            } else if (proEncours.getType() == PROPRIETE.TYPE_OBJET) {
                if (panAuto.getSeletedItem() != null) {
                    String val = panAuto.getSeletedItem() + "";
                    if (panAuto.getSeletedItem().equals(proEncours.getValeurs().firstElement())) {
                        val = "";
                    }
                    return val;
                } else {
                    return proEncours.getValeurs().firstElement();
                }
            }
        }
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        PROPRIETE proEncours = listePro.elementAt(row);
        switch (proEncours.getType()) {
            case PROPRIETE.TYPE_BOUTON_ACTUALISER:
                modifie = true;
                ligneModifiee = row;
                initiliserBouton();
                boutonActualiser.setText(proEncours.getNom().replaceFirst(PROPRIETE.Prefixe_BT, ""));
                boutonActualiser.setIcon(proEncours.getIcone());
                return boutonActualiser;
            case PROPRIETE.TYPE_SAISIE_MOT_DE_PASSE:
                return editerMotdePasse(row, value, proEncours);
            case PROPRIETE.TYPE_SAISIE_TEXTE:
                return editerJS2BtexteField(row, value, proEncours);
            case PROPRIETE.TYPE_SAISIE_TEXTE_MULTIPLES:
                return editerZonneTexte(row, value, proEncours);
            case PROPRIETE.TYPE_PHOTO:
                return editerPhoto(row, value, proEncours);
            case PROPRIETE.TYPE_SAISIE_NUMBRE:
                return editerJS2BtexteField(row, value, proEncours);
            case PROPRIETE.TYPE_CHOIX_DATE:
                return editerJDatechooser(row, value);
            case PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE:
                return editerListeAChoixMultiple(row, value, proEncours);
            case PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE_ALPHA_NUMERIQUE:
                return editerListeAChoixMultipleAN(row, value, proEncours);
            case PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE_DATE:
                return editerListeAChoixMultipleDATE(row, value, proEncours);
            case PROPRIETE.TYPE_CHOIX_LISTE:
                return editerJComboBox(row, value, proEncours);
            case PROPRIETE.TYPE_OBJET:
                return editerJComboBoxAuto(row, value, proEncours, isSelected);
            default:
                return null;
        }
    }

    private PanelValeur editerListeAChoixMultiple(final int row, final Object value, final PROPRIETE proEncours) {
        Thread th = new Thread() {

            @Override
            public void run() {
                ligneModifiee = row;
                panVal.setNom("Choix ...");
                listeChoixMultiple = new DialChoixMultiple(null, true, DialChoixMultiple.MODE_CHOIX);
                listeChoixMultiple.setTitle(proEncours.getNom() + " - Choix");
                Vector choix = (Vector) proEncours.getValeurSelectionne();
                listeChoixMultiple.setImage(proEncours.getIcone());

                if (proEncours.getType() == PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE) {
                    listeChoixMultiple.setMode(DialChoixMultiple.MODE_CHOIX);
                }
                listeChoixMultiple.setValeursCherchables(proEncours.getValeurs(), choix);
                listeChoixMultiple.show();
                panVal.setNom(choix.size() + " Choix effectué(s).");
            }
        };
        th.start();
        return panVal;
    }

    private PanelValeur editerListeAChoixMultipleAN(final int row, final Object value, final PROPRIETE proEncours) {
        Thread th = new Thread() {

            @Override
            public void run() {
                ligneModifiee = row;
                panVal.setNom("Choix ...");
                if (listeChoixMultiple_AN == null) {
                    listeChoixMultiple_AN = new DialChoixMultiple(null, true, DialChoixMultiple.MODE_AJOUT_AN);
                }
                listeChoixMultiple_AN.setTitle(proEncours.getNom() + " - Choix");
                Vector choix = (Vector) proEncours.getValeurSelectionne();
                listeChoixMultiple_AN.setImage(proEncours.getIcone());

                if (proEncours.getType() == PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE_ALPHA_NUMERIQUE) {
                    listeChoixMultiple_AN.setMode(DialChoixMultiple.MODE_AJOUT_AN);
                }
                listeChoixMultiple_AN.setValeursCherchables(proEncours.getValeurs(), choix);
                listeChoixMultiple_AN.show();
                panVal.setNom(choix.size() + " Choix effectué(s).");
            }
        };
        th.start();
        return panVal;
    }

    private PanelValeur editerListeAChoixMultipleDATE(final int row, final Object value, final PROPRIETE proEncours) {
        Thread th = new Thread() {

            @Override
            public void run() {
                ligneModifiee = row;
                panVal.setNom("Choix ...");
                if (listeChoixMultiple_DATE == null) {
                    listeChoixMultiple_DATE = new DialChoixMultiple(null, true, DialChoixMultiple.MODE_AJOUT_DATES);
                }
                listeChoixMultiple_DATE.setTitle(proEncours.getNom() + " - Choix");
                Vector choix = (Vector) proEncours.getValeurSelectionne();
                listeChoixMultiple_DATE.setImage(proEncours.getIcone());

                if (proEncours.getType() == PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE_DATE) {
                    listeChoixMultiple_DATE.setMode(DialChoixMultiple.MODE_AJOUT_DATES);
                }
                listeChoixMultiple_DATE.setValeursCherchables(choix, choix);
                listeChoixMultiple_DATE.show();
                panVal.setNom(choix.size() + " Choix effectué(s).");
            }
        };
        th.start();
        return panVal;
    }

    private PanelValeur editerZonneTexte(final int row, final Object value, final PROPRIETE proEncours) {
        Thread th = new Thread() {

            @Override
            public void run() {
                ligneModifiee = row;
                panVal.setNom("Edition ...");
                if (champTexteMutiple == null) {
                    champTexteMutiple = new DialZonneTexte(null, true, 160);
                }
                champTexteMutiple.setTitre(proEncours.getNom());
                champTexteMutiple.setTexte(value + "");
                champTexteMutiple.show();
                panVal.setNom(getTexteCourt(champTexteMutiple.getTexte()));
            }
        };
        th.start();
        return panVal;
    }

    private PanelValeur editerPhoto(final int row, final Object value, final PROPRIETE proEncours) {
        Thread th = new Thread() {

            @Override
            public void run() {
                ligneModifiee = row;
                panVal.setNom("Edition ...");
                if ((proEncours.getValeurSelectionne() + "").length() == 0) {
                    String d = (new Date().getTime()) + "";
                    proEncours.setValeurSelectionne("Photos/Capture" + d + ".jpg");
                }
                champPhoto = new DialPhoto(null, true);
                champPhoto.setActiver(!isModifier);
                champPhoto.setPhoto(proEncours.getValeurSelectionne() + "");
                champPhoto.show();
                panVal.setNom(getTexteCourt(champPhoto.getPhoto()));
            }
        };
        th.start();
        return panVal;
    }

    private JS2bTextField editerJS2BtexteField(final int row, final Object value, final PROPRIETE proEncours) {
        Thread th = new Thread() {

            @Override
            public void run() {
                ligneModifiee = row;
                chamTexte.setIcon(proEncours.getIcone());
                if ((value + "").length() != 0) {
                    chamTexte.setText(value + "");
                } else {
                    chamTexte.setText("");
                }
            }
        };
        th.start();
        return chamTexte;
    }
    
    private JPasswordField editerMotdePasse(final int row, final Object value, final PROPRIETE proEncours) {
        Thread th = new Thread() {

            @Override
            public void run() {
                ligneModifiee = row;
                if ((value + "").length() != 0) {
                    champMotDePasse.setText(value + "");
                } else {
                    champMotDePasse.setText("");
                }
            }
        };
        th.start();
        return champMotDePasse;
    }

    private void setRendu(final ImageIcon icone) {
        listeChoix.setRenderer(new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                panCombo.setIcone(icone);
                panCombo.setNom(value + "");
                panCombo.sentirSelection(isSelected, index);
                return panCombo;
            }
        });
    }

    private JComboBox editerJComboBox(int row, Object value, PROPRIETE proEncours) {
        ligneModifiee = row;
        if (listeChoix == null) {
            listeChoix = new JComboBox();
        }
        setRendu(proEncours.getIcone());
        listeChoix.removeAllItems();
        for (Object o : proEncours.getValeurs()) {
            listeChoix.addItem(o);
        }
        listeChoix.setSelectedItem(value);
        fireEditingStopped();
        return listeChoix;
    }

    private void gererSelectionPanelObjetAuto(PanelAuto pan) {
        pan.setCouleurs(Color.BLACK, Color.YELLOW);
    }

    private PanelAuto editerJComboBoxAuto(int row, Object value, final PROPRIETE proEncours, boolean isSelected) {
        ligneModifiee = row;
        try {
            if(panAuto == null){
                listeChoixAuto = new AutocompleteJComboBox();
                panAuto = new PanelAuto(listeChoixAuto);
            }
            listeChoixAuto.removeAllItems();
            List<String> myWords = new ArrayList<String>();
            for (Object o
                    : proEncours.getValeurs()) {
                myWords.add(o + "");
            }
            StringSearchable searchable = new StringSearchable(myWords);

            listeChoixAuto.setSearchable(searchable);

            panAuto.setImage(proEncours.getIcone());
            panAuto.setDialogueEtListe(myWords, proEncours);

            gererSelectionPanelObjetAuto(panAuto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return panAuto;
    }

    private JDateChooser editerJDatechooser(int row, Object value) {
        ligneModifiee = row;
        Date dateActuelle = (Date) value;
        if (champDate == null) {
            champDate = new JDateChooser(new Date());
            initialiserLesEcouteurs();
        }
        if (dateActuelle != null) {
            champDate.setDate(dateActuelle);
        } else {
            champDate.setDate(new Date());
        }
        fireEditingStopped();
        return champDate;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        ligneModifiee = row;
        serial++;
        if(panPro == null){
            panPro = new PanelPropriete();
        }
        if (column == 0) {
            String titreBt = value+"";
            
            if(titreBt.startsWith(PROPRIETE.Prefixe_BT)){
                return new JLabel();
            }else if(titreBt.startsWith(PROPRIETE.Prefixe_SP)){
                afficherSeparateur("");
                return panSep;
            }else{
                panPro.setNom(value + " ");
                gererSelectionPanelProp(isSelected, row, panPro);
                return panPro;
            }
        } else {
            PROPRIETE proEncours = listePro.elementAt(row);
            switch (proEncours.getType()) {
                case PROPRIETE.TYPE_BOUTON_ACTUALISER:
                    initiliserBouton();
                    boutonActualiser.setText(proEncours.getNom().replaceFirst(PROPRIETE.Prefixe_BT, ""));
                    boutonActualiser.setIcon(proEncours.getIcone());
                    return boutonActualiser;
                case PROPRIETE.TYPE_SAISIE_MOT_DE_PASSE:
                    afficherValeurMotDePasse(proEncours, isSelected, row, value);
                    return panVal;
                case PROPRIETE.TYPE_SAISIE_TEXTE:
                    afficherValeurTexte(proEncours, isSelected, row, value);
                    return panVal;
                case PROPRIETE.TYPE_SAISIE_TEXTE_MULTIPLES:
                    String texteCourt = ((value + ""));//.substring(0, 10))+"...";
                    afficherValeurTexte(proEncours, isSelected, row, texteCourt);
                    return panVal;
                case PROPRIETE.TYPE_SAISIE_NUMBRE:
                    afficherValeurTexte(proEncours, isSelected, row, value);
                    return panVal;
                case PROPRIETE.TYPE_CHOIX_DATE:
                    afficherValeurDate(proEncours, isSelected, row, value);
                    return panVal;
                case PROPRIETE.TYPE_CHOIX_LISTE:
                    afficherComboBox(proEncours, isSelected, row, value);
                    return panVal;
                case PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE:
                    afficherListeChoixMultiple(proEncours, isSelected, row, value);
                    return panVal;
                case PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE_ALPHA_NUMERIQUE:
                    afficherListeChoixMultiple(proEncours, isSelected, row, value);
                    return panVal;
                case PROPRIETE.TYPE_CHOIX_LISTE_MULTIPLE_DATE:
                    afficherListeChoixMultiple(proEncours, isSelected, row, value);
                    return panVal;
                case PROPRIETE.TYPE_OBJET:
                    afficherComboBox(proEncours, isSelected, row, value);
                    return panVal;
                case PROPRIETE.TYPE_PHOTO:
                    afficherValeurTexte(proEncours, isSelected, row, value);
                    return panVal;
                default:
                    afficherSeparateur(proEncours.getNom().replaceFirst(PROPRIETE.Prefixe_SP, ""));
                    return panSep;
            }

        }
    }

    private void afficherValeurTexte(PROPRIETE proEncours, boolean isSelected, int row, Object value) {
        panVal.setImage(proEncours.getIcone());
        gererSelectionPanelVal(isSelected, row, panVal);
        if ((value + "").length() != 0) {
            String valeur = (value + "").trim();
            panVal.setNom(valeur);
        } else {
            panVal.setCouleurTexte(Color.gray);
            panVal.setNom("Saisissez");
        }
    }
    
    private void afficherSeparateur(String nom) {
        gererSelectionPanelVal(panSep);
        panSep.setNom(nom);
    }
    
    private void afficherValeurMotDePasse(PROPRIETE proEncours, boolean isSelected, int row, Object value) {
        panVal.setImage(proEncours.getIcone());
        gererSelectionPanelVal(isSelected, row, panVal);
        String avatar = "*******";
        if ((value + "").length() != 0) {
            panVal.setNom(avatar);
        } else {
            panVal.setCouleurTexte(Color.gray);
            panVal.setNom(avatar);
        }
    }

    public String getTexteCourt(String textelong) {
        String tex = textelong;
        if (tex.length() >= tailleExemplaire) {
            tex = tex.substring(0, tailleExemplaire);
            tex = tex + "...";
        }
        return tex;
    }

    private void afficherListeChoixMultiple(PROPRIETE proEncours, boolean isSelected, int row, Object value) {
        panVal.setImage(proEncours.getIcone());
        gererSelectionPanelVal(isSelected, row, panVal);
        Vector listeElementsSelected = (Vector) value;
        if (!listeElementsSelected.isEmpty()) {
            //Je revoi la taille du texte qui sera affiché
            String valeurSelection = (listeElementsSelected.size() + " " + proEncours.getNom());
            panVal.setNom(valeurSelection + "");
        } else {
            panVal.setCouleurTexte(Color.gray);
            panVal.setNom("Choisissez");
        }
    }

    private void afficherComboBox(PROPRIETE proEncours, boolean isSelected, int row, Object value) {
        panVal.setImage(proEncours.getIcone());
        gererSelectionPanelVal(isSelected, row, panVal);
        if ((value + "").trim().length() != 0) {
            if (value.equals(proEncours.getValeurs().firstElement())) {
                panVal.setCouleurTexte(Color.gray);
            }
            //Je revoi la taille du texte qui sera affiché
            panVal.setNom(value + "");
        } else {
            panVal.setCouleurTexte(Color.gray);
            panVal.setNom("Choisissez");
        }
    }

    private void afficherValeurDate(PROPRIETE proEncours, boolean isSelected, int row, Object value) {
        panVal.setImage(proEncours.getIcone());
        gererSelectionPanelVal(isSelected, row, panVal);
        if ((value + "").length() != 0) {
            Date dateEncours = (Date) value;
            panVal.setNom(getDate(dateEncours));
        } else {
            panVal.setCouleurTexte(Color.gray);
            panVal.setNom("...");
        }
    }

    private void gererSelectionPanelProp(boolean isSelected, int row, PanelPropriete pan) {
        if (isSelected == true) {
            pan.setCouleurs(Color.BLACK, Color.YELLOW);
        } else {
            if ((row % 2) == 0) {
                pan.setCouleurs(new Color(220, 220, 220), Color.BLACK);
            } else {
                pan.setCouleurs(Color.WHITE, Color.black);//Activer uniquement cette ligne afin que les elements de la liste ne s'affichent qu'en blanc
            }
        }
    }

    private void gererSelectionPanelVal(boolean isSelected, int row, PanelValeur pan) {
        if (isSelected == true) {
            pan.setCouleurs(Color.BLACK, Color.YELLOW);
        } else {
            if ((row % 2) == 0) {
                pan.setCouleurs(new Color(220, 220, 220), Color.BLUE);
            } else {
                pan.setCouleurs(Color.WHITE, Color.BLUE);//Activer uniquement cette ligne afin que les elements de la liste ne s'affichent qu'en blanc
            }
        }
    }
    
    private void gererSelectionPanelVal(PanelSeparateur pan) {
        pan.setCouleurs(Color.DARK_GRAY, Color.YELLOW);
    }

    public static void stopperEdition() {
        moi.fireEditingStopped();
    }

}
