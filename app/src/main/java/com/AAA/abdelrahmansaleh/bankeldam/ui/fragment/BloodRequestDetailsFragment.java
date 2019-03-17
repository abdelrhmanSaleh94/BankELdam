package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.donationDetails.DonationDetails;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.donationDetails.DonationDetailsData;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
public class BloodRequestDetailsFragment extends Fragment {


    @BindView(R.id.fragment_BloodDetails_Name)
    TextView fragmentBloodDetailsName;
    @BindView(R.id.fragment_BloodDetails_Age)
    TextView fragmentBloodDetailsAge;
    @BindView(R.id.fragment_BloodDetails_BloodType)
    TextView fragmentBloodDetailsBloodType;
    @BindView(R.id.fragment_BloodDetails_NumberBuges)
    TextView fragmentBloodDetailsNumberBuges;
    @BindView(R.id.fragment_BloodDetails_HospitalName)
    TextView fragmentBloodDetailsHospitalName;
    @BindView(R.id.fragment_BloodDetails_Tv_Address)
    TextView fragmentBloodDetailsTvAddress;
    @BindView(R.id.fragment_BloodDetails_Tv_phone)
    TextView fragmentBloodDetailsTvPhone;
    @BindView(R.id.fragment_BloodDetails_Tv_Details)
    TextView fragmentBloodDetailsTvDetails;
    @BindView(R.id.fragment_BloodDetails_btn_call)
    Button fragmentBloodDetailsBtnCall;
    Unbinder unbinder;
    MapView mMapView;

    private String phone;
    private double latitude;
    private double longitude;
    private GoogleMap mMap;
    private LatLng loc;

    public BloodRequestDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_blood_request_details, container, false );
        unbinder = ButterKnife.bind( this, view );

        mMapView = view.findViewById( R.id.googleMap );

        final String id = String.valueOf( getArguments().getInt( "bundleBloodId" ) );
        String apiToken = SharedPreferencesManger.LoadStringData( getActivity(), "apiToken" );
        ApiServices apiServices = RetrofitClient.getClient().create( ApiServices.class );
        apiServices.getDonationDetails( apiToken, id ).enqueue( new Callback<DonationDetails>() {
            @Override
            public void onResponse(Call<DonationDetails> call, Response<DonationDetails> response) {
                if (response.body().getStatus() == 1) {
                    DonationDetailsData data = response.body().getData();
                    phone = data.getPhone();
                    fragmentBloodDetailsHospitalName.setText( data.getHospitalName() );
                    fragmentBloodDetailsAge.setText( data.getPatientAge() );
                    fragmentBloodDetailsBloodType.setText( data.getBloodType() );
                    fragmentBloodDetailsName.setText( data.getPatientName() );
                    fragmentBloodDetailsNumberBuges.setText( data.getBagsNum() );
                    fragmentBloodDetailsTvDetails.setText( data.getNotes() );
                    fragmentBloodDetailsTvAddress.setText( data.getHospitalAddress() );
                    fragmentBloodDetailsTvPhone.setText( phone );
                    latitude = Double.parseDouble( data.getLatitude() );
                    longitude = Double.parseDouble( data.getLongitude() );


                } else {
                    Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }

            }

            @Override
            public void onFailure(Call<DonationDetails> call, Throwable t) {
                Toast.makeText( getActivity(), t.getMessage(), Toast.LENGTH_SHORT ).show();
            }
        } );
        mMapView.onCreate( savedInstanceState );


        try {
            MapsInitializer.initialize( getActivity().getApplicationContext() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync( new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                loc = new LatLng( latitude, longitude );
                mMap.addMarker( new MarkerOptions().position( loc ).title( "hospital Marker" ).snippet( "Marker Description" ) );
                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom( loc, 16 ) );
            }
        } );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_BloodDetails_btn_call, R.id.googleMap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_BloodDetails_btn_call:
                if (!phone.isEmpty()) {
                    Intent intent = new Intent( Intent.ACTION_DIAL );
                    intent.setData( Uri.parse( "tel:" + phone ) );
                    startActivity( intent );
                } else {
                    Toast.makeText( getActivity(), "Error on load", Toast.LENGTH_SHORT ).show();
                }
                break;
            case R.id.googleMap:
                Uri gmmIntentUri = Uri.parse( "geo:" + latitude + "," + longitude + "" );
                Intent mapIntent = new Intent( Intent.ACTION_VIEW, gmmIntentUri );
                mapIntent.setPackage( "com.google.android.apps.maps" );
                startActivity( mapIntent );
                break;
        }

    }

}
