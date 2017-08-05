package testpackage;

/**
 * Created by Joseph on 7/6/2017. Purpose of MainController.java: Handles I/O for
 * MainUserInterface.fxml
 */

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jsoup.nodes.Document;

public class MainController {

  @FXML // fx:id="searchTextfield"
  private TextField searchTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="craigslistButton"
  private Button craigslistButton; // Value injected by FXMLLoader

  @FXML // fx:id="settingsButton"
  private Button settingsButton; // Value injected by FXMLLoader

  @FXML // fx:id="updateListButton"
  private Button updateListButton; // Value injected by FXMLLoader

  @FXML // fx:id="successText"
  private Text successText; // Value injected by FXMLLoader

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

  public void initialize() {
    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();
    System.out.println(
        "MainController Init prints main.getCurrentTime = " + Main.getCurrentTime().toString());
    // testLink is called twice in the try
    try {
      Main.getStage()
          .setTitle("GimmeTheFreeStuff - " + gimmeTheFreeStuff.getTitle(getSetProps.getLink()));
      populateTable("useCompareLists");
    } catch (IOException e) {
      System.err.println("Unable to set the title OR Unable to populate table");
      e.printStackTrace();
    }
    System.out.println("*** MainController Initialized ***");
  }

  // populateTable:  List<Item> -> List<Item>
  // Fills in the columns with data
  void populateTable(String temp) throws IOException {
    Main main = new Main();
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    urlCol.setCellValueFactory(new PropertyValueFactory<>("urlLink"));
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
//    List<Item> oList = parseItemList(temp);
    ObservableList<Item> oList = FXCollections.observableArrayList(parseItemList(temp));

    // If the URL link is clicked then it will open it in the browser
    for (Item e : oList) {
      Hyperlink link = e.getUrlLink();
      link.setOnAction(event -> {
        String a = e.getUrlLink().toString();
        String[] split = a.split("'");
        main.openUrl(split[1]);
      });
    }

    tableView.setRowFactory(tv -> new TableRow<Item>() {
      @Override
      public void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
          this.setId("rowOldItem");
        } else if (item.isStatus()) {
          this.setId("rowNewItem");
        } else {
          this.setId("rowOldItem");
        }
      }
    });

//    for (Item e : oList) {
//      tableView.setRowFactory(new Callback<TableView<Item>, TableRow<Item>>() {
//        @Override
//        public TableRow<Item> call(TableView<Item> param) {
//          final TableRow<Item> row = new TableRow<Item>(){
//            @Override
//            protected void updateItem(Item item, boolean state) {
//              super.updateItem(item, state);
//              if (e.isStatus()) {
//                row.pseudoClassStateChanged(newItem, true);
//                System.out.println("psudeo true");
//              } else {
//                tableView.pseudoClassStateChanged(oldItem, false);
//                System.err.println("psudeo false");
//              }
//            }
//          };
//          return row;
//        }
//      });
//
//    }

    tableView.getItems().setAll(oList);
    success();
    System.out.println("POPULATED TABLE");
  }

  // parseItemList:  List<Item> ->  List<Item>
  // Returns the list by using the url from user.properties
  private List<Item> parseItemList(String temp) throws IOException {

    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();

    String url = getSetProps.getLink();
    Document doc = gimmeTheFreeStuff.testLink(url);
    List<Item> list = gimmeTheFreeStuff.getData(doc, url);

    if (temp.compareTo("useCompareLists") == 0) {
      System.out.println("OLD VERSION = " + Main.getCurrentTime());
      List<Item> tempList = gimmeTheFreeStuff.compareLists(list, Main.getCurrentTime());
      Main.setCurrentTime(LocalDateTime.now());
      return tempList;
    }
    return list;
  }

  // success:  N/A -> N/A
  // Shows "Refreshed List!" for a few seconds if table is populated
  private void success() {
    successText.setVisible(true);
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        successText.setVisible(false);
        ;
        timer.cancel();
      }
    };
    timer.schedule(task, 1500);
  }

  @SuppressWarnings("EmptyMethod")
  @FXML
  private void searchTextfieldEnter(KeyEvent event) {
  }

  // gotoSettings: N/A -> N/A
  // When "Settings" button is clicked, it switches stages
  @FXML
  private void gotoSettings() throws IOException {
    Stage stage = (Stage) settingsButton.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("SettingsUserInterface.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    System.out.println("Switched to Settings page");
  }

  // craigslistClicked: N/A -> N/A
  // When "Take me to Craigslist" button is clicked, it takes you to Craigslist
  @FXML
  private void craigslistClicked() throws IOException {
    Main main = new Main();
    GetSetProps getSetProps = new GetSetProps();
    main.openUrl(getSetProps.getLink());
  }

  // updateListClicked: N/A -> N/A
  // When "Update List" button is clicked, it updates the list
  @FXML
  private void updateListClicked() throws IOException {
    populateTable("useCompareLists");
  }
}