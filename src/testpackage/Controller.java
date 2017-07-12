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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Joseph on 7/6/2017.
 */
public class Controller {

  /*fx:id="statusID"/>
            <TableColumn prefWidth="91.0" text="Date Posted" fx:id="dateID"/>
            <TableColumn prefWidth="113.0" text="Name" fx:id="nameID"/>
            <TableColumn prefWidth="100.0" text="Distance from Location" fx:id="distanceID"/>
            <TableColumn prefWidth="145.0" text="Websitelink" fx:id="urlID"/>
            */

  // TODO - MAIN UI
  @FXML // fx:id="searchTextfield"
  private TextField searchTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="craigslistButton"
  private Button craigslistButton; // Value injected by FXMLLoader

  @FXML // fx:id="settingsButton"
  private Button settingsButton; // Value injected by FXMLLoader

  @FXML // fx:id="updateListButton"
  private Button updateListButton; // Value injected by FXMLLoader

  @FXML // fx:id="tableView"
  private TableView<?> tableView; // Value injected by FXMLLoader

  @FXML // fx:id="statusCol"
  private TableColumn<?, ?> statusCol; // Value injected by FXMLLoader

  @FXML // fx:id="dateCol"
  private TableColumn<?, ?> dateCol; // Value injected by FXMLLoader

  @FXML // fx:id="nameCol"
  private TableColumn<?, ?> nameCol; // Value injected by FXMLLoader

  @FXML // fx:id="urlCol"
  private TableColumn<?, ?> urlCol; // Value injected by FXMLLoader

  // TODO - SETTINGS UI
  @FXML // fx:id="settings"
  private AnchorPane settings; // Value injected by FXMLLoader

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

  //TODO - MAIN UI
  @FXML
  void craigslistClicked(ActionEvent event) {
  }

  @FXML
  void searchTextfieldEnter(KeyEvent event) {
  }

  @FXML
  void updateListClicked(ActionEvent event) {
  }

  //TODO - SETTINGS UI
  @FXML
  void refreshListChecked(ActionEvent event) {
  }

  @FXML
  void updatesChecked(ActionEvent event) {
  }

  //  public void refreshListClicked() {
//    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
//    //obj.getData("life");
//
//  }
  void firstStartup() {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    currentCraigslistText.setText("obj.getUserInput");
  }

  // If user HITS enter gets the link from the textfield and tries to parse it with jSoup
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
  void switchScreens(ActionEvent e) throws IOException {
    Stage stage;
    Parent root;
    if (e.getSource() == settingsButton) {
      //currentScreen = false;
      System.out.println("settings button clicked");

      stage = (Stage) settingsButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("SettingsUserInterface.fxml"));
    } else {
      // currentScreen = true;
      System.out.println("back to main clicked");

      stage = (Stage) backButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    }
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }


  public void initialize() {
    System.out.println("initialized");
//      statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
//      dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
//      nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//      urlCol.setCellValueFactory(new PropertyValueFactory<>("urlLink"));
//   // tableView.getItems().setAll(parseItemList());

  }

//  public List<Item> parseItemList() throws ParseException {
//    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
//    System.out.println("reached parseItemList");
//    return obj.getData("https://bloomington.craigslist
// .org/search/zip?search_distance=10&postal=47405");
//
//  }


}
