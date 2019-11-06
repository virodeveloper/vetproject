package com.example.vet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {
    Animation frombottom,fromtop;
    ImageView imageView,img;
    TextView textView;
   // VideoView videoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        videoHolder=findViewById(R.id.vi);
//        try {
////            VideoView videoHolder = new VideoView(this);
////            setContentView(videoHolder);
//            //videoHolder.setVideoURI(uri);
//            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pick);
//            videoHolder.setVideoURI(video);
//            videoHolder.requestFocus();
//
//            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                public void onCompletion(MediaPlayer mp) {
//                    jump();
//                }
//            });
//            videoHolder.start();
//        } catch (Exception ex) {
//            jump();
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        jump();
//        return true;
//    }
//
//    private void jump() {
//        if (isFinishing())
//            return;
//        startActivity(new Intent(this, Login.class));
//        finish();
//    }

        setContentView(R.layout.activity_splash_screen);

        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    imageView=findViewById(R.id.img);
                    textView=findViewById(R.id.ima);


                    frombottom= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.frombottom);
                    fromtop= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fromtop);
                   // imageView.setAnimation(frombottom);
                   // textView.setAnimation(fromtop);

                    //Display for 3 seconds
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    //Goes to Activity  StartingPoint.java(STARTINGPOINT)
                    Intent openstartingpoint=new Intent(getApplicationContext(),Login.class);
                    startActivity(openstartingpoint);
                }
            }
        };
        timer.start();
    }


    //Destroy Welcome_screen.java after it goes to next activity
    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        finish();

    }


}
