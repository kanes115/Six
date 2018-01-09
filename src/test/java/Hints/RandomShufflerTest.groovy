package Hints

import game.Card
import game.RandomShuffler
import spock.lang.Specification

class RandomShufflerTest extends Specification {

    def "Deck has proper size"() {
        given:
        RandomShuffler shuffler = new RandomShuffler()

        when:
        List<Card> cards = shuffler.getDeck();

        then:
        cards.size() == 104
    }

    def "Test getNextCard"() {
        given:
        RandomShuffler shuffler = new RandomShuffler()
    }


}
