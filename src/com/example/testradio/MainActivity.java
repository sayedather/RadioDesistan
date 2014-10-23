package com.example.testradio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.io.IOException;
import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;
import android.widget.Toast;

import com.ather.radiodesistan.R;

public class MainActivity extends Activity implements OnClickListener {

	
	private ProgressDialog pDialog;

	private ImageView play;
    
    private ImageView stop;

    private MediaPlayer player;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        initializeUIElements();

        initializeMediaPlayer();
    }

    private void initializeUIElements() {


        play = (ImageView) findViewById(R.id.play);
        play.setOnClickListener(this);
        

        stop = (ImageView) findViewById(R.id.stop);
        stop.setVisibility(View.INVISIBLE);
        stop.setOnClickListener(this);
        
       

    }

    public void onClick(View v) {
        if (v == play) {
            startPlaying();
        } else if (v == stop) {
            stopPlaying();
        }
    }

    private void startPlaying() {
    	play.setEnabled(false);
      //  stop.setVisibility(View.VISIBLE);
      //  play.setVisibility(View.INVISIBLE);

      
    	 pDialog = new ProgressDialog(MainActivity.this);
    	// pDialog.setTitle("Title Here");
    	 pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         pDialog.setMessage("Playing...");
       //  pDialog.setProgress(0);
       //  pDialog.setMax(20);
       //  pDialog.incrementProgressBy(5);

         pDialog.show();

        player.prepareAsync();

        player.setOnPreparedListener(new OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
            	
                player.start();
                pDialog.dismiss();
                play.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), "Stream Started...", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void stopPlaying() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
            initializeMediaPlayer();
           
        }
    	play.setEnabled(true);
       play.setVisibility(View.VISIBLE);
        stop.setVisibility(View.INVISIBLE);
       
    }

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource("http://s10.voscast.com:7202/;stream.mp3");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

   /*     player.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
               
                Log.i("Buffering", "" + percent);
            }
        });
   */
    }
    
  
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.exit) {
            	
        	finish();
            System.exit(0);
            return true;
        }
        if (id == R.id.fb) {
        	Toast.makeText(getApplicationContext(), "Like us on Facebook", Toast.LENGTH_LONG).show();
            Intent fb = new Intent(Intent.ACTION_VIEW);
            fb.setData(Uri.parse("https://www.facebook.com/RadioDesistan"));
            startActivity(fb);
            return true;
        }
        if (id == R.id.twit) {
        	 Toast.makeText(getApplicationContext(), "Follow us on Twitter", Toast.LENGTH_LONG).show();
             Intent twit = new Intent(Intent.ACTION_VIEW);
             twit.setData(Uri.parse("https://twitter.com/radiodesistan"));
             startActivity(twit);
            return true;
        }
        if (id == R.id.ig) {
        	Toast.makeText(getApplicationContext(), "Follow us on Instagram", Toast.LENGTH_LONG).show();
            Intent ig = new Intent(Intent.ACTION_VIEW);
            ig.setData(Uri.parse("http://instagram.com/radiodesistan"));
            startActivity(ig);
            return true;
        }
        if (id == R.id.about) {
        	  android.app.AlertDialog.Builder alert = new AlertDialog.Builder(this);
              alert.setTitle("Radio Desistan");
              alert.setMessage("Radio Desistan is completely non profit non commercial Online Radio Station and a Social Hub for connecting Desi's World Wide which aims to serve the Desi community who understands Urdu/Hindi no matter where they are from\n\nThey might be from Pakistan, India, Bangladesh or Srilanka living in any part of the world. we wish to unite all desi's to a single platform spreading peace & love.");
              alert.setIcon(R.drawable.about);
              alert.setPositiveButton("Close", null);
              alert.show();
            return true;
        }
        
        if (id == R.id.share) {
        	Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
            sharingIntent.setType("text/plain");
            String shareBody = "Take a look at \"Radio Desistan\" - https://play.google.com/store/apps/details?id=com.ather.radiodesistan";
      //    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hey");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    

}