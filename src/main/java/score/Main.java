package score;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(8080);
        get("/hello", (req, res) -> "Hello World");
        post("/score", (req, res) -> {
            System.out.println(req.body());
            return "thanks";
        });
    }
}
