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
  //  https://coderanch.com/t/650211/java/Set-stage-title-class
  private static Stage stage;
  public static Stage getStage() {
    return stage;
  }

  public static void main(String[] args) {
    launch(args);
  }

  void openUrl(String url) {
    getHostServices().showDocument(url);
  }


  @Override
  public void start(Stage primaryStage) throws IOException {
    stage = primaryStage;
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
//    MainController mainController = new MainController();

    // Setup MainUI with user properties if those exist, or default properties
    obj.startup();
    Parent root = FXMLLoader.load(getClass().getResource("MainUserInterface.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();

//    mainController.newTryEMailStage(primaryStage);
  }

}