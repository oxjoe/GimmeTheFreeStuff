package testpackage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jsoup.nodes.Document;

/**
 * Created by Joseph on 7/6/2017.
 */

// todo Bugs
//  Gets the lastest Postings not including updates which is why if u open craigslist and compare it to the app it will look different
//  refreshTextfield and updateTextfield can't accept ""
//  Numbers are all in ints

public class Controller {

  @FXML // fx:id="linkTextfield"
  private TextField linkTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="backButton"
  private Button backButton; // Value injected by FXMLLoader

  @FXML // fx:id="statusText"
  private Text statusText; // Value injected by FXMLLoader

  @FXML // fx:id="refreshCheckbox"
  private CheckBox refreshCheckbox; // Value injected by FXMLLoader

  @FXML // fx:id="refreshTextfield"
  private TextField refreshTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="updatesCheckbox"
  private CheckBox updateCheckbox; // Value injected by FXMLLoader

  @FXML // fx:id="updatesTextfield"
  private TextField updateTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="currentLink"
  private Hyperlink currentLink; // Value injected by FXMLLoader

  public void initialize() {
    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();
    addListeners();

    try {
      Main.getStage()
          .setTitle("GimmeTheFreeStuff - " + gimmeTheFreeStuff.getTitle(getSetProps.getLink()));
      fillInUI();
      System.out.println("Set title and filled in UI");
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("*** Controller Initialized ***");
  }

  // addListeners: N/A -> N/A
  // Adds listeners to the two textfields in case user changes the refreshRate (minutes) and
  // updateRate (days)
  @FXML
  void addListeners() {
    GetSetProps obj = new GetSetProps();

    refreshTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.matches("\\d+")) {
        try {
          obj.setAllProps("refreshRate", newValue);
          System.out.println("Success - Added refresh listener to textfield");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    updateTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.matches("\\d+")) {
        try {
          obj.setAllProps("updateRate", newValue);
          System.out.println("Success - Added update listener to textfield");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  // refreshListChecked: N/A -> N/A
  // If checkbox is checked or unchecked then set it to true or false in user.properties
  @FXML
  void refreshChecked() throws IOException {
    GetSetProps obj = new GetSetProps();
    if (refreshCheckbox.isSelected()) {
      obj.setAllProps("refreshStatus", "true");
      System.out.println("refreshStatus set to true");
    } else {
      obj.setAllProps("refreshStatus", "false");
      System.out.println("refreshStatus set to false");
    }
  }

  // changeLink: N/A -> N/A
  // Gets the link from the textfield to see if it does or doesn't work and displays the
  // appropriate text using passOrFailed
  @FXML
  void changeLink() {
    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();

    String url = linkTextfield.getText();
    Document doc = gimmeTheFreeStuff.testLink(url);

    if (doc == null) {
      passOrFailed("Failed");
      return;
    } else {
      passOrFailed("Success");
      currentLink.setText(url);
      try {
        getSetProps.setAllProps("link", url);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  // passOrFailed: String -> N/A
  // Sets text to either Success or Failed after a short delay if the link was successfully
  // parsed in changeLink
  void passOrFailed(String status) {
    Timer timer = new Timer();

    if (status.compareTo("Failed") == 0) {
      statusText.setText("Failed");
      statusText.setFill(Color.RED);
    } else {
      statusText.setText("Success");
      statusText.setFill(Color.GREEN);
    }

    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        statusText.setText("Press enter to submit");
        statusText.setFill(Color.BLACK);
        timer.cancel();
      }
    };
    timer.schedule(task, 2000);
  }

  // fillInUI: N/A -> N/A
  // Fills in UI by getting properties from user.properties
  @FXML
  void fillInUI() throws IOException {
    GetSetProps obj = new GetSetProps();
    currentLink.setText(obj.getLink());
    refreshTextfield.setText(obj.getRefreshRate());
    refreshCheckbox.setSelected(Boolean.parseBoolean(obj.getRefreshStatus()));
    updateTextfield.setText(obj.getUpdateRate());
    updateCheckbox.setSelected(Boolean.parseBoolean(obj.getUpdateStatus()));
  }

  // updatesChecked: N/A -> N/A
  // Sets updateStatus to true or false if the checkbox is checked or not
  @FXML
  void updateChecked() throws IOException {
    GetSetProps obj = new GetSetProps();
    if (updateCheckbox.isSelected()) {
      obj.setAllProps("updateStatus", "true");
      System.out.println("Update changed to TRUE");
    } else {
      obj.setAllProps("updateStatus", "false");
      System.out.println("Update to FALSE");
    }
  }

  // gotoMain: N/A -> N/A
  // When "Back to Main Screen" button is clicked, it switches stages
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

  // If user hits enter in the
  @FXML
  void craigslistTextfieldEnter(KeyEvent e) {
    if (e.getCode() == KeyCode.ENTER) {
      changeLink();
    }
  }
}