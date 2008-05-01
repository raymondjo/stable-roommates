package roommates;

import java.io.*;

/**
 * The class Pelt is used to build a Person's preference list. Each object of
 * the class Pelt comprises three data elements: a preference list element i.e.
 * the id of a Person, a pointer to the next Pelt and a pointer to the preceding
 * Pelt.
 */
public class Pelt implements Cloneable {
	/** Array for Pelt */
	private int a[] = new int[3];

	/*--------------------------------------------------------------------*/

	/**
	 * Initialises preference list element and sets predecessor and successor
	 * pointers to point to relevant Pelts.
	 * 
	 * @param i
	 *            position of Pelt in preference list
	 * @param j
	 *            preference list element i.e. Woman's id
	 */
	public Pelt( int i, int j ) {
		a[0] = i - 1; // predeccessor
		a[1] = j; // entry
		a[2] = i + 1; // successor
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Clone method for Pelt.
	 */
	public Object clone() {
		Pelt pObj = new Pelt( 0, 0 );
		for ( int i = 0; i <= 2; i++ )
			pObj.a[i] = a[i]; // assignment ok here as a[i] is a variable
		return pObj;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns pointer to preceding Pelt.
	 * 
	 * @return position in preference list of preceding Pelt.
	 */
	public int getPred() {
		return a[0];
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns preference list element.
	 * 
	 * @return id of a Person
	 */
	public int getEntry() {
		return a[1];
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns pointer to succeeding Pelt.
	 * 
	 * @return position in preference list of succeeding Pelt
	 */
	public int getSucc() {
		return a[2];
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Changes predeccessor pointer to point to Pelt at position i in preference
	 * list.
	 * 
	 * @param i
	 *            position in preference list of preceding Pelt
	 */
	public void changePred( int i ) {
		a[0] = i;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Changes Entry entry of Pelt to have value i.
	 * 
	 * @param i
	 *            an integer representing new value of Entry
	 */
	public void changeEntry( int i ) {
		a[1] = i;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Changes successor pointer to point to Pelt at position i in preference
	 * list.
	 * 
	 * @param i
	 *            position in preference list of succeeding Pelt
	 */
	public void changeSucc( int i ) {
		a[2] = i;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Prints preference list element
	 * 
	 * @param annotateMatching
	 *            a boolean indicating whether we are to super- impose the
	 *            stable matching onto the preference lists
	 * @param partner
	 *            an integer (this Person's partner id)
	 * @param size
	 *            an integer (number of persons)
	 */
	public void print( boolean annotateMatching, int partner, int size, BufferedWriter fo ) throws IOException {
		int j = String.valueOf( a[1] ).length(); // Number of digits in id
		int k = String.valueOf( size ).length(); // Number of digits in size
		for ( ; k > j; j++ )
			fo.write( " " );
		fo.write( String.valueOf( a[1] ) );
		if ( ( annotateMatching ) && ( a[1] == partner ) )
			fo.write( "*" );
		else
			fo.write( " " );
	}
}
