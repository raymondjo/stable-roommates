/**
 * Created on Apr 11, 2008
 */
package com.ebessette.stableroommates;

import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Phase 1 of the Stable Roommates algorithm<br>
 * Based heavily off of David Manlove's implementation
 * 
 * @author Eric <dev@ebessette.com>
 */
class Phase1<E> {

	/**
	 * The log for this class
	 */
	private static final Log LOG = LogFactory.getLog( Phase1.class );

	/**
	 * The targets which contain the element and preference lists
	 */
	private List<Target<E>> targets;

	/**
	 * Stack of free targets
	 */
	private Stack<Target<E>> free;

	/**
	 * Creates a new Phase 1
	 */
	public Phase1( List<Target<E>> prefs ) {
		this.targets = prefs;
		this.free = new Stack<Target<E>>();

		this.free.addAll( targets );
	}

	/**
	 * Phase 1 of the Stable Roommates algorithm<br>
	 * Implemented using the pseudo-code in Figure 1
	 * 
	 * @return boolean indicating whether Phase 1 terminated due to an empty
	 *         list or the proposal stack becoming empty
	 */
	public boolean applyPhase1() {

		Target<E> p = this.free.pop();

		// while some participant p has a non-empty list and p is not assigned
		// to anyone loop
		while ( p != null && !p.getPreferences().isEmpty() && !p.isAssigned() ) {
			LOG.debug( "p: " + p );

			// for each q at the head of p's list loop [that's tied]
			List<Target<E>> headPrefs = p.getPreferences().getHeadList();
			for ( Target<E> q : headPrefs ) {
				LOG.debug( " q: " + q );

				Preference<E> pp_q = q.getPreferences().findForTarget( p );
				// p HAS to be in q's preference list to be a possible match
				if ( pp_q == null ) {
					LOG.debug( " - p deletes q" );
					p.delete( q );
					continue;
				}

				// assign p to q; -- p `proposes' to q
				q.assign( p );

				// for each strict successor r of p in q's list loop
				for ( int i = 0; i < q.getPreferences().size(); i++ ) {
					Preference<E> pr_q = q.getPreferences().get( i );

					// Strict successor test
					if ( pr_q.getOrderNum() <= pp_q.getOrderNum() ) {
						continue;
					}

					Target<E> r = pr_q.getTarget();
					LOG.debug( "  r: " + r );

					// if r is assigned to q then
					if ( q.hasAssignment( r ) ) {
						// break the assignment;
						LOG.debug( "  - q deassigns r" );
						q.deassign( r );
						this.free.push( r );
					} // end if;

					// delete the pair {q, r};
					LOG.debug( "  - q deletes r" );
					q.delete( r );
					i--;

					// if r's list is empty then
					if ( r.getPreferences().isEmpty() ) {
						// no super-stable matching exists;
						return false;
					} // end if;
				} // end loop;

				// if >= 2 participants are assigned to q then
				if ( q.hasMultipleAssignments() ) {
					LOG.debug( " - q has multiple assignments" );

					// break all assignments to q;
					q.clearAssignments();

					// for each r tied with p in q's list loop;
					for ( int i = 0; i < q.getPreferences().size(); i++ ) {
						Preference<E> pr_q = q.getPreferences().get( i );

						// Only delete preferences of equal order number
						if ( pr_q.getOrderNum() < pp_q.getOrderNum() ) {
							continue;
						}
						else if ( pr_q.getOrderNum() < pp_q.getOrderNum() ) {
							break;
						}

						Target<E> r = pr_q.getTarget();
						LOG.debug( "  r: " + r );

						// delete the pair {q, r};
						LOG.debug( "  - q deletes r" );
						q.delete( r );
						i--;

						// if r's list is empty then
						if ( r.getPreferences().isEmpty() ) {
							// no super-stable matching exists;
							return false;
						} // end if;
					} // end loop;

					// if q's list is empty then
					if ( q.getPreferences().isEmpty() ) {
						// no super-stable matching exists;
						return false;
					}// end if;
				} // end if;
			} // end loop;

			if ( this.free.isEmpty() ) {
				p = null;
			}
			else {
				p = this.free.pop();
			}
		} // end loop;

		return true;
	}
}
