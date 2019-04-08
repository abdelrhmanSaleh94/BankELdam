package com.AAA.abdelrahmansaleh.bankeldam.data.rest;

import com.AAA.abdelrahmansaleh.bankeldam.data.model.city.City;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.contact.ContactUs;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.donation.DonationRequests;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.donationDetails.DonationDetails;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.donationRequests.DonationRequestPost;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.governorate.Governorates;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.listBloodType.ListBloodType;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.notificationsCount.NotificationsCount;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.notificationsList.NotificationsList;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.notificationsSettings.NotificationsSettings;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.postDetail.PostDetails;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.posts.Posts;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.registerNotificationToken.NotificationToken;
import com.AAA.abdelrahmansaleh.bankeldam.data.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {
    //User
    @POST("register")
    @FormUrlEncoded
    Call<User> registerUser(@Field("name") String name,
                            @Field("email") String email,
                            @Field("birth_date") String birth_date,
                            @Field("city_id") int city_id,
                            @Field("phone") String phone,
                            @Field("donation_last_date") String donation_last_date,
                            @Field("password") String password,
                            @Field("password_confirmation") String password_confirmation,
                            @Field("blood_type") String blood_type);


    @POST("login")
    @FormUrlEncoded
    Call<User> loginUser(@Field("phone") String phone, @Field("password") String password);


    @POST("reset-password")
    @FormUrlEncoded
    Call<User> resetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<User> newPassword(@Field("password") String password,
                           @Field("password_confirmation") String passwordConfirmation,
                           @Field("pin_code") String pinCode,
                           @Field("phone") String phone);

    @POST("profile")
    @FormUrlEncoded
    Call<User> profileEdit(@Field("name") String name,
                           @Field("email") String email,
                           @Field("birth_date") String birth_date,
                           @Field("city_id") int city_id,
                           @Field("phone") String phone,
                           @Field("donation_last_date") String donation_last_date,
                           @Field("password") String password,
                           @Field("password_confirmation") String password_confirmation,
                           @Field("blood_type") String blood_type,
                           @Field("api_token") String api_token);

    //Posts
    @GET("posts")
    Call<Posts> getPosts(@Query("api_token") String api_token,
                         @Query("page") int page);

    @GET("my-favourites")
    Call<Posts> getMyFavouritesPosts(@Query("api_token") String api_token);

    @GET("post")
    Call<PostDetails> getPostDetails(@Query("api_token") String api_token, @Query("post_id") int post_id);

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<Posts> postFav(@Field("post_id") String post_id
            , @Field("api_token") String api_token);

    //donation
    @POST("donation-request/create")
    @FormUrlEncoded
    Call<DonationRequestPost> donationRequest(@Field("api_token") String api_token,
                                              @Field("patient_name") String patient_name,
                                              @Field("patient_age") String patient_age,
                                              @Field("blood_type") String blood_type,
                                              @Field("bags_num") String bags_num,
                                              @Field("hospital_name") String hospital_name,
                                              @Field("hospital_address") String hospital_address,
                                              @Field("city_id") int city_id,
                                              @Field("phone") String phone,
                                              @Field("notes") String notes,
                                              @Field("latitude") double latitude,
                                              @Field("longitude") double longitude
    );

    @GET("donation-requests")
    Call<DonationRequests> getDonationRequests(@Query("api_token") String api_token, @Query("page") int page);

    @GET("donation-request")
    Call<DonationDetails> getDonationDetails(@Query("api_token") String api_token, @Query("donation_id") String donation_id);

    //Governorates
    @GET("governorates")
    Call<Governorates> governorateGet();

    @GET("cities")
    Call<City> getCities(@Query("governorate_id") int governorate_id);

    //postContact
    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs> PostContact(@Field("api_token") String api_token,
                                @Field("title") String title,
                                @Field("message") String message
    );

    //Notification
    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettings> notificationSettings(@Field("api_token") String api_token,
                                                     @Field("governorates[]") List<String> citieList,
                                                     @Field("blood_types[]") List<String> blood_typesList);

    @POST("register-token")
    @FormUrlEncoded
    Call<NotificationToken> notificationTokenRegister(@Field("token") String notification_token,
                                                      @Field("api_token") String api_token,
                                                      @Field("platform") String platform);

    @POST("remove-token")
    @FormUrlEncoded
    Call<NotificationToken> notificationTokenRemove(@Field("token") String notification_token,
                                                    @Field("api_token") String api_token
    );

    @GET("notifications-count")
    Call<NotificationsCount> getNotificationsCount(@Query("api_token") String api_token);

    @GET("notifications")
    Call<NotificationsList> getNotificationsList(@Query("api_token") String api_token,
                                                 @Query("page") int page);

    @GET("blood-types")
    Call<ListBloodType> getBloodList();
}
