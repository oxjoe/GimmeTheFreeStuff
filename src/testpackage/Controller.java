package testpackage;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jsoup.nodes.Document;

/**
 * Created by Joseph on 7/6/2017.
 */


// todo Bugs
//  refreshTextfield and updateTextfield can't accept ""
//  Numbers are all in ints

public class Controller {

  @FXML // fx:id="linkTextfield"
  private TextField linkTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="backButton"
  private Button backButton; // Value injected by FXMLLoader

  // Todo - change status text to show if input was accepted or not
  @FXML // fx:id="statusText"
  private Text statusText; // Value injected by FXMLLoader

  @FXML // fx:id="refreshCheckbox"
  private CheckBox refreshCheckbox; // Value injected by FXMLLoader

  @FXML // fx:id="refreshTextfield"
  private TextField refreshTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="updatesCheckbox"
  private CheckBox updatesCheckbox; // Value injected by FXMLLoader

  @FXML // fx:id="updatesTextfield"
  private TextField updatesTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="currentLink"
  private Hyperlink currentLink; // Value injected by FXMLLoader

  @FXML // fx:id="githubLink"
  private Hyperlink githubLink; // Value injected by FXMLLoader

  // Set title
  // Fill in UI
  // Add listeners to textfields
  public void initialize() {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();

    addListeners();
    try {
      Main.getStage().setTitle("GimmeTheFreeStuff - " + obj.getTitle(getSetProps.getLink()));
      fillInUI();
      System.out.println("Success - Set title");
      System.out.println("Success - Filled In UI");
    } catch (IOException e) {
      System.out.println("FAILED - Unable to set the title");
      System.out.print("FAILED - Couldn't fill in UI");
      e.printStackTrace();
    }
    System.out.println("*** Controller Initialized ***");
  }

  // Adds listeners to refreshTextField and updatesTextfield in case user changes them, but
  // doesn't check the box
  @FXML
  void addListeners() {
    GetSetProps obj = new GetSetProps();
    // Listener for refreshTextfield
    refreshTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        obj.setRefresh(Integer.parseInt(newValue));
        System.out.println("Success - Added refresh listener to textfield");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    // Listener for updatesTextfield
    updatesTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        obj.setUpdate(Integer.parseInt(newValue));
        System.out.println("Success - Added update listener to textfield");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

  }

  // Fills in UI by getting properties from user.properties
  @FXML
  void fillInUI() throws IOException {
    GetSetProps getSetProps = new GetSetProps();
    currentLink.setText(getSetProps.getLink());
    refreshTextfield.setText(Integer.toString(getSetProps.getRefresh()));
    refreshCheckbox.setSelected(getSetProps.isRefreshChecked());
    updatesTextfield.setText(Integer.toString(getSetProps.getUpdates()));
    updatesCheckbox.setSelected(getSetProps.isUpdatesChecked());
  }

  // If checkbox is checked then save number of minutes/days and set corresponding property to
  // true in user.properties
  @FXML
  void refreshListChecked() {
    if (refreshCheckbox.isSelected()) {
      GetSetProps obj = new GetSetProps();
      try {
        obj.setRefresh(Integer.parseInt(refreshTextfield.getText()));
        obj.setRefreshChecked(true);
        System.out.println("Success - refresh settings are changed in file");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  void updatesChecked() {
    if (updatesCheckbox.isSelected()) {
      GetSetProps obj = new GetSetProps();
      try {
        obj.setUpdate(Integer.parseInt(updatesTextfield.getText()));
        obj.setUpdatesChecked(true);
        System.out.println("Success - update settings are changed in file");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  // Gets the link from the TextField and tries to parse it with jSoup
  void testLink() {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();
    String url = linkTextfield.getText();
    Document doc = obj.changeLink(url);
    currentLink.setText(url);
    if (doc == null) {
    // Todo, Important - Let user know that document couldn't parse by jSoup
    }
    try {
      getSetProps.setLink(url);
    } catch (IOException e) {
      System.out.println("FAILED - Couldn't save url property into file");
      e.printStackTrace();
    }
  }

  // Button to switch back to the Main UI
  @FXML
  void gotoMain() throws IOException {
    Stage stage = (Stage) backButton.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    System.out.println("*** Switched Screens ***");
  }

  // Opens GitHub in browser
  @FXML
  void openGithub() {
    Main main = new Main();
    main.openUrl("https://github.com/oxjoe/GimmeTheFreeStuff");
  }

  // Opens Craigslist in browser
  @FXML
  void openCraigslist() {
    Main main = new Main();
    main.openUrl(currentLink.getText());
  }

  // If user HITS enter then it goes to testLink()
  @FXML
  void craigslistTextfieldEnter(KeyEvent e) {
    if (e.getCode() == KeyCode.ENTER) {
      testLink();
    }
  }
}