package testpackage;
/**
 * Created by Joseph on 7/6/2017. Purpose of Main.java: Starting point of JavaFX application
 */

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*bugs
* Whenever it changes stages (between the Main UI and the Settings UI) including when it starts
* up the following exception occurs: javafx.fxml.FXMLLoader$ValueElement processValue
*
* When I run the code in GimmeTheFreeStuff main, the follow exception occurs:
* Exception in thread "main" java.lang.ExceptionInInitializerError
*
* fx-border-color overlaps with the last new item
*/

/*todo
* Search TextField
*Use currentTime to Option to start up with a brand new list (what craigslist shows) or when you
* closed the program.
*Colors of Posted last 2 days, etc... Current updates to
Posted today and yestarday: green
Change table limit too

* SETTINGS
* REGEX for timer display
* Minimize to system tray
* Check for app updates every day
* Start on computer startup
*

*/

public class Main extends Application {

  private static Stage stage;
  private static LocalDateTime currentTime;

  public static void main(String[] args) {
    launch(args);
  }

  public static Stage getStage() {
    return stage;
  }

  public static void setStage(Stage stage) {
    Main.stage = stage;
  }

  public static LocalDateTime getCurrentTime() {
    return currentTime;
  }

  public static void setCurrentTime(LocalDateTime currentTime) {
    Main.currentTime = currentTime;
  }

  @Override
  public void stop() throws Exception {
    if (Refresh.isSchedulerState()) {
      Refresh.shutdownScheduler();
    }
    System.out.println("*** STAGE CLOSED ***");
    super.stop();
  }

  // openUrl: String -> Opens string of url in browser
  // Must be in Main b/c only main extends application
  void openUrl(String url) {
    getHostServices().showDocument(url);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
////    Used for testing timer
    LocalDate aDate = LocalDate.now().minusDays(1);
    LocalTime aTime = LocalTime.of(14, 35, 0);
    LocalDateTime temp = LocalDateTime.of(aDate, aTime);
    setCurrentTime(temp);

    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();
    setStage(primaryStage);
//    setCurrentTime(LocalDateTime.now());

    // First time starting up!
    gimmeTheFreeStuff.startup();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainUserInterface.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();

    // Sets the current instance of the Controller so Refresh can update it if needed
    MainController controller = loader.getController();
    Refresh.setMainController(controller);

    // Checks to see if refresh rate status is true so it can start a timer to get updated
    // Craigslist data
    if (getSetProps.getRefreshStatus().compareTo("true") == 0) {
      Refresh.createAndStartScheduler();
    } else {
      Refresh.setSchedulerState(false);
    }
  }
}