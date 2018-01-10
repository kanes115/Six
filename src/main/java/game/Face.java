package game;

/**
 * Created by Kanes on 05.12.2017.
 */
public enum Face {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

    public boolean isUnnecessary(){
        return ordinal() < 4 || ordinal() == 11;
    }

    public Face next(){
        return values()[(this.ordinal() + 1) % 12];
    }
}
