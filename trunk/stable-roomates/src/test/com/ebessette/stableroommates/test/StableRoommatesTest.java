/**
 * Created on Apr 15, 2008
 */
package com.ebessette.stableroommates.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.ebessette.stableroommates.InvalidPreferencesException;
import com.ebessette.stableroommates.NoStableMatchingException;
import com.ebessette.stableroommates.Preference;
import com.ebessette.stableroommates.PreferenceList;
import com.ebessette.stableroommates.StableRoommates;
import com.ebessette.stableroommates.Target;

/**
 * Testing the stable roommates algorithm
 * 
 * @author Eric <dev@ebessette.com>
 */
public class StableRoommatesTest {

	/**
	 * @throws InvalidPreferencesException
	 * @throws NoStableMatchingException
	 */
	//DEBUG @Test
	public void superStableMatching() throws InvalidPreferencesException, NoStableMatchingException {

		// Create preference list (data1.txt)
		List<Target<Integer>> l = new ArrayList<Target<Integer>>();
		for ( int i = 0; i < 10; i++ ) {
			l.add( new Target<Integer>( i + 1 ) );
			l.get( i ).setPreferences( new PreferenceList<Integer>() );
		}

		Target<Integer> t = l.get( 0 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 8 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 9 ) ) );
		t = l.get( 1 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 8 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 6 ) ) );
		t = l.get( 2 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 8 ) ) );
		t = l.get( 3 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 8 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 7 ) ) );
		t = l.get( 4 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 8 ) ) );
		t = l.get( 5 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 8 ) ) );
		t = l.get( 6 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 8 ) ) );
		t = l.get( 7 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 8 ) ) );
		t = l.get( 8 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 0 ) ) );
		t = l.get( 9 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 8 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 6 ) ) );

		StableRoommates<Integer> sr = new StableRoommates<Integer>( l );

		sr.match();
	}

	/**
	 * @throws InvalidPreferencesException
	 * @throws NoStableMatchingException
	 */
	//DEBUG @Test( expected = NoStableMatchingException.class )
	public void noStableMatching() throws InvalidPreferencesException, NoStableMatchingException {

		// Create preference list (data2.txt)
		List<Target<Integer>> l = new ArrayList<Target<Integer>>();
		for ( int i = 0; i < 6; i++ ) {
			l.add( new Target<Integer>( i + 1 ) );
			l.get( i ).setPreferences( new PreferenceList<Integer>() );
		}

		Target<Integer> t = l.get( 0 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 4 ) ) );
		t = l.get( 1 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 3 ) ) );
		t = l.get( 2 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 3 ) ) );
		t = l.get( 3 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 0 ) ) );
		t = l.get( 4 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 1 ) ) );
		t = l.get( 5 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 2 ) ) );

		StableRoommates<Integer> sr = new StableRoommates<Integer>( l );

		sr.match();
	}

	/**
	 * @throws InvalidPreferencesException
	 * @throws NoStableMatchingException
	 */
	@Test
	@Ignore( "TODO Need to figure out how to detect an infinite loop" )
	public void infiniteLoop() throws InvalidPreferencesException, NoStableMatchingException {

		// BUG Can this happen in a tournament? If so, how can I catch it?

		// Create preference list (data3.txt)
		List<Target<Integer>> l = new ArrayList<Target<Integer>>();
		for ( int i = 0; i < 10; i++ ) {
			l.add( new Target<Integer>( i + 1 ) );
			l.get( i ).setPreferences( new PreferenceList<Integer>() );
		}

		Target<Integer> t = l.get( 0 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 8 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 9 ) ) );
		t = l.get( 1 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 8 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 5 ) ) );
		t = l.get( 2 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 8 ) ) );
		t = l.get( 3 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 8 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 7 ) ) );
		t = l.get( 4 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 8 ) ) );
		t = l.get( 5 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 8 ) ) );
		t = l.get( 6 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 8 ) ) );
		t = l.get( 7 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 8 ) ) );
		t = l.get( 8 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 6 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 9 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 0 ) ) );
		t = l.get( 9 );
		t.getPreferences().add( new Preference<Integer>( 0, l.get( 2 ) ) );
		t.getPreferences().add( new Preference<Integer>( 1, l.get( 0 ) ) );
		t.getPreferences().add( new Preference<Integer>( 2, l.get( 5 ) ) );
		t.getPreferences().add( new Preference<Integer>( 3, l.get( 4 ) ) );
		t.getPreferences().add( new Preference<Integer>( 4, l.get( 1 ) ) );
		t.getPreferences().add( new Preference<Integer>( 5, l.get( 8 ) ) );
		t.getPreferences().add( new Preference<Integer>( 6, l.get( 7 ) ) );
		t.getPreferences().add( new Preference<Integer>( 7, l.get( 3 ) ) );
		t.getPreferences().add( new Preference<Integer>( 8, l.get( 6 ) ) );

		StableRoommates<Integer> sr = new StableRoommates<Integer>( l );

		sr.match();
	}

	/**
	 * @throws InvalidPreferencesException
	 * @throws NoStableMatchingException
	 */
	@Test
	public void singleDivRoundOne() throws InvalidPreferencesException, NoStableMatchingException {

		// Create preference list (data1.txt)
		List<Target<Integer>> l = new ArrayList<Target<Integer>>();
		for ( int i = 0; i < 8; i++ ) {
			l.add( new Target<Integer>( i + 1 ) );
			l.get( i ).setPreferences( new PreferenceList<Integer>() );

		}

		for ( int i = 0; i < 8; i++ ) {
			Target<Integer> t = l.get( i );

			for ( int j = 0; j < 8; j++ ) {
				if ( i != j ) {
					t.getPreferences().add( new Preference<Integer>( 0, l.get( j ) ) );
				}
			}
		}

		StableRoommates<Integer> sr = new StableRoommates<Integer>( l );

		sr.match();
	}
}
