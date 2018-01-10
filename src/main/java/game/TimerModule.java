package game;

import com.google.inject.AbstractModule;
import hints.ITimer;
import hints.NormalTimer;

import java.time.Clock;

public class TimerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ITimer.class).to(NormalTimer.class);
    }
}
