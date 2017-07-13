package testpackage;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Created by Joseph on 7/6/2017.
 */
public class MainController{

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

  @FXML
  void craigslistClicked(ActionEvent event) {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    Main main = new Main();
    main.openUrl(obj.getCraigslist());
    System.out.println("Clicked in MainController");
  }

  @FXML
  void searchTextfieldEnter(KeyEvent event) {
  }

  @FXML
  void updateListClicked(ActionEvent event) {
  }

  @FXML
  void gotoSettings(ActionEvent e) throws IOException {
    Stage stage = (Stage) settingsButton.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("SettingsUserInterface.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    System.out.println("Switched to Settings page");
  }


  public void initialize() {
    System.out.println("*** MainController Initialized ***");
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    try {
      obj.startup();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(obj.getCraigslist() + " in MainController");
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
