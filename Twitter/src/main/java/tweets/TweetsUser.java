package tweets;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import user.BaseTwitterUser;

import static io.restassured.RestAssured.given;

public class TweetsUser extends BaseTwitterUser {

    protected final static String USER_TIMELINE_ENDPOINT = "/statuses/user_timeline.json";
    protected final static String TWEET_ENDPOINT ="/statuses/update.json";
    protected final static String DESTROY_ENDPOINT ="/statuses/destroy.json";

    public void getUserTimeLine(){
        given()
                .auth()
                .oauth(this.apiKey,this.apiSecretKey,this.accessToken,this.accessTokenSecret)
        .when()
                .get(this.getFullUrl(USER_TIMELINE_ENDPOINT))
        .then()
               .statusCode(200);
    }

    public Response createTweet(String tweet){
       return given()
                .auth()
                .oauth(this.apiKey,this.apiSecretKey,this.accessToken,this.accessTokenSecret)
                .param("status",tweet)
        .when()
               .post(this.getFullUrl(TWEET_ENDPOINT));
    }

    public Response deleteTweet(Long tweetId){
        return given()
                .auth()
                .oauth(this.apiKey,this.apiSecretKey,this.accessToken,this.accessTokenSecret)
                .queryParam("id",tweetId)
                .when()
                .post(this.getFullUrl(DESTROY_ENDPOINT));
    }
}
