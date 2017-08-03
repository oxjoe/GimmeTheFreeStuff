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
 * Created by Joseph on 7/6/2017. Purpose of Controller.java: Handles I/O for
 * SettingsUserInterface.fxml
 */

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

    try {
      Main.getStage()
          .setTitle("GimmeTheFreeStuff - " + gimmeTheFreeStuff.getTitle(getSetProps.getLink()));
      fillInUI();
    } catch (IOException e) {
      e.printStackTrace();
    }
    addListeners();
    System.out.println("*** Controller Initialized ***");
  }

  // addListeners: N/A -> N/A
  // Adds listeners to the two textfields in case user changes the refreshRate (minutes) and
  // updateRate (days)
  @FXML
  private void addListeners() {
    GetSetProps obj = new GetSetProps();

    refreshTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.matches("\\d+") && newValue.compareTo(oldValue) != 0) {
        try {
          obj.setAllProps("refreshRate", newValue);
          if (obj.getRefreshStatus().compareTo("true") == 0) {
            Refresh.shutdownScheduler();
            Refresh.createAndStartScheduler();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    updateTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.matches("\\d+") && newValue.compareTo(oldValue) != 0) {
        try {
          obj.setAllProps("updateRate", newValue);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  // refreshListChecked: N/A -> N/A
  // If checkbox is checked or unchecked then set it to true or false in user.properties
  @FXML
  private void refreshChecked() throws IOException {
    GetSetProps getSetProps = new GetSetProps();

    if (refreshCheckbox.isSelected()) {
      getSetProps.setAllProps("refreshStatus", "true");
      Refresh.createAndStartScheduler();
      System.out.println("refreshCheckbox is True");
    } else if (!refreshCheckbox.isSelected()) {
      getSetProps.setAllProps("refreshStatus", "false");
      Refresh.shutdownScheduler();
      System.out.println("refreshCheckbox is False");
    }
  }

  // changeLink: N/A -> N/A
  // Gets the link from the textfield to see if it does or doesn't work and displays the
  // appropriate text using passOrFailed
  @FXML
  private void changeLink() {
    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();

    String url = linkTextfield.getText();
    Document doc = gimmeTheFreeStuff.testLink(url);

    if (doc == null) {
      passOrFailed("Failed");
    } else {
      passOrFailed("Success");
      currentLink.setText(url);
      try {
        getSetProps.setAllProps("link", url);
        if (getSetProps.getRefreshStatus().compareTo("true") == 0) {
          Refresh.shutdownScheduler();
          Refresh.createAndStartScheduler();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  // passOrFailed: String -> N/A
  // Sets text to either Success or Failed after a short delay if the link was successfully
  // parsed in changeLink
  private void passOrFailed(String status) {
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
  private void fillInUI() throws IOException {
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
  private void updateChecked() throws IOException {
    GetSetProps obj = new GetSetProps();
    if (updateCheckbox.isSelected()) {
      obj.setAllProps("updateStatus", "true");
      System.out.println("updateCheckbox changed to TRUE");
    } else {
      obj.setAllProps("updateStatus", "false");
      System.out.println("updateCheckbox changed to FALSE");
    }
  }

  // gotoMain: N/A -> N/A
  // When "Back to Main Screen" button is clicked, it switches stages
  @FXML
  private void gotoMain() throws IOException {
    Stage stage = (Stage) backButton.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainUserInterface.fxml"));
    Parent root = loader.load();

    // Sets the current instance of the Controller so Refresh can update it if needed
    MainController controller = loader.getController();
    Refresh.setMainController(controller);

    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    System.out.println("*** Switched Screens ***");
  }

  // Opens GitHub in browser
  @FXML
  private void openGithub() {
    Main main = new Main();
    main.openUrl("https://github.com/oxjoe/GimmeTheFreeStuff");
  }

  // Opens Craigslist in browser
  @FXML
  private void openCraigslist() {
    Main main = new Main();
    main.openUrl(currentLink.getText());
  }

  // Listener for Enter key being hit in link textfield
  @FXML
  private void craigslistTextfieldEnter(KeyEvent e) {
    if (e.getCode() == KeyCode.ENTER) {
      changeLink();
    }
  }
}