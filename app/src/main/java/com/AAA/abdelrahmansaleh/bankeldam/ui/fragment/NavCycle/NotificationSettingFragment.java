package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.adapter.BloodTypeAdapter;
import com.AAA.abdelrahmansaleh.bankeldam.adapter.GovernorateAdapter;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.governorate.Governorates;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.governorate.GovernoratesData;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.listBloodType.ListBloodType;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.listBloodType.ListBloodTypeData;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.notificationsSettings.NotificationsSettings;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.HelperMethod;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationSettingFragment extends Fragment {


    @BindView(R.id.cardCountry_Rv)
    RecyclerView cardCountryRv;
    Unbinder unbinder;
    @BindView(R.id.cardBloodType_Rv)
    RecyclerView cardBloodTypeRv;
    private List<GovernoratesData> governoratesList;
    private GovernorateAdapter adapter;
    private BloodTypeAdapter adapterBloodType;
    private ApiServices apiServices;
    private String apiToken;
    private List<ListBloodTypeData> bloodList;

    public NotificationSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_notification_setting, container, false );
        unbinder = ButterKnife.bind( this, view );
        HelperMethod.showProgressDialog( getActivity(), "loading .... " );
        bloodList = new ArrayList<>();
        governoratesList = new ArrayList<>();
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        GridLayoutManager managerBlood = new GridLayoutManager( getContext(), 4 );
        cardBloodTypeRv.setLayoutManager( managerBlood );
        adapterBloodType = new BloodTypeAdapter( getContext(), bloodList );
        cardBloodTypeRv.setAdapter( adapterBloodType );
        getBloodTypeList();
        getGovernorates();
        GridLayoutManager manager = new GridLayoutManager( getContext(), 3 );
        cardCountryRv.setLayoutManager( manager );
        adapter = new GovernorateAdapter( getContext(), governoratesList );
        cardCountryRv.setAdapter( adapter );
        apiToken = SharedPreferencesManger.LoadStringData( getActivity(), "apiToken" );
        return view;
    }

    private void getBloodTypeList() {
        apiServices.getBloodList().enqueue( new Callback<ListBloodType>() {
            @Override
            public void onResponse(Call<ListBloodType> call, Response<ListBloodType> response) {
                if (response.body().getStatus() == 1) {
                    bloodList.addAll( response.body().getData() );
                    adapterBloodType.notifyDataSetChanged();
                } else {
                    Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<ListBloodType> call, Throwable t) {

            }
        } );
    }

    private void getGovernorates() {

        apiServices.governorateGet().enqueue( new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    governoratesList.addAll( response.body().getData() );
                    adapter.notifyDataSetChanged();
                    HelperMethod.dismissProgressDialog();
                } else {
                    Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        } );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fragmentNotification_btn_save)
    public void onViewClicked() {

        saveData();
    }

    private void saveData() {
        if (adapterBloodType.bloodTypeSelectedList.isEmpty()) {
            Toast.makeText( getContext(), "Not Selected NotificationData", Toast.LENGTH_SHORT ).show();
        } else {
            apiServices.notificationSettings( apiToken, adapter.governorateSelectedList,
                    adapterBloodType.bloodTypeSelectedList ).enqueue( new Callback<NotificationsSettings>() {
                @Override
                public void onResponse(Call<NotificationsSettings> call, Response<NotificationsSettings> response) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    } else {
                        Toast.makeText( getContext(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onFailure(Call<NotificationsSettings> call, Throwable t) {
                    Toast.makeText( getContext(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            } );
        }
    }
}
