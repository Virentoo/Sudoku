package pl.Barszczewski.Kacper;

import java.util.TimerTask;


public class MyTimerTask extends TimerTask {

    private int minutes;
    private int seconds;
    private int milliseconds;
    private boolean work = false;
    private int sleep;

    public MyTimerTask() {
        minutes = 0;
        seconds = 0;
        milliseconds = 0;
    }


    public void minutes(int i) {
        minutes = i;
    }

    public void seconds(int i) {
        seconds = i;
    }

    public void milliseconds(int i) {
        milliseconds = i;
    }

    public String getTime() {
        return fillZero(minutes) + ":" + fillZero(seconds) + ":" + milliseconds;
    }

    private String fillZero(int time) {
        if (time < 10) return "0" + time;
        else return String.valueOf(time);
    }

    private void nextMili() {
        if (milliseconds + 1 > 9) {
            milliseconds = 0;
            nextSeconds();
        }else milliseconds++;
    }

    private void nextSeconds() {
        seconds++;
        if (seconds > 59) {
            seconds = 0;
            nextMinutes();
        }
    }

    private void nextMinutes() {
        minutes++;
		System.gc();
    }
    
    public void start() {
    	work = true;
    }
    
    public void stop() {
    	work = false;
    }
    
    public void reset() {
    	minutes = 0;
    	seconds = 0;
    	milliseconds = 0;
    }

    @Override
    public void run(){
    	if(sleep != 0) {
    		sleep -= 100;
    	}else if (work) { 
    		nextMili();
    	}
    }
    
    public void sleep(int time) {
    	sleep = time;
    }
}
