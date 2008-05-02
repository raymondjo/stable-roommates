/**
 * Created on Apr 11, 2008
 */
package com.ebessette.stableroommates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Eric <dev@ebessette.com>
 */
public class Target<E> {

	/**
	 * The element we're trying to match
	 */
	private E element;

	/**
	 * The preference list for this element
	 */
	private PreferenceList<E> preferences;

	/**
	 * The targets assigned to this one
	 */
	private List<Target<E>> assignments;

	/**
	 * Is this target assigned to another one?
	 */
	int isAssigned;

	/**
	 * Creates a new Target
	 */
	public Target() {
		this.preferences = new PreferenceList<E>();
		this.assignments = new LinkedList<Target<E>>();
		this.isAssigned = 0;
	}

	/**
	 * Creates a
	 * 
	 * @param p
	 */
	public Target( E p ) {
		this();
		this.element = p;
	}

	/**
	 * @return
	 */
	public E getElement() {
		return this.element;
	}

	/**
	 * Gets the preferences
	 * 
	 * @return The preferences
	 */
	public PreferenceList<E> getPreferences() {
		return preferences;
	}

	/**
	 * Sets the preferences
	 * 
	 * @param preferences
	 *            The preferences to set
	 */
	public void setPreferences( PreferenceList<E> preferences ) {
		this.preferences = preferences;
	}

	/**
	 * Assign a target to this one (not necessarily mutual)
	 * 
	 * @param t
	 *            The target to assign
	 */
	public void assign( Target<E> t ) {
		if ( hasAssignment( t ) ) {
			return;
		}

		t.isAssigned++;
		this.assignments.add( t );
	}

	/**
	 * Is this target assigned to someone else?
	 * 
	 * @return
	 */
	public boolean isAssigned() {
		return ( this.isAssigned > 0 );
	}

	/**
	 * Is the target assigned to this one?
	 * 
	 * @param t
	 *            The target to check
	 * @return True if assigned, false if not
	 */
	public boolean hasAssignment( Target<E> t ) {

		return this.assignments.contains( t );
	}

	/**
	 * Remove an assignment, if one exists
	 * 
	 * @param t
	 *            The target to remove
	 */
	public void deassign( Target<E> t ) {
		if ( !hasAssignment( t ) ) {
			return;
		}

		t.isAssigned--;
		this.assignments.remove( t );
	}

	/**
	 * Does this target have multiple assignments?
	 * 
	 * @return
	 */
	public boolean hasMultipleAssignments() {

		return ( this.assignments.size() > 1 );
	}

	/**
	 * Remove all assignments
	 * 
	 * @return The list of assignments before they were cleared
	 */
	public List<Target<E>> clearAssignments() {

		List<Target<E>> al = new ArrayList<Target<E>>();
		al.addAll( this.assignments );

		while ( !this.assignments.isEmpty() ) {
			deassign( this.assignments.get( 0 ) );
		}

		this.assignments.clear();

		return al;
	}

	/**
	 * Get an unmodifiable list of the assignments for this target
	 * 
	 * @return
	 */
	public List<Target<E>> getAssignments() {
		return Collections.unmodifiableList( this.assignments );
	}

	/**
	 * Delete a target from the preference list. Note this is a mutual action,
	 * meaning this target gets deleted from <code>t</code>'s preference list
	 * too.
	 * 
	 * @param t
	 *            The target to delete
	 */
	public void delete( Target<E> t ) {

		Preference<E> p = this.preferences.findForTarget( t );
		if ( p != null ) {
			this.preferences.remove( p );
			t.delete( this );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		if ( getElement() == null ) {
			return null;
		}

		return getElement().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ) {

		if ( !( obj instanceof Target ) || obj == null ) {
			return false;
		}

		Target<E> t = (Target<E>) obj;

		if ( this.getElement() == null && t.getElement() == null ) {
			return true;
		}
		else if ( this.getElement() == null || t.getElement() == null ) {
			return false;
		}

		return this.getElement().equals( t.getElement() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		if ( this.getElement() == null ) {
			return Integer.MIN_VALUE;
		}

		return this.getElement().hashCode();
	}
}
