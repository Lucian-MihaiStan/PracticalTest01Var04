package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;

public class ProcessingThread extends Thread {

    private final Context context;
    private final String name;
    private final String group;
    private boolean running = true;

    public ProcessingThread(Context context, String name, String group) {
        this.context = context;
        this.name = name;
        this.group = group;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, Process.myPid() + " "  + Process.myTid() + " Thread has started! (name = " + name + ", group = " + group + ")");
        while (running) {
            sendMessage();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_ACTION);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, new Date(System.currentTimeMillis()) + " " + name + " " + group);
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        running = false;
    }
}
