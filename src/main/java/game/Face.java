package game;

import java.util.Random;

/**
 * Created by Kanes on 05.12.2017.
 */
public enum Face {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

    public boolean isUnnecessary(){
        return ordinal() < 4 || ordinal() == 12;
    }

    public Face next(){
        return values()[(this.ordinal() + 1) % 13];
    }

    // Test API
    public static Face getRandomFace(){
        Random generator = new Random();
        return values()[Math.abs(generator.nextInt()) % 13];
    }
}
