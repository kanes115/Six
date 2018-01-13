package game

import game.Moves.AssignColorOnCard
import game.Moves.DeleteUnnecessaryPair
import game.Moves.InsideMatrixRelocation
import game.Moves.Move
import game.Positions.CasualPosition
import hints.TestDeckShuffler
import spock.lang.Specification

/*
Integration test.
Testing different moves here, starting state of controller, board and rows.
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

}
