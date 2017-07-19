package testpackage;
/**
 * Created by Joseph on 7/6/2017.
 */

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
  // TODO currentTime description
  private static Date currentTime;

  public Date getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(Date currentTime) {
    this.currentTime = currentTime;
  }

  public static void main(String[] args) {
    launch(args);
  }

  // openUrl: String -> Opens string of url in browser
  // Must be in Main b/c only main extends application
  void openUrl(String url) {
    getHostServices().showDocument(url);
  }

  public static Stage getStage() {
    return stage;
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    stage = primaryStage;
    //    String dateStr = "2017-05-28 10:36";
//    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr);
    try {
      currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2017-07-19 01:20");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    obj.startup();

    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}