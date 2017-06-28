/**
 * Created by Joseph on 6/27/2017.
 */
public class Item {

  private String pictureLink;
  private String websiteLink;
  private String itemTitle;
  private int itemTime;

  public Item(String itemTitle, String websiteLink, String pictureLink, int itemTime) {
    this.itemTitle = itemTitle;
    this.websiteLink = websiteLink;
    this.itemTime = itemTime;
    this.pictureLink = pictureLink;

  }
}
