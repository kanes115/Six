package score;

import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import java.util.List;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8080);
        get("/hello", (req, res) -> "Hello World");
        post("/score", (req, res) -> {
            Score score = new Gson().fromJson(req.body(), Score.class);
            return "thanks";
        });
    }
}
