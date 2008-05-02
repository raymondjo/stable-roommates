/**
 * Created on Apr 11, 2008
 */
package com.ebessette.stableroommates;

/**
 * 
 * @author Eric <dev@ebessette.com>
 */
public class Preference<E> implements Comparable<Preference<E>> {

	/**
	 * The order number, 0 being the most preferred. Cannot be negative.
	 */
	private int orderNum;

	/**
	 * 
	 */
	private Target<E> target;

	/**
	 * Creates a new Preference
	 */
	public Preference() {
		setOrderNum( 0 );
		setTarget( null );
	}

	/**
	 * Creates a new Preference
	 * 
	 * @param num
	 *            The order number
	 * @param e
	 *            The target object
	 */
	public Preference( int num, Target<E> e ) {
		setOrderNum( num );
		setTarget( e );
	}

	/**
	 * Gets the orderNum
	 * 
	 * @return The orderNum
	 */
	public int getOrderNum() {
		return orderNum;
	}

	/**
	 * Sets the orderNum
	 * 
	 * @param orderNum
	 *            The orderNum to set
	 */
	public void setOrderNum( int orderNum ) {
		if ( orderNum < 0 ) {
			orderNum = 0;
		}

		this.orderNum = orderNum;
	}

	/**
	 * Gets the target
	 * 
	 * @return The target
	 */
	public Target<E> getTarget() {
		return target;
	}

	/**
	 * Sets the target
	 * 
	 * @param target
	 *            The target to set
	 */
	public void setTarget( Target<E> target ) {
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ) {

		if ( !( obj instanceof Preference ) || obj == null ) {
			return false;
		}

		Preference<Target<E>> p = (Preference<Target<E>>) obj;

		if ( this.getTarget() == null ) {
			return ( this.getOrderNum() == p.getOrderNum() && p.getTarget() == null );
		}
		else {
			return ( this.getOrderNum() == p.getOrderNum() && this.getTarget().equals( p.getTarget() ) );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		if ( getTarget() == null ) {
			return super.hashCode();
		}

		return getTarget().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "Order Number = " + this.getOrderNum() + ": \n\t" + getTarget().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo( Preference<E> p ) {

		if ( p == null ) {
			return 1;
		}

		return ( this.getOrderNum() - p.getOrderNum() );
	}

}
