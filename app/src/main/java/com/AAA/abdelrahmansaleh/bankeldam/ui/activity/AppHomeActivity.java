package com.AAA.abdelrahmansaleh.bankeldam.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.notificationsCount.NotificationsCount;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.registerNotificationToken.NotificationToken;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.HelperMethod;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.HomeCycle.ArticleFragment;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.HomeCycle.BloodRequestsFragment;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.HomeCycle.HomeFragment;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle.ContactFragment;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle.FavouriteFragment;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle.NotificationSettingFragment;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle.PersonalFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.mainActivity_Appbar)
    AppBarLayout mainActivityAppbar;
    @BindView(R.id.homeActivity_notification_icon)
    ImageView homeActivityNotificationIcon;
    @BindView(R.id.homeActivity_Toolbar)
    Toolbar homeActivityToolbar;
    @BindView(R.id.homeContainer)
    FrameLayout homeContainer;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.home_Toolbar_TV)
    TextView homeToolbarTV;
    @BindView(R.id.notificationsTV)
    TextView notificationsTV;
    private Fragment[] fragments = {new ArticleFragment(), new BloodRequestsFragment()};
    private ApiServices apiServices;
    private String apiToken;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_app_home );
        ButterKnife.bind( this );
        Toolbar toolbar = (Toolbar) findViewById( R.id.homeActivity_Toolbar );
        setSupportActionBar( toolbar );
        setTitle( "" );
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        toolbar.setNavigationIcon( R.drawable.ic_menu_icon );

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        token = FirebaseInstanceId.getInstance().getToken();
        apiToken = SharedPreferencesManger.LoadStringData( this, "apiToken" );
/*        PagerAdapterMainActivity pagerAdapter = new PagerAdapterMainActivity( getSupportFragmentManager() );
        homeActivityVpager.setAdapter( pagerAdapter );*/
        HelperMethod.replaceFragment( new HomeFragment(), getSupportFragmentManager(), R.id.homeContainer, homeToolbarTV, "بنك الدم" );
        sendNotification();
        getNotificationCount();
    }

    @OnClick(R.id.homeActivity_notification_icon)
    public void goToNotification() {
        Intent intent = new Intent( AppHomeActivity.this, NotificationActivity.class );
        startActivity( intent );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
            HelperMethod.replaceFragment( new HomeFragment(), getSupportFragmentManager(), R.id.homeContainer, homeToolbarTV, "بنك الدم" );
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent( AppHomeActivity.this, NavActivity.class );
        if (id == R.id.nav_personal) {

            HelperMethod.replaceFragment( new PersonalFragment(), getSupportFragmentManager(), R.id.homeContainer, homeToolbarTV, "تعديل البيانات" );

        } else if (id == R.id.nav_notificationStt) {

            HelperMethod.replaceFragment( new NotificationSettingFragment(), getSupportFragmentManager(), R.id.homeContainer, homeToolbarTV, "اعدادات الاشعارات" );

        } else if (id == R.id.nav_rating) {
            Uri uri = Uri.parse( "market://details?id=" + "com.facebook.katana" );
            Intent goToMarket = new Intent( Intent.ACTION_VIEW, uri );
            goToMarket.addFlags( Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK );
            try {
                startActivity( goToMarket );
            } catch (ActivityNotFoundException e) {
                startActivity( new Intent( Intent.ACTION_VIEW,
                        Uri.parse( "http://play.google.com/store/apps/details?id=" + "com.facebook.katana" ) ) );
            }

        } else if (id == R.id.nav_countact) {
            HelperMethod.replaceFragment( new ContactFragment(), getSupportFragmentManager(), R.id.homeContainer, homeToolbarTV, "تواصل معنا" );

        } else if (id == R.id.nav_fav) {
            HelperMethod.replaceFragment( new FavouriteFragment(), getSupportFragmentManager(), R.id.homeContainer, homeToolbarTV, "المفضلة" );


        } else if (id == R.id.nav_logout) {
            removeNotification();
            startActivity( new Intent( AppHomeActivity.this, LoginActivity.class ) );
            finish();
        } else if (id == R.id.nav_home) {
            HelperMethod.replaceFragment( new HomeFragment(), getSupportFragmentManager(), R.id.homeContainer, homeToolbarTV, getResources().getString( R.string.title_activity_app_home ) );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void removeNotification() {
        apiServices.notificationTokenRemove( token, apiToken ).enqueue( new Callback<NotificationToken>() {
            @Override
            public void onResponse(Call<NotificationToken> call, Response<NotificationToken> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText( AppHomeActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( AppHomeActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationToken> call, Throwable t) {
                Toast.makeText( AppHomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    @OnClick(R.id.homeActivity_notification_icon)
    public void onViewClicked() {
    }


    public void sendToDetails(Integer id) {

        Intent intent = new Intent( AppHomeActivity.this, DetialsActivity.class );
        intent.putExtra( "intentPostId", id );
        startActivity( intent );
    }

    public void sendToDetailsBlood(Integer id) {
        Toast.makeText( this, "hi" + id, Toast.LENGTH_SHORT ).show();
        Intent intent = new Intent( AppHomeActivity.this, DetialsActivity.class );
        intent.putExtra( "intentBloodId", id );
        startActivity( intent );

    }

    private void sendNotification() {
        apiServices.notificationTokenRegister( token, apiToken, "android" ).enqueue( new Callback<NotificationToken>() {
            @Override
            public void onResponse(Call<NotificationToken> call, Response<NotificationToken> response) {
                if (response.body().getStatus() == 1) {

                } else {
                    Toast.makeText( AppHomeActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationToken> call, Throwable t) {

            }
        } );
    }

    public void getNotificationCount() {
        apiServices.getNotificationsCount( apiToken ).enqueue( new Callback<NotificationsCount>() {
            @Override
            public void onResponse(Call<NotificationsCount> call, Response<NotificationsCount> response) {
                if (response.body().getStatus() == 1) {
                    Integer notificationsCount = response.body().getData().getNotificationsCount();
                    if (notificationsCount > 9) {
                        notificationsTV.setText( "9+" );
                    } else if (notificationsCount == 0) {
                        notificationsTV.setVisibility( View.INVISIBLE );
                    } else {
                        notificationsTV.setText( notificationsCount.toString() );
                    }
                } else {
                    Toast.makeText( AppHomeActivity.this, "faLSE", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationsCount> call, Throwable t) {
            }
        } );
    }

}

