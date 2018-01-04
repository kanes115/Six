package game;

import com.sun.istack.internal.NotNull;

/**
 * Created by Kanes on 05.12.2017.
 */
public enum Face {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, JACK, QUEEN, KING, ACE;

    public boolean isRemovable(){
        return ordinal() < 4 || ordinal() == 11;
    }

    public Face next(@NotNull Face f){
        return values()[(f.ordinal() + 1) % 12];
    }
}
