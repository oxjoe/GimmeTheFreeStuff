package testpackage;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.print.DocFlavor.URL;

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
  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private TextField searchTextfield;
  @FXML
  private Button craigslistButton;
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
  //@FXML
  //private TableColumn<Item, String> distanceID;
  @FXML
  private TableColumn<Item, String> urlID;

  public void initialize(URL location, ResourceBundle resources) throws ParseException {
    dateID.setCellValueFactory(new PropertyValueFactory<Item, Date>("dateID"));
    nameID.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
    urlID.setCellValueFactory(new PropertyValueFactory<Item, String>("url"));
    tableView.getItems().setAll(parseItemList());
  }

  public List<Item> parseItemList() throws ParseException {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    System.out.println(obj.getData("https://bloomington.craigslist.org/search/zip?search_distance=10&postal=47405"));
    System.out.println("reached parseItemList");
    return obj.getData("https://bloomington.craigslist.org/search/zip?search_distance=10&postal=47405");

  }

//  public void refreshListClicked() {
//    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
//    //obj.getData("life");
//
//  }

}
