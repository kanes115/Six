package game

import spock.lang.Specification

/**
 * Created by aga on 15.12.17.
 */



class GameCheckerTests extends Specification  {

    def "should return true if move is  correct"() {
        given:
        Board board = Stub()
        MoveChecker checker = Stub()
        GameController gameController = new GameController(checker,board,State.INPROGRESS)
        Move move = Stub()
        checker.isCorrect(move) >> true
        expect:
        gameController.tryMove(move)
    }

}
