package testpackage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Joseph on 7/15/2017.
 */
public class GetSetProps {

  private Properties props;

  public GetSetProps() {
    props = new Properties();
  }

  public void setProps(Properties props) {
    this.props = props;
  }

  public void setAllProps(String key, String value) throws IOException {
    GetSetProps obj = new GetSetProps();

    String link = obj.getLink();
    String refreshRate = obj.getRefreshRate();
    String refreshStatus = obj.getRefreshStatus();
    String updateRate = obj.getUpdateRate();
    String updateStatus = obj.getUpdateStatus();

    FileOutputStream out = new FileOutputStream("user.properties");

    switch (key) {
      case "link":
        props.setProperty("link", value);
        props.setProperty("refreshRate", refreshRate);
        props.setProperty("refreshChecked", refreshStatus);
        props.setProperty("updateRate", updateRate);
        props.setProperty("updateChecked", updateStatus);
        System.out.println("Set link");
        break;
      case "refreshRate":
        props.setProperty("link", link);
        props.setProperty("refreshRate", value);
        props.setProperty("refreshChecked", refreshStatus);
        props.setProperty("updateRate", updateRate);
        props.setProperty("updateChecked", updateStatus);
        System.out.println("Set refreshRate");
        break;
      case "refreshStatus":
        props.setProperty("link", link);
        props.setProperty("refreshRate", refreshRate);
        props.setProperty("refreshChecked", value);
        props.setProperty("updateRate", updateRate);
        props.setProperty("updateChecked", updateStatus);
        System.out.println("Set refreshStatus");
        break;
      case "updateRate":
        props.setProperty("link", link);
        props.setProperty("refreshRate", refreshRate);
        props.setProperty("refreshChecked", refreshStatus);
        props.setProperty("updateRate", value);
        props.setProperty("updateChecked", updateStatus);
        System.out.println("Set updateRate");
        break;
      case "updateStatus":
        props.setProperty("link", link);
        props.setProperty("refreshRate", refreshRate);
        props.setProperty("refreshChecked", refreshStatus);
        props.setProperty("updateRate", updateRate);
        props.setProperty("updateChecked", value);
        System.out.println("Set updateStatus");
        break;
    }
    props.store(out, null);
    out.close();
  }

  public String getLink() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String temp = props.getProperty("link");
    in.close();
    return temp;
  }

  public String getRefreshRate() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String temp = props.getProperty("refreshRate");
    in.close();
    return temp;
  }

  public String getRefreshStatus() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String temp = props.getProperty("refreshChecked");
    in.close();
    return temp;
  }

  public String getUpdateRate() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String temp = props.getProperty("updateRate");
    in.close();
    return temp;
  }

  public String getUpdateStatus() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String temp = props.getProperty("updateChecked");
    in.close();
    return temp;
  }
}
