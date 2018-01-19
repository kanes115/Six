package game

import game.Moves.AssignColorOnCard
import game.Moves.DeleteDuplicate
import game.Moves.DeleteUnnecessaryPair
import game.Moves.FromStack
import game.Moves.InsideMatrixRelocation
import game.Moves.Move
import game.Positions.CasualPosition

import game.Positions.StackPosition
import hints.TestDeckShuffler
import spock.lang.Specification

/*
Integration test.
Testing different moves here, starting state of controller, board and rows.

These tests are mostly WHITE-BOX. Note that the system is quite
small and the effort of being black-box is not worth it.

Still we interfere with the internal state of GameController or Board only.
And not to much. It is only removing cards, putting them in some places -
to be fully black-box we would need to move them around using different moves
from Moves package.

TestDeckShuffler is a shuffler that gives a deck that will put cards in such a way
that the game is won straightaway.
 */

class GameControllerTest extends Specification {

    def "already won board has rows with colors assigned"(){
        when:
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        then:
        gameController
                .getBoard()
                .getRows()
                .stream()
                .allMatch({it -> it.isColorAssigned })
    }

    def "DeleteUnnecessaryPair: you can remove two unnecessary cards"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        CasualPosition pos1 = gameController.getBoard().getPositionAt(0, 0)
        CasualPosition pos2 = gameController.getBoard().getPositionAt(0, 1)

        when:
        pos1.removeCard()
        pos2.removeCard()
        pos1.putCard(new Card(Color.CLUBS, Face.ACE))
        pos2.putCard(new Card(Color.CLUBS, Face.ACE))

