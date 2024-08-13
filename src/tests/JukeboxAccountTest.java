package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Date;
import model.Jukebox;
import model.JukeboxAccount;

class JukeboxAccountTest {

	@BeforeEach
	public void setup() throws InterruptedException {
		aJBA1 = new JukeboxAccount("account1", "hunter2");
		aJBA2 = new JukeboxAccount("account2", "hunter2");
		aJB = new Jukebox();
		currentDate = new Date();
	}

	private JukeboxAccount aJBA1, aJBA2;
	private Jukebox aJB;
	private Date currentDate;

	@Test
	void testJBA() {
		assertEquals(aJBA1.getUsername(), "account1");
		assertTrue(aJBA1.checkCredentials("account1", "hunter2"));
		assertFalse(aJBA1.checkCredentials("account1", "aaghg"));
		assertFalse(aJBA1.checkCredentials("who", "hunter2"));
		assertEquals(aJBA1.getSongsPlayedToday(), 0);
		assertTrue(aJBA1.canPlaySong());
		aJBA1.incrementSongsPlayed();
		aJBA1.incrementSongsPlayed();
		aJBA1.incrementSongsPlayed();
		assertEquals(aJBA1.getSongsPlayedToday(), 3);
		assertFalse(aJBA1.canPlaySong());
	}

	@Test
	void testJukeboxLogIn() {
		assertTrue(aJB.createAccount("account3", "hunter2"));
		assertTrue(aJB.createAccount(aJBA2));
		assertFalse(aJB.createAccount("account3", "different password"));
		assertFalse(aJB.createAccount(aJBA2));
		assertTrue(aJB.logIn("account2", "hunter2"));
		assertFalse(aJB.logIn("account3", "hunter2"));
		aJB.logOut();
		assertFalse(aJB.logIn("account3", "wrong password"));
		assertTrue(aJB.logIn("account3", "hunter2"));
		aJB.logOut();
	}

	@Test
	void testDate() {
		Date date1 = new Date("1970-01-01");
		assertFalse(currentDate.dateHasChanged());
		assertNotEquals(date1.toString(), currentDate.toString());
		assertEquals(date1.toString(), "1970-01-01");
		assertTrue(date1.dateHasChanged());
		assertEquals(date1.toString(), currentDate.toString());
	}
}
