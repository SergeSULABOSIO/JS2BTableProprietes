/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sources.AutoCompletion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Gateway
 */
public class StringSearchable implements Searchable<String, String> {

    private List<String> terms = new ArrayList<String>();

    public StringSearchable(List<String> terms) {
        this.terms.addAll(terms);
    }

    @Override
    public Collection<String> search(String value) {
        List<String> founds = new ArrayList<String>();
        for (String s : terms) {
            if (s.toUpperCase().indexOf(value.toUpperCase()) != -1) {
                founds.add(s);
            }
        }
        return founds;

    }
    
    
    public Vector getListe(){
        Vector lit = new Vector();
        for (int i = 0; i < terms.size(); i++) {
            lit.addElement(terms.get(i));
        }
        return lit;
    }

}
