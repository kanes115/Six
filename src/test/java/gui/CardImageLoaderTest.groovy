package gui

import javafx.scene.image.Image
import spock.lang.Specification

class CardImageLoaderTest extends Specification {

    def "Check whether all images path are correctly and allow to load appropriate image"(){
        given:
        List<Card> cards = Card.values()
        when:
        for (Card card :cards){
            new Image(getClass().getResourceAsStream(card.getPathToFilename()))
        }
        then:
        notThrown(Exception)

    }
}
