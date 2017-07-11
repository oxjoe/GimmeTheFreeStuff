package testpackage;

import java.io.IOException;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Created by Joseph on 7/6/2017.
 */
public class Controller{

  /*fx:id="statusID"/>
            <TableColumn prefWidth="91.0" text="Date Posted" fx:id="dateID"/>
            <TableColumn prefWidth="113.0" text="Name" fx:id="nameID"/>
            <TableColumn prefWidth="100.0" text="Distance from Location" fx:id="distanceID"/>
            <TableColumn prefWidth="145.0" text="Websitelink" fx:id="urlID"/>
            */

  //
//  @FXML
//  private TextField searchTextfield;
//  @FXML
//  private Button craigslistButton;
  @FXML
  private Button backButton;
  @FXML
  private Button settingsButton;
  @FXML
  private Button updateListButton;
  @FXML
  private TableView<Item> tableView;
  @FXML
  private TableColumn<Item, Date> dateID;
  @FXML
  private TableColumn<Item, String> nameID;
  @FXML
  private TableColumn<Item, Boolean> statusID;
  //@FXML
  //private TableColumn<Item, String> distanceID;
  @FXML
  private TableColumn<Item, String> urlID;

//  private Boolean currentScreen;
//
//  public Controller() {
//    currentScreen = true;
//  }

  @FXML
  void switchScreens(ActionEvent e) throws IOException {
    Stage stage;
    Parent root;
    if (e.getSource() == settingsButton) {
      //currentScreen = false;
      System.out.println("Settings Button Clicked");

      stage = (Stage) settingsButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("SettingsUserInterface.fxml"));
    } else {
     // currentScreen = true;
      System.out.println("going back to main");

      stage = (Stage) backButton.getScene().getWindow();
      root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    }
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void initialize() {
      System.out.println("MAIN INIT");
//      statusID.setCellValueFactory(new PropertyValueFactory<>("status"));
//      dateID.setCellValueFactory(new PropertyValueFactory<>("date"));
//      nameID.setCellValueFactory(new PropertyValueFactory<>("name"));
//      urlID.setCellValueFactory(new PropertyValueFactory<>("urlLink"));
//   // tableView.getItems().setAll(parseItemList());

  }

//  public List<Item> parseItemList() throws ParseException {
//    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
//    System.out.println("reached parseItemList");
//    return obj.getData("https://bloomington.craigslist
// .org/search/zip?search_distance=10&postal=47405");
//
//  }

//  public void refreshListClicked() {
//    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
//    //obj.getData("life");
//
//  }

}
