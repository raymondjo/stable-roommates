package roommates;

import java.util.Stack;
import java.io.*;

/**
 * The class Phase 1 contains the method corresponding to Phase 1 of the
 * algorithm. Two objects will be instances of this class, corresponding to the
 * operation of Phase 1 on two different stable tables in Phase 2 of the
 * algorithm.
 */

public class Phase1 {
	/**
	 * Pointer to stable table that this instance of Phase 1 will be applied to
	 */
	private PersonArray pa;

	/** Stack of free persons */
	public Stack propStack = new Stack();

	/** Stack of pairs which have been deleted * */
	public Stack DelStack = new Stack();

	/** Boolean indicating whether a list has become empty during Phase 1 */
	public boolean noEmptyLists;

	/** Boolean indicating whether main loop should stop after a deletion */
	public boolean DelFlag;

	/** Boolean indicating whether we skip over certain bits of Phase 1 */
	public boolean SkipOver;

	/**
	 * Person ID integers used during applyPhase1 (declared here since they will
	 * be used when thisPhase1 is called subsequently)
	 */
	int i, j;

	/**
	 * Person whose list should be truncated (declared here since this will be
	 * used when thisPhase1 is called subsequently)
	 */
	Person q;

	/* Used for debugging purposes */
	public static int whichPhase1Instance;

	/*--------------------------------------------------------------------*/
	/**
	 * Constructor for class Phase 1
	 */
	public Phase1( PersonArray ra ) {
		pa = ra;
		noEmptyLists = true;
		SkipOver = false;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Phase 1 of the Stable Roommates algorithm
	 * 
	 * @param addtoDelStack
	 *            a boolean indicating whether deletions should be added to the
	 *            stack DelStack
	 * @param synchronise
	 *            a boolean indicating whether we are synchronising this
	 *            application of Phase 1 with another
	 * @param fo
	 *            a FormatOutput object used for debugging purposes
	 * @return boolean indicating whether Phase 1 terminated due to an empty
	 *         list or the proposal stack becoming empty
	 */
	public boolean applyPhase1( boolean addToDelStack, boolean synchronise, BufferedWriter fo ) throws IOException {
		DelFlag = false;
		// Terminate the main loop as soon as we have deleted an entry, or else
		// as soon as somebody's list becomes empty or the proposal stack
		// becomes empty *however* if were in the middle of deleting entries
		// then we must allow entry to the while loop so this can continue
		while ( ( ( !propStack.empty() ) & ( noEmptyLists ) & ( !DelFlag ) ) | ( synchronise & SkipOver & ( !DelFlag ) ) ) {
			// SkipOver indicates whether the Phase 1 instance halted whilst in
			// the middle of deleting a sequence of entries from somebody's list
			if ( !SkipOver ) {
				// pop a Person id Object off stack
				Integer iObj = (Integer) propStack.pop();
				i = iObj.intValue();
				Person p = pa.getPerson( i ); // Person p corresponding to id
				// i
				j = p.getFirstPerson(); // get first Person id on p's list
				q = pa.getPerson( j ); // Person q corresponding to id j
				// EAB - start
				if ( q == null ) {
					continue;
				}
				// EAB - end
				// fo.write("Table "+whichPhase1Instance+": "+i+" proposes to
				// "+j);
				// fo.newLine();
				if ( q.HoldsProposal() ) {
					// assign old partner of q to be free
					int k = q.getLastPerson(); // get last Person id on q's
					// list
					// con.println(k);
					Integer kObj = new Integer( k );
					propStack.push( kObj ); // Push person id object onto stack
				}
				q.SetProposedTo(); // p proposes to q
			}
			// Delete all persons after person with id i from Person q's list
			q.delete( i, pa, this, addToDelStack, synchronise, fo );
			// con.println(Person.noEmptyLists);
			// pa.print(con);
		}
		// Return whether this Phase 1 instance really has terminated and
		// doesn't
		// need to go back to delete more entries
		return ( ( !noEmptyLists ) | ( ( propStack.empty() ) & ( !SkipOver ) ) );
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Initialises stack of free person ids
	 */
	public void initFreeList() {
		pa.reset();

		// add all person ids to stack of free persons
		for ( int i = 1; i <= pa.getSize(); i++ ) {
			int j = pa.next().getID(); // id of next person to push onto stack
			Integer jObj = new Integer( j );
			propStack.push( jObj ); // Equivalent to s.push(object of i)
		}
	}
}
