package freestuff;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jsoup.nodes.Document;

/**
 * Created by Joseph on 7/6/2017.
 */

// Purpose of MainController.java: Handles I/O for MainUserInterface.fxml
public class MainController {

  private static Stage settingsStage;

  // tba
  @FXML // fx:id="searchTextfield"
  private TextField searchTextfield; // Value injected by FXMLLoader

  @FXML // fx:id="successText"
  private Text successText; // Value injected by FXMLLoader

  @FXML // fx:id="countdownLabel"
  private Label countdownLabel; // // Value injected by FXMLLoader

  @FXML // fx:id="tableView"
  private TableView<Item> tableView; // Value injected by FXMLLoader

  @FXML // fx:id="statusCol"
  private TableColumn<Item, Boolean> statusCol; // Value injected by FXMLLoader

  @FXML // fx:id="dateCol"
  private TableColumn<Item, String> dateCol; // Value injected by FXMLLoader

  @FXML // fx:id="nameCol"
  private TableColumn<Item, String> nameCol; // Value injected by FXMLLoader

  @FXML // fx:id="urlCol"
  private TableColumn<Item, Hyperlink> urlCol; // Value injected by FXMLLoader

  public static Stage getSettingsStage() {
    return settingsStage;
  }

  public static void setSettingsStage(Stage settingsStage) {
    MainController.settingsStage = settingsStage;
  }

  public Label getCountdownLabel() {
    return countdownLabel;
  }

  //  private Duration countdown;
//  public Duration getCountdown() {
//    return countdown;
//  }
//  public void setCountdown(Duration countdown) {
//    this.countdown = countdown;
//  }

  public void initialize() {
    try {
      setTitle();
      populateTable("useCompareLists");
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("*** MainController Initialized ***");
  }

  // showProgress: long -> N/A
  // Updates countdownLabel with the countdown
  @FXML
  void showProgress(long delay) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a");
    LocalTime localTime = LocalTime.now().plusMinutes(delay);
    String niceTime = localTime.format(formatter);
    getCountdownLabel().setText(niceTime);
    System.out.println(niceTime);

/* tba timer live update
 //Display user friendly with hours and minutes, might be lagging and getting further apart?
//need to synchronize threads possibly?
Duration oneSecond = Duration.seconds(1);
    countdownLabel.setText(String.valueOf(getCountdown().toString()));
    System.out.println(getCountdown().toString());
    if (getCountdown().compareTo(oneSecond) == 0) {
      GetSetProps getSetProps = new GetSetProps();
      Long defaultDelay = Long.parseLong(getSetProps.getRefreshRate());
      setCountdown(Duration.minutes(defaultDelay));
    } else {
      setCountdown(getCountdown().subtract(oneSecond));
    }*/

  }

  // populateTable: String -> List<Item>
  // Fills in the columns with data and sets row colors
  void populateTable(String temp) throws IOException {
    Main main = new Main();
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    urlCol.setCellValueFactory(new PropertyValueFactory<>("urlLink"));
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
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

    // Sets the colors for the row, using the corresponding ID from main.css
    tableView.setRowFactory(tv -> new TableRow<Item>() {
      @Override
      public void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
          this.setId("rowOldItem");
        } else if (item.isStatus()) {
          this.setId("rowNewItem");
        } else if (!item.isStatus()) {
          this.setId("rowOldItem");
        }
      }
    });

    tableView.getItems().setAll(oList);
    success();
    System.out.println("POPULATED TABLE");
    System.out.println(LocalTime.now().toString());
  }

  // parseItemList: String ->  List<Item>
  // Returns the list by using the url from user.properties
  private List<Item> parseItemList(String temp) throws IOException {
    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();

    String url = getSetProps.getLink();
    Document doc = gimmeTheFreeStuff.testLink(url);
    List<Item> list = gimmeTheFreeStuff.getData(doc, url);

    if (temp.compareTo("useCompareLists") == 0) {
      List<Item> tempList = gimmeTheFreeStuff.compareLists(list, Main.getCurrentTime());
      Main.setCurrentTime(LocalDateTime.now());
      return tempList;
    }
    return list;
  }

  // success: N/A -> N/A
  // Shows "Refreshed List!" for a few seconds if table is populated
  private void success() {
    successText.setVisible(true);
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        successText.setVisible(false);
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
    Stage stage = new Stage();
    setSettingsStage(stage);
    Parent root = FXMLLoader.load(getClass().getResource("userinterface/SettingsUserInterface.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    System.out.println("Switched to Settings page");

    stage.initOwner(Main.getStage());
    stage.initModality(Modality.WINDOW_MODAL);
    stage.showAndWait();
  }

  // craigslistClicked: N/A -> N/A
  // When "Take me to Craigslist" button is clicked, it takes you to Craigslist
  @FXML
  private void craigslistClicked() throws IOException {
    Main main = new Main();
    GetSetProps getSetProps = new GetSetProps();
    main.openUrl(getSetProps.getLink());
  }

  // setTitle: N/A -> N/A
  // Sets title of Main UI
  @FXML
  private void setTitle() throws IOException {
    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();
    Main.getStage()
        .setTitle("GimmeTheFreeStuff - " + gimmeTheFreeStuff.getTitle(getSetProps.getLink()));
  }

  // updateListClicked: N/A -> N/A
  // When "Update List" button is clicked, it updates the list
  @FXML
  private void updateListClicked() throws IOException {
    populateTable("useCompareLists");
  }
}