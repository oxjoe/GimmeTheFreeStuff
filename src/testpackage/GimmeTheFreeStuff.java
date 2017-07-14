package testpackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Joseph on 6/26/2017.
 */
public class GimmeTheFreeStuff {

  public GimmeTheFreeStuff() {

  }

  public GimmeTheFreeStuff(Properties userProps, String link, int refresh, boolean refreshChecked,
      int update, boolean updateChecked) {
    this.userProps = userProps;
    this.link = link;
    this.refresh = refresh;
    this.refreshChecked = refreshChecked;
    this.update = update;
    this.updateChecked = updateChecked;
  }

  private Properties userProps;
  private String link;
  private int refresh;
  private boolean refreshChecked;
  private int update;
  private boolean updateChecked;

  // GOOD
  public void setUserProps(Properties userProps) {
    this.userProps = userProps;
  }
  public Properties getUserProps() {
    return userProps;
  }
  public String getLink() {
    return link;
  }
  public int getRefresh() {
    return refresh;
  }
  public boolean isRefreshChecked() {
    return refreshChecked;
  }
  public int getUpdate() {
    return update;
  }
  public boolean isUpdateChecked() {
    return updateChecked;
  }

  // These are all set
  public void setLink(String link) {
    this.link = link;
  }
  public void setRefresh(int refresh) {
    this.refresh = refresh;
  }
  public void setRefreshChecked(boolean refreshChecked) {
    this.refreshChecked = refreshChecked;
  }
  public void setUpdate(int update) {
    this.update = update;
  }
  public void setUpdateChecked(boolean updateChecked) {
    this.updateChecked = updateChecked;
  }


  public static void main(String[] args) throws Exception {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
   // Document document = obj.changeLink("https://chicago.craigslist.org/search/zip");
  //  List<Item> list = obj.getData(document, "https://chicago.craigslist.org/search/zip");
//    List<Item> mostRecentList = obj.sortByDate(list);
  }

  public static String propToString(Properties prop) {
    StringWriter writer = new StringWriter();
    prop.list(new PrintWriter(writer));
    return writer.getBuffer().toString();
  }

  void startup() throws IOException {
    Properties defaultProps = new Properties();
    FileInputStream in = new FileInputStream("default.properties");
    defaultProps.load(in);

    setUserProps(new Properties(defaultProps));

    System.out.println("user.properties with default.properties values - FIRST TIME STARTING UP");
//    System.out.println(propToString(getUserProps()));

    // EVERYTHING ABOVE HERE IS FOR FIRST TIME

    try {
      // If user props FILE exist
      in = new FileInputStream("user.properties");
      System.out.println("User properties DO exist, using user properties");
      setInstanceVars(getUserProps(), "user.properties");
    } catch (FileNotFoundException e) {
      //CREATE USER.PROPERTIES
      createUserProps();
      System.out.println("User properties DOESN'T exist, using default");
      setInstanceVars(defaultProps, "default.properties");
    } finally {
      in.close();
    }
  }

  private void createUserProps() throws FileNotFoundException, UnsupportedEncodingException {
    PrintWriter writer = new PrintWriter("user.properties", "UTF-8");
    writer.close();
  }

  // Sets instance variables as default properties or user properties depending which one exists
  private void setInstanceVars(Properties prop, String filename) throws IOException {
    FileInputStream in = new FileInputStream(filename);
    prop.load(in);
    setLink(prop.getProperty("link"));
    setRefresh(Integer.parseInt(prop.getProperty("refreshRate")));
    setRefreshChecked(Boolean.parseBoolean(prop.getProperty("refreshChecked")));
    setUpdate(Integer.parseInt(prop.getProperty("updateRate")));
    setUpdateChecked(
        updateChecked = Boolean.parseBoolean(prop.getProperty("updateChecked")));
    in.close();
  }

  // Takes user inputted Craigslist link and parses it with JSoup
  public Document changeLink(String link) {
    Document doc;
    try {
      doc = Jsoup.connect(link).get();
    } catch (IOException e) {
      System.err.print("changeLink() could not parse the given string");
      doc = null;
      e.printStackTrace();
    }
    return doc;
  }

  // Given a key returns its value from user.properties
  public String getPropertyValue(String key) throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    getUserProps().load(in);
    String value = getUserProps().getProperty(key);
    in.close();
    return value;
  }

  // Given a key and its value change to its new value
  public void changePropertyValue(String key, String value) throws IOException {
    FileOutputStream out = new FileOutputStream("user.properties");
    userProps.setProperty(key, value);
    userProps.store(out, null);
    out.close();
  }

  // NOT SURE IF FETCH IS NEEDED
//  void fetchUserProps() throws IOException {
//    getValue("link");
//    getValue("refresh");
//    getValue("refreshChecked");
//    getValue("updateChecked");
//    getValue("update");
//  }

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