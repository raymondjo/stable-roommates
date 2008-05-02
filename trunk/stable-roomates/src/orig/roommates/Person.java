package roommates;

import java.io.*;

/**
 * The class Person reads in the data and handles the data structures for a
 * Person and controls the Person's behaviour.
 */
public class Person implements Cloneable {
	/** ID of Person */
	private int id;

	/** boolean indicating whether Person holds a proposal from somebody */
	private boolean HoldsProposal = false;

	/** Preference list */
	private Pelt pref[];

	/** Ranking list */
	private int rank[];

	/** Index of last element in preference list */
	private int end;

	/**
	 * Preference array index marking next deletion from this Person's list
	 * declared here so that it can be used again when picking up from having
	 * terminated due to a previous deletion
	 */
	private int nextDel;

	/**
	 * Line of standard input read in (either a person's preference list or the
	 * single integer denoting the number of persons)
	 */
	public static String line;

	/*--------------------------------------------------------------------*/

	/**
	 * Constructor for use by clone method only
	 * 
	 * @param x
	 *            the length of the array pref[]
	 * @param y
	 *            the length of the array rank[]
	 */
	Person( int x, int y ) {
		pref = new Pelt[x];
		rank = new int[y];
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Constructor which initialises all fields.
	 * 
	 * @param i
	 *            is number of persons
	 * @param fi
	 *            a BufferedReader object (standard input)
	 * @exception throws
	 *                IOException
	 */
	public Person( int i, BufferedReader fi ) throws IOException {
		/**
		 * Person's preference list has length i-1. For convenience we declare
		 * an array with indices 0..i-1.
		 */
		pref = new Pelt[i];
		pref[0] = new Pelt( 0, 0 );
		/**
		 * Person's ranking list has length i-1. For convenience we declare an
		 * array with indices 0..i.
		 */
		rank = new int[i + 1];
		// Initialise unused entries in rank for completeness.
		rank[0] = 0;
		rank[id] = 0;
		end = i - 1; // Person's preference list has length i-1.
		read( fi );
		endPrefList(); // Mark end of Person's preference list.
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Clone method for Person.
	 */
	public Object clone() {
		// Create a new Person object
		Person pObj = new Person( pref.length, rank.length );
		// Copy data over from all relevant fields
		pObj.id = id;
		pObj.HoldsProposal = HoldsProposal;
		for ( int i = 0; i < pref.length; i++ ) {
			Pelt prefentry = (Pelt) pref[i].clone();
			pObj.pref[i] = prefentry;
		}
		for ( int i = 0; i < rank.length; i++ )
			pObj.rank[i] = rank[i];
		pObj.end = end;
		return pObj;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Reads the next integer from line (the string initially containing a line
	 * of standard input), and truncates line accordingly
	 * 
	 * @return an integer
	 */

	public static int nextNumber() {
		// Integer cannot have > line.length() digits
		int valueArray[] = new int[line.length()];
		int j = 0; // Index of line being scanned

		// Carry on scanning until we reach end of line or a space
		while ( ( j < line.length() ) && ( line.charAt( j ) != ' ' ) ) {
			// Store digit scanned in valueArray
			valueArray[j] = (int) line.charAt( j ) - (int) '0';
			j++;
		}

		int num = 1;
		int total = 0; // Integer computed so far
		for ( int k = j - 1; k >= 0; k-- ) {
			total += num * valueArray[k]; // Convert digits to integer
			num *= 10;
		}

		// Scan to beginning of next integer
		while ( ( j < line.length() ) && ( ( line.charAt( j ) == ' ' ) | ( line.charAt( j ) == ':' ) ) )
			j++;

		// Truncate what has already been scanned from line
		line = line.substring( j );
		// Return the integer that has been computed
		return total;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Reads id and preference list from file and calculates ranking list.
	 * 
	 * @param fi
	 *            a FormatInput object
	 * @exception throws
	 *                IOException
	 */
	private void read( BufferedReader fi ) throws IOException {
		// get person's id and advance past the following colon and space
		id = nextNumber();
		for ( int i = 1; i <= end; i++ ) {
			// read ith element of preference list
			int elt = nextNumber();
			pref[i] = new Pelt( i, elt );
			// rank as ith element of preference list
			rank[elt] = i;
		}
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Marks end of preference list.
	 */
	private void endPrefList() {
		pref[end].changeSucc( 0 ); // Indicates end of list.
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns id of Person.
	 * 
	 * @return this Person's id
	 */
	public int getID() {
		return id;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns id of person preceding person with id j in this Person's list.
	 * 
	 * @return a person's id
	 */
	public int getPredID( int j ) {
		int k = pref[rank[j]].getPred();
		// EAB - start
		if ( k < 0 || pref[k] == null ) {
			return -1;
		}
		// EAB - end
		return pref[k].getEntry();
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns id of first person in Person's preference list.
	 * 
	 * @return integer (Person id)
	 */
	public int getFirstPerson() {
		return pref[pref[0].getSucc()].getEntry();
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns id of last person in Person's preference list.
	 * 
	 * @return integer (Person id)
	 */
	public int getLastPerson() {
		return pref[end].getEntry();
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Sets Person to hold a proposal.
	 */
	public void SetProposedTo() {
		HoldsProposal = true;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Sets Person to not hold a proposal.
	 */
	public void SetNotProposedTo() {
		HoldsProposal = false;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns true if Person holds a proposal from somebody, false otherwise
	 * 
	 * @return a boolean value
	 */
	public boolean HoldsProposal() {
		return HoldsProposal;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Prints id and preference list of Person.
	 * 
	 * @param annotateMatching
	 *            a boolean indicating whether we are to superimpose a stable
	 *            matching onto the preference lists
	 * @param size
	 *            an integer (number of persons)
	 * @param fo
	 *            a FormatOutput object
	 */
	public void printPrefs( boolean annotateMatching, int size, BufferedWriter fo ) throws IOException {
		int j = String.valueOf( id ).length(); // Number of digits in id
		int k = String.valueOf( size ).length(); // Number of digits in size
		for ( ; k > j; j++ )
			fo.write( " " );
		fo.write( String.valueOf( id ) );
		fo.write( " : " );
		// fo.print(end);
		int i;
		if ( annotateMatching )
			i = 1;
		else
			i = pref[0].getSucc();
		while ( ( i != 0 ) && i < size ) // end of list condition
		{
			pref[i].print( annotateMatching, getFirstPerson(), size, fo );
			fo.write( " " );
			if ( annotateMatching )
				i++;
			else
				i = pref[i].getSucc();
		}
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Prints id of Person and who he is assigned to.
	 * 
	 * @param fo
	 *            a FormatOutput object
	 */
	public void printAssignedTo( BufferedWriter fo ) throws IOException {
		fo.write( "{" );
		fo.write( String.valueOf( id ) );
		fo.write( "," );
		fo.write( String.valueOf( getFirstPerson() ) );
		fo.write( "}" );
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Deletes person with id i from Person's preference list.
	 * 
	 * @param i
	 *            an integer (Person id)
	 * @param whichPhase1
	 *            a Phase 1 object (indicating which Phase 1 instance this is)
	 */
	public void deletePref( int i, Phase1 whichPhase1 ) {
		Pelt a = pref[rank[i]];
		int j = a.getSucc();
		int k = a.getPred();
		if ( j > 0 ) // If there is a successor element
		{
			Pelt b = pref[j];
			b.changePred( k ); // Update predecessor entry of a's successor
		}
		else // Else there is no successor element
		{
			end = k; // Update end of list pointer to a's predecessor
			if ( end == 0 ) // Check whether a list has become empty
				whichPhase1.noEmptyLists = false;
		}
		Pelt c = pref[k];
		c.changeSucc( j ); // Update successor entry of a's predecessor
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Deletes this Person from preference lists of persons who appear in this
	 * Person's preference list after Person p and truncates this Person's list
	 * at Person p (p is Person with id i).
	 * 
	 * @param i
	 *            an integer (Person id)
	 * @param pa
	 *            a PersonArray object (which Phase 1 table we are deleting
	 *            from)
	 * @param whichPhase1
	 *            a Phase 1 object (indicating which Phase 1 instance this is)
	 * @param addToDelStack
	 *            a boolean indicating whether whether deletions should be added
	 *            to the stack DelStack
	 * @param synchronise
	 *            a boolean indicating whether we are synchronising this
	 *            application of Phase 1 with another
	 * @param fo
	 *            a FormatOutput object (for debugging purposes)
	 */
	public void delete( int i, PersonArray pa, Phase1 whichPhase1, boolean addToDelStack, boolean synchronise,
			BufferedWriter fo ) throws IOException {

		// Test to see if we are merely picking up where we left off when
		// carrying
		// out a sequence of deletions
		if ( !whichPhase1.SkipOver ) {
			// Push relevant info onto stack for possible subsequent
			// reinstatement
			if ( addToDelStack ) {
				DelStackNode DelStackNodeObj = new DelStackNode( id, pref[rank[i]].getSucc() );
				whichPhase1.DelStack.push( DelStackNodeObj );
			}
			// Truncate list at position rank[i]
			end = rank[i];

			// nextDel indicates where we should start deleting from
			nextDel = pref[end].getSucc();
		}
		else {
			whichPhase1.SkipOver = false; // reset SkipOver flag
		}
		// Loop until an entry has been deleted, or we have deleted all entries
		while ( ( nextDel != 0 ) && ( !whichPhase1.SkipOver ) ) {
			Person r = pa.getPerson( pref[nextDel].getEntry() );
			// MOD: Is this condition necessary?
			if ( r == null ) {
				break;
			}
			fo.write( "Table " + Phase1.whichPhase1Instance + ": {" + id + ", " + r.getID() + "} is deleted. End is "
					+ end + ". NextDel is " + nextDel );
			// Carry out corresponding deletions from other preference lists
			r.deletePref( id, whichPhase1 );
			nextDel = pref[nextDel].getSucc();
			// fo.println(". NextDel is now "+nextDel);
			if ( synchronise ) {
				whichPhase1.DelFlag = true;
				if ( nextDel != 0 )
					whichPhase1.SkipOver = true;
			}
		}
		if ( nextDel == 0 )
			endPrefList(); // update end of list marker
		// fo.println("Table "+Phase1.whichPhase1Instance+": End is now "+end);
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Reinstate deleted person with id i from this Person's preference array.
	 * 
	 * @param i
	 *            a Person id
	 */
	public void reinstatepref( int i ) {
		Pelt a = pref[rank[i]];
		int j = a.getSucc();
		int k = a.getPred();
		if ( j > 0 ) // If there is a successor element
		{
			Pelt b = pref[j];
			b.changePred( rank[i] ); // Reinstate predecessor to point to a
		}
		else
			// Else there is no successor element
			end = rank[i]; // Update end of list pointer to point to a
		Pelt c = pref[k];
		c.changeSucc( rank[i] ); // Update successor entry of a's predecessor
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Perform reinstatement of a truncated list (like a reverse of delete)
	 * 
	 * @param startDel -
	 *            start reinstating from this preference array index
	 * @param pa
	 *            a PersonArray object (which Phase 1 table we are reinstating)
	 */
	public void reinstate( int startDel, PersonArray pa ) {
		int i = startDel;
		if ( i > 0 ) // Otherwise nothing to reinstate
		{
			Pelt a = pref[i];
			int k = a.getPred();
			Pelt c = pref[k];
			c.changeSucc( i ); // Point successor of c to previously deleted a
			while ( i > 0 ) {
				// For each entry r in tail of previously deleted entries in
				// Person's list, reinstate Person to r's list
				Person r = pa.getPerson( pref[i].getEntry() );
				r.reinstatepref( id );
				end = i;
				i = pref[i].getSucc();
			}
		}
	}
}
