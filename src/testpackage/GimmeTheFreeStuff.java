package testpackage;

/**
 * Created by Joseph on 6/26/2017. Purpose of GimmeTheFreeStuff.java: Handles the majority of the
 * code to scrap data from Craigslist
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import javafx.scene.control.Hyperlink;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class GimmeTheFreeStuff {

  private final String uP = "user.properties";
  private final String dP = "default.properties";
  private final String niceDate = "M/d/y @ h:mm a";

/*  public static void main(String[] args) {
//    This block of code throws error when testing getData through this class but not after the
//    application has started
    String a = "https://bloomington.craigslist.org/search/zip?search_distance=10&postal=47405";
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    Document doc = obj.testLink(a);
    obj.getData(doc, a);
  }*/

  // startup: N/A -> N/A
  // Runs the very first time when the application starts up. Checks to see if user.properties
  // exist, if they don't then it copies over the contents of default.properties to a newly
  // created user.properties
  public void startup() throws IOException {
    GetSetProps obj = new GetSetProps();
    Properties defaultProps = new Properties();

    FileInputStream in = new FileInputStream(dP);
    defaultProps.load(in);
    System.out.println("First time starting up");

    obj.setProps((new Properties(defaultProps)));

    // If user.properties DO exist then don't do anything
    // If user.properties DON'T exist then create them
    try {
      in = new FileInputStream(uP);
      System.out.println("User.properties DO exist");
    } catch (FileNotFoundException e) {
      System.out.println("User.properties DON'T exist");
      createUserProps();
      copyToUserProps();
      System.out.println("Created user.properties and copied contents of default.properties to it");
    } finally {
      in.close();
    }
  }

  // createUserProps: N/A - > N/A
  // Creates user.properties file
  private void createUserProps() throws FileNotFoundException, UnsupportedEncodingException {
    PrintWriter writer = new PrintWriter(uP, "UTF-8");
    writer.close();
  }

  // Question: How/do I need to close Files.copy, something about java.io.Flushable
  private void copyToUserProps() throws IOException {
    Files.copy(Paths.get(dP), new FileOutputStream(uP));
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
    }
    return doc;
  }

  // getData: (Document, String) -> List<Item>
  // Main function that gets the data from Craigslist and converts each listing into a list of
  // object Items
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

    // Format in current list: "yyyy-MM-dd HH:mm"
    // New format that looks nicer: niceDate
    DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    DateTimeFormatter newFormat = DateTimeFormatter.ofPattern(niceDate);

    // Makes the date/time more user friendly
    ListIterator<String> dateItr = dateList.listIterator();
    while (dateItr.hasNext()) {
      LocalDateTime date = LocalDateTime.parse(dateItr.next(), oldFormat);
      dateItr.set(date.format(newFormat));
    }

    // If this happens then something really bad happened, so bad that the program must exit
    if (!(nameList.size() == itemLinkList.size() && nameList.size() == dateList.size())) {
      System.err.println("All lists are NOT the same size, something has gone horribly wrong");
      System.exit(4);
    }

    List<Item> list = new ArrayList<>();
    // Loops through the other lists and adds each element into the new List<Item>
    for (int i = 0; i < nameList.size(); i++) {
      list.add(new Item(
          nameList.get(i),
          dateList.get(i),
          // Converts each element of type string in dateTimeList to type Date while adding it to
          // the list so it can be sorted easier later as well + be displayed more user friendly
          new Hyperlink(itemLinkList.get(i)),
          false));
    }
    return list;
  }

  // compareLists: (List<Item>, LocalDateTime) -> List<Item>
  // Compares each element in the list with the LocalDateTime object to see if the listing
  // appeared after (true) or before (false) the LocalDateTime object
  public List<Item> compareLists(List<Item> newList, LocalDateTime currentDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(niceDate);
    for (Item aNewList : newList) {
      LocalDateTime dateTime = LocalDateTime.parse(aNewList.getDate(), formatter);
      if (dateTime.isAfter(currentDate) || dateTime.isEqual(currentDate)) {
        aNewList.setStatus(true);
      } else {
        aNewList.setStatus(false);
      }
    }
    return newList;
  }
}