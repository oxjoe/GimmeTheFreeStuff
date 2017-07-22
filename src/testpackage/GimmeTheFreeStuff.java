package testpackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import javafx.scene.control.Hyperlink;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Joseph on 6/26/2017.
 */
public class GimmeTheFreeStuff {

  public static void main(String[] args) throws ParseException {
//    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
//
//    // To be used for displaying the date
////"yyyy-MM-dd HH:mm"
//
////    String dateStr = "2017-05-28 10:36";
////    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr);
////    String displayDate = new SimpleDateFormat("MMM d '@' h:mm a").format(date);
////    System.out.println(displayDate); // May, 28 @ 10:36 AM
//    String first = "2017-05-28 10:36";
//    String second = "2017-05-28 11:36";
//    String third = "2017-08-28 10:36";
//    String fourth = "2017-08-28 11:36";
//    Date one = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(first);
//    Date two = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(second);
//    Date three = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(third);
//    Date four = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fourth);
//    Date currentDate = new Date();
//    System.out.println("CURRENT TIME: " + currentDate);
//    List<Date> newList = new ArrayList<>();
//    newList.add(one);
//    newList.add(two);
//    newList.add(three);
//    newList.add(four);
//
//
//    for (int i = 0; i < newList.size(); i++) {
//      if ((newList.get(i)).before(currentDate)) {
//        System.out.println(true);
//      } else if (newList.get(i).after(currentDate)) {
//        System.out.println(false);
//      }
//    }
//
  }

  // startup: N/A -> N/A
  // Runs the very first time when the application starts up. Checks to see if user.properties
  // exist, if they don't then it creates them with "default.properties" as the template
  public void startup() throws IOException {
    GetSetProps getSetProps = new GetSetProps();
    Properties defaultProps = new Properties();
    FileInputStream in = new FileInputStream("default.properties");
    defaultProps.load(in);

    // Similar to
    // Properties userProps = new Properties(defaultProps);
    // but in GetSetProps class
    getSetProps.setProps((new Properties(defaultProps)));

    System.out.println("First time starting up");

    // If user.properties DO exist then don't do anything
    // If user.properties DON'T exist then create them
    try {
      in = new FileInputStream("user.properties");
      System.out.println("User.properties DO exist");
    } catch (FileNotFoundException e) {
      System.out.println("User.properties DON'T exist");
      createUserProps();
      System.out.println("Created user.properties");
    } finally {
      in.close();
    }
  }

  // createUserProps: N/A - > N/A
  // Creates user.properties file
  private void createUserProps() throws FileNotFoundException, UnsupportedEncodingException {
    PrintWriter writer = new PrintWriter("user.properties", "UTF-8");
    writer.close();
  }

  // getTitle: String -> String
  // Turns the link into a document so jsoup can parse it and get the title element in the head
  // of the html document
  public String getTitle(String link) {
    Document doc = testLink(link);
    Element element = doc.select("title").first();
    String[] split = element.html().split("free");
    return split[0];
  }

  // testLink: String -> Document
  // Tests to see if the given string can be parsed by jsoup, if it can then returns a Document
  // object
  public Document testLink(String link) {
    Document doc = null;
    try {
      doc = Jsoup.connect(link).get();
    } catch (IOException e) {
      e.printStackTrace();
      //doc = null;
    } finally {
      System.out.println("print no matter what happens in testlink");
      return doc;
    }
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

// To be used for displaying the date
//"yyyy-MM-dd HH:mm"

//    String dateStr = "2017-05-28 10:36";
//    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr);
//    String displayDate = new SimpleDateFormat("MMM d '@' h:mm a").format(date);
//    System.out.println(displayDate); // May, 28 @ 10:36 AM

    // Combines the other lists into one list of Items
    for (int i = 0; i < nameList.size(); i++) {
      try {
        list.add(new Item(
            nameList.get(i),
            new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateList.get(i)),
            // Converts each element of type string in dateTimeList to type Date while adding it to
            // the list so it can be sorted easier later as well + be displayed more user friendly
            new Hyperlink(itemLinkList.get(i)),
            false));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
  //System.out.println(list);
    return list;
}

  // TableView already has built in sort
  // sortByDate: List<Item> -> List<Item>
  // Takes a list of items and returns the list of items sorted by date (most recent)
  public List<Item> sortByDate(List<Item> list) {
    Collections.sort(list);
    //System.out.println(list);
    return list;
  }


  public List<Item> compareLists(List<Item> newList, Date currentDate) {
    for (int i = 0; i < newList.size(); i++) {
      if ((newList.get(i).getDate()).after(currentDate)) {
        System.out.println(newList.get(i).getDate().toString() + "is after " + currentDate);
        newList.get(i).setStatus(true);
      } else if ((newList.get(i).getDate()).before(currentDate)) {
        newList.get(i).setStatus(false);
      }
    }
    return newList;
  }
}


/* TODO to be added
// make the time when the program closes and next time it starts up compares the previous with the
// new list, and have an option to not do that
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