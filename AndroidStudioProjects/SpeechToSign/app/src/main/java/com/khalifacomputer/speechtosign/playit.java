package com.khalifacomputer.speechtosign;

/**
 * Created by hany on 01/09/2015.
 */
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.drm.DrmManagerClient;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
public class playit extends Activity implements SurfaceHolder.Callback{

    MediaPlayer mMediaPlayer;
   SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean pausing = false;
    private int mVideoWidth;
    private int mVideoHeight;
   private int current_video=0;
    String[] videoos;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_it);
        String ss  =getIntent().getExtras().getString("SentText");
        if(ss.indexOf("~")>0)
        videoos =ss.split("~");
        else videoos[0]=ss;
        for(int i=0;i<videoos.length;i++) videoos[i]="http://khalifacomputer.com/tamkeen/tamkeen1/words/res/video/"+ videoos[i];
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView)findViewById(R.id.Sview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // String uriPath  =  "http://khalifacomputer.com/tamkeen/tamkeen1/words/res/video/0903602603602/1.3gp";

       // mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);





        //mMediaPlayer.setDataSource( ,Uri.parse("http://54.196.206.122/entercom-koitfmaac-64"));







    }

    protected void PlayThisVideo()
    {
        //String uriPath  =getIntent().getExtras().getString("SentText");
        Log.i("Link ------->",videoos[current_video]);
        mMediaPlayer = MediaPlayer.create(this,Uri.parse(videoos[current_video]));
        mMediaPlayer.setDisplay(surfaceHolder);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        //mMediaPlayer.prepareAsync();

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mMediaPlayer.reset();
                mMediaPlayer.release();
                if(current_video<videoos.length-1)
                {
                    current_video=current_video+1;
                    PlayThisVideo();
                }
                else GoBack();


                //mMediaPlayer.release();
                //setResult(Activity.RESULT_OK);
               // finish();
// TODO Auto-generated method stub
//your code if the file was completely played either show an alert to user or start another activity or file.
//even you can finish you activity here

            }
        });


        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // do stuff here
                Log.d("----->", "onPrepared called");
                mVideoWidth = mMediaPlayer.getVideoWidth();
                mVideoHeight = mMediaPlayer.getVideoHeight();
                if (mVideoWidth != 0 && mVideoHeight != 0) {
                    surfaceHolder.setFixedSize(mVideoWidth, mVideoHeight);
                    mMediaPlayer.start();
                    Log.i("MP---->","Start");
                }
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

                                      Toast.makeText(getApplicationContext(),                                            "Error" + "\n"
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


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if(current_video<videoos.length-1)PlayThisVideo();
        else GoBack();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

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

    protected  void  GoBack()
    {
        Intent myIntent = new Intent(this,MainActivity.class);

        //Log.i("EDIT_TEXT", txtText.getText().toString());
        startActivity(myIntent);
        finish();
    }

}
