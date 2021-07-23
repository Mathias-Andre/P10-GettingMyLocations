    package com.example.p10_gettingmylocations;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.File;

    public class Music extends Service {

        private MediaPlayer player = new MediaPlayer();

    public Music() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    // execution of service will start on calling this method
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            File file = new File(getFilesDir().getAbsolutePath() + "/Download", "music.mp3");

            // specify the path of the audio file
            player.setDataSource(file.getPath());
            player.prepare();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // providing the boolean value as true to play the audio on loop
        player.setLooping(true);

        // starting the process
        player.start();

        // returns the status of the program
        return START_STICKY;
    }

    @Override
// execution of the service will stop on calling this method
    public void onDestroy() {
        super.onDestroy();

        // stopping the process
        player.stop();
    }
}