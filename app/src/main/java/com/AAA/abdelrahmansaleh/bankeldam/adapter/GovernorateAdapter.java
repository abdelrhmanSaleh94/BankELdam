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
import com.AAA.abdelrahmansaleh.bankeldam.data.model.governorate.GovernoratesData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GovernorateAdapter extends RecyclerView.Adapter<GovernorateAdapter.GovernorateViewHolder> {
    Context context;
    List<GovernoratesData> governoratesList = new ArrayList<>();
    public List<String> governorateSelectedList = new ArrayList<>();

    public GovernorateAdapter(Context context, List<GovernoratesData> governoratesList) {
        this.context = context;
        this.governoratesList = governoratesList;
    }

    @NonNull
    @Override
    public GovernorateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.country_checkbox, parent, false );
        return new GovernorateViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final GovernorateViewHolder holder, final int position) {
        holder.checkBox.setText( governoratesList.get( position ).getName() );
        holder.checkBox.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    governorateSelectedList.add( governoratesList.get( position ).getId().toString() );
                } else {
                    governorateSelectedList.remove( governoratesList.get( position ).getId().toString() );
                }
            }
        } );
    }

    @Override
    public int getItemCount() {
        return governoratesList.size();
    }

    public class GovernorateViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        public GovernorateViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
        }
    }
}
