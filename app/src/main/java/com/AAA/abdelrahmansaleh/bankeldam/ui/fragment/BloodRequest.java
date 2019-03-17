package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.city.City;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.city.CityData;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.donationRequests.DonationRequestPost;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.governorate.Governorates;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.governorate.GovernoratesData;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;
import com.AAA.abdelrahmansaleh.bankeldam.ui.activity.AppHomeActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class BloodRequest extends Fragment {


    @BindView(R.id.BloodRequest_fr_name)
    EditText BloodRequestFrName;
    @BindView(R.id.BloodRequest_fr_age)
    EditText BloodRequestFrAge;
    @BindView(R.id.BloodRequest_fr_bloodType)
    EditText BloodRequestFrBloodType;
    @BindView(R.id.BloodRequest_fr_numberBages)
    Spinner BloodRequestFrNumberBages;
    @BindView(R.id.BloodRequest_fr_hospitalName)
    EditText BloodRequestFrHospitalName;
    @BindView(R.id.BloodRequest_fr_hospitalAdress)
    EditText BloodRequestFrHospitalAdress;
    @BindView(R.id.BloodRequest_fr_Country)
    Spinner BloodRequestFrCountry;
    @BindView(R.id.BloodRequest_fr_City)
    Spinner BloodRequestFrCity;
    @BindView(R.id.BloodRequest_fr_Phone)
    EditText BloodRequestFrPhone;
    @BindView(R.id.BloodRequest_fr_Notes)
    EditText BloodRequestFrNotes;
    @BindView(R.id.BloodRequest_fr_Enter)
    Button BloodRequestFrEnter;
    Unbinder unbinder;
    @BindView(R.id.BloodRequest_fr_btnLocation)
    ImageButton BloodRequestFrBtnLocation;
    private ApiServices apiServices;
    private Integer governorateIdList;
    private Integer City_Id;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 12;
    private double latitude;
    private double longitude;
    private boolean mLocationPermissionsGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Geocoder geocoder;
    private List<Address> addresses;
    private int numberPosition;
    private String apiToken;

    public BloodRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_blood_request, container, false );
        unbinder = ButterKnife.bind( this, view );
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient( getActivity() );
        geocoder = new Geocoder( getContext(), Locale.getDefault() );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        apiToken= SharedPreferencesManger.LoadStringData( getActivity(),"apiToken" );
        List<String > numberList=new ArrayList<>(  );
        numberList.add( getResources().getString( R.string.NumberOfBags ) );
        for (int i=1;i<=5;i++){
            numberList.add( String.valueOf( i ) );
        }
        ArrayAdapter<String> spinnerNumberBagesAdapter = new ArrayAdapter<String>
                ( getActivity(), android.R.layout.simple_spinner_item, numberList );
        BloodRequestFrNumberBages.setAdapter( spinnerNumberBagesAdapter );
        BloodRequestFrNumberBages.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                     numberPosition = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        getGovernorates();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getGovernorates() {

        apiServices.governorateGet().enqueue( new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    List<GovernoratesData> data = response.body().getData();
                    List<String> governoratesNames = new ArrayList<>();
                    final List<Integer> governoratesId = new ArrayList<>();
                    governoratesNames.add( getActivity().getResources().getString( R.string.country ) );
                    governoratesId.add( 0 );
                    for (int i = 0; i < data.size(); i++) {
                        governoratesNames.add( data.get( i ).getName() );
                        governoratesId.add( data.get( i ).getId() );

                    }
                    ArrayAdapter<String> spinnerCountryAdapter = new ArrayAdapter<String>
                            ( getActivity(), android.R.layout.simple_spinner_item, governoratesNames );
                    BloodRequestFrCountry.setAdapter( spinnerCountryAdapter );
                    BloodRequestFrCountry.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                governorateIdList = governoratesId.get( position );
                                getCities( governoratesId.get( position ) );
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    } );
                } else {
                    Log.i( TAG, "onRespo" + "sorry" );
                }

            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        } );
    }

    private void getCities(Integer id) {
        apiServices.getCities( id ).enqueue( new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (response.body().getStatus() == 1) {
                    List<CityData> data = response.body().getData();
                    final List<String> CityNames = new ArrayList<>();
                    final List<Integer> CityId = new ArrayList<>();
                    CityNames.add( getActivity().getResources().getString( R.string.city ) );
                    CityId.add( 0 );
                    for (int i = 0; i < data.size(); i++) {
                        CityNames.add( data.get( i ).getName() );
                        CityId.add( data.get( i ).getId() );

                    }
                    ArrayAdapter<String> spinnerCountryAdapter = new ArrayAdapter<String>
                            ( getActivity(), android.R.layout.simple_spinner_item, CityNames );
                    // spinnerCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    BloodRequestFrCity.setAdapter( spinnerCountryAdapter );
                    BloodRequestFrCity.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                City_Id = (CityId.get( position ));
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    } );

                } else {
                    Log.i( TAG, "onRespo" + "sorry" );
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        } );
    }

    @OnClick({R.id.BloodRequest_fr_btnLocation, R.id.BloodRequest_fr_Enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.BloodRequest_fr_btnLocation:
                getLocation();
                break;
            case R.id.BloodRequest_fr_Enter:
                sendRequest();
                break;
        }
    }

    private void sendRequest() {
        String bloodType = BloodRequestFrBloodType.getText().toString();
        String age = BloodRequestFrAge.getText().toString();
        String hospitalAddress = BloodRequestFrHospitalAdress.getText().toString();
        String hospitalName = BloodRequestFrHospitalName.getText().toString();
        String name = BloodRequestFrName.getText().toString();
        String notes = BloodRequestFrNotes.getText().toString();
        String phone = BloodRequestFrPhone.getText().toString();
        String number= String.valueOf( numberPosition );
        if (!bloodType.isEmpty() && !age.isEmpty() && !hospitalAddress.isEmpty() && !hospitalName.isEmpty()
                && !name.isEmpty() && !notes.isEmpty() && !phone.isEmpty()
                && latitude != 0.0 && longitude != 0.0 && City_Id != 0) {
            apiServices.donationRequest( apiToken,
                    name, age, bloodType, number, hospitalName, hospitalAddress, City_Id
                    , phone, notes, latitude, longitude ).enqueue( new Callback<DonationRequestPost>() {
                @Override
                public void onResponse(Call<DonationRequestPost> call, Response<DonationRequestPost> response) {
                    if (response.body().getStatus() == 1) {
                        startActivity( new Intent(getActivity() ,AppHomeActivity.class ) );
                    } else {
                        Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onFailure(Call<DonationRequestPost> call, Throwable t) {

                    Log.i( TAG, "onFailure: " + t.getMessage() );
                }
            } );

        } else {
            Toast.makeText( getActivity(), "Check DonationDataPost", Toast.LENGTH_SHORT ).show();
        }

    }

    private void getLocation() {
        getLocationPermission();
        try {
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener( getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        try {
                            addresses = geocoder.getFromLocation( latitude, longitude, 1 );// Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            String address = addresses.get( 0 ).getAddressLine( 0 ); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                            BloodRequestFrHospitalAdress.setText( address );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        new AlertDialog.Builder( getContext() ).setTitle( "Open Location" )
                                .setMessage( "Plz Open Location Click Ok To Go Setting Cancel  To Exit " )
                                .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                                        startActivity( intent );
                                    }
                                } ).setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit( 0 );
                            }
                        } ).show();

                    }
                }
            } );
        } catch (SecurityException e) {
            Log.d( "me", "getMydeviceLocation: " + e.getMessage() );
        }
    }

    private void getLocationPermission() {
        Log.i( "me", "getLocationPermission: getting location permissions" );
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission( getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission( getActivity().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;

            } else {
                ActivityCompat.requestPermissions( getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE );
            }
        } else {
            ActivityCompat.requestPermissions( getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.i( "me", "onRequestPermissionsResult: permission failed" );
                            return;
                        }
                    }
                    Log.d( "me", "onRequestPermissionsResult: permission granted" );
                    mLocationPermissionsGranted = true;
                    //initialize our map
                }
            }
        }
    }

}
