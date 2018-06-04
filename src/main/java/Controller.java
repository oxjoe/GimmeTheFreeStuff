import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Joseph on 7/6/2017.
 */

// Purpose of Controller.java: Handles I/O for Settings.fxml
public class Controller {

  @FXML // fx:id="currentLink"
  private Hyperlink currentLink; // Value injected by FXMLLoader

  @FXML // fx:id="linkTextfield"
  private TextField linkTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="statusText"
  private Text statusText; // Value injected by FXMLLoader

  @FXML // fx:id="refreshCheckbox"
  private CheckBox refreshCheckbox; // Value injected by FXMLLoader

  @FXML // fx:id="refreshTextfield"
  private TextField refreshTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="runOnStartupCheckbox"
  private CheckBox runOnStartupCheckbox; // Value injected by FXMLLoader

  @FXML // fx:id="updateCheckbox"
  private CheckBox updateCheckbox; // Value injected by FXMLLoader

  @FXML // fx:id="newUpdateLabel"
  private Hyperlink newUpdateHyperlink; // Value injected by FXMLLoader

  public void initialize() {
    try {
      setTitle();
      fillInUI();
    } catch (IOException e) {
      e.printStackTrace();
    }
    addListener();
    checkForUpdate();
    System.out.println("*** Controller Initialized ***");
  }

  // checkForUpdate: N/A -> N/A
  // Checks to see if a new update for GimmeTheFreeStuff is available
  private void checkForUpdate() {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    String currentVer = "https://github.com/oxjoe/GimmeTheFreeStuff/blob/master/v1.0.txt";
    Document doc = obj.testLink(currentVer);
    // If I test the current version and its not there, then that means that I've updated this
    // app since the new version would be v1."new version number".txt
    if (doc == null) {
      newUpdateHyperlink.setVisible(true);
    }
  }

  // setTitle: N/A -> N/A
  // Sets title of Settings UI
  private void setTitle() throws IOException {
    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();
    MainController.getSettingsStage()
            .setTitle("GimmeTheFreeStuff - " + gimmeTheFreeStuff.getTitle(getSetProps.getLink()));
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
    boolean updateMainUI = false;

    if (doc == null) {
      passOrFailed("Failed");
    } else {
      passOrFailed("Success");
      currentLink.setText(url);
      updateMainUI = true;
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
    // If link has been changed then reinitialize MainController.java (title and populate table)
    if (updateMainUI) {
      Refresh.getMainController().initialize();
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
        statusText.setVisible(false);
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
    updateCheckbox.setSelected(Boolean.parseBoolean(obj.getUpdateStatus()));
    runOnStartupCheckbox.setSelected(Boolean.parseBoolean(obj.getRunOnStartupStatus()));
  }

  // addListener: N/A -> N/A
  // Adds listener to refreshTextfield in case user changes refreshRate
  @FXML
  private void addListener() {
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

  // runOnStartUpChecked: N/A -> N/A
  // Sets runOnStartUpChecked to true or false if the checkbox is checked or not
  @FXML
  private void runOnStartupChecked() throws IOException {
    GetSetProps obj = new GetSetProps();
    if (runOnStartupCheckbox.isSelected()) {
      obj.setAllProps("runOnStartup", "true");
      System.out.println("runOnStartup changed to TRUE");
    } else {
      obj.setAllProps("runOnStartup", "false");
      System.out.println("runOnStartup changed to FALSE");
    }
  }

  // gotoMain: N/A -> N/A
  // When "Back to Main Screen" button is clicked, it switches stages
  @FXML
  private void gotoMain() throws IOException {
    MainController.getSettingsStage().close();
    System.out.println("*** Going to Main ***");
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