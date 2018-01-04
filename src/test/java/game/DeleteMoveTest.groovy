package game

import spock.lang.Specification

class DeleteMoveTest extends Specification {

    def "Delete move removes cards in given positions if cards are removable and the same"(){
        given:
        CasualPosition pos1 = new CasualPosition(new Card(Color.CLUBS, Face.ACE))
        CasualPosition pos2 = new CasualPosition(new Card(Color.CLUBS, Face.ACE))
        Move m = new DeleteMove(pos1, pos2, State.PREPARING)
        when:
        m.execute()
        then:
        m.isMade()
        pos1.isEmpty()
        pos2.isEmpty()
    }

    def "Delete move reverts changes - cards appear in the positions again"(){
        given:
        CasualPosition pos1 = new CasualPosition(new Card(Color.CLUBS, Face.ACE))
        CasualPosition pos2 = new CasualPosition(new Card(Color.CLUBS, Face.ACE))
        Move m = new DeleteMove(pos1, pos2, State.PREPARING)
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
        CasualPosition pos1 = new CasualPosition(new Card(Color.CLUBS, Face.THREE))
        CasualPosition pos2 = new CasualPosition(new Card(Color.CLUBS, Face.ACE))
        Move m = new DeleteMove(pos1, pos2, State.PREPARING)
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
        CasualPosition pos1 = new CasualPosition(new Card(Color.CLUBS, Face.SIX))
        CasualPosition pos2 = new CasualPosition(new Card(Color.CLUBS, Face.ACE))
        Move m = new DeleteMove(pos1, pos2, State.PREPARING)
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
        CasualPosition pos1 = new CasualPosition(new Card(Color.CLUBS, Face.SIX))
        CasualPosition pos2 = new CasualPosition(new Card(Color.CLUBS, Face.ACE))
        Move m = new DeleteMove(pos1, pos2, State.PREPARING)
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
