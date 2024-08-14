# Jukebox

Jukebox is a Java-based music player project developed by Ahmad and Oscar. The application allows users to play music files, manage playlists, and interact with the player through a graphical user interface (GUI).


## 📊 Project Structure

The project is organized as follows:

```
Jukebox/
├── .idea/
├── out/
├── songfiles/
├── src/
│ ├── controller_view/
│ │ ├── JukeboxGUI.java
│ │ ├── JukeboxGUI_OLD.txt
│ │ ├── LoginCreateAccountPane.java
│ │ └── SongSelector.java
│ ├── demoMediaPlayer/
│ │ ├── PlayAnMP3.java
│ │ └── PlayThreeSongs.java
│ ├── model/
│ │ ├── Date.java
│ │ ├── Jukebox.java
│ │ ├── JukeboxAccount.java
│ │ ├── PlayList.java
│ │ └── Song.java
│ └── tests/
│ ├── JukeboxAccountTest.java
│ └── Test.java
├── .gitattributes
├── .gitignore
├── README.md
├── jukebox-ahmad-oscar-main.iml
├── jukebox.ser
└── jukebox_EMPTY.ser
```


## Features

- **Music Playback:** Play MP3 files from your local collection.
- **Playlist Management:** Create, save, and load playlists.
- **User Accounts:** Login and create accounts to personalize your experience.
- **Graphical User Interface:** Interact with the Jukebox using a user-friendly GUI.
- **Testing Suite:** Includes unit tests to ensure the integrity of the application.

## Installation

To set up the project locally, follow these steps:

1. Clone the repository:
    ```
    git clone <repository-url>
    ```
2. Navigate to the project directory:
    ```
    cd Jukebox
    ```
3. Open the project in your preferred Java IDE (such as IntelliJ IDEA or Eclipse).

4. Ensure you have JDK installed and properly configured.

## Usage

1. Build and run the project from your IDE.
2. Use the GUI to log in or create an account.
3. Load or create a playlist and start playing your favorite songs.

## Project Files

### `src/controller_view/JukeboxGUI.java`

This file contains the main GUI implementation for the Jukebox, allowing users to interact with the player.

### `src/controller_view/LoginCreateAccountPane.java`

Handles the user interface components for logging in and creating new accounts within the Jukebox application.

### `src/controller_view/SongSelector.java`

Manages the selection of songs within the GUI, allowing users to choose tracks from their playlists.

### `src/demoMediaPlayer/PlayAnMP3.java`

A simple class that demonstrates playing a single MP3 file.

### `src/demoMediaPlayer/PlayThreeSongs.java`

Demonstrates playing multiple songs in sequence using the Jukebox's media player.

### `src/model/Date.java`

Represents date management within the application, likely used for tracking play dates or account creation dates.

### `src/model/Jukebox.java`

The core class that manages the overall Jukebox functionality, including interacting with songs, playlists, and accounts.

### `src/model/JukeboxAccount.java`

Handles user account information and operations such as login, registration, and account management.

### `src/model/PlayList.java`

Manages playlists within the Jukebox, including creating, saving, and loading playlists.

### `src/model/Song.java`

Represents individual songs within the Jukebox, including attributes like title, artist, and file location.

### `src/tests/JukeboxAccountTest.java`

Contains unit tests for the `JukeboxAccount` class to ensure proper functionality.

### `src/tests/Test.java`

General testing file that likely contains various unit tests to ensure the stability and reliability of the Jukebox application.



