package com.AAA.abdelrahmansaleh.bankeldam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.posts.Posts;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.posts.PostsData;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;
import com.AAA.abdelrahmansaleh.bankeldam.ui.activity.AppHomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.AAA.abdelrahmansaleh.bankeldam.R.id;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    Context context;
    List<PostsData> postsList = new ArrayList<>();


    public PostsAdapter(Context context, List<PostsData> postsList) {
        this.context = context;
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.article_card, parent, false );
        return new PostsViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final PostsViewHolder holder, int position) {
        holder.FavTittle.setText( postsList.get( position ).getTitle() );
        Glide.with( context ).load( postsList.get( position ).getThumbnailFullPath() )
                .into( holder.ArticleDetailsFragmentImage );
        if (postsList.get( position ).getIsFavourite()) {
            holder.favCheck.setButtonDrawable( R.drawable.ic_harte );
        } else {
            holder.favCheck.setButtonDrawable( R.drawable.ic_fav_hurt );
        }
        holder.favCheck.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setButtonDrawable( R.drawable.ic_fav_hurt );
                    holder.sendPostID();
                } else {
                    buttonView.setButtonDrawable( R.drawable.ic_harte );
                    holder.sendPostID();
                }
            }
        } );

    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }


    public class PostsViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(id.Fav_tittle)
        TextView FavTittle;
        @BindView(id.ArticleDetailsFragment_Image)
        ImageView ArticleDetailsFragmentImage;
        @BindView(id.favCheck)
        ToggleButton favCheck;
        private int adapterPosition;

        public PostsViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterPosition = getAdapterPosition();
                    PostsData postsData = postsList.get( adapterPosition );
                    ((AppHomeActivity) context).sendToDetails( postsData.getId() );
                }
            } );
        }

        public void sendPostID() {
            String apiToken = SharedPreferencesManger.LoadStringData( (AppHomeActivity) context, "apiToken" );
            String id = String.valueOf( postsList.get( getAdapterPosition() ).getId() );
            if (!id.isEmpty()){
                ApiServices apiServices =RetrofitClient.getClient().create( ApiServices.class );
                apiServices.postFav( id,apiToken ).enqueue( new Callback<Posts>() {
                    @Override
                    public void onResponse(Call<Posts> call, Response<Posts> response) {
                        Toast.makeText( context, "isFav", Toast.LENGTH_SHORT ).show();
                    }

                    @Override
                    public void onFailure(Call<Posts> call, Throwable t) {

                    }
                } );
        }else {
                Toast.makeText( context, "id "+id, Toast.LENGTH_SHORT ).show();
            }
        }


    }
}
