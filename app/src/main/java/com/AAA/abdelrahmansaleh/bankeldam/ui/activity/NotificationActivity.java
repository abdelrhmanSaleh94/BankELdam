package com.AAA.abdelrahmansaleh.bankeldam.ui.activity;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.adapter.NotificationListAdapter;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.notificationsList.NotificationsList;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.notificationsList.NotificationsListData;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.OnEndless;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.notificationRV)
    RecyclerView notificationRV;
    private List<NotificationsListData> notificationList=new ArrayList<>( );
    private NotificationListAdapter adapter;
    private String api_token;
    private int maxPages;
    private ApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notification );
        ButterKnife.bind( this );
        apiServices= RetrofitClient.getClient().create( ApiServices.class );
        api_token = SharedPreferencesManger.LoadStringData( this, "apiToken" );
        LinearLayoutManager manager=new LinearLayoutManager(this );
        notificationRV.setLayoutManager( manager );
        OnEndless onEndless=new OnEndless(manager,5) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPages) {
                    getNotification(current_page);
                }
            }
        };
        notificationRV.addOnScrollListener( onEndless );
        adapter=new NotificationListAdapter( this,notificationList );
        notificationRV.setAdapter( adapter );
        getNotification(1);
    }

    private void getNotification(int page) {
        apiServices.getNotificationsList( api_token,page ).enqueue( new Callback<NotificationsList>() {
            @Override
            public void onResponse(Call<NotificationsList> call, Response<NotificationsList> response) {
                if (response.body().getStatus()==1){
                    Toast.makeText( NotificationActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    maxPages=response.body().getData().getLastPage();
                    notificationList.addAll( response.body().getData().getData() );
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText( NotificationActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<NotificationsList> call, Throwable t) {
                Toast.makeText( NotificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT ).show();
                Log.i( "me","onFailure: "+t.getMessage() );
            }
        } );
    }

}
