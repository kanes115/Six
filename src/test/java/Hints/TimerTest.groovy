package Hints

import hints.ITimer
import hints.NormalTimer
import spock.lang.Specification

class TimerTest extends Specification {

    def "Timer stops"() {
        given:
        ITimer timer = new NormalTimer()
        when:
        timer.start()
        timer.stop()
        then:
        long time1 = timer.getTimeLong()
        sleep(100)
        time1 == timer.getTimeLong()
    }

    def "Timer formats String"() {
        given:
        ITimer timer = new NormalTimer(149000)
        when:
        String time = timer.getTime()
        then:
        time == "02:29"
    }


}
