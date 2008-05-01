/**
 * Created on Apr 11, 2008
 */
package com.ebessette.stableroommates;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A Target's preference list
 * 
 * @author Eric <dev@ebessette.com>
 */
public class PreferenceList<E> extends LinkedList<Preference<E>> {

	public PreferenceList() {
		super();
	}

	/**
	 * Creates a new Preference List
	 * 
	 * @param preferences
	 *            The list to initialize it with
	 */
	public PreferenceList( PreferenceList<E> preferences ) {
		this();
		this.addAll( preferences );
	}

	/**
	 * Find the preference, in this list, for a specific target.
	 * 
	 * @param t
	 *            The target to look up
	 * @return The preference, or null if not found
	 */
	public Preference<E> findForTarget( Target<E> t ) {

		Preference<E> p = null;

		for ( Iterator<Preference<E>> pi = this.iterator(); pi.hasNext(); ) {
			Preference<E> q = pi.next();
			if ( q.getTarget().equals( t ) ) {
				p = q;
				break;
			}
		}

		return p;
	}
}
