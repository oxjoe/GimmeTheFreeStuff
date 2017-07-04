import java.util.Date;

/**
 * Created by Joseph on 6/27/2017.
 */
public class Item implements Comparable<Item> {

  // TODO to be added
  //private String description;
  //private String mapsLink;
  //private String pictureLink
  private String name;
  private Date date;
  private String urlLink;

  public Item(String name, Date date, String urlLink) {
    this.name = name;
    this.date = date;
    this.urlLink = urlLink;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getUrlLink() {
    return urlLink;
  }

  public void setUrlLink(String urlLink) {
    this.urlLink = urlLink;
  }

  @Override
  public String toString() {
    return name + " || " + date + " || " + urlLink + "\n";
  }

  @Override
  public int compareTo(Item compareItem) {
    Date compareDate = compareItem.getDate();

    return compareDate.compareTo(this.date);
  }
}
