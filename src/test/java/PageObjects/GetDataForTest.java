package PageObjects;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class GetDataForTest {
    private Properties props;
    private String filePropertiesName = "test_data.properties";

    public GetDataForTest() {
        props = setDriverProperties();
    }

    private java.util.Properties setDriverProperties() {
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

    public String getUrlHomePage(){
        return props.getProperty("homePage");
    }
    public String getEmail(){
        return props.getProperty("emailValid");
    }
    public String getPassword(){
        return props.getProperty("passwordValid");
    }
    public String getEmailUnvalid(){
        return props.getProperty("emailUnvalid");
    }
    public String getPasswordUnvalid(){
        return props.getProperty("passwordUnvalid");
    }
}
