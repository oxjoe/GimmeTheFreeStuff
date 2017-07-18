package testpackage;
/**
 * Created by Joseph on 7/6/2017.
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  // Must be static since Application will only be created once in JVM
  // https://coderanch.com/t/650211/java/Set-stage-title-class
  private static Stage stage;

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
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    obj.startup();
    stage = primaryStage;
    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}