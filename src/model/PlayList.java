package model;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * TODO implement serialization, fix the UI freezing when waiting after a song.
 * threading issues? alt-tabbing also breaks things sometimes but that's
 * probably beyond me to fix
 */
public class PlayList implements Serializable {

	private LinkedBlockingQueue<Song> playList;
	private Song currentSong;
	private boolean playListStarted;

	public PlayList() {
		this.playList = new LinkedBlockingQueue<>();
		playListStarted = false;
	}

	/**
	 * adds a song object to the queue. if the playlist is already in motion, does
	 * nothing else as other functions handle the continuous playback. if the
	 * playlist was stopped, also initiates the playlist.
	 *
	 * @param songToAdd song object to be added
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	public void queueUpNextSong(String songToAdd) throws InterruptedException, FileNotFoundException {
		Song toAdd = new Song(songToAdd);
		playList.offer(toAdd);

		if (playListStarted) {
			return;
		} else {
			playListStarted = true;
			getNextSong();
		}
	}

	/**
	 * adds a song object to the queue.
	 */
	public void queueUpNextSong(Song songToAdd) {
		playList.offer(songToAdd);
	}

	/**
	 * pops the next song object off the queue, update and return the current song
	 *
	 * @return
	 * @throws InterruptedException
	 */
	public Song getNextSong() throws InterruptedException, FileNotFoundException {
		if (playList.peek() == null) {
			playListStarted = false;
			return null;
		}
		currentSong = playList.poll(100, TimeUnit.MILLISECONDS);
		return currentSong;
	}

	/**
	 * gets the queue as an arraylist of song objects for display in the GUI
	 */
	public ArrayList<Song> getQueueAsList() {
		return new ArrayList<Song>(playList);
	}
	
	public String getSongFileName() {
		return currentSong.getFileName();
	}

	public void setPlayListStarted(boolean playListStarted) {
		this.playListStarted = playListStarted;
	}

	public Song getCurrentSong() {
		return currentSong;
	}

	public boolean isPlayListStarted() {
		return playListStarted;
	}

}
