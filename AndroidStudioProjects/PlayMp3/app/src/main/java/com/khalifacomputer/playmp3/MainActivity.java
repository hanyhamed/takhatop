package com.khalifacomputer.playmp3;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    Button BtnExit;
    Button BtnSound;
    EditText txtText;
    MediaPlayer mMediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnSound=(Button) findViewById(R.id.buttonsound);
        BtnExit=(Button)findViewById(R.id.buttonexit);
        txtText = (EditText) findViewById(R.id.txtText);
        BtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Go
                finish();
            }

        });
        BtnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//Go
//
               if (mMediaPlayer != null) {
                mMediaPlayer.release();
                mMediaPlayer = null;
            }

                String ss="http://translate.google.com.eg/translate_tts?ie=UTF-8&q=";
                String sss="&tl=ar&total=1&idx=0&textlen=22&tk=866076&client=t&prev=input";

                String url = ss + txtText.getText().toString() + sss;
                mMediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(url));
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);

                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mMediaPlayer.reset();
                        mMediaPlayer.release();
   // TODO Auto-generated method stub
//your code if the file was completely played either show an alert to user or start another activity or file.
//even you can finish you activity here

                    }
                });


                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // do stuff here

                            mMediaPlayer.start();
                            Log.i("MP---->","Start");
                        }

                });

                mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener(){
                                                    @Override
                                                    public boolean onError(MediaPlayer mp,int what,int extra){
                                                        String errorWhat;
                                                        switch(what){
                                                            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                                                                errorWhat = "MEDIA_ERROR_UNKNOWN";
                                                                break;
                                                            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                                                                errorWhat = "MEDIA_ERROR_SERVER_DIED";
                                                                break;
                                                            default:
                                                                errorWhat = "!";
                                                        }

                                                        String errorExtra;
                                                        switch(extra){
                                                            case MediaPlayer.MEDIA_ERROR_IO:
                                                                errorExtra = "MEDIA_ERROR_IO";
                                                                break;
                                                            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                                                                errorExtra = "MEDIA_ERROR_MALFORMED";
                                                                break;
                                                            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                                                                errorExtra = "MEDIA_ERROR_UNSUPPORTED";
                                                                break;
                                                            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                                                                errorExtra = "MEDIA_ERROR_TIMED_OUT";
                                                                break;
                                                            default:
                                                                errorExtra = "!";
                                                        }

                                                        Toast.makeText(getApplicationContext(), "Error" + "\n"
                                                                        + errorWhat + "\n"
                                                                        + errorExtra,
                                                                Toast.LENGTH_LONG).show();

                                                        //release
                                                        Log.i("MP error---->",errorWhat+" "+errorExtra);

                                                        return true;
                                                    }
                                                }
                );
            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
