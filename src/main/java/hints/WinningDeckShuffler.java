package hints;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.Card;
import game.CardShuffler;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import score.ScorePage;

import java.util.List;

public class WinningDeckShuffler implements CardShuffler {

    List<Card> deck;

    public WinningDeckShuffler() {
        HttpClient httpClient = new DefaultHttpClient();
        Gson gson = new Gson();

        try {
            HttpGet request = new HttpGet("http://localhost:8080/deck");
            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine());
            this.deck = gson.fromJson(EntityUtils.toString(response.getEntity()), new TypeToken<List<Card>>(){}.getType());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
    @Override
    public List<Card> getRestCards() {
        return null;
    }

    @Override
    public List<Card> getNextCards(int n) {
        return null;
    }

    @Override
    public Card getNextCard() {
        return null;
    }

    @Override
    public void saveDeck() {

    }
}
