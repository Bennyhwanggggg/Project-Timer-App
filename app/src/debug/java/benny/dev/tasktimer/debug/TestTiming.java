package benny.dev.tasktimer.debug;

/**
 * Created by timbuchalka for Android Oreo with Java course
 * from www.learnprogramming.academy
 */

public class TestTiming {

    long taskId;
    long startTime;
    long duration;

    public TestTiming(long taskId, long startTime, long duration) {
        this.taskId = taskId;
        this.startTime = startTime / 1000; // store seconds, not milliseconds
        this.duration = duration;
    }
}
