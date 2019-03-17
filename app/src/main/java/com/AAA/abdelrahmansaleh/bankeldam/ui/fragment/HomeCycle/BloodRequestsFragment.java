package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.HomeCycle;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.adapter.DonationAdapter;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.donation.DonationData;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.donation.DonationRequests;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.OnEndless;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.BloodRequest;

import java.util.ArrayList;
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
public class BloodRequestsFragment extends Fragment {


    @BindView(R.id.BloodRequestsFragment_FB)
    FloatingActionButton BloodRequestsFragmentFB;
    Unbinder unbinder;
    @BindView(R.id.BloodRequestsFragment_RecyclerView)
    RecyclerView BloodRequestsFragmentRecyclerView;
    private DonationAdapter donationAdapter;
    private List<DonationData> donationList = new ArrayList<>();
    private int maxPages=0;

    public BloodRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_blood_requests, container, false );
        unbinder = ButterKnife.bind( this, view );
        LinearLayoutManager manager = new LinearLayoutManager( getContext() );
        BloodRequestsFragmentRecyclerView.setLayoutManager( manager );
        OnEndless onEndless=new OnEndless(manager,5) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPages) {
                    getDonations(current_page);
                }
            }
        };
        BloodRequestsFragmentRecyclerView.addOnScrollListener( onEndless );
        donationAdapter = new DonationAdapter( getContext(), donationList );
        BloodRequestsFragmentRecyclerView.setAdapter(donationAdapter );
        getDonations(1);
        return view;
    }

    private void getDonations(final int page) {
        String apiToken = SharedPreferencesManger.LoadStringData( getActivity(), "apiToken" );
        ApiServices apiServices= RetrofitClient.getClient().create( ApiServices.class );
        apiServices.getDonationRequests( apiToken ,page).enqueue( new Callback<DonationRequests>() {
            @Override
            public void onResponse(Call<DonationRequests> call, Response<DonationRequests> response) {
                if (response.body().getStatus()==1){
                    maxPages=response.body().getData().getLastPage();
                    if (maxPages==page) {
                        Toast.makeText( getActivity(), "End", Toast.LENGTH_SHORT ).show();
                    }
                   donationList.addAll( response.body().getData().getData());
                   donationAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<DonationRequests> call, Throwable t) {

            }
        } );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.BloodRequestsFragment_FB)
    public void onViewClicked() {
        getActivity().getSupportFragmentManager().beginTransaction().replace( R.id.homeContainer, new BloodRequest() ).commit();
    }
}
