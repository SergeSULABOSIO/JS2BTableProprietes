/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sources.AutoCompletion;
import java.util.Collection;
import java.util.Vector;

/**
 *
 * @author Gateway
 */
public interface Searchable<E, V>{
	public Collection<E> search(V value);
        
        public Vector getListe();
        
}