        then:
        Move m = new DeleteUnnecessaryPair(pos1, pos2)
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
        m.isMade()
        pos1.isEmpty()
        pos2.isEmpty()
    }


    def "DeleteUnnecessaryPair: you cannot delete two necessary cards"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        CasualPosition pos1 = gameController.getBoard().getPositionAt(0, 0)
        CasualPosition pos2 = gameController.getBoard().getPositionAt(0, 1)

        when:
        pos1.removeCard()
        pos2.removeCard()
        pos1.putCard(new Card(Color.CLUBS, Face.SIX))
        pos2.putCard(new Card(Color.CLUBS, Face.SIX))

        then:
        Move m = new DeleteUnnecessaryPair(pos1, pos2)
        MoveResponse mr = gameController.tryMove(m)
        !mr.wasOk()
        !m.isMade()
        !pos1.isEmpty()
        !pos2.isEmpty()
    }

    def "DeleteUnnecessaryPair: you can delete cards from stack position and casual one"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        StackPosition pos1 = gameController.getBoard().getDeckPosition()
        CasualPosition pos2 = gameController.getBoard().getPositionAt(0, 1)

        when:
        pos2.removeCard()
        pos1.putCard(new Card(Color.CLUBS, Face.TWO))
        pos2.putCard(new Card(Color.CLUBS, Face.TWO))

        then:
        Move m = new DeleteUnnecessaryPair(pos1, pos2)
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
        m.isMade()
        pos2.isEmpty()
    }

    def "DeleteUnnecessaryPair: you can delete cards across stacks as well"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        StackPosition pos1 = gameController.getBoard().getDeckPosition()
        StackPosition pos2 = gameController.getBoard().getRejectedPosition()

        when:
        pos1.putCard(new Card(Color.CLUBS, Face.TWO))
        pos2.putCard(new Card(Color.CLUBS, Face.TWO))

        then:
        Move m = new DeleteUnnecessaryPair(pos1, pos2)
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
        m.isMade()
    }


    def "InsideMatrixRelocation: after relocating a card the target row ALWAYS has a color assigned"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        CasualPosition pos1 = gameController.getBoard().getPositionAt(0, 0)
        CasualPosition pos2 = gameController.getBoard().getPositionAt(0, 1)

        when:
        pos1.removeCard()
        pos1.putCard(new Card(Color.CLUBS, pos2.targetFace))
        pos2.removeCard()
        Color colorThatShouldBeAssigned = pos1.getCard().getColor()

        then:
        Move m = new InsideMatrixRelocation(pos1, pos2)
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
        m.isMade()
        pos1.isEmpty()
        !pos2.isEmpty()
        pos2.getRow().isColorAssigned()
        pos2.getRow().getColor() == colorThatShouldBeAssigned
    }

    def "InsideMatrixRelocation: you cannot move card to a row with different color assigned"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        CasualPosition pos1 = gameController.getBoard().getPositionAt(0, 0)
        CasualPosition pos2 = gameController.getBoard().getPositionAt(1, 1)
        CasualPosition pos3 = gameController.getBoard().getPositionAt(2, 2)
        CasualPosition pos4 = gameController.getBoard().getPositionAt(1, 2)

        when:
        pos1.removeCard()
        pos2.removeCard()
        pos3.removeCard()
        pos4.removeCard()
        pos1.putCard(new Card(Color.HEARTS, pos2.targetFace))
        Card notMovedCard = new Card(Color.SPADES, pos4.targetFace)
        pos3.putCard(notMovedCard)

        then:
        MoveResponse mr1 = gameController.tryMove(new InsideMatrixRelocation(pos1, pos2))
        mr1.wasOk()
        MoveResponse mr2 = gameController.tryMove(new InsideMatrixRelocation(pos3, pos4))
        !mr2.wasOk()
        pos3.getCard().equals(notMovedCard)
        pos4.isEmpty()
    }

    def "AssignColorOnCard: when you use this move on board with no color assigned anywhere, it is always assigned"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        CasualPosition pos1 = gameController.getBoard().getPositionAt(0, 0)

        when:
        pos1.removeCard()
        pos1.putCard(new Card(Color.CLUBS, Face.SIX))
        gameController.getBoard().getRows().stream().forEach({it -> it.unAssignColor()})
        Color colorThatShouldBeAssigned = pos1.getCard().getColor()
        Move m = new AssignColorOnCard(pos1)

        then:
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
        pos1.getRow().isColorAssigned()
        pos1.getRow().getColor() == colorThatShouldBeAssigned
    }

    def "AssignColorOnCard: if you use this move on board that has CLUBS assigned and on position that has a card CLUBS and is in this CLUBS row - error is returned"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        CasualPosition pos1 = gameController.getBoard().getPositionAt(0, 0)

        when:
        pos1.removeCard()
        pos1.putCard(new Card(Color.CLUBS, Face.SIX))
        pos1.getRow().assignColor(Color.CLUBS)
        Move m = new AssignColorOnCard(pos1)

        then:
        MoveResponse mr = gameController.tryMove(m)
        !mr.wasOk()
        pos1.getRow().isColorAssigned()
    }

    def "AssignColorOnCard: if you use it on a row with no color but a color of the card you put is assigned to other row - error"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        CasualPosition pos1 = gameController.getBoard().getPositionAt(0, 0)

        when:
        pos1.removeCard()
        pos1.putCard(new Card(Color.CLUBS, Face.SIX))
        gameController.getBoard().getPositionAt(1, 0).getRow().assignColor(Color.CLUBS)
        Move m = new AssignColorOnCard(pos1)

        then:
        MoveResponse mr = gameController.tryMove(m)
        !mr.wasOk()
        !pos1.getRow().isColorAssigned()
    }

    def "AssignColorOnCard: you cannot assign color on card that is not in its final place"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        CasualPosition pos1 = gameController.getBoard().getPositionAt(0, 0)

        when:
        pos1.removeCard()
        pos1.putCard(new Card(Color.CLUBS, Face.EIGHT))
        Move m = new AssignColorOnCard(pos1)

        then:
        MoveResponse mr = gameController.tryMove(m)
        !mr.wasOk()
        !pos1.getRow().isColorAssigned()
    }

    def "FromStack:You cannot move card from deck to matrix if rejected stack is not empty"(){
        given:
        GameController gameController = new GameController(new RandomShuffler(), false)
        CasualPosition cas = gameController.getBoard().getPositionAt(0, 0)
        StackPosition rej = gameController.getBoard().getRejectedPosition()
        StackPosition deck = gameController.getBoard().getDeckPosition()

        when:
        rej.putCard(new Card(Color.CLUBS, Face.EIGHT))
        Move m = new FromStack(deck, cas)

        then:
        MoveResponse mr = gameController.tryMove(m)
        !mr.wasOk()

    }

    def "FromStack:You can move card from deck to matrix if it is its final place"(){
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        CasualPosition cas1 = gameController.getBoard().getPositionAt(0, 0)
        CasualPosition cas2 = gameController.getBoard().getPositionAt(0,1)
        Card card = cas2.getCard()
        StackPosition deck = gameController.getBoard().getDeckPosition()

        when:
        cas2.removeCard()
        deck.putCard(card)
        Move m = new FromStack(deck, cas2)

        then:
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
    }

    def "FromStack:You can move card from stack to position that's both first free position and final position"(){
        GameController gameController = new GameController(new TestDeckShuffler(),false)
        CasualPosition cas1 = gameController.getBoard().getPositionAt(1,1)
        Card card = cas1.getCard()
        cas1.removeCard()
        StackPosition deck = gameController.getBoard().getDeckPosition()
        deck.putCard(card)

        when:
        Move m = new FromStack(deck, cas1)

        then:
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
    }

    def "FromStack:You can move card from deck to matrix if it is first free position"(){
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        CasualPosition cas1 = gameController.getBoard().getPositionAt(1,1)
        CasualPosition cas2 = gameController.getBoard().getPositionAt(1,2)
        CasualPosition cas3 = gameController.getBoard().getPositionAt(1,3)
        StackPosition deck = gameController.getBoard().getDeckPosition()

        when:
        cas1.removeCard()
        Move m = new FromStack(deck, cas1)

        then:
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
    }

    def "FromStack: You cannot move card from deck to martrix if its not first free position and its not card's final place"(){
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        CasualPosition cas1 = gameController.getBoard().getPositionAt(1,1)
        CasualPosition cas2 = gameController.getBoard().getPositionAt(1,2)
        CasualPosition cas3 = gameController.getBoard().getPositionAt(1,3)
        StackPosition deck = gameController.getBoard().getDeckPosition()
        Card card = cas1.getCard()
        cas1.removeCard()
        cas2.removeCard()

        when:
        Move m = new FromStack(deck, cas2)

        then:
        MoveResponse mr = gameController.tryMove(m)
        !mr.wasOk()
    }

    def "FromStack: You can move from deck to rejected only if board doesn't have free positions"(){
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        StackPosition deck = gameController.getBoard().getDeckPosition()
        StackPosition rej = gameController.getBoard().getRejectedPosition()

        when:
        Move m = new FromStack(deck, rej)

        then:
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
    }

    def "FromStack: You cannot move from deck to rejected if board have free positions"(){
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        StackPosition deck = gameController.getBoard().getDeckPosition()
        StackPosition rej = gameController.getBoard().getRejectedPosition()
        CasualPosition cas1 = gameController.getBoard().getPositionAt(1,1)
        cas1.removeCard()

        when:
        Move m = new FromStack(deck, rej)

        then:
        MoveResponse mr = gameController.tryMove(m)
        !mr.wasOk()
    }

    def "DeleteDuplicate: You can remove duplicate from matrix only if the other card is on its final place"(){
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        CasualPosition cas1 = gameController.getBoard().getPositionAt(1,1)
        Card card = cas1.getCard()
        CasualPosition cas2 = gameController.getBoard().getPositionAt(1,2)
        cas2.removeCard()
        cas2.putCard(card)

        when:
        Move m = new DeleteDuplicate(cas2)

        then:
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
        cas2.isEmpty()
    }

    def "DeleteDuplicate: You cannot remove duplicate from matrix if it's in its final position"(){
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        CasualPosition cas1 = gameController.getBoard().getPositionAt(1,1)
        Card card = cas1.getCard()
        CasualPosition cas2 = gameController.getBoard().getPositionAt(1,2)
        cas2.removeCard()
        cas2.putCard(card)

        when:
        Move m = new DeleteDuplicate(cas1)

        then:
        MoveResponse mr = gameController.tryMove(m)
        !mr.wasOk()
        !cas1.isEmpty()
        !cas2.isEmpty()
    }

    def "DeleteDuplicate: You cannot remove duplicate if the other card is not on its final place"(){
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        CasualPosition cas1 = gameController.getBoard().getPositionAt(1,1)
        CasualPosition cas2 = gameController.getBoard().getPositionAt(1,2)
        CasualPosition cas3 = gameController.getBoard().getPositionAt(1,3)
        Card card = cas1.getCard()
        cas1.removeCard()
        cas2.removeCard()
        cas2.putCard(card)
        Card card2 = cas3.getCard()
        cas3.removeCard()
        cas3.putCard(card)
        cas1.putCard(card2)

        when:
        Move m = new DeleteDuplicate(cas2)

        then:
        MoveResponse mr = gameController.tryMove(m)
        !mr.wasOk()
    }

    def "DeleteDuplicate: You can remove duplicate from deck only if the other card is on its final place"(){
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        CasualPosition cas1 = gameController.getBoard().getPositionAt(1,1)
        Card card = cas1.getCard()
        StackPosition deck = gameController.getBoard().getDeckPosition()
        deck.putCard(card)

        when:
        Move m = new DeleteDuplicate(deck)

        then:
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
    }

    def "DeleteDuplicate: You can remove duplicate from rejected only if the other card is on its final place"(){
        GameController gameController = new GameController(new TestDeckShuffler(), false)
        CasualPosition cas1 = gameController.getBoard().getPositionAt(1,1)
        Card card = cas1.getCard()
        StackPosition rej = gameController.getBoard().getRejectedPosition()
        rej.putCard(card)

        when:
        Move m = new DeleteDuplicate(rej)

        then:
        MoveResponse mr = gameController.tryMove(m)
        mr.wasOk()
    }









}
