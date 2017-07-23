package testpackage;

import javafx.scene.control.Hyperlink;

/**
 * Created by Joseph on 6/27/2017.
 */
public class Item implements Comparable<Item>{

  //private String description;
  //private String mapsLink;
  //private String pictureLink
  private String name;
  private String date;
  private Hyperlink urlLink;
  private boolean status;

  public Item(String name, String date, Hyperlink urlLink, boolean status) {
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

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Hyperlink getUrlLink() {
    return urlLink;
  }

  public void setUrlLink(Hyperlink urlLink) {
    this.urlLink = urlLink;
  }

  @Override
  public String toString() {
    return status + " || " + name + " || " + date + " || " + urlLink + "\n";
  }

  @Override
  public int compareTo(Item compareItem) {
    String compareDate = compareItem.getDate();

    return compareDate.compareTo(this.date);
  }
}
