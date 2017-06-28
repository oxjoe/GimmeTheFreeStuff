import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Joseph on 6/26/2017.
 */
public class GimmeTheFreeStuff {

  public static void main(String[] args) throws IOException {
    // OVERALL PLAN
    // When you start up, show a giant list of all the free stuff
    // if you hit repeat every hour then get the current time/date and when the next hour is here
    // only show the stuff that is after the -----^



    String html = "https://bloomington.craigslist.org/search/zip?search_distance=10&postal=47405";
    Document doc = Jsoup.connect(html).get();

    Map<String, List<Item>> map = new HashMap<>();

    List<Item> list = new ArrayList<>();



    Item templete = new Item("","","",0);

    // NEED TO GET PICTURE ELEMENT

    // gets the result date
    Elements timeElement = doc.getElementsByClass("result-date");

    // gets the whole line
    Elements linkAndTitleElement = doc.getElementsByClass("result-title hdrlnk");


//    System.out.println(linkAndTitleElement);
//    <a href="/zip/6195172248.html" data-id="6195172248" class="result-title hdrlnk">curb alert FREE Child's Easel</a>

//    ListIterator litr = list.listIterator();

//    int maximumItemsToGet = 10;
//    while (true) {
//      list.add(new Item (linkAndTitleElement.html(), "website_link","picture_link", 123));
//    }

    // How do i get each indivudal element in linkand title element?

    System.out.println(list);

//    for (Element e : linkAndTitleElement) {
//      map.put()
//      list.add(e.html());
//    }

// make a method that will add each string of the below code into a new item and for the other links as well
    for (Element o : linkAndTitleElement) {
      System.out.println(o.html() + "\n");
    }

    //Iterator here
/*    for (Element e : timeElement) {
      map.put(e.html(),)
    }*/


   // System.out.println(linkAndTitleElement.html());   // returns Bicycle for Free!
//    System.out.println(timeElement.html());           // returns June 27 THIS IS GONNA BE KEY
  }
}
