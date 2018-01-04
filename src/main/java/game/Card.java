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

    @Override
    public boolean equals(Object other){
        if(other.getClass() != this.getClass())
            return false;
        Card card = (Card) other;
        return card.getColor() == this.getColor() &&
                card.getFace() == this.getFace();

    }
}
