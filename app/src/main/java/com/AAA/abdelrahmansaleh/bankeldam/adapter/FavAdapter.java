package com.AAA.abdelrahmansaleh.bankeldam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.posts.PostsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private Context context;
    private List<PostsData> postsData = new ArrayList<>();

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.article_card, parent, false );
        return new FavViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.FavTittle.setText( postsData.get( position ).getTitle() );
        Glide.with( context ).load( postsData.get( position ).getThumbnailFullPath() )
                .into( holder.ArticleDetailsFragmentImage );

    }

    @Override
    public int getItemCount() {
        return postsData.size();
    }

    public void setData(List<PostsData> postsData) {
        this.postsData = postsData;
    }

    public FavAdapter(Context context, List<PostsData> postsData) {
        this.context = context;
        this.postsData = postsData;
    }

    public class FavViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.Fav_tittle)
        TextView FavTittle;
        @BindView(R.id.ArticleDetailsFragment_Image)
        ImageView ArticleDetailsFragmentImage;

        @BindView(R.id.favCheck)
        ToggleButton favCheck;

        public FavViewHolder(View itemView) {
            super( itemView );
            view = itemView;
            ButterKnife.bind( this, view );

        }
    }
}
