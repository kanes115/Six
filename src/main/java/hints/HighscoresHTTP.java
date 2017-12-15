package hints;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import score.Score;

import java.util.List;

public class HighscoresHTTP implements Highscores {
    @Override
    public List<Score> getHighscores(int page) {
        return null;
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
        }
    }
}
