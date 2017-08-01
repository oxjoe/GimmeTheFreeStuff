package testpackage;

/**
 * Created by Joseph on 7/24/2017. Purpose of Refresh.java: Provides the methods that will get a new
 * a list from Craigslist every x amount of minutes
 */

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;

class Refresh {

  private static ScheduledExecutorService scheduler;

  static void shutdownScheduler() {
    scheduler.shutdownNow();
    System.out.println("**** shutdownScheduler **** ");
  }

  // Question: Could be optimized
  // refreshListWithTimer: N/A -> N/A
  // Repopulates table every "refreshRate from user.properties" amount of minutes
  static void createAndStartScheduler() throws IOException {
//    GetSetProps getSetProps = new GetSetProps();

    scheduler = Executors.newSingleThreadScheduledExecutor();
    System.out.println("**** createAndStartScheduler **** ");

//    long delay = Long.parseLong(getSetProps.getRefreshRate());
    long delay = 2;

    scheduler.scheduleAtFixedRate(() -> Platform.runLater(() -> {
      MainController mainController = new MainController();
      System.out.println("repeat every " + delay);
      System.out.println("Platform.isFxApplicationThread() = " + Platform
          .isFxApplicationThread()); // returns True
      try {
        mainController.populateTable("");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }), delay, delay, TimeUnit.SECONDS);
  }
}