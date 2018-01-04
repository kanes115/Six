package game

import spock.lang.Specification

class FaceTest extends Specification {

    def "Some cards are removable"(){
        expect:
        Face.ACE.isRemovable()
        !Face.EIGHT.isRemovable()
        !Face.JACK.isRemovable()
        Face.THREE.isRemovable()
    }

    def "next() method returns the next face"(){
        expect:
        Face.THREE.next() == Face.FOUR
        Face.FOUR.next() == Face.FIVE
        Face.ACE.next() == Face.TWO
        Face.SIX.next().next().next() == Face.NINE
    }
}
