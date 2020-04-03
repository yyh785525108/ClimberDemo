package com.prudential.climberdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.os.Handler;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.prudential.climberdemo.util.BlurUtil;
import com.prudential.climberdemo.view.CircleProgressView;



public class MainActivity extends AppCompatActivity {
    private CircleProgressView circleProgressView;
    private TextView stepsTv;

    private ConstraintLayout mainBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    /**
     * init all the view
     */
    private void initView(){
        stepsTv=findViewById(R.id.steps_tv);
        circleProgressView = findViewById(R.id.circle_progress);
        mainBg=findViewById(R.id.main_bg);
        circleProgressView.setCurrent(12000);
        stepsTv.setText(String.valueOf(circleProgressView.getCurrent()));

        //get the blurBitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.login_bg);
        Bitmap blurBitmap=BlurUtil.getBlurBackground(this,bitmap);
        mainBg.setBackground( new BitmapDrawable(getResources(),blurBitmap));



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                circleProgressView.startAnimProgress(14000, 6*100);
                //update value of text
                circleProgressView.setOnAnimProgressListener(new CircleProgressView.OnAnimProgressListener() {
                    @Override
                    public void valueUpdate(int progress) {
                        stepsTv.setText(String.valueOf(progress));
                    }
                });

            }
        }, 2000);//start run after 2 seconds




    }





}
