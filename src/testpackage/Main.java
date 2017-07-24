package testpackage;
/**
 * Created by Joseph on 7/6/2017.
 */

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  // Must be static since Application will only be created once in JVM
  // https://coderanch.com/t/650211/java/Set-stage-title-class
  private static Stage stage;
  private static LocalDateTime currentTime;
  private static ScheduledExecutorService scheduler;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void stop() throws Exception {
    getScheduler().shutdownNow();
    System.out.println("*** STAGE CLOSED ***");
    super.stop();
  }

  public static Stage getStage() {
    return stage;
  }

  public static void setStage(Stage stage) {
    Main.stage = stage;
  }

  public LocalDateTime getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(LocalDateTime currentTime) {
    this.currentTime = currentTime;
  }

  public static ScheduledExecutorService getScheduler() {
    return scheduler;
  }

  public void setScheduler(ScheduledExecutorService scheduler) {
    this.scheduler = scheduler;
  }

  // openUrl: String -> Opens string of url in browser
  // Must be in Main b/c only main extends application
  void openUrl(String url) {
    getHostServices().showDocument(url);
  }

  // refreshListWithTimer: long -> N/A
  // Repopulates table every long delay minutes
  static void refreshListWithTimer(long delay) {
    MainController obj = new MainController();
    getScheduler().scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        System.out.println("repeat every " + delay + " seconds in MAIN.java");
//        try {
//          obj.populateTable("");
//        } catch (IOException e) {
//          e.printStackTrace();
//        } finally {
//          obj.success();
//        }
      }
    }, delay, delay, TimeUnit.SECONDS);
  }

  @Override
  public void start(Stage primaryStage) throws IOException{
    setStage(primaryStage);
    //setCurrentTime(LocalDateTime.now());
    LocalDate aDate = LocalDate.now().minusDays(0);
    LocalTime aTime = LocalTime.of(14,35,0); // 6 PM
    LocalDateTime temp = LocalDateTime.of(aDate, aTime);
    setCurrentTime(temp);
    setScheduler(Executors.newScheduledThreadPool(5));

    GimmeTheFreeStuff gimmeTheFreeStuff = new GimmeTheFreeStuff();
    GetSetProps getSetProps = new GetSetProps();
    gimmeTheFreeStuff.startup();
    // Checks to see if refresh rate status is true so it can start a timer to get a get updated
    // Craigslist data
    if (getSetProps.getRefreshStatus().compareTo("true") == 0) {
      // todo change delay back to file property
      //long delay = Long.parseLong(getSetProps.getRefreshRate());
      long delay = 3;
      Main.refreshListWithTimer(delay);
    }
    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}