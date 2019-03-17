package com.AAA.abdelrahmansaleh.bankeldam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BloodTypeAdapter extends RecyclerView.Adapter<BloodTypeAdapter.BloodTypeViewHolder> {

    private Context context;
    private List<String> bloodTypeList = new ArrayList<>();
    public List<String> bloodTypeSelectedList = new ArrayList<>();

    public BloodTypeAdapter(Context context, List<String> bloodTypeList) {
        this.context = context;
        this.bloodTypeList = bloodTypeList;
    }

    @NonNull
    @Override
    public BloodTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.country_checkbox, parent, false );
        return new BloodTypeViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull BloodTypeViewHolder holder, int position) {
        holder.checkBox.setText( bloodTypeList.get( position ) );

    }

    @Override
    public int getItemCount() {
        return bloodTypeList.size();
    }

    public class BloodTypeViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        @BindView(R.id.checkBox)
        CheckBox checkBox;
        private String type;

        public BloodTypeViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
            checkBox.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type = bloodTypeList.get( getAdapterPosition() );
                    if (checkBox.isChecked()) {
                        bloodTypeSelectedList.add( type );
                    } else {
                        bloodTypeSelectedList.remove( type );
                        Toast.makeText( context, "Remove"+type, Toast.LENGTH_SHORT ).show();
                    }

                }
            } );
        }
    }
}
