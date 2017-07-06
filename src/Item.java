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
  private boolean status;

  public Item(String name, Date date, String urlLink, boolean status) {
    this.name = name;
    this.date = date;
    this.urlLink = urlLink;
    this.status = status;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
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
    return status + " || " + name + " || " + date + " || " + urlLink + "\n";
  }

  @Override
  public int compareTo(Item compareItem) {
    Date compareDate = compareItem.getDate();

    return compareDate.compareTo(this.date);
  }
}
