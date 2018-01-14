package gui

import game.Card
import javafx.scene.image.Image
import spock.lang.Specification


class CardImageLoaderTest extends Specification {

    def "Check whether all images path are correctly and allow to load appropriate image"() {
        given:
        //Make private field public, only for testing
        ImagePathsFactory.getDeclaredField("paths").setAccessible(true)
        Set<Card> cards = ImagePathsFactory.paths.keySet()
        when:
        for (Card card : cards) {
            new Image(getClass().getResourceAsStream(ImagePathsFactory.getPathToCardImage((Card) card)))
        }
        then:
        notThrown(Exception)
    }
}
