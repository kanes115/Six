package game

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
        gameController.tryMove(m)
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
        !gameController.tryMove(m)
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
        Move m = new InsideMatrixRelocation(pos1, pos2, gameController.getBoard())
        gameController.tryMove(m)
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
        gameController.tryMove(new InsideMatrixRelocation(pos1, pos2, gameController.getBoard()))
        !gameController.tryMove(new InsideMatrixRelocation(pos3, pos4, gameController.getBoard()))
        pos3.getCard().equals(notMovedCard)
        pos4.isEmpty()
    }


}
