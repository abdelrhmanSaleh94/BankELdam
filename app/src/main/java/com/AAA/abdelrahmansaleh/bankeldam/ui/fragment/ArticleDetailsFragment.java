package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.AAA.abdelrahmansaleh.bankeldam.R;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.postDetail.PostDetails;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.postDetail.PostDetailsData;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.ApiServices;
import com.AAA.abdelrahmansaleh.bankeldam.data.rest.RetrofitClient;
import com.AAA.abdelrahmansaleh.bankeldam.helper.SharedPreferencesManger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleDetailsFragment extends Fragment {


    @BindView(R.id.articleDetailsFragment_Image)
    ImageView articleDetailsFragmentImage;
    Unbinder unbinder;
    @BindView(R.id.articleFragment_favCheck)
    ImageView articleFragmentFavCheckIm;
    @BindView(R.id.articleFragment_tittle)
    TextView articleFragmentTittleTV;
    @BindView(R.id.articleFragment_content)
    TextView articleFragmentContentTV;
    private String apiToken;

    ApiServices apiServices;
    private int postId;

    public ArticleDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_article_details, container, false );
        unbinder = ButterKnife.bind( this, view );
        apiToken = SharedPreferencesManger.LoadStringData( getActivity(), "apiToken" );
        if (getArguments() != null) {
            postId =  getArguments().getInt( "bundlePostsId" ) ;
            if (postId != 0) {
                apiServices = RetrofitClient.getClient().create( ApiServices.class );
                apiServices.getPostDetails( apiToken, postId ).enqueue( new Callback<PostDetails>() {
                    @Override
                    public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                        if (response.body().getStatus()==1) {
                            PostDetailsData data = response.body().getData();
                            Glide.with( getContext() ).load( data.getThumbnailFullPath() ).into( articleDetailsFragmentImage );
                            articleFragmentTittleTV.setText( data.getTitle() );
                            articleFragmentContentTV.setText( data.getContent() );

                        }
                    }

                    @Override
                    public void onFailure(Call<PostDetails> call, Throwable t) {

                    }
                } );
            } else {
                Toast.makeText( getActivity(), "postId====== " + 0, Toast.LENGTH_SHORT ).show();
            }
        }else {
            Toast.makeText( getActivity(), "IT'Null", Toast.LENGTH_SHORT ).show();
        }
        //fetchData();
        return view;
    }

    /*public void fetchData() {
        apiServices.getPostDetails( apiToken, postId ).enqueue( new Callback<PostsData>() {
            @Override
            public void onResponse(Call<PostsData> call, Response<PostsData> response) {
                Integer id = response.body().getId();
                Toast.makeText( getActivity(), "id====" + id, Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onFailure(Call<PostsData> call, Throwable t) {

            }
        } );
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
