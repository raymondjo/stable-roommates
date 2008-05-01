/**
 * Created on Apr 11, 2008
 */
package com.ebessette.stableroommates;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Phase 1 of the Stable Roommates algorithm
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
	 * Players who can freely be matched
	 */
	private Stack<Target<E>> free;

	/**
	 * Should we skip over certain bits of Phase 1?
	 */
	boolean skipOver;

	/**
	 * Creates a new Phase 1
	 */
	public Phase1( List<Target<E>> prefs ) {
		this.targets = prefs;
		this.free = new Stack<Target<E>>();
		this.skipOver = false;

		// Initialize the stack
		this.free.addAll( this.targets );
	}

	/**
	 * Do phase 1
	 * 
	 * @param addToDelStack
	 * @param synchronise
	 * @return
	 */
	public boolean applyPhase1() {

		// If stack is not empty
		while ( !this.free.isEmpty() ) {

			Target<E> tp = this.free.pop();
			LOG.debug( "p: " + tp.getElement() );

			// If a target's preferences are empty, then no solution can be
			// found
			if ( tp.getPreferences().isEmpty() ) {
				return false;
			}

			// q is p's first preference
			Preference<E> pq = tp.getPreferences().getFirst();
			Target<E> tq = pq.getTarget();

			LOG.debug( "q:" + tq.getElement() );

			// If worst proposal, then re-add to stack
			if ( tq.hasProposal() ) {
				if ( tq.getPreferences().findForTarget( tq.getProposal() ).getOrderNum() > tq.getPreferences()
						.findForTarget( tp ).getOrderNum() ) {
					this.free.push( tq.getProposal() );
				}
			}

			// p proposes to q;
			tq.propose( tp );

			// Get the order num for p in q's list (should never be null)
			Preference<E> tqpp = tq.getPreferences().findForTarget( tp );
			int orderNumP = Integer.MAX_VALUE;
			if ( tqpp != null ) {
				orderNumP = tqpp.getOrderNum();
			}

			PreferenceList<E> tqp = new PreferenceList<E>( tq.getPreferences() );

			// -- for each strict successor r of p in q's list loop
			for ( Iterator<Preference<E>> pqpi = tqp.iterator(); pqpi.hasNext(); ) {

				Preference<E> pr = pqpi.next();
				Target<E> tr = pr.getTarget();

				// Continue if r's order number is less than or equal to p's
				// in q's list
				if ( pr.getOrderNum() <= orderNumP ) {
					continue;
				}

				LOG.debug( "r:" + tr.getElement() );

				// -- if r is assigned to q then
				if ( tq.getProposal().equals( pr.getTarget() ) ) {
					// -- break the assignment
					tq.propose( null );
				} // -- end if

				// -- delete the pair {q,r}
				tq.delete( tr );

				// -- if r's list is empty then
				if ( tr.getPreferences().isEmpty() ) {
					// -- no super-stable matching exists
					return false;
				} // -- end if
			} // -- end loop
		} // -- end loop;

		return true;
	}
}
