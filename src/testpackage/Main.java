package testpackage;
/**
 * Created by Joseph on 7/6/2017.
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

public class Main extends Application {

  private static Stage stage;
  private static LocalDateTime currentTime;

  public Main() {
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
    // todo current time ofr when it updates and delete the other 3 lines
    //setCurrentTime(LocalDateTime.now());
    LocalDate aDate = LocalDate.now().minusDays(0);
    LocalTime aTime = LocalTime.of(14, 35, 0); // 6 PM
    LocalDateTime temp = LocalDateTime.of(aDate, aTime);
    setCurrentTime(temp);


    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();


    gimmeTheFreeStuff.startup();
    // Checks to see if refresh rate status is true so it can start a timer to get a get updated
    // Craigslist data
    if (getSetProps.getRefreshStatus().compareTo("true") == 0) {
      //todo TRYING IT WITHOUT CREATING OBJECTS BUT JUST USING STATIC
      // Refresh refresh = new Refresh();
      Refresh.createScheduler();
      Refresh.refreshListWithTimer();
    }
    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}