package game

import spock.lang.Specification

class RowTest extends Specification {

    def "Color is not assigned at the beginning even if all the colors are the same (would need proper faces then)"(){
        given:
        List<Card> rowCards = new LinkedList<>()
        Color c = Color.CLUBS
        for(int i = 0; i < 8; i++)
            rowCards << new Card(c, Face.getRandomFace())
        when:
        def row = new Row(rowCards, null)
        then:
        !row.isColorAssigned()
    }

    def "Color is assigned at the beginning if color is the same and faces 6 - king"(){
        given:
        List<Card> rowCards = new LinkedList<>()
        Color c = Color.CLUBS
        Face f = Face.SIX
        for(int i = 0; i < 8; i++) {
            rowCards << new Card(c, f)
            f = ++f
        }
        when:
        def row = new Row(rowCards, null)
        then:
        row.isColorAssigned()
    }


}
