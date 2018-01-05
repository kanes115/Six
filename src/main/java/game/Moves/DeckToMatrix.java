package game.Moves;

import game.Positions.CasualPosition;
import game.Positions.DeckPosition;
import game.Positions.RejectedPosition;

public class DeckToMatrix implements Move {

    // It moves a card from deck to rejected stack.
    public DeckToMatrix(DeckPosition deck, RejectedPosition rej){

    }

    // It relocates a card from deck top to matrix.
    // It does it only if the card is moved to it's FINAL PLACE
    // or if it's the first free place
    public DeckToMatrix(DeckPosition deck, CasualPosition cas){

    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public void revert() {

    }

    @Override
    public boolean isMade() {
        return false;
    }

}
