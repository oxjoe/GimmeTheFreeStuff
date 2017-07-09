package testpackage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Timer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Joseph on 6/26/2017.
 */
public class GimmeTheFreeStuff {

  public static void main(String[] args) throws Exception {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    String userLink = obj.changeLink();
    List<Item> list = obj.getData(userLink);
    List<Item> mostRecentList = obj.sortByDate(list);
    obj.refreshCraigslist(userLink, 10);
  }

  // Get user input and confirm if the link works or not
  public String changeLink() {
    String html = "https://bloomington.craigslist.org/search/zip?search_distance=10&postal=47405";
    return html;
  }

  // Catch exception for new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr)
  public List<Item> getData(String userLink) throws ParseException {

    // Takes the user input link and tries to parse it with jsoup
    Document doc = null;
    try {
      doc = Jsoup.connect(userLink).get();
    } catch (IOException e) {
      System.out.println("link does not exist");
    }

    Elements linkAndTitleElement = doc.getElementsByClass("result-title hdrlnk");
    // <a href="/zip/6195172248.html" data-id="6195172248" class="result-title hdrlnk">curb alert
    // FREE Child's Easel</a>
    List<String> nameList = linkAndTitleElement.eachText();
    // [ISO: free unwanted wood furniture, Free wood, etc...

    List<String> itemLinkList = linkAndTitleElement.eachAttr("href");
    // [/zip/6157287163.html, /zip/6196217563.html, etc...

    // Method to append the main Craigslist site in front of each element of websiteList
    // Split the /search part of the link:
    // https://bloomington.craigslist.org/search/zip?search_distance=10&postal=47405
    // and add it to the beginning of each element in the list
    String[] split = userLink.split("/search");
    String mainLink = split[0];
    ListIterator<String> websiteItr = itemLinkList.listIterator();
    String temp;
    while (websiteItr.hasNext()) {
      temp = websiteItr.next();
      websiteItr.set(mainLink + temp);
    }
    // websiteLink now shows : [https://bloomington.craigslist.org/zip/6202373846.html,
    // https://bloomington.craigslist.org/zip/6202308061.html, etc...

    Elements timeElement = doc.getElementsByClass("result-date");
    // <time class="result-date" datetime="2017-06-28 15:45" title="Wed 28 Jun 03:45:58 PM">Jun
    // 28</time>
    List<String> dateList = timeElement.eachAttr("datetime");
    // [2017-06-28 15:45, 2017-06-28 10:18, 2017-06-27 11:17, 2017-06-27 09:47, etc...

    // Checks if all of the them are the same size by the transitive property, yay for math!
    if (!(nameList.size() == itemLinkList.size() && nameList.size() == dateList.size())) {
      System.out.println("All lists are NOT the same size");
    }

    List<Item> list = new ArrayList<>();
    // Combines the other lists into one list of obj(Items)
    for (int i = 0; i < nameList.size(); i++) {
      list.add(new Item(
          nameList.get(i),
          new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateList.get(i)),
          // Converts each element of type string in dateTimeList
          // to type Date while adding it to the list so it can be sorted easier later as well
          // as be displayed more user friendly
          itemLinkList.get(i),
          false));
    }
    return list;
  }

  // sortByDate: List<Item> -> List<Item>
  // Takes a list of items and returns the list of items sorted by date (most recent)
  public List<Item> sortByDate(List<Item> list) {
    Collections.sort(list);
    System.out.println(list);
    return list;
  }

  public List<Item> refreshCraigslist(String link, int minutes) {
    Date currentDate = new Date();
    System.out.println(currentDate.toString());
    Timer timer = new Timer();
    List<Item> newList = new ArrayList<>();
//    class timerTask extends TimerTask {
//
//      public void run() {
//        try {
//          newList = sortByDate(getData(link));
//
//          // sortList = (list);
//        } catch (ParseException e) {
//          e.printStackTrace();
//        }
//// TODO       if user says stop then timer.cancel();
//      }
//    }
//    timer.schedule(new timerTask(), minutes, (long) minutes / 60000);
    return compareLists(newList, currentDate);
  }

  public List<Item> compareLists(List<Item> newList, Date currentDate) {
    for (int i = 0; i < newList.size(); i++) {
      if ((newList.get(i).getDate()).after(currentDate)) {
        newList.get(i).setStatus(true);
      } else if (((newList.get(i).getDate()).before(currentDate))) {// not sure if this line is needed
        newList.get(i).setStatus(false);
      }
    }
    return newList;
  }

}

/*
// To be used for displaying the date
//"yyyy-MM-dd HH:mm"
    String dateStr = "2017-05-28 10:36";
    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr);
    String displayDate = new SimpleDateFormat("MMM d '@' h:mm a").format(date);
    System.out.println(displayDate); // May, 28 @ 10:36 AM
    */


/* TODO to be added
// There's the thumbnail picture, actual picture and some may have multiple pictures
    Elements pictureElement = doc.getElementsByTag(
        "img");
    System.out.println(pictureElement.outerHtml());
    */
/*<a href="/zip/6194742649.html" class="result-image gallery" data-ids="1:00909_bcr5WxpXUwA"><img
 alt="" class="" src="https://images.craigslist.org/00909_bcr5WxpXUwA_300x300.jpg" title=""
 style="">
        </a>
        *//*

    List<String> pictureList = new ArrayList<>();
    pictureList = pictureElement.eachAttr(
        "src");
    System.out.println(pictureElement);
*/

