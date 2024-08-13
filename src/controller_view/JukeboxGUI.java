package controller_view;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashSet;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Jukebox;
import model.JukeboxAccount;
import model.Song;

public class JukeboxGUI extends Application {

	private TextField usernameField;
	private PasswordField passwordField;
	private Button loginButton;
	private Button logoutButton;
	private Button addButton;
	private Button createAccount;
	private Button play;
	private Button pause;
	private Label loggedInUserLabel;
	private Jukebox jukebox;
	private SongSelector songSelector;
	ListView<String> currentPlayListView;
	private ObservableList<String> currentPlayList;
	private VBox root;
	private final String FILE_NAME = "jukebox.ser";
	private Song currentSong;
	private MediaPlayer mediaPlayer;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		jukebox = new Jukebox();
		songSelector = new SongSelector();
		// startup alert
		alertOnStart();

		layoutButtons();
		layoutGUI();
		registerButtons();

		primaryStage.setTitle("Jukebox");
		primaryStage.setScene(new Scene(root));

		// Ask the user if he wants to save the current data
		alertOnClose(primaryStage);

		primaryStage.show();
	}

	private void layoutGUI() {
		HBox loginBox = new HBox(10, new Label("Username:"), usernameField, new Label("Password:"), passwordField,
				loginButton, logoutButton, createAccount);
		loginBox.setAlignment(Pos.CENTER);
		loginBox.setPadding(new Insets(10));

		VBox userBox = new VBox(10, loggedInUserLabel, addButton, play, pause);
		userBox.setAlignment(Pos.CENTER);
		userBox.setPadding(new Insets(10));

		currentPlayList = FXCollections.observableArrayList();
		currentPlayListView = new ListView<>(currentPlayList);
		currentPlayListView.setPrefWidth(325);

		HBox mainBox = new HBox(10, songSelector.getTableView(), userBox, currentPlayListView);
		mainBox.setPadding(new Insets(10));

		root = new VBox(10, loginBox, mainBox);
		root.setPadding(new Insets(10));
	}

	private void layoutButtons() {
		usernameField = new TextField();
		passwordField = new PasswordField();
		loginButton = new Button("Login");
		logoutButton = new Button("Logout");
		logoutButton.setDisable(true);
		addButton = new Button("Add to Playlist");
		createAccount = new Button("Create account");
		loggedInUserLabel = new Label("Not logged in");
		play = new Button(">");
		pause = new Button("||");
		addButton.setDisable(true);
		pause.setDisable(true);
		play.setDisable(true);
	}

	private void getCurrentUserInfo() throws FileNotFoundException, InterruptedException {
		JukeboxAccount currentAccount = jukebox.getCurAccount();
		currentAccount.getAccountPlayList().setPlayListStarted(false);
		for (int i = 0; i < currentAccount.getAccountPlayListAsList().size(); i++) {
			currentPlayList.add(currentAccount.getAccountPlayListAsList().get(i).getTitle());
		}

		if (!jukebox.getCurAccount().getAccountPlayList().isPlayListStarted()) {
			jukebox.getCurAccount().getAccountPlayList().setPlayListStarted(true);
			playNextSong();
		}
	}
	private void registerButtons() {
		// Login
		loginButton.setOnAction(e -> {
			if (usernameField.getText().length() == 0 || passwordField.getText().length() == 0) {
				alertWindowAccountNullEntry();
				return;
			}
			if (!jukebox.logIn(usernameField.getText(), passwordField.getText())) {
				alertWindowIncorrectLogin();
				return;
			}
			addButton.setDisable(false);
			pause.setDisable(false);
			play.setDisable(false);
			loggedInUserLabel.setText("current user is "+usernameField.getText());
			usernameField.setText("");
			usernameField.setDisable(true);
			passwordField.setText("");
			passwordField.setDisable(true);
			logoutButton.setDisable(false);
			loginButton.setDisable(true);
			createAccount.setDisable(true);
			try {
				getCurrentUserInfo();
			} catch (FileNotFoundException | InterruptedException ex) {
				throw new RuntimeException(ex);
			}
		});

		// Logout
		logoutButton.setOnAction(e -> {
			try {
				pause();
				mediaPlayer = null;
			} catch (FileNotFoundException ex) {
				throw new RuntimeException(ex);
			}
			jukebox.logOut();
			currentPlayList.clear();
			addButton.setDisable(true);
			pause.setDisable(true);
			play.setDisable(true);
			loggedInUserLabel.setText("Not logged in");
			usernameField.setDisable(false);
			passwordField.setDisable(false);
			logoutButton.setDisable(true);
			loginButton.setDisable(false);
			createAccount.setDisable(false);
		});

		//Add Song
		addButton.setOnAction(e -> {
			try {
				Song selected = songSelector.getSelectedSong();
				if (!jukebox.getCurAccount().queueUpNextSong(selected)) {
					alertWindowNoCredits();
					return;
				}
				currentPlayList.add(selected.getTitle());
				if (!jukebox.getCurAccount().getAccountPlayList().isPlayListStarted()) {
					jukebox.getCurAccount().getAccountPlayList().setPlayListStarted(true);
					playNextSong();
				}
			} catch (InterruptedException | FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});

		// Create Account
		createAccount.setOnAction(e -> {
			if (usernameField.getText().length() == 0 || passwordField.getText().length() == 0) {
				alertWindowAccountNullEntry();
				return;
			}
			if (!jukebox.createAccount(usernameField.getText(), passwordField.getText())) {
				alertWindowAccountAlreadyExists();
				return;
			}
			usernameField.setText("");
			passwordField.setText("");
			alertWindowCreateAccount();
		});

		// Play Song
		play.setOnAction(e -> {
			try {
				resume();
			} catch (FileNotFoundException ex) {
				throw new RuntimeException(ex);
			}
		});

		// Pause Song
		pause.setOnAction(e -> {
			try {
				pause();
			} catch (FileNotFoundException ex) {
				throw new RuntimeException(ex);
			}
		});
	}

	private void alertWindowCreateAccount() {
		Alert a = new Alert(AlertType.INFORMATION);
		a.setHeaderText("Account has been created successfully");
		a.setTitle("Account Creation");
		a.show();
	}
	private void alertWindowAccountNullEntry() {
		Alert a = new Alert(AlertType.WARNING);
		a.setHeaderText("Empty user name and password");
		a.setContentText("Please enter user name and password");
		a.show();
	}
	private void alertWindowNoCredits() {
		Alert a = new Alert(AlertType.WARNING);
		a.setHeaderText("No jukebox credits available");
		a.setContentText("Please try again tomorrow");
		a.show();
	}

	private void alertWindowIncorrectLogin() {
		Alert a = new Alert(AlertType.WARNING);
		a.setHeaderText("Username/password is incorrect");
		a.setContentText("Please re-enter username or password");
		a.show();
	}

	private void alertWindowAccountAlreadyExists() {
		Alert a = new Alert(AlertType.WARNING);
		a.setHeaderText("An account with that username already exists");
		a.setContentText("Please choose a different username");
		a.show();
	}

	private void alertOnStart() {
		Alert alertOnStart = new Alert(Alert.AlertType.CONFIRMATION);
		alertOnStart.setTitle("Startup Option");
		alertOnStart.setHeaderText("Click cancel to start with no date");
		alertOnStart.setContentText("Start with saved date?");
		Optional<ButtonType> result = alertOnStart.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			// Load the todos list from the hard coded file
			loadFromFile();
		}
	}

	private void alertOnClose(Stage primaryStage) {
		primaryStage.setOnCloseRequest(event -> {
			if (true) {
				Alert alertOnClose = new Alert(Alert.AlertType.CONFIRMATION);
				alertOnClose.setTitle("Shutdown Option");
				alertOnClose.setHeaderText("Click cancel to not save any changes");
				alertOnClose.setContentText("To save the changes, click ok");
				Optional<ButtonType> resultOnClose = alertOnClose.showAndWait();
				if (resultOnClose.isPresent() && resultOnClose.get() == ButtonType.OK) {
					saveToFile();
				} else if (resultOnClose.get() == ButtonType.CANCEL) {
					// do nothing
				} else {
					event.consume();
				}
			}
		});
	}

	public void playNextSong() throws InterruptedException, FileNotFoundException {

		currentSong = jukebox.getCurAccount().getAccountPlayList().getNextSong();
		if (currentSong != null) {
			currentPlayListView.getSelectionModel().select(0);
			mediaPlayer = new MediaPlayer(currentSong.getSong());
			mediaPlayer.setOnReady(() -> { // so that metadata is loaded before playing the song
				// sets playtime for a song object at runtime
				if (currentSong.getPlayTimeAsInt() == 0) {
					currentSong.setPlayTime((int) mediaPlayer.getTotalDuration().toSeconds());
				}
				mediaPlayer.play();
				mediaPlayer.setOnEndOfMedia(new Waiter());
			});
		}
	}

	/**
	 * small class to wait 2 seconds after a song is completed.
	 */
	private class Waiter implements Runnable {
		@Override
		public void run() {

			// just for logging and testing purposes
			System.out.println("Song ended, sleeping for 2 sec.");

			currentPlayList.remove(0);


			try {
				mediaPlayer.dispose();
				Thread.sleep(2000);
				playNextSong();
			} catch (InterruptedException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * pauses the mediaplayer
	 */
	public void pause() throws FileNotFoundException {
		if (mediaPlayer != null) {
			mediaPlayer.pause();
		}
	}

	/**
	 * resumes the mediaplayer
	 */
	public void resume() throws FileNotFoundException {
		if (mediaPlayer != null) {
			mediaPlayer.play();
		}
	}

	private void saveToFile() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(jukebox.getAccounts());

			// just for logging and testing purposes
			for (JukeboxAccount j : jukebox.getAccounts()) {
				System.out.println("Account(s) saved to file \n" + j.toString());
			}

			objectOutputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadFromFile() {
		HashSet<JukeboxAccount> accountsSet = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			accountsSet = (HashSet<JukeboxAccount>) objectInputStream.readObject();
			jukebox.setAccounts(accountsSet);

			// just for logging and testing purposes
			for (JukeboxAccount j : accountsSet) {
				System.out.println("Account(s) loaded in startup \n" + j.toString());
			}

			objectInputStream.close();
			fileInputStream.close();
		}  catch(EOFException | StreamCorruptedException ignored){
		} catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}

}


}