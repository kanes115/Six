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
            List<Card> cards = shuffler.getDeck()

        when:
            List<Card> nextCards = new LinkedList<>()
            Card next = shuffler.getNextCard()
            while(next!=null){
                nextCards.add(next)
                next = shuffler.getNextCard()
            }
        then:
            cards.size() == nextCards.size()
            for(int i = 0; i < cards.size(); i++) {
                cards.get(i) == nextCards.get(i)
            }

    }

    def "getRestCardsTest"() {
        given:
            RandomShuffler allCardsDeck = new RandomShuffler()
            RandomShuffler notAllCardsDeck = new RandomShuffler()
            RandomShuffler emptyDeck = new RandomShuffler()

        when:
            notAllCardsDeck.getNextCard()
            while (emptyDeck.getNextCard() != null) {}
            List<Card> allCards = allCardsDeck.getRestCards()
            List<Card> notAllCards = notAllCardsDeck.getRestCards()
            List<Card> empty = emptyDeck.getRestCards()

        then:
            allCards.size() == 104
            notAllCards.size() == 103
            empty.size() == 0
    }


}
