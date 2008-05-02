/**
 * Created on Apr 11, 2008
 */
package com.ebessette.stableroommates;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Eric <dev@ebessette.com>
 */
public class StableRoommates<E> {

	/**
	 * The different modes for the algorithm<br>
	 * <ul>
	 * <li>SUPER = Only a super stable matching will be returned, otherwise an
	 * exception will be thrown.</li>
	 * <li>WEAK_UNMATCHED = Weakly stable matchings will be accepted, but
	 * return the preference list without trying to figure out a valid matching.</li>
	 * <li>WEAK_MATCHED = Weakly stable matchings are accepted and try to find
	 * a valid matching using random choices in ties.</li>
	 * </ul>
	 */
	public enum Mode {
		SUPER, WEAK_UNMATCHED, WEAK_MATCHED
	};

	private static final Log LOG = LogFactory.getLog( StableRoommates.class );

	/**
	 * The list of elements, each of which has a preference list
	 */
	private List<Target<E>> prefs;

	/**
	 * The matching mode of this instance
	 */
	private Mode mode;

	/**
	 * Creates a new Stable Roommates
	 */
	public StableRoommates( List<Target<E>> p ) throws InvalidPreferencesException {
		this( p, Mode.WEAK_UNMATCHED );
	}

	/**
	 * Creates a new Stable Roommates
	 */
	public StableRoommates( List<Target<E>> p, Mode mode ) throws InvalidPreferencesException {
		if ( p == null || p.size() % 2 != 0 ) {
			throw new InvalidPreferencesException();
		}

		displayPreferences( "Original", p );

		this.prefs = clonePreferenceList( p );

		// Make sure the preference lists are sorted correctly
		for ( Target<E> t : this.prefs ) {
			t.getPreferences().sort();
		}
	}

	/**
	 * Clone, or deep copy, the preferences list
	 * 
	 * @param preferences
	 *            The original list, which will remain unmodified
	 * @return The newly cloned list
	 */
	private List<Target<E>> clonePreferenceList( List<Target<E>> preferences ) {

		List<Target<E>> newList = new LinkedList<Target<E>>();

		for ( Target<E> t : preferences ) {
			newList.add( new Target<E>( t.getElement() ) );
		}

		for ( Target<E> copyT : newList ) {
			int i = preferences.indexOf( copyT );
			if ( i < 0 ) {
				// Should never get here
				throw new RuntimeException();
			}

			Target<E> t = preferences.get( i );

			for ( Preference<E> p : t.getPreferences() ) {

				Preference<E> copyP = new Preference<E>();
				copyP.setOrderNum( p.getOrderNum() );

				int j = newList.indexOf( p.getTarget() );
				if ( j < 0 ) {
					// Should never get here
					throw new RuntimeException();
				}
				copyP.setTarget( newList.get( j ) );

				copyT.getPreferences().add( copyP );
			}

			for ( Target<E> a : t.getAssignments() ) {

				int j = newList.indexOf( a );
				if ( j < 0 ) {
					// Should never get here
					throw new RuntimeException();
				}
				Target<E> copyA = newList.get( j );

				copyT.assign( copyA );
			}

		}

		return newList;
	}

	/**
	 * Match the elements
	 */
	public void match() throws NoStableMatchingException {

		// DEBUG temp output
		displayPreferences( "Starting List", this.prefs );

		Phase1<E> p1 = new Phase1<E>( this.prefs );

		boolean phase1Succeeded = p1.applyPhase1();

		LOG.debug( "Phase 1? " + phase1Succeeded );

		// DEBUG temp output
		displayPreferences( "After Phase 1", this.prefs );

		if ( !phase1Succeeded && this.mode == Mode.SUPER ) {
			throw new NoStableMatchingException();
		}

		if ( isSolution() || !phase1Succeeded ) {
			return;
		}

		boolean phase2Succeeded = applyPhase2( p1 );

		// DEBUG temp output
		displayPreferences( "After Phase 2", this.prefs );

		if ( isSolution() || this.mode == Mode.WEAK_UNMATCHED ) {
			return;
		}

		// TODO handle single length pref lists

		resolveTies( 0 );

		// DEBUG temp output
		displayPreferences( "After Ties Resolved", this.prefs );

		if ( !phase2Succeeded ) {
			throw new NoStableMatchingException();
		}
	}

