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

  @FXML // fx:id="currentCraigslistText"
  private Text currentCraigslistText; // Value injected by FXMLLoader

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
    //obj.getData(newLink);
    Timer timer = new Timer();
    statusText.setText("Success");
    statusText.setFill(Color.GREEN);
    class timerTask extends TimerTask {

      @Override
      public void run() {
        statusText.setText("");
        timer.cancel();
      }
    }
    timer.schedule(new timerTask(), 1500);
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

  }
}
