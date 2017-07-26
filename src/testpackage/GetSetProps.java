package testpackage;

/**
 * Created by Joseph on 7/15/2017. Purpose of GetSetProps.java: To get the keys and set the values
 * in user.properties
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

class GetSetProps {

  private Properties props;

  // Default constructor so when I create a GetSetProps, props will automatically be initialized
  GetSetProps() {
    props = new Properties();
  }

  void setProps(Properties props) {
    this.props = props;
  }

  String getLink() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String temp = props.getProperty("link");
    in.close();
    return temp;
  }

  String getRefreshRate() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String temp = props.getProperty("refreshRate");
    in.close();
    return temp;
  }

  String getRefreshStatus() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String temp = props.getProperty("refreshChecked");
    in.close();
    return temp;
  }

  String getUpdateRate() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String temp = props.getProperty("updateRate");
    in.close();
    return temp;
  }

  String getUpdateStatus() throws IOException {
    FileInputStream in = new FileInputStream("user.properties");
    props.load(in);
    String temp = props.getProperty("updateChecked");
    in.close();
    return temp;
  }

  // setAllProps: (String, String) -> N/A
  // Given a key and a value, sets all the properties in user.properties again since you can't
  // change one value without wiping user.properties
  void setAllProps(String key, String value) throws IOException {
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
}