package testpackage;

/**
 * Created by Joseph on 7/24/2017. Purpose of Refresh.java: Provides the methods that will get a new
 * a list from Craigslist every x amount of minutes
 */

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    GetSetProps getSetProps = new GetSetProps();
    MainController obj = new MainController();

    scheduler = Executors.newScheduledThreadPool(3);
    System.out.println("**** createAndStartScheduler **** ");

    long delay = Long.parseLong(getSetProps.getRefreshRate());

    scheduler.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        System.out.println("repeat every " + delay);
        //todo populateTable in refreshListWithTimer
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
}