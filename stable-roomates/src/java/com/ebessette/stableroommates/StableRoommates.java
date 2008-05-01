/**
 * Created on Apr 11, 2008
 */
package com.ebessette.stableroommates;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

	/**
	 * The list of elements, each of which has a preference list
	 */
	private List<Target<E>> prefs;

	/**
	 * Creates a new Stable Roommates
	 */
	public StableRoommates( List<Target<E>> p ) throws InvalidPreferencesException {
	}

	/**
	 * Creates a new Stable Roommates
	 */
	public StableRoommates( List<Target<E>> p, Mode mode ) throws InvalidPreferencesException {
		if ( p == null || p.size() % 2 != 0 ) {
			throw new InvalidPreferencesException();
		}

		this.prefs = p;
	}

	/**
	 * Match the elements
	 */
	public void match() throws NoStableMatchingException {

		// DEBUG temp output
		displayPreferences( "Original" );

		Phase1<E> p1 = new Phase1<E>( this.prefs );

		boolean phase1Succeeded = p1.applyPhase1();

		// DEBUG temp output
		displayPreferences( "After Phase 1" );

		if ( !phase1Succeeded ) {
			throw new NoStableMatchingException();
		}

		if ( isSolution() ) {
			return;
		}

		boolean phase2Succeeded = applyPhase2( p1 );

		// DEBUG temp output
		displayPreferences( "After Phase 2" );

		if ( isSolution() ) {
			return;
		}

		// TODO handle single length pref lists

		resolveTies( 0 );

		// DEBUG temp output
		displayPreferences( "After Ties Resolved" );

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
	 * @param count
	 * @return
	 */
	private boolean resolveTies( int count ) {

		List<Target<E>> tied = new LinkedList<Target<E>>( this.prefs );

		// int iteration = 0;

		Target<E> t = null;
		while ( ( t = findWithMultiple( tied ) ) != null ) {

			System.out.println( "t = " + t );

			Preference<E> p = t.getPreferences().getFirst();
			Target<E> s = p.getTarget();
			System.out.println( "	s = " + s );

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
	 * Phase 2 of the Stable Roommates algorithm
	 * 
	 * @return True
	 */
	private boolean applyPhase2( Phase1<E> firstPhase1 ) {

		return false;
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
	 */
	private void displayPreferences( String title ) {

		if ( title != null ) {
			System.out.println( title + ":" );
		}

		for ( Target<E> t : this.prefs ) {
			String output = t.getElement().toString();
			if ( t.getProposal() != null ) {
				output += " [" + t.getProposal().getElement() + "]";
			}
			output += ": ";

			for ( Preference<E> p : t.getPreferences() ) {
				output += " " + p.getTarget().getElement() + " ";
			}

			System.out.println( output );
		}

	}
}
