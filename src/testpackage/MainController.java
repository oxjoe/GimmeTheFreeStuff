package testpackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
  private TableView<Item> tableView; // Value injected by FXMLLoader

  @FXML // fx:id="statusCol"
  private TableColumn<Item, Boolean> statusCol; // Value injected by FXMLLoader

  @FXML // fx:id="dateCol"
  private TableColumn<Item, Date> dateCol; // Value injected by FXMLLoader

  @FXML // fx:id="nameCol"
  private TableColumn<Item, String> nameCol; // Value injected by FXMLLoader

  @FXML // fx:id="urlCol"
  private TableColumn<Item, String> urlCol; // Value injected by FXMLLoader

  private GimmeTheFreeStuff obj;

  public GimmeTheFreeStuff getObj() {
    return obj;
  }

  public void setObj(GimmeTheFreeStuff obj) {
    this.obj = obj;
  }

  @FXML
  void craigslistClicked(ActionEvent event) throws IOException {
    Main main = new Main();
    main.openUrl(getLinkFromUserProperties());
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

  void populateTable() throws IOException {
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    urlCol.setCellValueFactory(new PropertyValueFactory<>("urlLink"));
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    tableView.getItems().setAll(parseItemList());
  }

  public List<Item> parseItemList() throws IOException {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    String link = getLinkFromUserProperties();
    Document doc = obj.changeLink(link);
    List<Item> list = obj.getData(doc, link);
    List<Item> sortedList = obj.sortByDate(list);
    System.out.println(sortedList.toString());
    return sortedList;
  }

  private String getLinkFromUserProperties() throws IOException {
    // TODO - Make another class to deal with properties or an interface?
    Properties prop = new Properties();
    FileInputStream in = new FileInputStream("user.properties");
    prop.load(in);
    String url = prop.getProperty("link");
    in.close();
    return url;
  }

  public void initialize() {
    System.out.println("*** MainController Initialized ***");
    try {
      populateTable();
    } catch (IOException e) {
      System.out.println("Enable to populate table");
      e.printStackTrace();
    }
  }
}
