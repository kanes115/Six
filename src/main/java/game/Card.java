package game;

/**
 * Created by Kanes on 05.12.2017.
 */
public class Card {

    private Color color;
    private Face face;

    public Card(Color c, Face f){
        this.color = c;
        this.face = f;
    }

    public Color getColor(){
        return color;
    }

    public Face getFace(){
        return face;
    }
}
