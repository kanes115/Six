package game

import spock.lang.Specification

class BoardTest extends Specification {

    def "After removing cards, positions are empty"(){
        given:
        CardShuffler shuffler = new RandomShuffler()
        Board board = new Board(shuffler)
        when:
        def Pos1 = board.getPositionAt(0,0)
        def Pos2 = board.getPositionAt(1,1)
        board.removeCards(Pos1, Pos2)
        then:
        board.getPositionAt(0, 0).isEmpty()
        board.getPositionAt(1, 1).isEmpty()
    }

    def "Shuffler decides about what cards are put where on the table"(){
        given:
        def rest = new LinkedList<Card>()
        rest << new Card(Color.DIAMONDS, Face.ACE)
        rest << new Card(Color.DIAMONDS, Face.ACE)

        CardShuffler shuffler = getOneCardShuffler(new Card(Color.CLUBS, Face.FOUR), rest)

        when:
        def board = new Board(shuffler)
        then:
        board.getPositionAt(0, 0).getCard().getColor() == Color.CLUBS
        board.getPositionAt(0, 0).getCard().getFace() == Face.FOUR
        board.getDeckPosition().getCard().getColor() == Color.DIAMONDS
        board.getDeckPosition().getCard().getFace() == Face.ACE
    }

    def "areAllCardsInPlace methods return false if they are not 6 - King and not on color-per-row basis"(){
        given:
        CardShuffler shuffler = getOneCardShuffler(new Card(Color.DIAMONDS, Face.ACE))

        when:
        def board = new Board(shuffler)

        then:
        !board.areAllCardsInPlace()
        !board.areAllRowsAssigned() //this also can't be fulfilled here
    }

    def "Board with randomShuffler (that gives a proper amount of cards never has free positions at the beginning"(){
        given:
        CardShuffler shuffler = getOneCardShuffler(new Card(Color.DIAMONDS, Face.ACE))

        when:
        def board = new Board(shuffler)

        then:
        !board.hasFreePositions()
    }

    def "getPositionAt(row, col) return IllegalArgumentException when indexes out of bounds (row: 0-3, col: 0-5)"(){
        given:
        CardShuffler shuffler = getOneCardShuffler(new Card(Color.DIAMONDS, Face.ACE))
        def board = new Board(shuffler)

        when:
        board.getPositionAt(row, column)
        then:
        thrown IllegalArgumentException

        where:
        row | column
        100 | 100
        4   | 6
        2   | 6
        4   | 2
        -1  | -1
    }


    // Gives a shuffler that will make board have:
    // on matrix only `card`, on deck `rest`
    def getOneCardShuffler(Card card, List<Card> rest){
        CardShuffler shuffler = Stub(CardShuffler.class)

        shuffler.getRestCards() >> rest

        List<Card> row = new LinkedList<>()
        for(int i = 0; i < 8; i ++)
            row << card

        shuffler.getNextCards(8) >> row
        return shuffler
    }
    def getOneCardShuffler(Card card){
        def rest = new LinkedList<Card>()
        rest << new Card(Color.DIAMONDS, Face.ACE)
        rest << new Card(Color.DIAMONDS, Face.ACE)
        return getOneCardShuffler(card, rest)
    }



}
