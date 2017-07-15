package testpackage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Joseph on 7/15/2017.
 */
public class GetSetProps {

  public GetSetProps() {
    props = new Properties();
  }

  public GetSetProps(Properties props, String link, int refresh, boolean refreshChecked,
      int updates, boolean updateCheckeds) {
    this.props = props;
    this.link = link;
    this.refresh = refresh;
    this.refreshChecked = refreshChecked;
    this.updates = updates;
    this.updateCheckeds = updateCheckeds;
  }

  private Properties props;
  private String link;
  private int refresh;
  private boolean refreshChecked;
  private int updates;
  private boolean updateCheckeds;

  public Properties getProps() {
    return props;
  }
  public void setProps(Properties props) {
    this.props = props;
  }

  public String getLink() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String propLink = props.getProperty("link");
    in.close();
    return propLink;
  }

  public void setLink(String link) throws IOException {
    FileOutputStream out = new FileOutputStream("user.properties");
    props.setProperty("link", link);
    props.store(out, null);
    out.close();
  }

  public int getRefresh() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    int propRefreshRate = Integer.parseInt(props.getProperty("refreshRate"));
    in.close();
    return propRefreshRate;
  }

  public void setRefresh(int refresh) throws IOException {
    FileOutputStream out = new FileOutputStream("user.properties");
    props.setProperty("refreshRate", Integer.toString(refresh));
    props.store(out, null);
    out.close();
  }

  public boolean isRefreshChecked() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    boolean refreshStatus = Boolean.parseBoolean(props.getProperty("refreshChecked"));
    in.close();
    return refreshStatus;
  }

  public void setRefreshChecked(boolean refreshChecked) throws IOException {
    FileOutputStream out = new FileOutputStream("user.properties");
    props.setProperty("link", link);
    props.store(out, String.valueOf(refreshChecked));
    out.close();
  }

  public int getUpdates() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    int propUpdateRate = Integer.parseInt(props.getProperty("updateRate"));
    in.close();
    return propUpdateRate;
  }

  public void setUpdate(int updates) throws IOException {
    FileOutputStream out = new FileOutputStream("user.properties");
    props.setProperty("updateRate", Integer.toString(updates));
    props.store(out, null);
    out.close();
  }

  public boolean isUpdatesChecked() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    boolean updateStatus = Boolean.parseBoolean(props.getProperty("updateChecked"));
    in.close();
    return updateStatus;
  }

  public void setUpdatesChecked(boolean updateCheckeds) throws IOException {
    FileOutputStream out = new FileOutputStream("user.properties");
    props.setProperty("updateChecked", String.valueOf(updateCheckeds));
    props.store(out, null);
    out.close();
  }
}
