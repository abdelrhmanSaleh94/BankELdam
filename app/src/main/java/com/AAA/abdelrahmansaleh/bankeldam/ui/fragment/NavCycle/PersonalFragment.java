package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle;


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
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.HelperMethod;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {


    @BindView(R.id.personal_Fg_Name)
    EditText personalFgName;
    @BindView(R.id.personal_Fg_Email)
    EditText personalFgEmail;
    @BindView(R.id.personal_Fg_birth)
    EditText personalFgBirth;
    @BindView(R.id.personal_Fg_Blood)
    EditText personalFgBlood;
    @BindView(R.id.personal_Fg_LastDate)
    EditText personalFgLastDate;
    @BindView(R.id.personal_Fg_Country)
    Spinner personalFgCountry;
    @BindView(R.id.personal_Fg_City)
    Spinner personalFgCity;
    @BindView(R.id.personal_Fg_Phone)
    EditText personalFgPhone;
    @BindView(R.id.personal_Fg_Password)
    EditText personalFgPassword;
    @BindView(R.id.personal_Fg_returnPassword)
    EditText personalFgReturnPassword;
    @BindView(R.id.login_Frg_Btn_edit)
    Button loginFrgBtnEdit;
    Unbinder unbinder;
    private Integer governorateIdList;
    private ApiServices apiServices;
    private Integer City_Id;
    private int cityid;
    private String governorateId;
    private String apiToken;

    private List<Integer> CityId = new ArrayList<>();

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_personal, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        personalFgName.setText( SharedPreferencesManger.LoadStringData( getActivity(), "name" ) );
        personalFgBirth.setText( SharedPreferencesManger.LoadStringData( getActivity(), "birthDate" ) );
        personalFgBlood.setText( "A+" );
        personalFgEmail.setText( SharedPreferencesManger.LoadStringData( getActivity(), "email" ) );
        personalFgPhone.setText( SharedPreferencesManger.LoadStringData( getActivity(), "phone" ) );
        personalFgLastDate.setText( SharedPreferencesManger.LoadStringData( getActivity(), "donationLastDay" ) );
        personalFgPassword.setText( SharedPreferencesManger.LoadStringData( getActivity(), "pass" ) );
        personalFgReturnPassword.setText( SharedPreferencesManger.LoadStringData( getActivity(), "pass" ) );
        cityid = Integer.parseInt( SharedPreferencesManger.LoadStringData( getActivity(), "cityId" ) );
        apiToken = SharedPreferencesManger.LoadStringData( getActivity(), "apiToken" );
        getCities( cityid );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.personal_Fg_birth, R.id.personal_Fg_LastDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_Fg_birth:
                HelperMethod.getDate( personalFgBirth, getActivity() );
                break;
            case R.id.personal_Fg_LastDate:
                HelperMethod.getDate( personalFgLastDate, getActivity() );
                break;
        }
    }

    public void getGovernorates(final int govId) {

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
                    personalFgCountry.setAdapter( spinnerCountryAdapter );
                    personalFgCountry.setSelection( govId );
                    personalFgCountry.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


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

    private void getCities(final Integer id) {
        apiServices.getCities( id ).enqueue( new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (response.body().getStatus() == 1) {
                    List<CityData> data = response.body().getData();
                    governorateId = response.body().getData().get( 1 ).getGovernorateId();
                    final List<String> CityNames = new ArrayList<>();
                    CityId = new ArrayList<>();
                    CityNames.add( getActivity().getResources().getString( R.string.city ) );
                    CityId.add( 0 );
                    for (int i = 0; i < data.size(); i++) {
                        CityNames.add( data.get( i ).getName() );
                        CityId.add( data.get( i ).getId() );

                    }
                    ArrayAdapter<String> spinnerCountryAdapter = new ArrayAdapter<String>
                            ( getActivity(), android.R.layout.simple_spinner_item, CityNames );
                    personalFgCity.setAdapter( spinnerCountryAdapter );
                    personalFgCity.setSelection( id );
                    getGovernorates( Integer.parseInt( governorateId ) );
                    personalFgCity.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
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

    @OnClick(R.id.login_Frg_Btn_edit)
    public void onViewClicked() {
        String pass2 = personalFgReturnPassword.getText().toString();
        String pass1 = personalFgPassword.getText().toString();
        String lastDate = personalFgLastDate.getText().toString();
        String phone = personalFgPhone.getText().toString();
        String email = personalFgEmail.getText().toString();
        String bloodType = personalFgBlood.getText().toString();
        String birth = personalFgBirth.getText().toString();
        String name = personalFgName.getText().toString();
        if (!pass2.isEmpty() && !pass1.isEmpty() && !lastDate.isEmpty() && !phone.isEmpty() &&
                !email.isEmpty() && !bloodType.isEmpty() && !birth.isEmpty() && !name.isEmpty() && cityid != 0) {
            apiServices.profileEdit( name, email, birth, CityId.get( personalFgCity.getSelectedItemPosition() ), phone, lastDate
                    , pass1, pass2, bloodType, apiToken ).enqueue( new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    } else {
                        Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText( getActivity(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            } );
        }

    }
}
