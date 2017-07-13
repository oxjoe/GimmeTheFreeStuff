package testpackage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
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

/**
 * Created by Joseph on 7/6/2017.
 */
public class Controller {

  @FXML // fx:id="currentCraigslistLink"
  private Hyperlink currentCraigslistLink; // Value injected by FXMLLoader

  @FXML // fx:id="newCraigslistTextfield"
  private TextField newCraigslistTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="backButton"
  private Button backButton; // Value injected by FXMLLoader

  @FXML // fx:id="enterNewLinkButton"
  private Button enterNewLinkButton; // Value injected by FXMLLoader

  @FXML // fx:id="statusText"
  private Text statusText; // Value injected by FXMLLoader

  @FXML // fx:id="refreshListCheckbox"
  private CheckBox refreshListCheckbox; // Value injected by FXMLLoader

  @FXML // fx:id="minutesTextfield"
  private TextField minutesTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="updatesCheckbox"
  private CheckBox updatesCheckbox; // Value injected by FXMLLoader

  @FXML // fx:id="daysTextfield"
  private TextField daysTextfield; // Value injected by FXMLLoader

  @FXML
  void refreshListChecked(ActionEvent event) {
  }

  @FXML
  void updatesChecked(ActionEvent event) {
  }

  @FXML
  void currentCraiglistClicked(ActionEvent event) {
    Main main = new Main();
    main.openUrl(currentCraigslistLink.getText());
    System.out.println("Clicked in Controller");
  }

  // If user HITS enter then gets the link from the textfield and tries to parse it with jSoup
  @FXML
  void newCraigslistTextfieldEnter(KeyEvent e) {
    if (e.getCode() == KeyCode.ENTER) {
      testLink();
    }
  }

  // If user CLICKS enter gets the link from the textfield and tries to parse it with jSoup
  @FXML
  void changeLinkClicked(ActionEvent e) {
    testLink();
  }

  void testLink() {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    String site = newCraigslistTextfield.getText();
    obj.changeLink(site);
    obj.setCraigslist();
    try {
      FileOutputStream out = new FileOutputStream("user.properties");
      obj.getUserProperties().setProperty("craigslist", site);
      obj.getUserProperties().store(out, null);
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    statusText.setText("Success!");
    statusText.setFill(Color.GREEN);
    Timer timer = new Timer();
    class timerTask extends TimerTask {

      @Override
      public void run() {

        timer.cancel();
      }
    }
    timer.schedule(new timerTask(), 1500);

//    statusText.setText("Press enter to submit");
//    statusText.setFill(Color.BLACK);
    currentCraigslistLink.setText(site);
  }

  @FXML
  void gotoMain(ActionEvent e) throws IOException {
    Stage stage = (Stage) backButton.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    System.out.println("Switched to Main page");
  }

  public void initialize() {
    System.out.println("*** Controller Initialized ***");
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    try {
      obj.startup();
    } catch (IOException e) {
      e.printStackTrace();
    }
    currentCraigslistLink.setText(obj.getCraigslist());
    System.out.println("SHOWS: " + currentCraigslistLink.getText());
  }
}
