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
}
