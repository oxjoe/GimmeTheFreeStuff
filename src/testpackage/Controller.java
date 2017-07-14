package testpackage;

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
import org.jsoup.nodes.Document;

/**
 * Created by Joseph on 7/6/2017.
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

  @FXML // fx:id="minTextfield"
  private TextField minTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="updatesCheckbox"
  private CheckBox updatesCheckbox; // Value injected by FXMLLoader

  @FXML // fx:id="daysTextfield"
  private TextField daysTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="currentLink"
  private Hyperlink currentLink; // Value injected by FXMLLoader

  @FXML
  void refreshListChecked(ActionEvent e) {

  }
  @FXML
  void updatesChecked(ActionEvent e) {

  }

  public void initialize() {
    System.out.println("*** Controller Initialized ***");
    try {
      fillInUI();
    } catch (IOException e) {
      System.err.print("Couldn't fill in UI");
      e.printStackTrace();
    }
  }

  // Fills in ui with properties
  void fillInUI() throws IOException {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    currentLink.setText(obj.getPropertyValue("link"));
    minTextfield.setText(obj.getPropertyValue("updateRate"));
    daysTextfield.setText(obj.getPropertyValue("refreshRate"));
    refreshCheckbox.setSelected(Boolean.parseBoolean(obj.getPropertyValue("refreshChecked")));
    updatesCheckbox.setSelected(Boolean.parseBoolean(obj.getPropertyValue("updateChecked")));
    System.out.println("FINISHED FILL IN UI");
  }

  // Gets the link from the TextField and tries to parse it with jSoup
  void testLink() {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    String url = linkTextfield.getText();
    Document doc = obj.changeLink(url);
    while (doc == null) {
      // TODO - CONTROLLER
      // display something to let the user know that it couldn't parse the link
      // when its not null that means it successfully parsed
    }
    try {
      obj.changePropertyValue("link", url);
      success(url);
    } catch (IOException e) {
      System.err.print("obj.changeValue(\"url\", site) -> didn't work in testlink");
      e.printStackTrace();
    }
  }


  // TODO - CONTROLLER
  // change "press enter to submit" to Success for 1.5 seconds then change it back to "press...
  // Change previous hyperlink to new link
  // clear texfield
  void success(String url) {
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
  }


  // Button to switch UI's
  @FXML
  void gotoMain() throws IOException {
    Stage stage = (Stage) backButton.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("GimmeTheFreeStuff for CITYNAME");
    // TODO stage.setTitle("GimmeTheFreeStuff for CITYNAME")
    stage.show();
    System.out.println("Switched to Main page");
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