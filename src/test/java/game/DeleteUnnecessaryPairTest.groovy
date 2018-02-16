package game

import game.Moves.DeleteUnnecessaryPair
import game.Moves.Move
import game.Positions.CasualPosition
import spock.lang.Specification

// Target face is not important here, Face.ACE chosen randomly

class DeleteUnnecessaryPairTest extends Specification {

    def "Delete move removes cards in given positions if cards are removable and the same"(){
        given:
        Row stubrow = Stub(Row.class)
        stubrow.getBoard() >> null
        CasualPosition pos1 = new CasualPosition(new Card(Color.CLUBS, Face.ACE), Face.ACE, stubrow)
        CasualPosition pos2 = new CasualPosition(new Card(Color.CLUBS, Face.ACE), Face.ACE, stubrow)
        Move m = new DeleteUnnecessaryPair(pos1, pos2)
        when:
        m.execute()
        then:
        m.isMade()
        pos1.isEmpty()
        pos2.isEmpty()
    }

    def "Delete move reverts changes - cards appear in the positions again"(){
        given:
        Row stubrow = Stub(Row.class)
        stubrow.getBoard() >> null
        CasualPosition pos1 = new CasualPosition(new Card(Color.CLUBS, Face.ACE), Face.ACE, stubrow)
        CasualPosition pos2 = new CasualPosition(new Card(Color.CLUBS, Face.ACE), Face.ACE, stubrow)
        Move m = new DeleteUnnecessaryPair(pos1, pos2)
        when:
        m.execute()
        pos1.isEmpty()
        pos2.isEmpty()
        m.revert()
        then:
        !m.isMade()
        !pos1.isEmpty()
        !pos2.isEmpty()
        pos1.getCard() == new Card(Color.CLUBS, Face.ACE)
    }

    def "Delete move does not delete different cards"(){
        given:
        Row stubrow = Stub(Row.class)
        stubrow.getBoard() >> null
        CasualPosition pos1 = new CasualPosition(new Card(Color.CLUBS, Face.THREE), Face.ACE, stubrow)
        CasualPosition pos2 = new CasualPosition(new Card(Color.CLUBS, Face.ACE), Face.ACE, stubrow)
        Move m = new DeleteUnnecessaryPair(pos1, pos2)
        when:
        !m.execute()
        !pos1.isEmpty()
        !pos2.isEmpty()
        then:
        !m.isMade()
        !pos1.isEmpty()
        !pos2.isEmpty()
        pos1.getCard() == new Card(Color.CLUBS, Face.THREE)
        pos2.getCard() == new Card(Color.CLUBS, Face.ACE)
    }

    def "Delete move does not delete cards if at least one is not removable"(){
        given:
        Row stubrow = Stub(Row.class)
        stubrow.getBoard() >> null
        CasualPosition pos1 = new CasualPosition(new Card(Color.CLUBS, Face.SIX), Face.ACE, stubrow)
        CasualPosition pos2 = new CasualPosition(new Card(Color.CLUBS, Face.ACE), Face.ACE, stubrow)
        Move m = new DeleteUnnecessaryPair(pos1, pos2)
        when:
        !m.execute()
        !pos1.isEmpty()
        !pos2.isEmpty()
        then:
        !m.isMade()
        !pos1.isEmpty()
        !pos2.isEmpty()
        pos1.getCard() == new Card(Color.CLUBS, Face.SIX)
        pos2.getCard() == new Card(Color.CLUBS, Face.ACE)
    }

    def "Delete move simply ignores revert() call if the move was not made"(){
        given:
        Row stubrow = Stub(Row.class)
        stubrow.getBoard() >> null
        CasualPosition pos1 = new CasualPosition(new Card(Color.CLUBS, Face.SIX), Face.ACE, stubrow)
        CasualPosition pos2 = new CasualPosition(new Card(Color.CLUBS, Face.ACE), Face.ACE, stubrow)
        Move m = new DeleteUnnecessaryPair(pos1, pos2)
        when:
        m.revert()
        then:
        !m.isMade()
        !pos1.isEmpty()
        !pos2.isEmpty()
        pos1.getCard() == new Card(Color.CLUBS, Face.SIX)
        pos2.getCard() == new Card(Color.CLUBS, Face.ACE)
    }
}
