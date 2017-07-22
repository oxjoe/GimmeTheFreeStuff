package testpackage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jsoup.nodes.Document;

/**
 * Created by Joseph on 7/6/2017.
 */
public class MainController {

  @FXML // fx:id="searchTextfield"
  private TextField searchTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="successText"
  private Text successText; // Value injected by FXMLLoader

  @FXML // fx:id="settingsButton"
  private Button settingsButton; // Value injected by FXMLLoader

  @FXML // fx:id="tableView"
  private TableView<Item> tableView; // Value injected by FXMLLoader

  @FXML // fx:id="statusCol"
  private TableColumn<Item, Boolean> statusCol; // Value injected by FXMLLoader

  @FXML // fx:id="dateCol"
  // USing string to display it because SimpleDateFormat.format returns a string
  private TableColumn<Item, String> dateCol; // Value injected by FXMLLoader

  @FXML // fx:id="nameCol"
  private TableColumn<Item, String> nameCol; // Value injected by FXMLLoader

  @FXML // fx:id="urlCol"
  private TableColumn<Item, Hyperlink> urlCol; // Value injected by FXMLLoader

  // Todo:
  // Make date posted user friendly
  // searchTextField enter

  public void initialize() {
    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();
    Main main = new Main();
    System.out.println(
        "MainController Init prints main.getCurrentTime = " + main.getCurrentTime().toString());
    // testLink is called twice in the try
    try {
      Main.getStage()
          .setTitle("GimmeTheFreeStuff for " + gimmeTheFreeStuff.getTitle(getSetProps.getLink()));
      populateTable("");
    } catch (IOException e) {
      System.out.println("Unable to set the title OR Unable to populate table");
      e.printStackTrace();
    }






    System.out.println("*** MainController Initialized ***");
  }

  void success() {
    Timer timer = new Timer();
    successText.setText("REFRESHED LIST");
    successText.setFill(Color.GREEN);
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        successText.setText("");
        timer.cancel();
      }
    };
    timer.schedule(task, 2000);
  }


  // gotoSettings: N/A -> N/A
  // When "Settings" button is clicked, it switches stages
  @FXML
  void gotoSettings() throws IOException {
    Stage stage = (Stage) settingsButton.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("SettingsUserInterface.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    System.out.println("Switched to Settings page");
  }

  // populateTable:  List<Item> -> List<Item>
  // Fills in the columns with data
  void populateTable(String temp) throws IOException {
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    urlCol.setCellValueFactory(new PropertyValueFactory<>("urlLink"));
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    ObservableList<Item> oList = FXCollections.observableArrayList(parseItemList(temp));

//    tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//      @Override
//      public void handle(MouseEvent event) {
//        ObservableList<TablePosition> a = tableView.getSelectionModel().getSelectedCells();
//        System.out.println(a);

////tableView.getSelectionModel().getSelectedItem().getUrlLink()

//      }
//    });

    tableView.getItems().setAll(oList);

    success();
    System.out.println("Populated Table");
  }
  @FXML
  void openItemUrl() {

  }

  // parseItemList:  List<Item> ->  List<Item>
  // Returns the list by using the url from user.properties
  public List<Item> parseItemList(String temp) throws IOException {
    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();
    Main main = new Main();

    String url = getSetProps.getLink();
    Document doc = gimmeTheFreeStuff.testLink(url);
    List<Item> list = gimmeTheFreeStuff.getData(doc, url);

    for (Item e : list) {
      Hyperlink link = e.getUrlLink();
      link.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          String a = e.getUrlLink().toString();
          String[] split = a.split("'");
          System.out.println(Arrays.toString(split));
          main.openUrl(split[1]);
        }
      });
    }
    //List<Item> sortedList = gimmeTheFreeStuff.sortByDate(list);

    //    makeDataUserFriendly(sortedList);
    if (temp.compareTo("useCompareLists") == 0) {
      System.out.println("OLD = " + main.getCurrentTime().toString());
      List<Item> tempList = gimmeTheFreeStuff.compareLists(list, main.getCurrentTime());
      main.setCurrentTime(new Date());
      System.out.println("NEW = " + main.getCurrentTime().toString());
      return tempList;
    }
    return list;
  }

  @FXML
  void searchTextfieldEnter(KeyEvent event) {
  }

  // craigslistClicked: N/A -> N/A
  // When "Take me to Craigslist" button is clicked, it takes you to Craigslist
  @FXML
  void craigslistClicked() throws IOException {
    Main main = new Main();
    GetSetProps getSetProps = new GetSetProps();
    main.openUrl(getSetProps.getLink());
  }

  // updateListClicked: N/A -> N/A
  // When "Update List" button is clicked, it updates the list
  @FXML
  void updateListClicked(ActionEvent event) throws IOException {
    populateTable("useCompareLists");
  }
}
