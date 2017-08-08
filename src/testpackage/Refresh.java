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
import javafx.util.Duration;

class Refresh {

  private static ScheduledExecutorService scheduler;
  private static boolean schedulerState;
  private static MainController mainController;

  public static MainController getMainController() {
    return mainController;
  }

  public static void setMainController(MainController mainController) {
    Refresh.mainController = mainController;
  }

  static void shutdownScheduler() {
    scheduler.shutdown();
    setSchedulerState(false);
    System.out.println("**** shutdownscheduler **** ");
  }

  public static boolean isSchedulerState() {
    return schedulerState;
  }

  public static void setSchedulerState(boolean schedulerState) {
    Refresh.schedulerState = schedulerState;
  }

  // refreshListWithTimer: N/A -> N/A
  // Repopulates table every "refreshRate from user.properties" amount of minutes and shows the
  // countdown timer
  static void createAndStartScheduler() throws IOException {
    System.out.println("**** createAndStartScheduler **** ");

    GetSetProps getSetProps = new GetSetProps();
    long delay = Long.parseLong(getSetProps.getRefreshRate());

    setSchedulerState(true);
    scheduler = Executors.newScheduledThreadPool(10);

    scheduler.scheduleAtFixedRate(() -> Platform.runLater(() -> {
      System.out.println("repeat every " + delay);
      try {
        getMainController().populateTable("");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }), delay, delay, TimeUnit.SECONDS);

    // Set number that it will countdown from once it resets
    getMainController().setCountdown(Duration.seconds(delay));
    scheduler.scheduleAtFixedRate(() -> Platform.runLater(() -> {
      try {
        getMainController().showProgress();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }), 0, 1, TimeUnit.SECONDS);
  }
}