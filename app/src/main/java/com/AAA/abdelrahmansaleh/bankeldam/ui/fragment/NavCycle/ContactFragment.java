package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.contact.ContactUs;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
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
public class ContactFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.contact_Ed_Name)
    EditText contactEdName;
    @BindView(R.id.contact_Ed_Email)
    EditText contactEdEmail;
    @BindView(R.id.contact_Ed_phone)
    EditText contactEdPhone;
    @BindView(R.id.contact_Ed_Tittle)
    EditText contactEdTittle;
    @BindView(R.id.contact_Ed_Massage)
    EditText contactEdMassage;
    private String apiToken;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_contact, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiToken = SharedPreferencesManger.LoadStringData( getActivity(), "apiToken" );
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.contact_facebook, R.id.contact_inst, R.id.contact_twitter, R.id.contact_whatsApp,
            R.id.contact_youtube, R.id.contact_google, R.id.contact_Btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contact_facebook:
                openUrl( "https://www.facebook.com" );
                break;
            case R.id.contact_inst:
                openUrl( "https://www.instagram.com" );
                break;
            case R.id.contact_twitter:
                openUrl( "https://twitter.com" );
                break;
            case R.id.contact_whatsApp:
                openUrl( "https://wa.me/+201098046024" );
                break;
            case R.id.contact_youtube:
                openUrl( "https://www.youtube.com" );
                break;
            case R.id.contact_google:
                openUrl( "https://plus.google.com" );
                break;
            case R.id.contact_Btn_send:
                sendMassage();
                break;
        }
    }

    private void sendMassage() {
        String massage = contactEdMassage.getText().toString();
        String tittle = contactEdTittle.getText().toString();
        if (!massage.isEmpty() && !tittle.isEmpty()) {
            ApiServices apiServices = RetrofitClient.getClient().create( ApiServices.class );
            apiServices.PostContact( apiToken, tittle,massage).enqueue( new Callback<ContactUs>() {
                @Override
                public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                    if (response.body().getStatus()==1) {
                        Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onFailure(Call<ContactUs> call, Throwable t) {
                    Toast.makeText( getActivity(), t.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            } );
        }else {
            Toast.makeText( getActivity(), "Check DonationDetailsData Input", Toast.LENGTH_SHORT ).show();
        }
    }

    public void openUrl(String url) {
        Intent i = new Intent( Intent.ACTION_VIEW );
        i.setData( Uri.parse( url ) );
        startActivity( i );
    }


}
