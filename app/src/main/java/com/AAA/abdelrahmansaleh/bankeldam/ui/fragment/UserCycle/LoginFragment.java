package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.UserCycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.registerNotificationToken.NotificationToken;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.user.Client;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.user.User;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;
import com.AAA.abdelrahmansaleh.bankeldam.ui.activity.AppHomeActivity;
import com.AAA.abdelrahmansaleh.bankeldam.helper.HelperMethod;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


public class LoginFragment extends Fragment {


    @BindView(R.id.login_Frg_Image)
    ImageView loginFrgImage;
    @BindView(R.id.login_Frg_Ed_Phone)
    EditText loginFrgEdPhone;
    @BindView(R.id.login_Frg_Ed_Password)
    EditText loginFrgEdPassword;
    @BindView(R.id.login_Frg_Tv_ForgetPassword)
    TextView loginFrgTvForgetPassword;
    @BindView(R.id.login_Frg_CheckBox_RememberMe)
    CheckBox loginFrgCheckBoxRememberMe;
    @BindView(R.id.login_Frg_Btn_Login)
    Button loginFrgBtnLogin;
    @BindView(R.id.login_Frg_Btn_Register)
    Button loginFrgBtnRegister;
    Unbinder unbinder;
    private String password;
    private ApiServices apiServices;
    private String token;
    private String apiToken;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_login, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiServices = RetrofitClient.getClient().create( ApiServices.class );
        String phone = SharedPreferencesManger.LoadStringData( getActivity(), "phone" );
        String pass = SharedPreferencesManger.LoadStringData( getActivity(), "pass" );
        loginFrgEdPassword.setText( pass );
        loginFrgEdPhone.setText( phone );
        return view;

    }

    public void newAccount() {
        HelperMethod.replaceFragment( new NewAccountFragment(), getActivity().getSupportFragmentManager(),
                R.id.login_Activity_frame_Container, null, null );
    }

    public void forgetPassword() {
        HelperMethod.replaceFragment( new ForgetPasswordFragment(), getActivity().getSupportFragmentManager(),
                R.id.login_Activity_frame_Container, null, null );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_Frg_Tv_ForgetPassword, R.id.login_Frg_CheckBox_RememberMe, R.id.login_Frg_Liner, R.id.login_Frg_Btn_Login, R.id.login_Frg_Btn_Register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_Frg_Tv_ForgetPassword:
                forgetPassword();
                break;
            case R.id.login_Frg_CheckBox_RememberMe:
                break;
            case R.id.login_Frg_Btn_Login:
                loginUser();
                break;
            case R.id.login_Frg_Btn_Register:
                newAccount();
                break;
        }
    }

    private void loginUser() {
        HelperMethod.showProgressDialog( getActivity(), "Loading" );
        final String phone = loginFrgEdPhone.getText().toString();
        final String password = loginFrgEdPassword.getText().toString();
        if (!phone.isEmpty() && !password.isEmpty()) {
            apiServices.loginUser( phone, password ).enqueue( new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body().getStatus() == 1) {
                        loginFrgCheckBoxRememberMe.setChecked( true );
                        if (loginFrgCheckBoxRememberMe.isChecked()) {
                            Client client = response.body().getData().getClient();
                            SharedPreferencesManger.SaveData( getActivity(), "pass", password );
                            SharedPreferencesManger.SaveData( getActivity(), "phone", phone );
                            SharedPreferencesManger.SaveData( getActivity(), "name", client.getName() );
                            SharedPreferencesManger.SaveData( getActivity(), "birthDate", client.getBirthDate() );
                            SharedPreferencesManger.SaveData( getActivity(), "pinCode", client.getPinCode() );
                            SharedPreferencesManger.SaveData( getActivity(), "apiToken", response.body().getData().getApiToken() );
                            SharedPreferencesManger.SaveData( getActivity(), "cityId", client.getCityId() );
                            SharedPreferencesManger.SaveData( getActivity(), "email", client.getEmail() );
                            SharedPreferencesManger.SaveData( getActivity(), "userId", client.getId() );
                           // SharedPreferencesManger.SaveData( getActivity(), "typeBlood", client.getBloodType() );
                            SharedPreferencesManger.SaveData( getActivity(), "donationLastDay", client.getDonationLastDate() );

                        } else {
                            SharedPreferencesManger.clean( getActivity() );
                        }
                        startActivity( new Intent( getActivity(), AppHomeActivity.class ) );
                        getActivity().finish();
                    } else {
                        HelperMethod.dismissProgressDialog();
                        Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText( getActivity(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            } );
        } else {
            HelperMethod.dismissProgressDialog();
            Toast.makeText( getActivity(), "CheckData", Toast.LENGTH_SHORT ).show();
        }
    }



}
