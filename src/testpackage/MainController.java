package testpackage;

import java.io.IOException;
import java.util.List;
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
import org.jsoup.nodes.Document;

/**
 * Created by Joseph on 7/6/2017.
 */
public class MainController {

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

  // Opens Craigslist in browser
  @FXML
  void craigslistClicked() {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    Main main = new Main();
    main.openUrl(obj.getLink());
  }

  @FXML
  void searchTextfieldEnter(KeyEvent event) {
  }

  @FXML
  void updateListClicked(ActionEvent event) {
//    populateTable()
  }

  @FXML
  void gotoSettings() throws IOException {
    Stage stage = (Stage) settingsButton.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("SettingsUserInterface.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("GimmeTheFreeStuff for CITYNAME");
    // TODO stage.setTitle("GimmeTheFreeStuff for CITYNAME")
    stage.show();
    System.out.println("Switched to Settings page");
  }

  void populateTable() {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    String link = obj.getLink();
    Document doc = obj.changeLink(link);
    List<Item> list = obj.getData(doc, link);
        List<Item> correctList = obj.sortByDate(list);
    System.out.println(correctList.toString());
  }

  // TABLE THINGS
//      statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
//      dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
//      nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//      urlCol.setCellValueFactory(new PropertyValueFactory<>("urlLink"));
//   // tableView.getItems().setAll(parseItemList());



//  public List<Item> parseItemList() throws ParseException {
//    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
//    System.out.println("reached parseItemList");
//    return obj.getData("https://bloomington.craigslist
// .org/search/zip?search_distance=10&postal=47405");
//
//  }

  public void initialize() {
    System.out.println("*** MainController Initialized ***");
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    // Setup MainUI with user properties if those exist, or default properties
    try {
      obj.startup();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //populateTable();
  }
}
