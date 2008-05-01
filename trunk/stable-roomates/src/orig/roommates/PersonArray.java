package roommates;

import java.io.*;

/**
 * The class PersonArray contains methods for handling the set of all persons.
 * The set is stored in an array from index one onwards, with the Person with id
 * i stored in array element i.
 */
public class PersonArray implements Cloneable {
	/** Size of array */
	private int size;

	/** Iterator for array */
	private int index;

	/** Array of Person objects */
	private Person persons[];

	/** Integer giving id of person with list length >1, or 0 if no such person */
	public int NonSingleList;

	/*--------------------------------------------------------------------*/

	/**
	 * Initialises array of Person objects of size i.
	 * 
	 * @param i
	 *            the size of the set of persons
	 */
	public PersonArray( int i ) {
		size = i;
		persons = new Person[size + 1];
		index = 1;
		NonSingleList = size;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Clone method for PersonArray.
	 */
	public Object clone() {
		// Create a new PersonArray object
		PersonArray pObj = new PersonArray( size );
		// Copy data over from all relevant fields
		// pObj.size already initialised by constructor
		pObj.index = index;
		pObj.NonSingleList = NonSingleList;
		for ( int i = 1; i <= size; i++ )
		// persons has been initialised within indices 1..size
		{
			Person personentry = (Person) persons[i].clone();
			pObj.persons[i] = personentry;
		}
		return pObj;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Adds a Person to the array.
	 * 
	 * @param m
	 *            a Person object
	 */
	public void add( Person p ) {
		persons[index++] = p;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns the Person with id i.
	 * 
	 * @param i
	 *            a Person's id
	 * @return the Person object at position i in the array
	 */
	public Person getPerson( int i ) {
		return persons[i];
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns the size of the array.
	 * 
	 * @return size of the array
	 */
	public int getSize() {
		return size;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Prints out all preference lists in the array.
	 * 
	 * @param annotateMatching
	 *            a boolean indicating whether we should super- impose a stable
	 *            matching onto the preference lists
	 * @param fo
	 *            a FormatOutput object
	 */
	public void print( boolean annotateMatching, BufferedWriter fo ) throws IOException {
		// fo.print("Number of people: ");
		// fo.println(size);
		for ( int i = 1; i <= size; i++ ) {
			persons[i].printPrefs( annotateMatching, size, fo );
			fo.newLine();
		}
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Prints out final matching without repeating ordered pairs of the form
	 * (a,b) (b,a) -- outputs {a,b} instead
	 * 
	 * @param fo
	 *            a FormatOutput object
	 */
	public void printMatching( BufferedWriter fo ) throws IOException {
		boolean done[] = new boolean[size + 1];
		for ( int i = 1; i <= size; i++ )
			done[i] = false;
		for ( int i = 1; i <= size; i++ )
			if ( !done[i] ) {
				Person p = persons[i];
				int j = p.getFirstPerson();
				done[j] = true;
				p.printAssignedTo( fo );
				fo.write( "  " );
			}
		fo.newLine();
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Resets array to first Person.
	 */
	public void reset() {
		index = 1;
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns the Person object pointed to by index and moves to the next one.
	 * 
	 * @return a Person object
	 */
	public Person next() {
		if ( index > size )
			return null;
		else
			return persons[index++];
	}

	/*--------------------------------------------------------------------*/

	/**
	 * Returns id of a person with list length > 1, or 0 if no such person
	 * exists.
	 * 
	 * @return an integer object
	 */

	public int Find() {
		Person p;
		boolean NonSingleListFound = false;
		while ( ( NonSingleList >= 1 ) && ( !NonSingleListFound ) ) {
			p = getPerson( NonSingleList );
			if ( p.getFirstPerson() != p.getLastPerson() )
				NonSingleListFound = true;
			else
				NonSingleList--;
		}
		return NonSingleList;
	}
}
