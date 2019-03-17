package com.AAA.abdelrahmansaleh.bankeldam.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.donation.DonationData;
import com.AAA.abdelrahmansaleh.bankeldam.ui.activity.AppHomeActivity;
import com.AAA.abdelrahmansaleh.bankeldam.ui.activity.DetialsActivity;
import com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.BloodRequestDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.content.ContextCompat.startActivity;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {
    Context context;
    List<DonationData> donationDataList = new ArrayList<>();

    public DonationAdapter(Context context, List<DonationData> donationDataList) {
        this.context = context;
        this.donationDataList = donationDataList;
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.request_blood_card, parent, false );
        return new DonationViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        holder.cardBloodTVBloodType.setText( donationDataList.get( position ).getBloodType() );
        holder.cardBloodTVName.setText( donationDataList.get( position ).getPatientName() );
        holder.cardBloodTVCity.setText( donationDataList.get( position ).getCity().getName() );
        holder.cardBloodTVHospital.setText( donationDataList.get( position ).getHospitalAddress() );
    }

    @Override
    public int getItemCount() {
        return donationDataList.size();
    }


    public class DonationViewHolder extends RecyclerView.ViewHolder {

        private View view;
        @BindView(R.id.cardBlood_TV_blooType)
        TextView cardBloodTVBloodType;
        @BindView(R.id.cardBlood_TV_Name)
        TextView cardBloodTVName;
        @BindView(R.id.cardBlood_TV_hospital)
        TextView cardBloodTVHospital;
        @BindView(R.id.cardBlood_TV_city)
        TextView cardBloodTVCity;
        @BindView(R.id.cardBlood_btnDetails)
        Button cardBloodBtnDetails;
        @BindView(R.id.cardBlood_btnCall)
        Button cardBloodBtnCall;

        public DonationViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );


        }

        @OnClick({R.id.cardBlood_btnDetails, R.id.cardBlood_btnCall})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.cardBlood_btnDetails:
                    Integer id = donationDataList.get( getAdapterPosition() ).getId();
                    ((AppHomeActivity) context).sendToDetailsBlood( id );

                    break;
                case R.id.cardBlood_btnCall:
                    int position = getAdapterPosition();
                    String phone = donationDataList.get( position ).getPhone();
                    Intent intent = new Intent( Intent.ACTION_DIAL );
                    intent.setData( Uri.parse( "tel:" + phone ) );
                    startActivity( context, intent, null );
                    break;
            }
        }

    }
}
