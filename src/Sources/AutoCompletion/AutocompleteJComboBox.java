/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sources.AutoCompletion;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Gateway
 */
public class AutocompleteJComboBox extends JComboBox {

    static final long serialVersionUID = 4321421L;
    private Searchable<String, String> searchable;
    private Vector ListeCherch = new Vector();
    public DialAuto dial = null;
    private PanelCombo pan = new PanelCombo();
    private ImageIcon icone = null;

    /**
     *
     * Constructs a new object based upon the parameter searchable
     *
     * @param dial
     * @param icone
     *
     */
    public void setDialogue(DialAuto dial, final ImageIcon icone) {
        this.dial = dial;
        this.icone = icone;
        this.setRenderer(new ListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                pan.setTexte(value+"");
                pan.setIcone(icone);
                pan.sentirSelection(isSelected, index);
                return pan;
            }
        });
        
    }

    public AutocompleteJComboBox() {
        super();
//        this.searchable = s;
        setEditable(true);
        Component c = getEditor().getEditorComponent();

        if (c instanceof JTextComponent) {
            final JTextComponent tc = (JTextComponent) c;
            tc.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void changedUpdate(DocumentEvent arg0) {
//                    update();
                }

                @Override
                public void insertUpdate(DocumentEvent arg0) {
                    update();
                }

                @Override
                public void removeUpdate(DocumentEvent arg0) {
                    update();
                }

                public void update() {
                    //perform separately, as listener conflicts between the editing component
                    //and JComboBox will result in an IllegalStateException due to editing 
                    //the component when it is locked. 
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            List<String> founds = new ArrayList<String>(searchable.search(tc.getText()));
                            Set<String> foundSet = new HashSet<String>();

                            for (String s : founds) {
                                foundSet.add(s.toLowerCase());
                            }

                            Collections.sort(founds);//sort alphabetically
                            setEditable(false);
                            removeAllItems();
                            //if founds contains the search text, then only add once.
                            if (!foundSet.contains(tc.getText())) {
                                addItem(tc.getText());
                            }

                            for (String s : founds) {
                                addItem(s);
                            }

                            try {
                                setEditable(true);
                                setPopupVisible(true);
                            } catch (Exception e) {
                                return;
                            }

                        }
                    });
                }
            });

            //When the text component changes, focus is gained 
            //and the menu disappears. To account for this, whenever the focus
            //is gained by the JTextComponent and it has searchable values, we show the popup.
            tc.addFocusListener(new FocusListener() {

                @Override
                public void focusGained(FocusEvent arg0) {
                    if (tc.getText().length() > 0) {
                        setPopupVisible(true);
                    }
                }

                @Override
                public void focusLost(FocusEvent arg0) {

                }
            });

            tc.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        ENTER();
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        ESCAPE();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        } else {
            throw new IllegalStateException("Editing component is not a JTextComponent!");
        }
    }

    private void ENTER() {
        if (!ListeCherch.contains(getSelectedItem())) {
            setSelectedItem(ListeCherch.firstElement());
        }
//        System.out.println("Vous avez choisi = " + getSelectedItem());
        dial.combo.setSelectedItem(getSelectedItem());
        if (dial != null) {
            dial.dispose();
        }
    }

    private void ESCAPE() {
        if ((getSelectedItem() == null) || (!ListeCherch.contains(getSelectedItem()))) {
            setSelectedItem(ListeCherch.firstElement());
        }
//        System.out.println("ECHAP = " + getSelectedItem());
        if (dial != null) {
            dial.dispose();
        }
    }

    public void setSearchable(Searchable<String, String> s) {
        this.searchable = s;
        this.ListeCherch = s.getListe();
    }

}
