package edu.iastate.cs228.hw2;

/**
 * Purpose:
 * This class serves as a means of calculating execution time within a program and makes use of system.nanotime to
 * achieve accurate results down to the nearest nanosecond.
 *
 * @author:wesolowskitj
 * @version: 1.1
 * <b/>
 * Created on 12/18/2014 at 2:30 PM.
 */
public class Stopwatch {
    private long startTime;
    private long endTime;
    private long exeTime;

    public void start(){
        startTime = System.nanoTime();
    }
    public void stop(){
        endTime = System.nanoTime();
        exeTime = endTime - startTime;
    }
    public void clear(){
        startTime = 0;
        endTime = 0;
        exeTime = 0;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getExeTime() {
        return exeTime;
    }

    public long returnTimeMilliseconds(long time){
        return time /1000000;
    }//divides accordingly to output in milli instead of nano

    public void stopWatch(){

    };
}
