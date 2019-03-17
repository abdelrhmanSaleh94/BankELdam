package com.AAA.abdelrahmansaleh.bankeldam.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.UserCycle.LoginFragment;
import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.helper.HelperMethod;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN );
        HelperMethod.replaceFragment( new LoginFragment(), getSupportFragmentManager(), R.id.login_Activity_frame_Container, null, null );

    }

}
