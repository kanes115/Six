package game;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by Kanes on 10.12.2017.
 */


public class Main {

    public static void main(String[] args){

        System.out.println("started");

        // Injecting dependencies into Board example
        // Should be put before initializing Board
        Injector injector = Guice.createInjector(new BoardModule());
        Board board = injector.getInstance(Board.class);
        //************
    }

}
