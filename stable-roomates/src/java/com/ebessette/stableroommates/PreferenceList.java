/**
 * Created on Apr 11, 2008
 */
package com.ebessette.stableroommates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

	/**
	 * Sort this list in the correct order
	 */
	public void sort() {
		Collections.sort( this );
	}

	/**
	 * Get a list of targets that are tied for first in this preference list<br>
	 * The list should never be empty if used properly, but it WILL never be
	 * null.
	 * 
	 * @return The list of targets tied for the head of the list
	 */
	public List<Target<E>> getHeadList() {

		List<Target<E>> targets = new ArrayList<Target<E>>();

		if ( this.isEmpty() ) {
			return targets;
		}

		int firstOrderNum = this.getFirst().getOrderNum();
		for ( Preference<E> p : this ) {
			if ( p.getOrderNum() > firstOrderNum ) {
				break;
			}

			targets.add( p.getTarget() );
		}

		return targets;
	}
}
