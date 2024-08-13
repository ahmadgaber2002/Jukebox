package model;

import java.util.HashSet;

public class Jukebox {

	private JukeboxAccount curAccount;
	private HashSet<JukeboxAccount> accounts;

	public Jukebox()  { //throws InterruptedException
		this.curAccount = null;
		this.accounts = new HashSet<>();
	}

	/**
	 * creates a jukebox account with the given details.
	 * 
	 * @param username
	 * @param password
	 * @return true if the account can be created, false if the account already
	 *         exists
	 */
	public boolean createAccount(String username, String password) {
		// can't create an account if someone else is already logged in
		if (curAccount != null) {
			return false;
		}
		// checks to make sure username is unique
		for (JukeboxAccount jba : accounts) {
			if (jba != null && jba.getUsername().equals(username)) {
				return false;
			}
		}
		accounts.add(new JukeboxAccount(username, password));
		return true;
	}

	/**
	 * for testing. creates an account based off of an existing account object
	 * 
	 * @param JBA
	 * @return true if the account can be created, false if the account already
	 *         exists
	 */
	public boolean createAccount(JukeboxAccount JBA) {
		// can't create an account if someone else is already logged in
		if (curAccount != null) {
			return false;
		}
		// checks to make sure username is unique
		for (JukeboxAccount jba : accounts) {
			if (jba != null && jba.getUsername().equals(JBA.getUsername())) {
				return false;
			}
		}
		accounts.add(JBA);
		return true;
	}

	/**
	 * iterates through the set to see if an account with the given username exists,
	 * then checks to log in
	 * 
	 * @param username
	 * @param password
	 * @return true if the account credentials are correct, otherwise false
	 */
	public boolean logIn(String username, String password) {
		// can't login if someone is already logged in
		if (curAccount != null) {
			return false;
		}
		for (JukeboxAccount acct : accounts) {
			if (acct.getUsername().equals(username)) {
				if (acct.checkCredentials(username, password)) {
					curAccount = acct;
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * sets current account to null
	 */
	public void logOut() {
		this.curAccount = null;
	}

	public JukeboxAccount getCurAccount() {
		return curAccount;
	}

	public void setAccounts(HashSet<JukeboxAccount> accounts) {
		this.accounts = accounts;
	}

	public HashSet<JukeboxAccount> getAccounts() {
		return accounts;
	}

}
