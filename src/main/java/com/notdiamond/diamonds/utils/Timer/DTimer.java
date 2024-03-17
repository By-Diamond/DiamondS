package com.notdiamond.diamonds.utils.Timer;

import java.util.Timer;
import java.util.TimerTask;

public class DTimer {
    public static double Time = 0;
    public static void RegisterTimer() {
        TimerTask Timer = new TimerTask() {
            @Override
            public void run() {
                Time = Time + 0.1;
            }
        };
        Timer DTimer = new Timer();
        DTimer.schedule(Timer, 0, 100);
    }
}
