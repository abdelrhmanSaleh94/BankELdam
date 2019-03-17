package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.UserCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.city.City;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.city.CityData;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.governorate.Governorates;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.governorate.GovernoratesData;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.user.User;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.helper.HelperMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient.getClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewAccountFragment extends Fragment {


    @BindView(R.id.newAccount_Fg_Name)
    EditText newAccountFgName;
    @BindView(R.id.newAccount_Fg_Email)
    EditText newAccountFgEmail;
    @BindView(R.id.newAccount_Fg_birth)
    EditText newAccountFgBirth;
    @BindView(R.id.newAccount_Fg_Blood)
    EditText newAccountFgBlood;
    @BindView(R.id.newAccount_Fg_LastDate)
    EditText newAccountFgLastDate;
    @BindView(R.id.newAccount_Fg_Country)
    Spinner newAccountFgCountry;
    @BindView(R.id.newAccount_Fg_City)
    Spinner newAccountFgCity;
    @BindView(R.id.newAccount_Fg_Phone)
    EditText newAccountFgPhone;
    @BindView(R.id.newAccount_Fg_Password)
    EditText newAccountFgPassword;
    @BindView(R.id.newAccount_Fg_returnPassword)
    EditText newAccountFgReturnPassword;
    @BindView(R.id.login_Frg_Btn_Login)
    Button loginFrgBtnLogin;
    Unbinder unbinder;
    private ApiServices apiServices;
    private Integer City_Id;
    private Integer governorateIdList;


    public NewAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_new_account, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiServices = getClient().create( ApiServices.class );

        getGovernorates();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getGovernorates() {

        final List<String> countryArray = new ArrayList<>();
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
                    // spinnerCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    newAccountFgCountry.setAdapter( spinnerCountryAdapter );
                    newAccountFgCountry.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
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
                    newAccountFgCity.setAdapter( spinnerCountryAdapter );
                    newAccountFgCity.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
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

    @OnClick({R.id.newAccount_Fg_birth, R.id.newAccount_Fg_LastDate, R.id.login_Frg_Btn_Login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.newAccount_Fg_birth:
                HelperMethod.getDate( newAccountFgBirth, getActivity() );
                break;
            case R.id.newAccount_Fg_LastDate:
                HelperMethod.getDate( newAccountFgLastDate, getActivity() );
                break;
            case R.id.login_Frg_Btn_Login:
                createUser();
                break;
        }
    }

    private void createUser() {
        String name = newAccountFgName.getText().toString();
        String phone = newAccountFgPhone.getText().toString();
        String dateBirth = newAccountFgBirth.getText().toString();
        String lastDate = newAccountFgLastDate.getText().toString();
        String typeBlood = newAccountFgBlood.getText().toString();
        String password = newAccountFgPassword.getText().toString();
        String confirmationPassword = newAccountFgReturnPassword.getText().toString();
        String email = newAccountFgEmail.getText().toString();
        if (!name.isEmpty() && !phone.isEmpty() && !dateBirth.isEmpty() && !lastDate.isEmpty() &&
                !typeBlood.isEmpty() && !password.isEmpty() && !confirmationPassword.isEmpty()
                && !email.isEmpty() && City_Id != 0 && governorateIdList!=0 ) {

            apiServices.registerUser( name, email, dateBirth, City_Id, phone, lastDate, password, confirmationPassword, typeBlood ).enqueue( new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText( getActivity(), "Done", Toast.LENGTH_SHORT ).show();
                    } else {
                        Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.i( TAG, "onFailure: " + t.getMessage() );
                }
            } );
        } else {
            Toast.makeText( getActivity(), "Check DonationDataPost", Toast.LENGTH_SHORT ).show();
        }
    }
}
