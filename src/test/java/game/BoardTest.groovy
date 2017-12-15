package game

import spock.lang.Specification

class BoardTest extends Specification {

    def "After removing cards, positions are empty"(){
        given:
        Board b = new Board()
        when:
        def Pos1 = b.getPositionAt(0,0)
        def Pos2 = b.getPositionAt(1,1)
        b.removeCards(Pos1, Pos2)
        then:
        b.getPositionAt(0, 0).isEmpty()
        b.getPositionAt(1, 1).isEmpty()
    }

    def "Shuffler decides about what cards are put where on the table"(){
        given:
        CardShuffler s = Stub()
        def rest = new LinkedList<Card>()
        rest.add(new Card(Color.DIAMONDS, Face.ACE))
        rest.add(new Card(Color.DIAMONDS, Face.ACE))

        s.getNextCard() >>> new Card(Color.CLUBS, Face.FOUR)
        s.getRestCards() >>> rest

        when:
        def b = new Board(s)
        then:
        b.getPositionAt(0, 0).getCard().getColor() == Color.CLUBS
        b.getPositionAt(0, 0).getCard().getFace() == Face.FOUR
        b.getDeckPosition().getCard().getColor() == Color.DIAMONDS
        b.getDeckPosition().getCard().getFace() == Face.ACE
    }



}
