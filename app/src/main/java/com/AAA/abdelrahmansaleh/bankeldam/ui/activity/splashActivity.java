package com.AAA.abdelrahmansaleh.bankeldam.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.google.firebase.FirebaseApp;
import butterknife.BindView;
import butterknife.ButterKnife;

public class splashActivity extends AppCompatActivity {

    @BindView(R.id.splashBackground)
    ImageView splashBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        ButterKnife.bind( this );
        FirebaseApp.initializeApp( this );
        Glide.with( this ).load( R.mipmap.ic_launcher_background22 ).into( splashBackground );
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent( splashActivity.this, LoginActivity.class );
                startActivity( intent );
                finish();
            }
        }, 4000 );

    }


}
