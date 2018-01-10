package game;

import com.google.inject.AbstractModule;

import java.time.Clock;

public class TimerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ITimer.class).to(NormalTimer.class);
    }
}
