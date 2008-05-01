/**
 * Created on Apr 11, 2008
 */
package com.ebessette.stableroommates;

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
	 * The proposed target for this one
	 */
	private Target<E> proposal;

	/**
	 * Creates a new Target
	 */
	public Target() {
		this.proposal = null;
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
	 * @return
	 */
	public Target<E> getProposal() {
		return this.proposal;
	}

	/**
	 * @return
	 */
	public boolean hasProposal() {
		return ( this.proposal != null );
	}

	/**
	 * @param t
	 */
	public void propose( Target<E> t ) {
		this.proposal = t;
	}

	public void delete( Target<E> t ) {

		Preference<E> p = this.preferences.findForTarget( t );
		if ( p != null ) {
			this.preferences.remove( p );
			t.delete( this );
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		if ( getElement() == null ) {
			return null;
		}
		
		return getElement().toString();
	}
}
