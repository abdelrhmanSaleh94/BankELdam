package com.AAA.abdelrahmansaleh.bankeldam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.notificationsList.NotificationsListData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder> {
    Context context;
    List<NotificationsListData> notificationsList = new ArrayList<>();


    public NotificationListAdapter(Context context, List<NotificationsListData> notificationsList) {
        this.context = context;
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.notification_card, parent, false );
        return new NotificationViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        holder.notificationTitle.setText( notificationsList.get( position ).getTitle() );
        holder.notificationTime.setText( notificationsList.get( position ).getCreatedAt() );

    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.textView)
        TextView notificationTitle;
        @BindView(R.id.textView2)
        TextView notificationTime;

        public NotificationViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
        }
    }
}
