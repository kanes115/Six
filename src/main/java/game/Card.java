package game;

/**
 * Created by Kanes on 05.12.2017.
 */
public class Card {

    private final Color color;
    private final Face face;

    public Card(Color color, Face face){
        this.color = color;
        this.face = face;
    }

    public Color getColor(){
        return color;
    }

    public Face getFace(){
        return face;
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Card))
            return false;
        Card card = (Card) other;
        return card.getColor() == this.getColor() &&
                card.getFace() == this.getFace();

    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + face.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.getFace() + " of " +  this.getColor();
    }
}
