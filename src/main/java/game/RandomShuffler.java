package game;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Collections;

/**
 * Created by Kanes on 05.12.2017.
 */
public class RandomShuffler extends CardShuffler {
    public RandomShuffler() {
        super();
        for (Color color : Color.values()) {
            for (Face face : Face.values()) {
                deck.add(new Card(color, face));
                deck.add(new Card(color, face));
            }
        }
        Collections.shuffle(deck);
    }

    public void saveDeck() {
        HttpClient httpClient = new DefaultHttpClient();
        Gson gson = new Gson();

        try {
            HttpPost request = new HttpPost("http://localhost:8080/deck");
            StringEntity params = new StringEntity(gson.toJson(deck));
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
