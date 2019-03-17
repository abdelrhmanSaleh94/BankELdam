package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.UserCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.user.User;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.HelperMethod;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;

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
public class ConfirmationCodeFragment extends Fragment {


    @BindView(R.id.ConfiPass_Frg_Ed_Code)
    EditText ConfiPassFrgEdCode;
    @BindView(R.id.ConfiPass_Frg_Ed_Password)
    EditText ConfiPassFrgEdPassword;
    @BindView(R.id.ConfiPass_Frg_Ed_PasswordAgain)
    EditText ConfiPassFrgEdPasswordAgain;
    @BindView(R.id.ConfiPass_Frg_Btn_Login)
    Button ConfiPassFrgBtnLogin;
    Unbinder unbinder;

    public ConfirmationCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_confirmation_code, container, false );
        unbinder = ButterKnife.bind( this, view );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ConfiPass_Frg_Btn_Login)
    public void onViewClicked() {
        String password = ConfiPassFrgEdPassword.getText().toString();
        String passwordAgain = ConfiPassFrgEdPasswordAgain.getText().toString();
        String code = ConfiPassFrgEdCode.getText().toString();
        String phonePass = SharedPreferencesManger.LoadStringData( getActivity(), "phonePass" );
        if (!password.isEmpty() && !passwordAgain.isEmpty() && !code.isEmpty()) {
            ApiServices apiServices = RetrofitClient.getClient().create( ApiServices.class );
            apiServices.newPassword( password, passwordAgain, code, phonePass ).enqueue( new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_LONG ).show();
                        HelperMethod.replaceFragment( new LoginFragment(), getActivity().getSupportFragmentManager(), R.id.login_Activity_frame_Container, null, null );
                    } else {
                        Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_LONG ).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            } );
        }
    }
}
