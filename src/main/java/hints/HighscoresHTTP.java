package hints;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import score.Score;
import score.ScorePage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class HighscoresHTTP implements Highscores {

    private String next = "/scores";


    @Override
    public List<Score> getHighscores() {
        HttpClient httpClient = new DefaultHttpClient();
        Gson gson = new Gson();

        try {
            HttpGet request = new HttpGet("http://localhost:8080" + next);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine());
            ScorePage scorePage =  gson.fromJson(EntityUtils.toString(response.getEntity()), ScorePage.class);
            this.next = scorePage.getNext();
            return scorePage.getScores();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return new LinkedList<>();
    }

    @Override
    public void addScore(Score score) {
        HttpClient httpClient = new DefaultHttpClient();
        Gson gson = new Gson();

        try {
            HttpPost request = new HttpPost("http://localhost:8080/score");
            StringEntity params = new StringEntity(gson.toJson(score));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            System.out.println(response.getStatusLine());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
}
