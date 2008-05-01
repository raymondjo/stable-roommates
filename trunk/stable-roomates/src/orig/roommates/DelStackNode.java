package roommates;

/**
 * The class delStackNode contains the data for a single entry in the stack of
 * pairs of preference array indices.
 */
public class DelStackNode {
	public int personID; // id of Person object responsible for delete

	public int startDel; // index of array to start reinstating from

	public DelStackNode( int i, int j ) {
		personID = i;
		startDel = j;
	}
}
