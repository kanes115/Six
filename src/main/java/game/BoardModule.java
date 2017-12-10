package game;

import com.google.inject.AbstractModule;

/**
 * Created by Kanes on 10.12.2017.
 */
public class BoardModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CardShuffler.class).to(RandomShuffler.class);
    }
}
