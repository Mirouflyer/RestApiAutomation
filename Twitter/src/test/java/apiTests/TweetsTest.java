package apiTests;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tweets.TweetsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TweetsTest {

    protected TweetsUser tweetsUser;
    protected Long tweetId;
    protected List<Long> tweetIdList = new ArrayList<Long>();

    @BeforeClass
    public void setUp() {
        this.tweetsUser = new TweetsUser();
    }

    @Test
    public void testGetUserTimeLine() throws Exception {
        tweetsUser.getUserTimeLine();
    }

    @Test
    public void testCreateTweet() throws Exception {
        String tweet = "I guess it's working right now";
        Response response = tweetsUser.createTweet(tweet);
        response.then().statusCode(HttpStatus.SC_OK);
        this.tweetId = response.path("id");
        this.tweetIdList.add(this.tweetId);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testCannotTweetSameTweetInRow() throws Exception {
        String tweet = "OK" + UUID.randomUUID();
        Response response = tweetsUser.createTweet(tweet);
        response.then().statusCode(HttpStatus.SC_OK);
        this.tweetIdList.add(this.tweetId);
        Assert.assertEquals(200, response.getStatusCode());
        response = tweetsUser.createTweet(tweet);
        response.then().statusCode(HttpStatus.SC_FORBIDDEN);
        Assert.assertEquals(403, response.getStatusCode());
    }

    @Test(dependsOnMethods = {"testCreateTweet"}, enabled = false)
    public void testUserCanDeleteTweet() {
        Response response = this.tweetsUser.deleteTweet(this.tweetId);
        response.then().statusCode(HttpStatus.SC_OK);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @AfterClass
    public void cleanUp(){
        if(this.tweetIdList != null){
            for(Long tweetId : tweetIdList){
                this.tweetsUser.deleteTweet(tweetId);
            }
        }

    }

}
