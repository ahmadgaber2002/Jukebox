package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.scene.media.Media;

public class Song implements Serializable {

	private String fileName;
	private String title;
	private String artist;
	private int playTime;

	public Song(String title, String artist, int playTime, String fileName) throws FileNotFoundException {
		this.title = title;
		this.artist = artist;
		this.playTime = playTime;
		this.fileName = fileName;
	}
	
	/**
	 * specifically for playing the songs from filenames
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public Song(String filename) throws FileNotFoundException {
		this.fileName = filename;
	}

	public Media getSong() throws FileNotFoundException {
		File file = new File("songfiles/" + fileName);
		if (!file.exists()) {
			throw new FileNotFoundException("File not found: " + fileName);
		}
		Media song = new Media(file.toURI().toString());
		return song;
	}

	public String getTitle() {
		return this.title;
	}

	public String getArtist() {
		return this.artist;
	}

	public String getFileName() {
		return this.fileName;
	}
	
	public int getPlayTimeAsInt() {
		return this.playTime;
	}

	public String getPlayTime() {
		int m = playTime / 60;
		int s = playTime % 60;
		return String.format("%d:%02d", m, s);
	}

	/**
	 * can't find mp3 duration before runtime, so it has to be set manually later
	 * via this setter
	 * 
	 * @param value the duration of the song in seconds
	 */
	public void setPlayTime(int value) {
		this.playTime = value;
	}

	/**
	 * formats playtime as minutes:seconds
	 * 
	 * @return string representation of above
	 */
	public String getFormattedPlayTime() {
		int m = playTime / 60;
		int s = playTime % 60;
		return String.format("%d:%02d", m, s);
	}

	@Override
	public String toString() {
		return title + ", " + artist + ", " + getFormattedPlayTime() + ", " + fileName;
	}
	
}
