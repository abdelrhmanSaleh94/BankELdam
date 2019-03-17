package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.HomeCycle;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.AAA.abdelrahmansaleh.bankeldam.helper.HelperMethod;
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

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {


    @BindView(R.id.ArticleFragment_FB)
    FloatingActionButton ArticleFragmentFB;
    Unbinder unbinder;
    @BindView(R.id.ArticleFrag_RecyclerView)
    RecyclerView ArticleFragRecyclerView;
    private List<PostsData> postsList =new ArrayList<>(  );
    private PostsAdapter postsAdapter;
    private String apiToken;

    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_article, container, false );
        unbinder = ButterKnife.bind( this, view );
        LinearLayoutManager manager=new LinearLayoutManager( getActivity() );
        ArticleFragRecyclerView.setLayoutManager( manager );
        postsAdapter=new PostsAdapter( getContext(),postsList );
        ArticleFragRecyclerView.setAdapter( postsAdapter );
        apiToken = SharedPreferencesManger.LoadStringData( getActivity(), "apiToken" );
        Log.i( TAG, "onCreateView: " +apiToken);
        getPosts(1);
        return view;
    }

    private void getPosts(int pages) {
        ApiServices apiServices = RetrofitClient.getClient().create( ApiServices.class );
        apiServices.getPosts( apiToken, pages ).enqueue( new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                try{
                    Posts posts = response.body();
                    if (posts.getStatus()==1){
                        postsList.addAll( posts.getData().getData() );
                        postsAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText( getActivity(), posts.getMsg(), Toast.LENGTH_SHORT ).show();
                    }
                }catch (Exception e){
                    Toast.makeText( getActivity(), e.getMessage(), Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {

            }
        } );

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ArticleFragment_FB)
    public void onViewClicked() {
        HelperMethod.replaceFragment( new BloodRequest(), getActivity().getSupportFragmentManager(), R.id.homeContainer, null, null );
    }
}
