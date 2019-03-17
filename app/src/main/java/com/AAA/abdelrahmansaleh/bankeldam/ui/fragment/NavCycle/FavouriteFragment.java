package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.NavCycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.adapter.PostsAdapter;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.posts.Posts;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.posts.PostsData;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;

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
public class FavouriteFragment extends Fragment {


    @BindView(R.id.favArticleRecyclerView)
    RecyclerView favArticleRecyclerView;
    Unbinder unbinder;
    private LinearLayoutManager manager;
    private List<PostsData> postsDataFavList = new ArrayList<>();
    private PostsAdapter favAdapter;
    private String apiToken;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_favourite, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiToken= SharedPreferencesManger.LoadStringData( getActivity(),"apiToken" );
        LinearLayoutManager manager = new LinearLayoutManager( getContext() );
        favArticleRecyclerView.setLayoutManager( manager );
        favAdapter = new PostsAdapter( getContext(), postsDataFavList );
        favArticleRecyclerView.setAdapter( favAdapter );
        fetchFavArticles( apiToken, 1 );

        return view;
    }

    private void fetchFavArticles(String apiToken, int pages) {
        ApiServices apiServices = RetrofitClient.getClient().create( ApiServices.class );
        apiServices.getMyFavouritesPosts( apiToken ).enqueue( new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        postsDataFavList.addAll( response.body().getData().getData() );
                        favAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText( getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT ).show();
                    }
                } catch (Exception e) {
                    Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {

            }
        } );
    }


    @OnClick(R.id.favArticleRecyclerView)
    public void onViewClicked() {
    }
}
