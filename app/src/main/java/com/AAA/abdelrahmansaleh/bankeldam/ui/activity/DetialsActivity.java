package com.AAA.abdelrahmansaleh.bankeldam.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.helper.HelperMethod;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.ArticleDetailsFragment;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.BloodRequestDetailsFragment;

public class DetialsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detials );

        int postId = getIntent().getIntExtra( "intentPostId", 0 );
        int intentBloodId = getIntent().getIntExtra( "intentBloodId", 0 );
        if (postId != 0) {
            ArticleDetailsFragment fragment = new ArticleDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt( "bundlePostsId", postId );
            fragment.setArguments( bundle );
            HelperMethod.replaceFragment( fragment, getSupportFragmentManager(), R.id.DetialsActivity_frame, null, null );
        }
        if (intentBloodId != 0) {
            BloodRequestDetailsFragment fragment = new BloodRequestDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt( "bundleBloodId", intentBloodId );
            fragment.setArguments( bundle );
            HelperMethod.replaceFragment( fragment, getSupportFragmentManager(), R.id.DetialsActivity_frame, null, null );

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
