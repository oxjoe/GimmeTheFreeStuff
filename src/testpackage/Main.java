package testpackage;
/**
 * Created by Joseph on 7/6/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
    //GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("GimmeTheFreeStuff");
    // i can set title in FXML document pretty sure

    primaryStage.show();
  }

}