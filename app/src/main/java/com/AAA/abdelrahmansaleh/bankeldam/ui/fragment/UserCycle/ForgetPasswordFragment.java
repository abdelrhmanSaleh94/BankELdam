package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.UserCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
public class ForgetPasswordFragment extends Fragment {


    @BindView(R.id.forgetPass_Frg_Tv)
    TextView forgetPassFrgTv;
    @BindView(R.id.forgetPass_Frg_Ed_Phone)
    EditText forgetPassFrgEdPhone;
    @BindView(R.id.login_Frg_Btn_Login)
    Button loginFrgBtnLogin;
    Unbinder unbinder;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_forget_password, container, false );
        unbinder = ButterKnife.bind( this, view );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.login_Frg_Btn_Login)
    public void onViewClicked() {
       restPasswordNow();
         }

    private void restPasswordNow() {
        final String phone=forgetPassFrgEdPhone.getText().toString();
        if (!phone.isEmpty()){
        ApiServices apiServices= RetrofitClient.getClient().create( ApiServices.class );
        apiServices.resetPassword( phone ).enqueue( new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getStatus()==1) {
                    SharedPreferencesManger.SaveData( getActivity(),"phonePass",phone );
                    HelperMethod.replaceFragment(  new ConfirmationCodeFragment() ,getActivity().getSupportFragmentManager(),
                            R.id.login_Activity_frame_Container,null,null);

                }else {
                    Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        } );
        }
    }

}
