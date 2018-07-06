package com.jack12324.som.capabilities;

import java.util.concurrent.Callable;

public class NemesisFactory implements Callable<INemesisList> {

    @Override
    public INemesisList call() {
        return new NemesisList(null);
    }
}
