package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * purpose is to assist jukebox acct in checking the date. probably doesnt need
 * to exist.
 */

public class Date implements Serializable {
	private LocalDate date;

	/**
	 * sets current date as today
	 */
	public Date() {
		this.date = LocalDate.now();
	}

	/**
	 * sets current date as a specific value, used in testing
	 * 
	 * @param date String representation of a date in YYYY-MM-DD format
	 */
	public Date(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.US);
		this.date = LocalDate.parse(date, formatter);
	}

	/**
	 * checks to see if today is the same as the saved current date. if not, updates
	 * the current date
	 * 
	 * @return true if the date was updated, false if not
	 */
	public boolean dateHasChanged() {
		LocalDate newDate = LocalDate.now();
		if (!newDate.equals(date)) {
			this.date = newDate;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return this.date.toString();
	}
}
