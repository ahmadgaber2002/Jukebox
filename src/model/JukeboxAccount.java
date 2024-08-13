package model;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * need to implement serialization
 *
 */
public class JukeboxAccount implements Serializable {

	private String username;
	private String password;
	private int songsPlayedToday;
	private Date lastLoginDate;

	private PlayList accountPlayList;

	public JukeboxAccount(String username, String password) {
		this.username = username;
		this.password = password;
		this.songsPlayedToday = 0;
		this.lastLoginDate = new Date();
		this.accountPlayList = new PlayList();
	}
	public String getUsername() {
		return username;
	}

	public int getSongsPlayedToday() {
		return songsPlayedToday;
	}

	public boolean updateDate() {
		return lastLoginDate.dateHasChanged();
	}

	public boolean canPlaySong() {
		if (lastLoginDate.dateHasChanged()) {
			songsPlayedToday = 0;
		}
		return songsPlayedToday < 3;
	}

	public void incrementSongsPlayed() {
		songsPlayedToday++;
	}

	public boolean checkCredentials(String username, String password) {
		return this.username.equals(username) && this.password.equals(password);
	}

	/**
	 * prompts the jukebox to queue up another song, checks to see if the account is
	 * capable of doing so. check for valid song names later
	 *
	 * @param song the name of the song
	 * @return true if the account can post a song, false if it cant
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	public boolean queueUpNextSong(Song song) throws InterruptedException, FileNotFoundException {
		// having no song credits
		if (this.canPlaySong()) {
			accountPlayList.queueUpNextSong(song);
			incrementSongsPlayed();
			return true;
		} else {
			return false;
		}
	}

	public PlayList getAccountPlayList() {
		return accountPlayList;
	}

	public ArrayList<Song> getAccountPlayListAsList() {
		return accountPlayList.getQueueAsList();
	}

	public String toString() {
		return "username: " + username + ", " + "songsPlayedToday: " + songsPlayedToday + ", "
				+ "lastLoginDate: " + lastLoginDate + ", " + "playlist: " + accountPlayList.getQueueAsList();
	}

}
