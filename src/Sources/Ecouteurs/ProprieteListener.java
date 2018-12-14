/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sources.Ecouteurs;

import java.util.EventListener;

/**
 *
 * @author user
 */
public interface ProprieteListener extends EventListener{
    
    public void proprieteValidee(ProprieteEvent event);
    
}
