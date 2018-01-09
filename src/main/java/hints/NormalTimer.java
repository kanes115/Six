package hints;

import java.util.concurrent.TimeUnit;

public class NormalTimer implements ITimer {

    private long totalTime = 0;
    private long startTime;
    private boolean isRunning = false;


    public NormalTimer() {

    }

    public NormalTimer(long intialTime) {
        this.totalTime = intialTime;
    }

    @Override
    public void start() {
        if(!isRunning)
            this.startTime = System.currentTimeMillis();
        this.isRunning = true;
    }

    @Override
    public void stop() {
        if(isRunning)
            this.totalTime += this.startTime - System.currentTimeMillis();
        this.isRunning = false;
    }

    @Override
    public long getTimeLong() {
        return isRunning ? (this.startTime - System.currentTimeMillis() + this.totalTime) : this.totalTime;
    }

    @Override
    public String getTime() {
        return timeFormatted();
    }


    private String timeFormatted() {
        long currentTime = getTimeLong();
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(currentTime),
                TimeUnit.MILLISECONDS.toSeconds(currentTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentTime))
        );
    }
}
