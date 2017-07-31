package testpackage;
/**
 * Created by Joseph on 7/6/2017. Purpose of Main.java: Starting point of JavaFX application
 */

import java.io.IOException;
import java.time.LocalDateTime;
import javafx.application.Application;
import javafx.application.Platform;
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
*/

/* tba
 Get the item description
 Calculate how long it would take to get to the place
 Notify if "guitar" appears
 Ignore "firewood"*/

/* notes
 Gets the latest new postings (doesn't include updates) which is why if you open Craigslist
 and compare the items, the application will look different.
 */

public class Main extends Application {

  private static Stage stage;
  private static LocalDateTime currentTime;

  public void testMain() {
    System.out.println("OUT OF : Platform.isFxApplicationThread() = " + Platform.isFxApplicationThread()); // returns False

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        MainController mainController = new MainController();
        System.out.println("Platform.isFxApplicationThread() = " + Platform.isFxApplicationThread()); // returns True
        System.out.println("Should about to change text");
        mainController.getSuccessText().setText("CHANGE PLZ");

      }
    });
  }

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
    Refresh.shutdownScheduler();
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
    setStage(primaryStage);
    setCurrentTime(LocalDateTime.now());

/*//    Used for testing timer
    LocalDate aDate = LocalDate.now().minusDays(0);
    LocalTime aTime = LocalTime.of(14, 35, 0);
    LocalDateTime temp = LocalDateTime.of(aDate, aTime);
        setCurrentTime(temp);*/

    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();

    gimmeTheFreeStuff.startup();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainUserInterface.fxml"));
    Parent root = loader.load();

//    MainController mainController = loader.getController();

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    // Checks to see if refresh rate status is true so it can start a timer to get a get updated
    // Craigslist data
    if (getSetProps.getRefreshStatus().compareTo("true") == 0) {
      Refresh.createAndStartScheduler();
    }
  }
}