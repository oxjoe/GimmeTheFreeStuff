package testpackage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Joseph on 7/24/2017.
 */
public class Refresh {

  private static ScheduledExecutorService scheduler;

  public static void shutdownScheduler() {
    scheduler.shutdownNow();
    System.err.println("**** shutdownScheduler **** ");

  }

  public static void createScheduler() {
    scheduler = Executors.newScheduledThreadPool(5);
    System.err.println("**** createScheduler **** ");

  }

  // refreshListWithTimer: N/A -> N/A
  // Repopulates table every long delay minutes
  static void refreshListWithTimer() throws IOException {
    GetSetProps getSetProps = new GetSetProps();
    MainController obj = new MainController();

    long delay = Long.parseLong(getSetProps.getRefreshRate());

    scheduler.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        System.err.println("repeat every " + delay + " seconds in MAIN.java");
//        try {
//          obj.populateTable("");
//        } catch (IOException e) {
//          e.printStackTrace();
//        } finally {
//          obj.success();
//        }
      }
    }, delay, delay, TimeUnit.SECONDS);
    System.err.println("**** refreshListWithTimer **** ");
  }
}
