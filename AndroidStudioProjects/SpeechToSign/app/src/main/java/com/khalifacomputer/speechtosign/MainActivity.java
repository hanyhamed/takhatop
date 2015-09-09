package com.khalifacomputer.speechtosign;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    protected static final int RESULT_SPEECH = 1;

    private ImageButton btnSpeak;
    EditText txtText;
   Button BtnExit;
   Button BtnSend;
   Button BtnSound;

    MediaPlayer mMediaPlayer;
   // TextToSpeech t1;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtText = (EditText) findViewById(R.id.txtText);
        BtnExit=(Button)  findViewById(R.id.buttonexit);
        BtnSend=(Button) findViewById(R.id.buttontrans);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        BtnExit=(Button)findViewById(R.id.buttonexit);
/*
       t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                  Locale loc = new Locale("ar_EG");
                    int result=t1.setLanguage(loc);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }



                }
            }


        });
*/
        BtnSound=(Button) findViewById(R.id.buttonsound);
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
                        Log.i("MP---->", "Start");
                    }

                });

                mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                                                    @Override
                                                    public boolean onError(MediaPlayer mp, int what, int extra) {
                                                        String errorWhat;
                                                        switch (what) {
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
                                                        switch (extra) {
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
                                                        Log.i("MP error---->", errorWhat + " " + errorExtra);

                                                        return true;
                                                    }
                                                }
                );
            }

        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");


                try {
                    startActivityForResult(intent, RESULT_SPEECH);

                    txtText.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Ops! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }

            }
        });

        BtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Go
                if (!txtText.getText().toString().equals("") ) {
                    Intent myIntent = new Intent(v.getContext(), CorrectPharase.class);
                    myIntent.putExtra("SentText", txtText.getText().toString());
                    //Log.i("EDIT_TEXT", txtText.getText().toString());
                    startActivity(myIntent);
                    finish();

                }

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txtText.setText(text.get(0));
                }
                break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO Auto-generated method stub
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }


    }


}
