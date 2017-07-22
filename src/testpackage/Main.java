package testpackage;
/**
 * Created by Joseph on 7/6/2017.
 */

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  // Must be static since Application will only be created once in JVM
  // https://coderanch.com/t/650211/java/Set-stage-title-class
  private static Stage stage;
  private static Date currentTime;

  public static void main(String[] args) {
    launch(args);
  }

  public static Stage getStage() {
    return stage;
  }

  public Date getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(Date currentTime) {
    this.currentTime = currentTime;
  }

  // openUrl: String -> Opens string of url in browser
  // Must be in Main b/c only main extends application
  void openUrl(String url) {
    getHostServices().showDocument(url);
  }

  @Override
  public void start(Stage primaryStage) throws IOException, ParseException {
    stage = primaryStage;
    setCurrentTime(new Date());
    // Its cuz i dont set current time?
    System.out.println("In Main: Current time is " + currentTime);
//    try {
//      currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2017-07-19 03:20");
//    } catch (ParseException e) {
//      e.printStackTrace();
//    }
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    obj.startup();

    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}