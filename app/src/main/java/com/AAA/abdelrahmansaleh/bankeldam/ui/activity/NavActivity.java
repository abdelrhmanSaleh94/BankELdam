package com.AAA.abdelrahmansaleh.bankeldam.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle.FavouriteFragment;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle.NotificationSettingFragment;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle.PersonalFragment;
import com.AAA.abdelrahmansaleh.bankeldam.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavActivity extends AppCompatActivity {

    @BindView(R.id.navActivity_tittle)
    TextView navActivityTittle;
    private String nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_nav );
        ButterKnife.bind( this );
        Intent intent = getIntent();
        nav = intent.getStringExtra( "person" );
        if (nav.equals( "person" )) {
            HelperMethod.replaceFragment( new PersonalFragment(), getSupportFragmentManager(), R.id.navActivity_container, navActivityTittle, "تعديل البيانات" );
        } else if (nav.equals( "fav" )) {
            HelperMethod.replaceFragment( new FavouriteFragment(), getSupportFragmentManager(), R.id.navActivity_container, navActivityTittle, nav );
        } else if (nav.equals( "notificationStt" )) {
            HelperMethod.replaceFragment( new NotificationSettingFragment(), getSupportFragmentManager(), R.id.navActivity_container, navActivityTittle, nav );
        } else if (nav.equals( "countact" )) {
            HelperMethod.replaceFragment( new NotificationSettingFragment(), getSupportFragmentManager(), R.id.navActivity_container, navActivityTittle, nav );
        }
    }

    @Override
    public void onBackPressed() {
        startActivity( new Intent( NavActivity.this,AppHomeActivity.class ) );
    }
}
