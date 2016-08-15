package com.miso.popularmovies;

/**
 * Created by michal.hornak on 15.08.2016.
 *
 * Support class to help with time outs.
 */
public class Timeout {

    private long startTime = System.currentTimeMillis();
    private long timeout = 10000;

    public Timeout(){}

    public Timeout(long timeout){
        this.timeout = timeout;
    }

    public boolean timedOut(){
        return (System.currentTimeMillis() - startTime < timeout);
    }
}
