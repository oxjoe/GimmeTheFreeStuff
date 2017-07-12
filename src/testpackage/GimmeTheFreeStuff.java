package testpackage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Joseph on 6/26/2017.
 */
public class GimmeTheFreeStuff {

  public static void startingUp() throws IOException {
//// create and load default properties
//    Properties defaultProps = new Properties();
//    FileInputStream in = new FileInputStream("defaultProperties");
//    defaultProps.load(in);
//    in.close();
//
//// create application properties with default
//    Properties applicationProps = new Properties(defaultProps);
//
//// now load properties
//// from last invocation
//    in = new FileInputStream("appProperties");
//    applicationProps.load(in);
//    in.close();
  }


  public static void exiting() throws IOException {
  }

  public static void main(String[] args) throws Exception {
    startingUp();
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    String userInput = "https://bloomington.craigslist"
        + ".org/search/zip?search_distance=10&postal=47405";
    //changeLink(userInput); for when user wants to change outside of execution
    Document document = obj.changeLink(userInput);
    List<Item> list = obj.getData(document, userInput);
    List<Item> mostRecentList = obj.sortByDate(list);
    //obj.refreshCraigslist(userLink, 10);
    exiting();
  }

  // changeLink: String -> Document
  // Takes user inputted Craigslist link and parses it with JSoup
  public Document changeLink(String userInput) {
    Document doc = null;
    try {
      doc = Jsoup.connect(userInput).get();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return doc;
  }

  // Catch exception for new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr)
  public List<Item> getData(Document doc, String userInput) {
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
    String[] split = userInput.split("/search");
    String mainLink = split[0];
    ListIterator<String> websiteItr = itemLinkList.listIterator();
    String temp;
    while (websiteItr.hasNext()) {
      temp = websiteItr.next();
      websiteItr.set(mainLink + temp);
    }
    // websiteLink now shows: [https://bloomington.craigslist.org/zip/6202373846.html,
    // https://bloomington.craigslist.org/zip/6202308061.html, etc...
    // instead of: [/zip/6202373846.html, /zip/6202308061.html, etc...

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
    // Combines the other lists into one list of Items
    for (int i = 0; i < nameList.size(); i++) {
      try {
        list.add(new Item(
            nameList.get(i),
            new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateList.get(i)),
            // Converts each element of type string in dateTimeList to type Date while adding it to
            // the list so it can be sorted easier later as well + be displayed more user friendly
            itemLinkList.get(i),
            false));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    //System.out.println(list);
    return list;
  }

  // sortByDate: List<Item> -> List<Item>
  // Takes a list of items and returns the list of items sorted by date (most recent)
  public List<Item> sortByDate(List<Item> list) {
    Collections.sort(list);
    //System.out.println(list);
    return list;
  }

//  public refreshCraigslistInXMin(String userLink, int minutes) {
//    Date currentDate = new Date();
//    System.out.println(currentDate.toString());
//    Timer timer = new Timer();
//    timer.schedule(new timerTask(), (long) minutes / 60000, (long) minutes / 60000);
//
//    //return listWithNewStuff;
//  }

//  class timerTask extends TimerTask {
//    GimmeTheFreeStuff objOne = new GimmeTheFreeStuff();
//    @Override
//    public void run() {
//
//      List<Item> newList = new ArrayList<>();
//      newList = sortByDate(getData(objOne.changeLink(userInput), userInput));
//      compareLists(newList, currentDate)
//      List<Item> listWithNewStuff = new ArrayList<>();
//      listWithNewStuff = compareLists(newList, currentDate);
//      List<Item> listWithNewStuff = new ArrayList<>();
//
////    if user says stop then timer.cancel();
//    }
//  }

  public List<Item> compareLists(List<Item> newList, Date currentDate) {
    for (int i = 0; i < newList.size(); i++) {
      if ((newList.get(i).getDate()).after(currentDate)) {
        newList.get(i).setStatus(true);
      } else if (((newList.get(i).getDate())
          .before(currentDate))) {// not sure if this line is needed
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
// Notify if guitar appears
// Ignore firewood,
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
//</editor-fold>
//endregion