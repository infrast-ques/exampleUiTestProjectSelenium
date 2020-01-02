package Drivers;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class GetPropertiesForDriver {
    private Properties props;
    private String filePropertiesName = "driverConfig.properties";

    public GetPropertiesForDriver() {
        props = setDriverProperties();
    }

    private Properties setDriverProperties() {
        props = new Properties();
        FileReader file;
        try {
            file = new FileReader(new File(getClass().getClassLoader().getResource(filePropertiesName).getPath()));
            props.load(file);
        } catch (Exception e) {
            System.out.println("Properties file not found");
            e.printStackTrace();
        }
        return props;
    }

    public String getDirChromeDriver() {
        return getClass().getClassLoader().getResource(props.getProperty("directoryChromeDriver")).getPath();
    }

    public String getDirFirefoxDriver() {
        return getClass().getClassLoader().getResource(props.getProperty("directoryFirefoxDriver")).getPath();
    }

    public String getDirPhantomJSDriver() {
        return getClass().getClassLoader().getResource(props.getProperty("directoryPhantomJSDriver")).getPath();
    }

    public int getImplicitlyWaitTime() {
        return Integer.parseInt(props.getProperty("implicitlyWaitTime"));
    }

    public int getWaitTime() {
        return Integer.parseInt(props.getProperty("waitTime"));
    }
}
