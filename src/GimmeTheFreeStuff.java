import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Joseph on 6/26/2017.
 */
public class GimmeTheFreeStuff {

  public static void main(String[] args) throws IOException {
    GimmeTheFreeStuff obj = new GimmeTheFreeStuff();
    String userLink = obj.getLink();
    obj.getData(userLink);

    // OVERALL PLAN
    // When you start up, show a giant list of all the free stuff
    // if you hit repeat every hour then get the current time/date and when the next hour is here
    // only show the stuff that is after the -----^

  }

  public String getLink() {
    String html = "https://bloomington.craigslist.org/search/zip?search_distance=10&postal=47405";
    // https://bloomington.craigslist.org/zip/6157287163.html
    return html;
  }

  public void getData(String userLink) {
    Document doc = null;
    try {
      doc = Jsoup.connect(userLink).get();
    } catch (IOException e) {
      System.out.println("link does not exist");
    }

    Map<String, List<Item>> map = new HashMap<>();
    List<Item> list = new ArrayList<>();
    Item templete = new Item("WEBSITELINK", "ITEMTIME");

    Elements linkAndTitleElement = doc.getElementsByClass(
        "result-title hdrlnk"); //    <a href="/zip/6195172248.html" data-id="6195172248" class="result-title hdrlnk">curb alert FREE Child's Easel</a>
    List<String> titleList = new ArrayList<>();
    titleList = linkAndTitleElement
        .eachText(); // [ISO: free unwanted wood furniture, Free wood, etc...

    List<String> websiteList = new ArrayList<>();
    websiteList = linkAndTitleElement
        .eachAttr("href");//   [/zip/6157287163.html, /zip/6196217563.html, etc...

    String[] split = userLink.split("/search");
    String mainLink = split[0];
    ListIterator<String> websiteItr = websiteList.listIterator();
    String temp = "";
    while (websiteItr.hasNext()) {
      temp = websiteItr.next();
      websiteItr.set(mainLink + temp);
    }    // websiteLink now shows : [https://bloomington.craigslist.org/zip/6202373846.html, https://bloomington.craigslist.org/zip/6202308061.html, https://bloomington.craigslist.org/zip/6202172740.html,

    Elements timeElement = doc.getElementsByClass(
        "result-date");    //<time class="result-date" datetime="2017-06-28 15:45" title="Wed 28 Jun 03:45:58 PM">Jun 28</time>
    List<String> dateTimeList = new ArrayList<>();
    dateTimeList = timeElement.eachAttr(
        "datetime");//   [2017-06-28 15:45, 2017-06-28 10:18, 2017-06-27 11:17, 2017-06-27 09:47,etc...

    ListIterator<String> titleItr = titleList.listIterator();
    ListIterator<String> dateItr = dateTimeList.listIterator();
    // Checks if all of the them are the same size by the transitive property, yay for math!
    if (!(titleList.size() == websiteList.size() && titleList.size() == dateTimeList.size())) {
      System.out.println("All lists are NOT the same size");
    }

    while (titleItr.hasNext() && dateItr.hasNext() && websiteItr.hasNext()) {
      list.add(new Item (websiteItr.next(), dateItr.next()));
      map.put(titleItr.next(), list.get(0)); // pretty sure im mapping one value to the ENTIRE LIST? 
    }
    for (Map.Entry x : map.entrySet()) {
      System.out.println(x.getKey() + " " + x.getValue());
    }
    System.out.println("it made it");
  }


}

/*

    //  Picture link goes with PICTURELINK
    // DELAY FOR NOW, ADD LATER
    // There's the thumbnail picture and the actual picture, also some may posts may have multiple pictures
    Elements pictureElement = doc.getElementsByTag(
        "img");
    System.out.println(pictureElement.outerHtml());
    */
/*<a href="/zip/6194742649.html" class="result-image gallery" data-ids="1:00909_bcr5WxpXUwA"><img alt="" class="" src="https://images.craigslist.org/00909_bcr5WxpXUwA_300x300.jpg" title="" style="">
        </a>
        *//*

    List<String> pictureList = new ArrayList<>();
    pictureList = pictureElement.eachAttr(
        "src");
    System.out.println(pictureElement);
*/

