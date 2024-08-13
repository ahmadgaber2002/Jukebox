package controller_view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Jukebox;

import java.io.FileNotFoundException;

//oscar was here, just doing an initial commit
public class LoginCreateAccountPane extends BorderPane {
  
  Jukebox jukebox;
  private Label label = new Label("Login or Create Account");
  private TextField usernameField;
  private PasswordField passwordField;
//  private Button loginButton;
//  private Button createAccount;

  public String getUsernameField() {
    return usernameField.getText();
  }

  public String getPasswordField() {
    return passwordField.getText();
  }

  public LoginCreateAccountPane () {
//    this.jukebox = theModel;
    usernameField = new TextField();
    passwordField = new PasswordField();
//    loginButton = new Button("Login");
//    createAccount = new Button("Create account");
    initialize();
  }

  private void initialize() {
    HBox userBox = new HBox(10, new Label("Username:"), usernameField);
    HBox passwordBox = new HBox(10, new Label("Password:"), passwordField);
//    HBox buttonsBox = new HBox(10, loginButton, createAccount);

    VBox mainBox = new VBox(10, userBox, passwordBox);
    userBox.setAlignment(Pos.CENTER);
    userBox.setPadding(new Insets(10));

    this.setTop(label);
    this.setCenter(mainBox);
  }

//  private void registerButtons() {
//    loginButton.setOnAction(e -> {
//      login();
//    });
//
//    createAccount.setOnAction(e -> {
//      createAccount();
//    });
//  }
//
//  private void createAccount() {
//  }
//
//  private void login() {
//    if (usernameField.getText().length() == 0 || passwordField.getText().length() == 0) {
//      alertWindowAccountNullEntry();
//      return;
//    }
//    if (!jukebox.logIn(usernameField.getText(), passwordField.getText())) {
//      alertWindowIncorrectLogin();
//      return;
//    }
//    usernameField.setText("");
//    passwordField.setText("");
//    try {
//      getCurrentUserInfo();
//    } catch (FileNotFoundException | InterruptedException ex) {
//      throw new RuntimeException(ex);
//    }
//  }
//
//  private void alertWindowAccountNullEntry() {
//    Alert a = new Alert(Alert.AlertType.WARNING);
//    a.setHeaderText("Empty user name and password");
//    a.setContentText("Please enter user name and password");
//    a.show();
//  }
//  private void alertWindowNoCredits() {
//    Alert a = new Alert(Alert.AlertType.WARNING);
//    a.setHeaderText("No jukebox credits available");
//    a.setContentText("Please try again tomorrow");
//    a.show();
//  }
//
//  private void alertWindowIncorrectLogin() {
//    Alert a = new Alert(Alert.AlertType.WARNING);
//    a.setHeaderText("Username/password is incorrect");
//    a.setContentText("Please re-enter username or password");
//    a.show();
//  }
//
//  private void alertWindowAccountAlreadyExists() {
//    Alert a = new Alert(Alert.AlertType.WARNING);
//    a.setHeaderText("An account with that username already exists");
//    a.setContentText("Please choose a different username");
//    a.show();
//  }


}