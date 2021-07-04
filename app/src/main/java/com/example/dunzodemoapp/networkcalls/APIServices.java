package com.example.dunzodemoapp.networkcalls;


import com.example.dunzodemoapp.models.PhotosList;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIServices {


    @GET(APITags.photos)
    Call<PhotosList> getImageList(@Query("method") String method,
                                  @Query("api_key") String apikey,
                                  @Query("text") String query,
                                  @Query("format") String format,
                                  @Query("nojsoncallback") int nojsoncallback,
                                  @Query("per_page") int per_page,
                                  @Query("page") int page);

}