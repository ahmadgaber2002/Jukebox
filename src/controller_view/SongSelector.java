package controller_view;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Song;

public class SongSelector {

	private TableView<Song> tableView;
	private ListView<Song> listView;
	private ObservableList<Song> songs;
	private List<Song> playlistList;
	private Song selectedSong;

	public SongSelector() throws FileNotFoundException {
		playlistList = new ArrayList<>();
		populateList();
		songs = FXCollections.observableArrayList(playlistList);
		listView = new ListView<>(songs);
		tableView = new TableView<>(songs);
		constructAndFillTable();
		tableView.setEditable(false);
		tableView.setPrefWidth(325);
	}
	
	private void populateList() throws FileNotFoundException {
		playlistList.add(new Song("Caught a Pokémon!", "Game Freak", 5, "Capture.mp3"));
		playlistList.add(new Song("Danse Macabre - Violin Hook", "Kevin MacLeod", 34, "DanseMacabreViolinHook.mp3"));
		playlistList.add(new Song("Determined Tumbao 20", "FreePlay Music", 20, "DeterminedTumbao.mp3"));
		playlistList.add(new Song("Longing In Their Hearts", "Bonnie Raitt", 288, "LongingInTheirHearts.mp3"));
		playlistList.add(new Song("Looping Sting", "Kevin MacLeod", 4, "LopingSting.mp3"));
		playlistList.add(new Song("Swing Cheese 15", "FreePlay Music", 15, "SwingCheese.mp3"));
		playlistList.add(new Song("The Curtain Rises High Quality ", "Kevin MacLeod", 28, "TheCurtainRises.mp3"));
		playlistList.add(new Song("Untameable Fire", "Unknown", 281, "UntameableFire.mp3"));
		playlistList.add(new Song("Neutral 1", "Project Moon", 243, "05 Neutral 1.mp3"));
	}

	private void constructAndFillTable() {
				// Create columns
		TableColumn<Song, String> titleColumn = new TableColumn<>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		titleColumn.setSortable(true);

		TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
		artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
		artistColumn.setSortable(true);

		TableColumn<Song, Duration> playtimeColumn = new TableColumn<>("Time");
		playtimeColumn.setCellValueFactory(new PropertyValueFactory<>("playTime"));
		playtimeColumn.setSortable(true);

		// Add columns to TableView
		tableView.getColumns().addAll(titleColumn, artistColumn, playtimeColumn);
	}
	
	public Song getSelectedSong() throws FileNotFoundException, InterruptedException {
		int index = tableView.getSelectionModel().getSelectedIndex();
		selectedSong = songs.get(index);
		return selectedSong;
	}


	public TableView<Song> getTableView() {
		return tableView;
	}
}

	//	public Song getSelectedSongView() throws FileNotFoundException, InterruptedException {
//		int index = listView.getSelectionModel().getSelectedIndex();
//		selectedSong = songs.get(index);
//		return selectedSong;

		// Add event listener for song selection
//		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//			if (newSelection != null) {
//				selectedSong = newSelection;
//			}
//		});
//		return selectedSong;
//	}

//	private void loadSongs() {
//		File folder = new File("songfiles/");
//		File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
//
//		if (listOfFiles != null) {
//			for (File file : listOfFiles) {
//				if (file.isFile()) {
//					try {
//						String fileName = file.getName();
//						Song song = new Song(fileName);
//						songs.add(song);
//					} catch (Exception e) {
//						System.err.println(e.getMessage());
//					}
//				}
//			}
//		}
//
//		tableView.setItems(songs);
//	}


