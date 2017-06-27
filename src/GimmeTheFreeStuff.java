import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Joseph on 6/26/2017.
 */
public class GimmeTheFreeStuff {

  public static void main(String[] args) throws IOException {
    // See if website has been updated if it has then get the latest things that are free
    // need to get description and picture to
    String html = "https://bloomington.craigslist.org/search/zip?search_distance=10&postal=47405";
    Document doc = Jsoup.connect(html).get();
    // gets the result date
    Elements timeElement = doc.getElementsByClass("result-date");
    // gets the whole line
    Elements titleElement = doc.getElementsByClass("result-title hdrlnk");
    System.out.println(titleElement);
    System.out.println(titleElement.html());
  }
}