	/**
	 * Does everyone's list just include ties?
	 * 
	 * @return True or false
	 */
	private boolean isSolution() {

		for ( Target<E> t : this.prefs ) {
			if ( t.getPreferences().size() > 1 ) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Does everyone's list just include ties?
	 * 
	 * @return True or false
	 */
	private boolean isEveryoneTied() {

		for ( Target<E> t : this.prefs ) {

			int curOrder = -1;
			for ( Preference<E> p : t.getPreferences() ) {

				if ( curOrder < 0 ) {
					curOrder = p.getOrderNum();
				}
				else if ( curOrder != p.getOrderNum() ) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Phase 2 of the Stable Roommates algorithm<br>
	 * Implemented using the pseudo-code in Figure 2
	 * 
	 * @return True
	 */
	private boolean applyPhase2( Phase1<E> firstPhase1 ) {

		// T := super-stable table generated by Phase 1;
		// while some list in T has length > 1 loop
		for ( Target<E> x = findWithMultiple( this.prefs ); x != null; x = findWithMultiple( this.prefs ) ) {

			// x := some participant whose list in T has length > 1;

			// let y in lT (x) be such that fT(y) = x;
			

			// calculate Tx; -- do not halt overall algorithm if a list becomes
			// empty

			// calculate Ty; -- do not halt overall algorithm if a list becomes
			// empty

			// if Tx != [] then

			// T := Tx;

			// else if Ty != [] then

			// T := Ty;

			// else

			// no super-stable matching exists; -- halt here

			// end if;

		} // end loop;

		// T specifies a super-stable matching;

		return true;
	}

	/**
	 * @param count
	 * @return
	 */
	private boolean resolveTies( int count ) {

		List<Target<E>> tied = clonePreferenceList( this.prefs );

		Target<E> t = null;
		while ( ( t = findWithMultiple( tied ) ) != null ) {

			LOG.debug( "t = " + t );

			Preference<E> p = t.getPreferences().getFirst();
			Target<E> s = p.getTarget();
			LOG.debug( " s = " + s );

			ArrayList<Preference<E>> toRetain = new ArrayList<Preference<E>>();
			toRetain.add( p );
			t.getPreferences().retainAll( toRetain );

			p = s.getPreferences().findForTarget( t );

			toRetain = new ArrayList<Preference<E>>();
			toRetain.add( p );
			s.getPreferences().retainAll( toRetain );

			for ( Target<E> x : tied ) {

				if ( x == t || x == s ) {
					continue;
				}

				Preference<E> y = x.getPreferences().findForTarget( s );
				if ( y != null ) {
					x.getPreferences().remove( y );
				}

				y = x.getPreferences().findForTarget( t );
				if ( y != null ) {
					x.getPreferences().remove( y );
				}
			}
		}

		return true;
	}

	/**
	 * Do all the targets in the list given have one or more preferences?
	 * 
	 * @param list
	 *            The list to check
	 * @return True if yes, false if not
	 */
	private boolean hasNoEmptyLists( List<Target<E>> list ) {

		for ( Target<E> t : list ) {
			if ( t.getPreferences().isEmpty() ) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Find a target with more than one preference
	 * 
	 * @param targets
	 * 
	 * @return
	 */
	private Target<E> findWithMultiple( List<Target<E>> targets ) {

		for ( Target<E> t : targets ) {
			if ( t.getPreferences().size() > 1 ) {
				return t;
			}
		}

		return null;
	}

	/**
	 * Debug method to display the preferences
	 * 
	 * @param list
	 */
	private void displayPreferences( String title, List<Target<E>> list ) {

		if ( title != null ) {
			LOG.info( title + ":" );
		}

		if ( list == null ) {
			LOG.info( "	List is null" );
			return;
		}
		else if ( list.isEmpty() ) {
			LOG.info( "	List is empty" );
			return;
		}

		String pattern = "";
		for ( int i = 0; i < ( ( list.size() + 1 ) / 10 + 1 ); i++ ) {
			pattern += "0";
		}
		NumberFormat nf = new DecimalFormat( pattern );

		boolean isElementNumber = Integer.class.isAssignableFrom( list.get( 0 ).getClass() );

		for ( Target<E> t : list ) {
			String output = t.getElement().toString();
			output += ": ";

			for ( Preference<E> p : t.getPreferences() ) {
				output += " (" + nf.format( p.getOrderNum() ) + ")";
				if ( isElementNumber ) {
					output += nf.format( p.getTarget().getElement() );
				}
				else {
					output += p.getTarget().getElement();
				}
				p.getTarget().getElement();
				output += " ";
			}

			LOG.info( output );
		}

	}
}
