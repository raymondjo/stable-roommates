import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import roommates.DelStackNode;
import roommates.Person;
import roommates.PersonArray;
import roommates.Phase1;

/**
 * The class SR is the main class in the implementation of the Stable Roommates
 * algorithm assuming an even number of persons, complete lists and no ties.
 * 
 * @author David Manlove, University of Glasgow, September 2000 extends
 *         implementation of EGS Algorithm by
 * @author Sandy Scott, University of Glasgow, June 1999
 */
public class SR {
	/** Array of Person objects */
	static PersonArray pa;

	/** Boolean indicating whether Phase 2 has found a stable matching */
	static boolean SMfound = false;

	/** InputStream corresponding to standard input */
	static BufferedReader fi = new BufferedReader( new InputStreamReader( System.in ) );

	/** OutputStream corresponding to standard output */
	static BufferedWriter fo = new BufferedWriter( new OutputStreamWriter( System.out ) );

	/*--------------------------------------------------------------------*/

	/**
	 * Main method of main program
	 */
	public static void main( String args[] ) throws IOException {
		readFile(); // Read in preference lists from standard input

		fo.write( "Given preference lists:" );

		fo.newLine();
		fo.newLine();

		pa.print( false, fo ); // Confirm input preference lists to user
		fo.newLine();

		Phase1 firstPhase1 = new Phase1( pa );
		// add all persons to stack of persons to propose
		firstPhase1.initFreeList();
		// apply Phase1 of SR algorithm
		boolean dummy = firstPhase1.applyPhase1( false, false, fo );

		fo.write( "Phase 1 lists:" );
		fo.newLine();
		fo.newLine();

		pa.print( false, fo );
		fo.newLine();
		fo.flush();

		if ( !firstPhase1.noEmptyLists ) {
			fo.write( "No stable matching exists." );
			fo.newLine();
		}
		else {
			applyPhase2( firstPhase1 );
			if ( SMfound ) {
				fo.write( "Stable matching displayed as follows:" );
				fo.newLine();
				fo.newLine();

				pa.print( true, fo );
				fo.newLine();

				fo.write( "Stable matching listed as follows:" );
				fo.newLine();
				fo.newLine();

				pa.printMatching( fo );
			}
			else {
				fo.write( "No stable matching exists." );
				fo.newLine();
			}
		}
		fo.close();
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Reads preference lists from files
	 * 
	 * @param pin
	 *            a FileIn object
	 * @exception throws
	 *                IOException
	 */
	public static void readFile() throws IOException {
		// read in line containing number of persons
		Person.line = fi.readLine();
		int s = Person.nextNumber();
		pa = new PersonArray( s );
		for ( int i = 1; i <= s; i++ ) {
			// read in line containing a single person's list
			Person.line = fi.readLine();
			pa.add( new Person( s, fi ) );
		}
		fi.close();
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Reinstate previously deleted entries using stack DelStack.
	 * 
	 * @param x
	 *            a Person who should be set to have received a proposal
	 * @param ra
	 *            a PersonArray (i.e. which Phase 1 table we are reinstating)
	 * @param whichPhase1
	 *            a Phase 1 object (the Phase 1 instance in question)
	 */
	public static void reinstate( Person x, PersonArray ra, Phase1 whichPhase1 ) {
		while ( !whichPhase1.DelStack.empty() ) {
			DelStackNode DelStackNodeObj = (DelStackNode) whichPhase1.DelStack.pop();
			Person p = ra.getPerson( DelStackNodeObj.personID );
			// start reinstating entries previously deleted from p's list from
			// position startDel onwards
			p.reinstate( DelStackNodeObj.startDel, ra );
		}

		// reinstatement may have occurred part way through a deletion sequence
		whichPhase1.SkipOver = false;

		whichPhase1.noEmptyLists = true;
		x.SetProposedTo();
		if ( !whichPhase1.propStack.empty() ) {
			// propStack has single entry - id of person whose list became empty
			Object dummyObj = whichPhase1.propStack.pop();
		}
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Phase 2 of the Stable Roommates algorithm
	 * 
	 * @param firstPhase1
	 *            a Phase 1 object (corresponding to very first application of
	 *            Phase 1
	 */
	public static void applyPhase2( Phase1 firstPhase1 ) throws IOException {
		int start; // id of person with list length >1, or 0 if no such person

		PersonArray qa = (PersonArray) pa.clone(); // Make a copy of pa
		Phase1 secondPhase1 = new Phase1( qa ); // Phase 1 application on qa

		// pa.print(fo);
		// qa.print(fo);

		while ( ( !SMfound ) & ( firstPhase1.noEmptyLists  ) & ( secondPhase1.noEmptyLists  ) ) {
			// Get id of a person with list length > 1, or 0 if no such person
			start = pa.Find();
			// Note that it is enough to consider pa only, since pa=qa here
			if ( start == 0 )
				// All lists have length 1, so we have a stable matching
				SMfound = true;
			else {
				/**
				 * Set up firstPhase1 instance on pa by letting person with id
				 * start be rejected by the person he is assigned to
				 */
				Person p1 = pa.getPerson( start );
				int AssignedTo = p1.getFirstPerson();
				Person q1 = pa.getPerson( AssignedTo );
				Integer startObj = new Integer( start );
				firstPhase1.propStack.push( startObj );
				q1.SetNotProposedTo();
				// note that p1's list has length >1 => q1's list has length >1
				q1.delete( q1.getPredID( start ), pa, firstPhase1, true, false, fo );

				/**
				 * Set up secondPhase1 instance on qa by letting person with id
				 * start reject the person he is assigned from
				 */
				Person p2 = qa.getPerson( start );
				int AssignedFrom = p2.getLastPerson();
				Person r2 = qa.getPerson( AssignedFrom );
				Integer AssignedFromObj = new Integer( AssignedFrom );
				secondPhase1.propStack.push( AssignedFromObj );
				p2.SetNotProposedTo();
				// note that p2's list has length >1 => r2's list has length >1
				p2.delete( p2.getPredID( AssignedFrom ), qa, secondPhase1, true, false, fo );

				/**
				 * The following two booleans indicate whether their respective
				 * Phase 1 instances terminated due to a list becoming empty or
				 * the proposals stack becoming empty
				 */
				boolean firstPhase1Terminated = false;
				boolean secondPhase1Terminated = false;

				/**
				 * Now set in motion the alternate sequence of firstPhase1 and
				 * secondPhase1 applications, halting until one terminates for a
				 * reason other than just a deletion having occurred.
				 */
				while ( ( !firstPhase1Terminated ) & ( !secondPhase1Terminated ) ) {
					Phase1.whichPhase1Instance = 1;
					firstPhase1Terminated = firstPhase1.applyPhase1( true, true, fo );
					/**
					 * Only switch to secondPhase1 instance if firstPhase1
					 * instance has terminated due to a deletion
					 */
					if ( !firstPhase1Terminated ) {
						Phase1.whichPhase1Instance = 2;
						secondPhase1Terminated = secondPhase1.applyPhase1( true, true, fo );
					}
				}

				// fo.write("Now finished synchronised part");
				// fo.newLine();
				// One of the Phase 1 applications has terminated

				if ( firstPhase1Terminated )
				// firstPhase1 application of Phase 1 has terminated first
				{
					// fo.write("firstPhase1 has terminated first");
					// fo.newLine();
					// fo.write(String.valueOf(firstPhase1.DelFlag));
					// fo.newLine();
					if ( ( !firstPhase1.noEmptyLists ) & ( secondPhase1.noEmptyLists  ) )
					// firstPhase1 application has led to an empty list
					{
						// Undo the firstPhase1 deletions from pa
						reinstate( q1, pa, firstPhase1 );

						// Carry on with the application of secondPhase1 to qa
						Phase1.whichPhase1Instance = 2;
						boolean dummy = secondPhase1.applyPhase1( false, false, fo );

						if ( secondPhase1.noEmptyLists ) {
							// Apply the same action of secondPhase1 on qa to pa
							Person r1 = pa.getPerson( AssignedFrom );
							firstPhase1.propStack.push( AssignedFromObj );
							p1.SetNotProposedTo();
							// note that p1's list has length >1 => r1's list
							// has length >1
							p1.delete( p1.getPredID( AssignedFrom ), pa, firstPhase1, false, false, fo );
							Phase1.whichPhase1Instance = 1;
							dummy = firstPhase1.applyPhase1( false, false, fo );
						}
					}
					else if ( firstPhase1.noEmptyLists )
					// firstPhase1 application has terminated with no empty list
					{
						// Undo the secondPhase1 deletions from qa
						reinstate( p2, qa, secondPhase1 );

						// pa.print(fo);
						// qa.print(fo);

						// Apply the same action of firstPhase1 on pa to qa
						Person q2 = qa.getPerson( AssignedTo );
						secondPhase1.propStack.push( startObj );
						q2.SetNotProposedTo();
						// note that p2's list has length >1 => q2's list has
						// length >1
						q2.delete( q2.getPredID( start ), qa, secondPhase1, false, false, fo );
						Phase1.whichPhase1Instance = 2;
						boolean dummy = secondPhase1.applyPhase1( false, false, fo );
					}
				}
				else
				// secondPhase1 application of Phase 1 has terminated first
				{
					// fo.write("secondPhase1 has terminated first");
					// fo.newLine();
					if ( ( !secondPhase1.noEmptyLists ) & ( firstPhase1.noEmptyLists  ) )
					// secondPhase1 application has led to an empty list
					{
						// Undo the secondPhase1 deletions from qa
						reinstate( p2, qa, secondPhase1 );

						// Carry on with the application of firstPhase1 to pa
						Phase1.whichPhase1Instance = 1;
						boolean dummy = firstPhase1.applyPhase1( false, false, fo );

						if ( firstPhase1.noEmptyLists ) {
							// Apply the same action of firstPhase1 on pa to qa
							Person q2 = qa.getPerson( AssignedTo );
							secondPhase1.propStack.push( startObj );
							q2.SetNotProposedTo();
							// note that p2's list has length >1 => q2's list
							// has length >1
							q2.delete( q2.getPredID( start ), qa, secondPhase1, false, false, fo );
							Phase1.whichPhase1Instance = 2;
							dummy = secondPhase1.applyPhase1( false, false, fo );
						}
					}
					else if ( secondPhase1.noEmptyLists )
					// secondPhase1 application has terminated with no empty
					// list
					{
						// Undo the firstPhase1 deletions from pa
						reinstate( q1, pa, firstPhase1 );

						// Apply the same action of secondPhase1 on qa to pa
						Person r1 = pa.getPerson( AssignedFrom );
						firstPhase1.propStack.push( AssignedFromObj );
						p1.SetNotProposedTo();
						// note that p1's list has length >1 => r1's list has
						// length >1
						p1.delete( p1.getPredID( AssignedFrom ), pa, firstPhase1, false, false, fo );
						Phase1.whichPhase1Instance = 1;
						boolean dummy = firstPhase1.applyPhase1( false, false, fo );
					}
				}

				// pa.print(false,fo);
				// qa.print(false,fo);
				// fo.flush();

				// Empty both DelStacks as entries are redundant (will not
				// increase
				// asymptotic time complexity)
				while ( !firstPhase1.DelStack.empty() ) {
					Object dummyObj = firstPhase1.DelStack.pop();
				}
				while ( !secondPhase1.DelStack.empty() ) {
					Object dummyObj = secondPhase1.DelStack.pop();
				}
			}
		}
	}
}
