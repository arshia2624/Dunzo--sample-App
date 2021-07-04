package com.example.dunzodemoapp.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.dunzodemoapp.MainActivity;
import com.example.dunzodemoapp.models.PhotosList;
import com.example.dunzodemoapp.networkcalls.APIServices;
import com.example.dunzodemoapp.networkcalls.APITags;
import com.example.dunzodemoapp.networkcalls.RequestController;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageDataSource extends PageKeyedDataSource<Integer, PhotosList.Photo> {

    APIServices apiServices;
    String method = "flickr.photos.search";
    String apikey = "062a6c0c49e4de1d78497d13a7dbb360";
    String format = "json";
    int nojsoncallback = 1;
    int page = 1;
    int perpage = 50;
    String query = "";

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private MutableLiveData<String> progressLiveStatus;
    Call<PhotosList> call;


    public ImageDataSource(MainActivity mainActivity) {
        progressLiveStatus = new MutableLiveData<>();
        apiServices = RequestController.getClient().create(APIServices.class);

        SharedPreferences sharedPref = mainActivity.getPreferences(Context.MODE_PRIVATE);
        setQuery(sharedPref.getString("query", ""));
    }

    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, PhotosList.Photo> callback) {
        Call<PhotosList> data = apiServices.getImageList(method, apikey, getQuery(), format, nojsoncallback, perpage, page);
        call = data;
        progressLiveStatus.postValue(APITags.LOADING);
        data.enqueue(new Callback<PhotosList>() {
            @Override
            public void onResponse(Call<PhotosList> call, Response<PhotosList> response) {
                progressLiveStatus.postValue(APITags.LOADED);
                List<PhotosList.Photo> photoList = response.body().getPhotos().getPhoto();

                page++;
                callback.onResult(photoList, null, page);
            }

            @Override
            public void onFailure(Call<PhotosList> call, Throwable t) {
                progressLiveStatus.postValue(APITags.LOADED);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PhotosList.Photo> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PhotosList.Photo> callback) {
        progressLiveStatus.postValue(APITags.LOADING);
        Call<PhotosList> data = apiServices.getImageList(method, apikey, getQuery(), format, nojsoncallback, perpage, params.key);
        call = data;
        data.enqueue(new Callback<PhotosList>() {
            @Override
            public void onResponse(Call<PhotosList> call, Response<PhotosList> response) {
                progressLiveStatus.postValue(APITags.LOADED);
                List<PhotosList.Photo> photoList = response.body().getPhotos().getPhoto();
                callback.onResult(photoList, params.key + 1);
            }

            @Override
            public void onFailure(Call<PhotosList> call, Throwable t) {
                progressLiveStatus.postValue(APITags.LOADED);
            }
        });
    }
}
