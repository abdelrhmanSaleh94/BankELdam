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
import com.AAA.abdelrahmansaleh.bankeldam.data.model.listBloodType.ListBloodTypeData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BloodTypeAdapter extends RecyclerView.Adapter<BloodTypeAdapter.BloodTypeViewHolder> {

    public List<String> bloodTypeSelectedList = new ArrayList<>();
    private Context context;
    private List<ListBloodTypeData> bloodTypeList = new ArrayList<>();

    public BloodTypeAdapter(Context context, List<ListBloodTypeData> bloodTypeList) {
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
    public void onBindViewHolder(@NonNull final BloodTypeViewHolder holder, final int position) {
        holder.checkBox.setText( bloodTypeList.get( position ).getName() );

        holder.checkBox.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListBloodTypeData type = bloodTypeList.get( position );
                if (holder.checkBox.isChecked()) {
                    bloodTypeSelectedList.add( type.getId().toString() );
                    Toast.makeText( context, "add" + type.getId().toString(), Toast.LENGTH_SHORT ).show();
                } else {
                    bloodTypeSelectedList.remove( type.getId().toString() );
                    Toast.makeText( context, "remove" + type.getId().toString(), Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    @Override
    public int getItemCount() {
        return bloodTypeList.size();
    }

    public class BloodTypeViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        public BloodTypeViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
        }
    }
}
