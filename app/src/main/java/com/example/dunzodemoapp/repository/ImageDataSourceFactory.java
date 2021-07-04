package com.example.dunzodemoapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.dunzodemoapp.MainActivity;

public class ImageDataSourceFactory extends DataSource.Factory {

    ImageDataSource imageDataSource;
    MutableLiveData<ImageDataSource> mutableLiveData;
    private String query = "";
    MainActivity mainActivity;

    public ImageDataSourceFactory(String query, MainActivity mainActivity) {
        mutableLiveData = new MutableLiveData<>();
        this.mainActivity = mainActivity;

    }

    @NonNull
    @Override
    public DataSource create() {
        imageDataSource = new ImageDataSource(mainActivity);
        mutableLiveData.postValue(imageDataSource);
        return imageDataSource;
    }

    public MutableLiveData<ImageDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

    public void clear() {
        if (imageDataSource != null) {
            imageDataSource.invalidate();
            imageDataSource.call.cancel();
        }

    }
}
