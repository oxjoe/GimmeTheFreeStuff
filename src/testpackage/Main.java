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

  public static void main(String[] args) {
    launch(args);
  }

  void openUrl(String url) {
    getHostServices().showDocument(url);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    // Setup MainUI with user properties if those exist, or default properties
    obj.startup();
    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("GimmeTheFreeStuff for CITYNAME"); // TODO
    primaryStage.show();
  }

}