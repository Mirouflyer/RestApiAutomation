package user;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseTwitterUser {

    protected String baseUri;
    protected String apiKey;
    protected String apiSecretKey;
    protected String accessToken;
    protected String accessTokenSecret;
    private Properties properties = new Properties();
    private InputStream inputStream = null;

    public BaseTwitterUser() {
        this.baseUri = "https://api.twitter.com/1.1";
        try {
            this.inputStream = new FileInputStream("src/main/auth/auth.properties");
            //load the properties file
            this.properties.load(this.inputStream);
            //set the keys and tokens
            this.apiKey = this.properties.getProperty("apiKey");
            this.apiSecretKey = this.properties.getProperty("apiSecretKey");
            this.accessToken = this.properties.getProperty("accessToken");
            this.accessTokenSecret = this.properties.getProperty("accessTokenSecret");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (this.inputStream != null)
                try {
                    this.inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
    }
    public String getFullUrl(String endPoint){
        return this.baseUri + endPoint;
    }
}
